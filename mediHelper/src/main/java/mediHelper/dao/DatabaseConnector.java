package mediHelper.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import mediHelper.entities.Dane;
import mediHelper.entities.Dzial;
import mediHelper.listener.DatabaseListener;

public class DatabaseConnector {

	private List<DatabaseListener> listeners;
	private EntityManagerFactory emFactory;

	public DatabaseConnector() {
		this.emFactory = Persistence.createEntityManagerFactory("MediHelper");
		listeners = new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	public List<Dzial> getListaDzial() {

		return (List<Dzial>) doWithEntityManager(new EntityManagerActivity() {

			@Override
			public Object run(EntityManager em) {

				List<Dzial> listaDzial = new ArrayList<>();
				Query query = em.createQuery("Select d from Dzial d");
				listaDzial = query.getResultList();
				return listaDzial;
			}
		});
	}

	public void calcDataAmount() {
		Number numer = -1;

		numer = (Number) doWithEntityManager(new EntityManagerActivity() {
			@Override
			public Object run(EntityManager em) {
				Query query = em.createQuery("Select count(d) from Dane d");
				return query.getSingleResult();
			}
		});
		for (DatabaseListener dL : listeners) {
			dL.dataAmount(numer.intValue());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getListaByDzial(Integer idDzial) {

		return (List<Object[]>) doWithEntityManager(new EntityManagerActivity() {

			@Override
			public Object run(EntityManager em) {

				Query query = em.createQuery(
						"Select d.nazwapolska, d.nazwalacinska FROM Dane d WHERE (id_dzial = " + idDzial + ")");
				List<Object[]> lista = query.getResultList();
				return lista;
			}
		});
	}

	public void addData(Dane data) {
		doWithEntityManager(new EntityManagerActivity() {

			@Override
			public Object run(EntityManager em) {
				em.getTransaction().begin();
				em.persist(data);
				em.getTransaction().commit();
				return null;
			}
		});
		calcDataAmount();
		getData("", null);
	}

	public void addDataAndDzial(Integer id_dane, String polish, String latina, String nowyDzial, Integer bledy) {
		doWithEntityManager(new EntityManagerActivity() {
			@Override
			public Object run(EntityManager em) {
				Dzial dzial = new Dzial(nowyDzial);

				em.getTransaction().begin();
				em.persist(dzial);
				em.getTransaction().commit();
				
				addData(new Dane(id_dane, polish, latina, dzial, bledy));
				return null;
			}
		});
		calcDataAmount();
		getData("", null);
		for (DatabaseListener listener : listeners) {
			listener.categoryChange();
		}
	}

	protected Dzial getDzialByNazwa(String nowyDzial) {

		return (Dzial) doWithEntityManager(new EntityManagerActivity() {
			@Override
			public Object run(EntityManager em) {
				Query query = em.createQuery("SELECT d FROM Dzial d WHERE nazwa = '" + nowyDzial + "'");
				return query.getSingleResult();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void getData(String string, Integer idDzial) {

		String sString = "'%" + string.toLowerCase() + "%'";
		// TODO implement search String queries
		List<Dane> lista = (List<Dane>) doWithEntityManager(new EntityManagerActivity() {

			@Override
			public Object run(EntityManager em) {

				Query query;
				if (idDzial != null) {
					query = em.createQuery("Select d.id, d.nazwapolska, d.nazwalacinska, dz, d.bledy FROM Dane d "
							+ "INNER JOIN d.dzial dz WHERE ((dz.id_dzial = " + idDzial + ") AND "
							+ "(lower(d.nazwapolska) LIKE " + sString + " OR " + "lower(d.nazwalacinska) LIKE " + sString + " OR "
							+ "lower(dz.nazwa) LIKE " + sString + "))");
				} else {
					query = em.createQuery("Select d.id, d.nazwapolska, d.nazwalacinska, dz, d.bledy FROM Dane d "
							+ "INNER JOIN d.dzial dz WHERE (lower(d.nazwapolska) LIKE " + sString + " OR "
							+ "lower(d.nazwalacinska) LIKE " + sString + " OR " + "lower(dz.nazwa) LIKE " + sString + ")");
				}

				List<Object[]> objLista = query.getResultList();
				List<Dane> dataRowLista = new ArrayList<>();
				for (Object[] obj : objLista) {
					dataRowLista.add(new Dane((Integer) obj[0], (String) obj[1], (String) obj[2], (Dzial) obj[3],
							(Integer) obj[4]));
				}
				return dataRowLista;
			}
		});
		for (DatabaseListener l : listeners) {
			l.dataIsRead(lista);
		}

	}

	public Dane getDataById(Integer id) {
		return (Dane) doWithEntityManager(new EntityManagerActivity() {

			@Override
			public Object run(EntityManager em) {
				return em.find(Dane.class, id);
			}
		});
	}

	public void editData(Dane data) {
		doWithEntityManager(new EntityManagerActivity() {

			@Override
			public Object run(EntityManager em) {
				Dane editDane = em.find(Dane.class, data.getId());

				if (!data.getDzial().equals(editDane.getDzial())) {
					Integer idDzial = editDane.getDzial().getId_dzial();
					Query query = em.createQuery("SELECT count(d) FROM Dane d where id_dzial = " + idDzial);
					Number num = (Number) query.getSingleResult();
					if (num.intValue() == 0) {
						em.getTransaction().begin();
						Dzial dz = em.find(Dzial.class, idDzial);
						em.remove(dz);
						em.getTransaction().commit();
					}
				}

				editDane.setNazwapolska(data.getNazwapolska());
				editDane.setNazwalacinska(data.getNazwalacinska());
				editDane.setDzial(data.getDzial());

				em.getTransaction().begin();
				em.merge(editDane);
				em.getTransaction().commit();
				return null;
			}
		});
		getData("", null);
	}

	public void deleteData(Integer id) {
		doWithEntityManager(new EntityManagerActivity() {

			@Override
			public Object run(EntityManager em) {

				// usuwanie działu, jeżeli nie ma już pojęć z danego działu
				Dane delData = em.find(Dane.class, id);
				Dzial dzial = delData.getDzial();
				Integer idDzial = dzial.getId_dzial();

				em.getTransaction().begin();
				Dane d = em.find(Dane.class, id);
				em.remove(d);
				em.getTransaction().commit();

				Query query = em.createQuery("SELECT count(d) FROM Dane d where id_dzial = " + idDzial);
				Number num = (Number) query.getSingleResult();
				if (num.intValue() == 0) {
					em.getTransaction().begin();
					Dzial dz = em.find(Dzial.class, idDzial);
					em.remove(dz);
					em.getTransaction().commit();
					for (DatabaseListener listener : listeners) {
						listener.categoryChange();
					}
				}
				return null;
			}
		});
		calcDataAmount();
		getData("", null);
	}

	private Object doWithEntityManager(EntityManagerActivity activity) {
		EntityManager em = null;
		try {
			em = emFactory.createEntityManager();
			return activity.run(em);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	private static interface EntityManagerActivity {
		Object run(EntityManager em);
	}

	public void addListener(DatabaseListener listener) {
		listeners.add(listener);
	}

}
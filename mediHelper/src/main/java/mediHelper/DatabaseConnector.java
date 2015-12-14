package mediHelper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import mediHelper.entities.Dane;
import mediHelper.entities.Dzial;

public class DatabaseConnector {

	private DatabaseListener listener;
	private EntityManagerFactory emFactory;

	public void setListener(DatabaseListener listener) {
		this.listener = listener;
	}

	public DatabaseConnector() {
		this.emFactory = Persistence.createEntityManagerFactory("MediHelper");
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
				System.out.println(query.getSingleResult());
				return query.getSingleResult();

			}
		});
		listener.dataAmount(numer.intValue());
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

	public void addData(String polish, String latina, Integer dzial) {
		doWithEntityManager(new EntityManagerActivity() {

			@Override
			public Object run(EntityManager em) {
				Dane dane = new Dane(polish, latina, dzial);
				em.getTransaction().begin();
				em.persist(dane);
				em.getTransaction().commit();
				calcDataAmount();
				return null;
			}
		});
	}
	public void addDataAndDzial(String polish, String latina, String nowyDzial) {
		doWithEntityManager(new EntityManagerActivity() {
			@Override
			public Object run(EntityManager em) {
				Dzial dzial = new Dzial(nowyDzial);
				
				em.getTransaction().begin();
				em.persist(dzial);
				em.getTransaction().commit();
				
				Integer idDzial = getIdDzialByNazwa(nowyDzial);
				addData(polish, latina, idDzial);
				return null;
			}
		});
	}

	protected Integer getIdDzialByNazwa(String nowyDzial) {
		
		return (Integer)doWithEntityManager(new EntityManagerActivity() {
			@Override
			public Object run(EntityManager em) {
				Query query = em.createQuery("SELECT d.id_dzial FROM Dzial d WHERE nazwa = '" + nowyDzial + "'");
				return query.getSingleResult();
			}
		});
	}

	public void editDane(Dane dane, int column) {
		doWithEntityManager(new EntityManagerActivity() {
			@Override
			public Object run(EntityManager em) {
				Dane editDane = em.find(Dane.class, dane.getId());
				if (column == 0) {
					editDane.setNazwapolska(dane.getNazwapolska());
				} else {
					editDane.setNazwalacinska(dane.getNazwalacinska());
				}
				
				em.getTransaction().begin();
				em.merge(editDane);
				em.getTransaction().commit();
				
				return null;
			}
		});
	}

}

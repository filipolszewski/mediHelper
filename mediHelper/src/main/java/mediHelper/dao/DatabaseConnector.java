package mediHelper.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import mediHelper.entities.Dane;
import mediHelper.entities.Dzial;
import mediHelper.entities.Stats;
import mediHelper.listener.DatabaseListener;

// Data Access Object spełniający wiele funkcji związanych z pracą na bazie danych

public class DatabaseConnector {

	private List<DatabaseListener> listeners;
	private EntityManagerFactory emFactory;

	public DatabaseConnector() {
		this.emFactory = Persistence.createEntityManagerFactory("MediHelper");
		listeners = new ArrayList<>();
	}

	// przechowywanie listenerów
	public void addListener(DatabaseListener listener) {
		listeners.add(listener);
	}

	// metoda pobierajaca liste działów
	@SuppressWarnings("unchecked")
	public List<Dzial> getListOfCategories() {

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

	// metoda sprawdzająca ilość rekrodów w tabeli Dane
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
			dL.dataAmountDelivered(numer.intValue());
		}
	}

	// metoda pobierająca dane w zależności od działu
	@SuppressWarnings("unchecked")
	public List<Object[]> getListOfDataByDzial(Integer idDzial) {

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

	// metoda dodająca pojedyńczą parę pojęć
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

	// metoda dodająca pojedyńczą parę pojęć oraz nowo stworzony dział, do
	// którego należą
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

	// metoda zwracająca obiekt klasy Dzial ze względu na nazwę
	protected Dzial getDzialByName(String newCategory) {

		return (Dzial) doWithEntityManager(new EntityManagerActivity() {
			@Override
			public Object run(EntityManager em) {
				Query query = em.createQuery("SELECT d FROM Dzial d WHERE nazwa = '" + newCategory + "'");
				return query.getSingleResult();
			}
		});
	}

	// metoda szukająca danych ze względu na searchString i dział
	@SuppressWarnings("unchecked")
	public void getData(String searchString, Integer idDzial) {

		String sString = "'%" + searchString.toLowerCase() + "%'";
		List<Dane> lista = (List<Dane>) doWithEntityManager(new EntityManagerActivity() {

			@Override
			public Object run(EntityManager em) {

				Query query;
				if (idDzial != null) {
					query = em.createQuery("Select d.id, d.nazwapolska, d.nazwalacinska, dz, d.bledy FROM Dane d "
							+ "INNER JOIN d.dzial dz WHERE ((dz.id_dzial = " + idDzial + ") AND "
							+ "(lower(d.nazwapolska) LIKE " + sString + " OR " + "lower(d.nazwalacinska) LIKE "
							+ sString + " OR " + "lower(dz.nazwa) LIKE " + sString + "))");
				} else {
					query = em.createQuery("Select d.id, d.nazwapolska, d.nazwalacinska, dz, d.bledy FROM Dane d "
							+ "INNER JOIN d.dzial dz WHERE (lower(d.nazwapolska) LIKE " + sString + " OR "
							+ "lower(d.nazwalacinska) LIKE " + sString + " OR " + "lower(dz.nazwa) LIKE " + sString
							+ ")");
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

	// metoda zwracająca obiekt Dane ze względu na id
	public Dane getDataById(Integer id) {
		return (Dane) doWithEntityManager(new EntityManagerActivity() {

			@Override
			public Object run(EntityManager em) {
				return em.find(Dane.class, id);
			}
		});
	}

	// metoda edytująca rekord w tabeli Dane
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

	// metoda usuwająca rekord w tabeli Dane po id
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

	// pobieranie danych do widoku statystyk. Pobierane i liczone są:
	// ilość testów dla działu, sumy wszystkich pytań i wszystkich dobrych
	// odpowiedzi, ilość błędów łącznie, potem ostatnia data i
	// id ostatniego testu, potem jego wynik.

	public void getStatsForCategory(String catName) {

		Stats stats = (Stats) doWithEntityManager(new EntityManagerActivity() {

			@SuppressWarnings("unchecked")
			@Override
			public Object run(EntityManager em) {

				Query countQuery = em.createQuery("SELECT count(t) FROM Testresult t where dzial = '" + catName + "'");
				Number num = (Number) countQuery.getSingleResult();
				Integer testCount = num.intValue();

				Query questionQuery = em.createQuery(
						"SELECT t.questionCount, t.correctCount FROM Testresult t where dzial = '" + catName + "'");

				Integer questionCount = 0;
				Integer correctCount = 0;

				List<Object[]> resultList = questionQuery.getResultList();
				for (Object[] o : resultList) {
					questionCount += (Integer) o[0];
					correctCount += (Integer) o[1];
				}
				Integer failCount = questionCount - correctCount;
				BigDecimal overallAccuracy = calcAccuracy(questionCount, correctCount);

				Query idQuery = em.createQuery(
						"SELECT t.id FROM Testresult t where dzial = '" + catName + "' ORDER BY t.id DESC");
				idQuery.setMaxResults(1);
				Object result = idQuery.getSingleResult();
				Integer id = (Integer) result;

				Query dateQuery = em.createQuery("SELECT t.date FROM Testresult t where t.id = " + id);
				String lastTestDate = (String) dateQuery.getSingleResult();

				Query lastTestCountQuery = em
						.createQuery("SELECT t.questionCount, t.correctCount FROM Testresult t where t.id = " + id);
				Object[] result2 = (Object[]) lastTestCountQuery.getSingleResult();

				BigDecimal lastTestAccuracy = calcAccuracy((Integer) result2[0], (Integer) result2[1]);

				return new Stats(testCount, failCount, overallAccuracy, lastTestDate, lastTestAccuracy);
			}

			private BigDecimal calcAccuracy(Integer questionCount, Integer correctCount) {
				BigDecimal accuracy = new BigDecimal("0");
				if (questionCount != 0) {
					accuracy = new BigDecimal(correctCount)
							.divide(new BigDecimal(questionCount), 3, RoundingMode.HALF_EVEN)
							.multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_EVEN);
				}
				return accuracy;
			}
		});
		for (DatabaseListener databaseListener : listeners) {
			databaseListener.onStatsDelivered(stats);
		}
	}

	// funkcja opakowująca wszystkie próby wykorzystania EntityManager w celu
	// czystszego kodu i zamykania zasobu
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

}

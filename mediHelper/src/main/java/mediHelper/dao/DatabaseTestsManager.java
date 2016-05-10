package mediHelper.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import mediHelper.entities.Dane;
import mediHelper.entities.Dzial;
import mediHelper.entities.Testresult;
import mediHelper.listener.DatabaseListener;

public class DatabaseTestsManager {

	private DatabaseListener listener;
	private EntityManagerFactory emFactory;

	public DatabaseTestsManager() {
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

	public void getNextQuestion(Dzial testDzial) {

		Dane dane = (Dane) doWithEntityManager(new EntityManagerActivity() {
			@Override
			public Object run(EntityManager em) {
				Query countQuery = em.createQuery("SELECT count(d) FROM Dane d WHERE d.dzial = " + testDzial.getId_dzial());
				Number number = (Number) countQuery.getSingleResult();
				
				Query query = em.createQuery("SELECT d FROM Dane d WHERE d.dzial = " + testDzial.getId_dzial());
				query.setFirstResult((int) (number.intValue() * Math.random()));
				query.setMaxResults(1);
				return query.getResultList().get(0);
			}
		});
		listener.nextQuestionGiven(dane);
	}

	public void setListener(DatabaseListener listener) {
		this.listener = listener;

	}

	public void addResult(Testresult result, Dzial testDzial) {

		doWithEntityManager(new EntityManagerActivity() {
			@SuppressWarnings("unchecked")
			@Override
			public Object run(EntityManager em) {
				em.getTransaction().begin();
				em.persist(result);
				em.getTransaction().commit();

				Query query = em.createQuery(
						"SELECT sum(r.questionCount), sum(r.correctCount) FROM Testresult r WHERE r.dzial = '"
								+ testDzial.getNazwa() + "'" );
				List<Object[]> resultList = query.getResultList();
				Object[] values = resultList.get(0);
				em.getTransaction().begin();
				Dzial dzial = em.find(Dzial.class, testDzial.getId_dzial());
				BigDecimal poprawnosc = BigDecimal.valueOf(100).multiply(BigDecimal.valueOf((long) values[1])
						.divide(BigDecimal.valueOf((long) values[0]), 3, BigDecimal.ROUND_DOWN));
				dzial.setPoprawnosc(poprawnosc);
				em.getTransaction().commit();
				
				return null;
			}
		});

	}

	public void incrementFailCount(Dane data) {
		doWithEntityManager(new EntityManagerActivity() {
			@Override
			public Object run(EntityManager em) {
				em.getTransaction().begin();
				Dane dane = em.find(Dane.class, data.getId());
				dane.setBledy(dane.getBledy() + 1);
				em.getTransaction().commit();
				return null;
			}
		});
	}

}

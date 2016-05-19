package mediHelper.controler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mediHelper.dao.DatabaseTestsManager;
import mediHelper.entities.Dane;
import mediHelper.entities.Dzial;
import mediHelper.entities.Testresult;
import mediHelper.listener.DatabaseListener;
import mediHelper.testing.AnswerResult;
import mediHelper.testing.TestStats;

// kontroler dla panelu testującego

public class TestingPanelKontroler {

	private DatabaseTestsManager dbConn;
	private Date testStart;
	private String testMode;
	private Dzial testDzial;
	private TestStats stats;

	private String datePattern = "yyyy-MM-dd";
	private SimpleDateFormat df = new SimpleDateFormat(datePattern);

	public TestingPanelKontroler(DatabaseListener listener) {
		dbConn = new DatabaseTestsManager();
		dbConn.setListener(listener);
	}

	public List<Dzial> getListaDzial() {
		return dbConn.getListaDzial();
	}

	// inicjalizacja testu - zapisanie obecnego trybu testowania i testowanego
	// działu
	public void startSession(String testMode, Dzial testDzial) {
		testStart = new Date();
		this.testMode = testMode;
		this.testDzial = testDzial;
		stats = new TestStats();
	}

	// pobranie kolejnej pary pojęć
	public void getNextQuestion() {
		dbConn.getNextQuestion(testDzial);
	}

	// zakończneie testu, wysłanie wyników do bazy
	public void stopSession() {

		if (stats.getMainCount() != 0) {
			Testresult result = new Testresult(df.format(testStart), testMode, testDzial, stats.getMainCount(),
					stats.getCorrectCount(), stats.getPercentage());
			dbConn.addResult(result, testDzial);
		}
	}

	// metoda analizująca odpowiedź udzieloną przez użytkownika
	public AnswerResult registerAnswer(Dane currentQuestion, String answer) {
		if (testMode.equals("Polski -> Łacina")) {
			if (answer.equals(currentQuestion.getNazwalacinska())) {
				return new AnswerResult(stats.correctAnswer(), true);
			} else
				dbConn.incrementFailCount(currentQuestion);
				return new AnswerResult(stats.wrongAnswer(), false);
		} else {
			if (answer.equals(currentQuestion.getNazwapolska())) {
				return new AnswerResult(stats.correctAnswer(), true);
			} else
				dbConn.incrementFailCount(currentQuestion);
				return new AnswerResult(stats.wrongAnswer(), false);
		}
	}

}

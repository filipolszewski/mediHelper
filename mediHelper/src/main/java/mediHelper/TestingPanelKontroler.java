package mediHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mediHelper.entities.Dane;
import mediHelper.entities.Dzial;
import mediHelper.entities.Testresult;

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

	public void startSession(String testMode, Dzial testDzial) {
		testStart = new Date();
		this.testMode = testMode;
		this.testDzial = testDzial;
		stats = new TestStats();
	}

	public void getNextQuestion() {
		dbConn.getNextQuestion(testDzial);

	}

	public void stopSession() {

		if (stats.getMainCount() != 0) {
			Testresult result = new Testresult(df.format(testStart), testMode, testDzial, stats.getMainCount(),
					stats.getCorrectCount(), stats.getPercentage());
			dbConn.addResult(result, testDzial);
		}
	}

	public AnswerResult registerAnswer(Dane currentQuestion, String answer) {
		if (testMode.equals("Polski -> Łacina")) {
			if (answer.equals(currentQuestion.getNazwalacinska())) {
				return new AnswerResult(stats.correctAnswer(), true);
			} else
				return new AnswerResult(stats.wrongAnswer(), false);
		} else {
			if (answer.equals(currentQuestion.getNazwapolska())) {
				return new AnswerResult(stats.correctAnswer(), true);
			} else
				return new AnswerResult(stats.wrongAnswer(), false);
		}
	}

}

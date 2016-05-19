package mediHelper.testing;

// klasa reprezentująca wynik odpowiedzi - przechowuje dotychczasowe statystyki i pole informujące, czy ta odpowiedź była poprawna

public class AnswerResult {

	private TestStats stats;

	private boolean answerCorrect;

	public AnswerResult(TestStats stats, boolean answerCorrect) {
		this.stats = stats;
		this.answerCorrect = answerCorrect;
	}

	public TestStats getStats() {
		return stats;
	}

	public void setStats(TestStats stats) {
		this.stats = stats;
	}

	public boolean isAnswerCorrect() {
		return answerCorrect;
	}

	public void setAnswerCorrect(boolean answerCorrect) {
		this.answerCorrect = answerCorrect;
	}

}

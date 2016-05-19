package mediHelper.testing;

import java.math.BigDecimal;

// klasa reprezentująca wynik zakończonego testu

public class TestStats {

	private Integer mainCount;
	private Integer correctCount;
	private Integer wrongCount;
	private BigDecimal percentage;
	
	public TestStats() {
		mainCount = 0;
		correctCount = 0;
		wrongCount = 0;
		percentage = new BigDecimal(100);
	}
	public void setStats(Integer mC, Integer cC, Integer wC, BigDecimal p) {
		mainCount = mC;
		correctCount = cC;
		wrongCount = wC;
		percentage = p;
	}
	
	public Integer getMainCount() {
		return mainCount;
	}
	public void setMainCount(Integer mainCount) {
		this.mainCount = mainCount;
	}
	public Integer getCorrectCount() {
		return correctCount;
	}
	public void setCorrectCount(Integer correctCount) {
		this.correctCount = correctCount;
	}
	public Integer getWrongCount() {
		return wrongCount;
	}
	public void setWrongCount(Integer wrongCount) {
		this.wrongCount = wrongCount;
	}
	public BigDecimal getPercentage() {
		return percentage;
	}
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}
	public TestStats correctAnswer() {
		mainCount++;
		correctCount++;
		percentage = BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(correctCount).divide(
			    BigDecimal.valueOf(mainCount), 4, BigDecimal.ROUND_DOWN));
		percentage.setScale(4, BigDecimal.ROUND_DOWN);
		return this;
	}
	public TestStats wrongAnswer() {
		mainCount++;
		wrongCount++;
		percentage = BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(correctCount).divide(
			    BigDecimal.valueOf(mainCount), 10, BigDecimal.ROUND_DOWN));
		percentage.setScale(4, BigDecimal.ROUND_DOWN);
		return this;
	}
	
}

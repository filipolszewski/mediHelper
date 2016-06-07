package mediHelper.entities;

import java.math.BigDecimal;

//Obiekt przechowujący dane wyświetlane w widoku statystyk

public class Stats {

	private Integer testCount;
	private Integer failCount;
	private BigDecimal overallAccuracy;
	private String lastTestDate;
	private BigDecimal lastTestAccuracy;

	public Stats(Integer testCount, Integer failCount, BigDecimal overallAccuracy, String lastTestDate,
			BigDecimal lastTestAccuracy) {

		this.testCount = testCount;
		this.failCount = failCount;
		this.overallAccuracy = overallAccuracy;
		this.lastTestDate = lastTestDate;
		this.lastTestAccuracy = lastTestAccuracy;
	}

	public Integer getTestCount() {
		return testCount;
	}

	public void setTestCount(Integer testCount) {
		this.testCount = testCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public BigDecimal getOverallAccuracy() {
		return overallAccuracy;
	}

	public void setOverallAccuracy(BigDecimal overallAccuracy) {
		this.overallAccuracy = overallAccuracy;
	}

	public String getLastTestDate() {
		return lastTestDate;
	}

	public void setLastTestDate(String lastTestDate) {
		this.lastTestDate = lastTestDate;
	}

	public BigDecimal getLastTestAccuracy() {
		return lastTestAccuracy;
	}

	public void setLastTestAccuracy(BigDecimal lastTestAccuracy) {
		this.lastTestAccuracy = lastTestAccuracy;
	}

}

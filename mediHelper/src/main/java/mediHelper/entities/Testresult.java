package mediHelper.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// Encja JPA reprezentująca rekord tabeli testresult - dane dotyczące wyniku testu

@Entity
@Table(schema = "medi")
public class Testresult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String date;
	private String mode;
	private String dzial;
	@Column(name = "question_count")
	private Integer questionCount;
	@Column(name = "correct_count")
	private Integer correctCount;

	private BigDecimal accuracy;

	public Testresult(String testDate, String testMode, Dzial testDzial, Integer mainCount, Integer correctCount,
			BigDecimal percentage) {
		date = testDate;
		mode = testMode;
		dzial = testDzial.getNazwa();
		questionCount = mainCount;
		this.correctCount = correctCount;
		accuracy = percentage;
	}

	public Testresult() {
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Integer getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
	}

	public Integer getCorrectCount() {
		return correctCount;
	}

	public void setCorrectCount(Integer correctCount) {
		this.correctCount = correctCount;
	}

	public BigDecimal getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(BigDecimal accuracy) {
		this.accuracy = accuracy;
	}

	public void setDzial(String dzial) {
		this.dzial = dzial;
	}

	public String getDzial() {
		return dzial;
	}
}

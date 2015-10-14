package mediHelper.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Klasa reprezentyjąca parę danych, jednocześnie encja dla JPA
 *
 * @author Filip Olszewski
 *
 */
@Entity
@Table(schema = "medi")
public class Dane {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nazwapolska")
	private String nazwapolska;

	@Column(name = "nazwalacinska")
	private String nazwalacinska;

	@ManyToOne
	@JoinColumn(name = "id_dzial")
	private Dzial dzial;

	public Dane() {}
	
	public Dane(String polish, String latina, Integer dzial) {
		this.nazwapolska = polish;
		this.nazwalacinska = latina;
		this.dzial = new Dzial(dzial);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNazwapolska() {
		return nazwapolska;
	}

	public void setNazwapolska(String nazwapolska) {
		this.nazwapolska = nazwapolska;
	}

	public String getNazwalacinska() {
		return nazwalacinska;
	}

	public void setNazwalacinska(String nazwalacinska) {
		this.nazwalacinska = nazwalacinska;
	}

	public Dzial getDzial() {
		return dzial;
	}

	public void setDzial(Dzial dzial) {
		this.dzial = dzial;
	}

}

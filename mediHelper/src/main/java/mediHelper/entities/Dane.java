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
 * @author Filip Olszewski
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

	@Column(name = "bledy")
	private Integer bledy;
	public Dane() {}
	
	public Dane(Object polish, Object latina, Object dzial) {
		this.nazwapolska = (String)polish;
		this.nazwalacinska = (String)latina;
		Integer idDzial = (Integer)dzial;
		this.dzial = new Dzial(idDzial);
		this.bledy = 0;
	}
	public Dane(Integer id, String polish, String latina, Dzial dzial, Integer bledy) {
		this.id = id;
		this.nazwapolska = polish;
		this.nazwalacinska = latina;
		this.dzial = dzial;
		this.bledy = bledy;
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

	public Integer getBledy() {
		return bledy;
	}

	public void setBledy(Integer bledy) {
		this.bledy = bledy;
	}

}

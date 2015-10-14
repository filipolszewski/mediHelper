package mediHelper.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "medi")
public class Dzial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_dzial;
	
	private String nazwa;
	
	public Dzial() {}
	public Dzial(Integer id) {
		this.id_dzial = id;
		nazwa = "";
	}

	public Dzial(String nazwa) {
		this.nazwa = nazwa;
	}
	public int getId_dzial() {
		return id_dzial;
	}

	public void setId_dzial(int id_dzial) {
		this.id_dzial = id_dzial;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	@Override
	public String toString() {
		return nazwa;
	}
}
package mediHelper;
//POJO class - row for table in DataPabel
import mediHelper.entities.Dzial;

public class DataRow {
	
	private Integer id;
	private String latinaName;
	private Dzial dzial;
	private Integer failCount;
	private String polishName;
	
	public DataRow(Integer id, String polishName, String latinaName, Dzial dzial, Integer failCount) {
		super();
		this.id = id;
		this.polishName = polishName;
		this.latinaName = latinaName;
		this.dzial = dzial;
		this.failCount = failCount;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPolishName() {
		return polishName;
	}
	public void setPolishName(String polishName) {
		this.polishName = polishName;
	}
	public String getLatinaName() {
		return latinaName;
	}
	public void setLatinaName(String latinaName) {
		this.latinaName = latinaName;
	}
	public Dzial getDzial() {
		return dzial;
	}
	public void setDzial(Dzial dzial) {
		this.dzial = dzial;
	}
	public Integer getFailCount() {
		return failCount;
	}
	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}	

}

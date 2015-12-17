package mediHelper;

import java.util.List;

import mediHelper.entities.Dzial;

public class TestingPanelKontroler {

	private TestingPanel panel;
	private DatabaseConnector dbConn;

	public TestingPanelKontroler(TestingPanel panel) {
		this.panel = panel;
		dbConn = new DatabaseConnector();
	}
	
	public List<Dzial> getListaDzial() {
		return dbConn.getListaDzial();
	}
	
}

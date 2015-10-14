package mediHelper;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import mediHelper.entities.Dzial;

public class MediKontroler {

	private JFrame parent;
	private DatabaseConnector dbConnector;

	public MediKontroler(JFrame parent) {
		this.parent = parent;
	}

	public void doStart() {
		dbConnector = new DatabaseConnector();
		dbConnector.setListener((DatabaseListener) parent);
		dbConnector.calcDataAmount();

	}

	public DefaultListModel<Dzial> getModelListaDzial() {
		DefaultListModel<Dzial> model = new DefaultListModel<>();
		for (Dzial dzial : dbConnector.getListaDzial()) {
			model.addElement(dzial);
		}
		return model;
	}

	public void showListChoice() {
		ListChoiceDialog choiceWindow = new ListChoiceDialog(this);
		choiceWindow.setVisible(true);
	}

	public void doShowListWindow(Integer idDzial) {
		List<Object[]> lista = dbConnector.getListaByDzial(idDzial);
		ListWindow listWindow = new ListWindow(lista);
		listWindow.setVisible(true);
	}

	public void doAddDialog() {
		AddDialog add = new AddDialog(this, dbConnector.getListaDzial());
		add.setVisible(true);
	}

	public void doAddData(String polish, String latina, Object dzial) {
		if (dzial.getClass().equals(String.class)) {
			String nowyDzial = (String) dzial;
			dbConnector.addDataAndDzial(polish, latina, nowyDzial);
		} else {
			Integer id = (Integer) dzial;
			dbConnector.addData(polish, latina, id);
		}
	}
}

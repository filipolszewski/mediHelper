package mediHelper;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import mediHelper.entities.Dane;
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
		ListWindow listWindow = new ListWindow(lista, this);
		listWindow.setVisible(true);
	}

	public void doEditData(Object[] d, int column) {
		Dane dane = new Dane(d[1], d[2], d[3]);
		dbConnector.editDane(dane, column);
	}
}

package mediHelper;

import java.util.List;

import mediHelper.entities.Dane;
import mediHelper.entities.Dzial;

public class Kontroler {

	DatabaseConnector dbConn = new DatabaseConnector();

	public Kontroler(DatabaseListener listener) {
		dbConn.addListener(listener);
	}

	public void doAddListener(DatabaseListener listener) {
		dbConn.addListener(listener);
	}

	public void doStartMainWindow() {
		dbConn.calcDataAmount();
	}

	public void doStartDataPanel(DatabaseListener listener) {
		dbConn.addListener(listener);
		dbConn.getData("", null);
	}

	public void doGetData(String searchString, Dzial dzial) {
		if (dzial != null) {
			dbConn.getData(searchString, dzial.getId_dzial());
		} else {
			dbConn.getData(searchString, null);
		}
	}

	public List<Dzial> getListaDzial() {
		return dbConn.getListaDzial();
	}

	public void doAddData() {
		Dane dane = new Dane("", "", null);
		EditDialog dialog = new EditDialog(dbConn.getListaDzial());
		dialog.setDane(dane);
		dialog.setModal(true);
		dialog.setVisible(true);
		Dane d = dialog.getData();
		if (dialog.userAccepted) {
			if (dialog.isNewCategorySelected()) {
				dbConn.addDataAndDzial(d.getId(), d.getNazwapolska(), d.getNazwalacinska(), d.getDzial().toString(), d.getBledy());
			} else {
				dbConn.addData(d);
			}
		}
	}

	public void doEditData(Integer id) {
		EditDialog dialog = new EditDialog(dbConn.getListaDzial());
		dialog.setDane(dbConn.getDataById(id));
		dialog.setModal(true);
		dialog.setVisible(true);
		if (dialog.userAccepted) {
			dbConn.editData(dialog.getData());
		}
	}

	public void doDelete(Integer id) {
		dbConn.deleteData(id);
	}

}

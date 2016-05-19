package mediHelper.controler;

import java.util.List;

import mediHelper.dao.DatabaseConnector;
import mediHelper.entities.Dane;
import mediHelper.entities.Dzial;
import mediHelper.listener.DatabaseListener;
import mediHelper.view.EditDialog;

// Kontroler dla MainWindow i DataPanel

public class MainWindowController {

	DatabaseConnector dbConn = new DatabaseConnector();

	// metoda dodaje listenerow spelniajacych interfejs do listy
	public void doAddListener(DatabaseListener listener) {
		dbConn.addListener(listener);
	}

	// metoda rejestruje listenera i zleca DAO zliczenie ilości danych
	public void doStartMainWindow(DatabaseListener listener) {
		dbConn.addListener(listener);
		dbConn.calcDataAmount();
	}

	// metoda rejestruje listenera i zleca DAO wydobycie danych potrzebnych do
	// wypełnienia DataPanel
	public void start(DatabaseListener listener) {
		dbConn.addListener(listener);
		dbConn.getData("", null);
	}

	// zleca DAO wydobycie danych potrzebnych do wypełnienia DataPanel
	public void doGetData(String searchString, Dzial dzial) {
		if (dzial != null) {
			dbConn.getData(searchString, dzial.getId_dzial());
		} else {
			dbConn.getData(searchString, null);
		}
	}

	// zleca DAO wydobycie listy Działów
	public List<Dzial> getListOfCategories() {
		return dbConn.getListOfCategories();
	}

	// utworzenie i pokazanie EditDialog. Zebranie danych i zlecenie do DAO
	// zapisania ich w bazie
	public void doAddData() {
		Dane dane = new Dane("", "", null);
		EditDialog dialog = new EditDialog(dbConn.getListOfCategories());
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

	// utworzenie, ustawienie wartości i pokazanie EditDialog. Zebranie
	// edytowanych danych i zlecenie do DAO zapisania ich w bazie
	public void doEditData(Integer id) {
		EditDialog dialog = new EditDialog(dbConn.getListOfCategories());
		dialog.setDane(dbConn.getDataById(id));
		dialog.setModal(true);
		dialog.setVisible(true);
		if (dialog.userAccepted) {
			dbConn.editData(dialog.getData());
		}
	}

	// zlecenie DAO usunięca danych o danym ID
	public void doDelete(Integer id) {
		dbConn.deleteData(id);
	}

	public void getStatsForCategory(Dzial selectedItem) {
		dbConn.getStatsForCategory(selectedItem.getNazwa());
	}

}

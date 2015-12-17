package mediHelper;

import mediHelper.entities.Dane;

public class DataKontroler {

	DatabaseConnector dbConn = new DatabaseConnector();
	DatabaseListener listener;
	
	
	
	public DataKontroler(DatabaseListener listener) {
		this.listener = listener;
		dbConn.setListener(listener);
	}

	public void doStart() {
		dbConn.getData("", null);
	}
	
	public void doAddData() {
		Dane dane = new Dane("", "", null);
		EditDialog dialog = new EditDialog(dbConn.getListaDzial());
		dialog.setDane(dane);
		dialog.setVisible(true);
		if (dialog.userAccepted) {
			dbConn.addData(dialog.getData());
		}
	}

	public void doEditData(Integer id) {
		EditDialog dialog = new EditDialog(dbConn.getListaDzial());
		dialog.setDane(dbConn.getDataById(id));
		dialog.setVisible(true);
		if (dialog.userAccepted) {
			dbConn.editData(dialog.getData());
		}
	}

}

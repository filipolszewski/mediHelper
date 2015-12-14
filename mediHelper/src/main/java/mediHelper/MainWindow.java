package mediHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements DatabaseListener {

	private static final String TITLE = "MediHelper";
	private MediKontroler controler;
	private JLabel infoLabel;
	private static final Font infoLblFont = new Font("Verdana", Font.BOLD, 12);
	private DataPanel dataPanel;
	private List<DataRow[]> lista;

	public MainWindow() {
		setSize(700, 450);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(TITLE);
		createComponents();
		createControler();
	}

	private void createControler() {
		controler = new MediKontroler(this);
		controler.doStart();
	}

	private void createComponents() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(mainPanel);
		createTabbedPane(mainPanel);
		createInfoPanel(mainPanel);
	}

	private void createInfoPanel(JPanel mainPanel) {
		JPanel infoPanel = new JPanel(new BorderLayout());
		
		infoLabel = new JLabel();
		infoLabel.setFont(infoLblFont);
		infoLabel.setForeground(Color.DARK_GRAY);
		infoPanel.add(infoLabel, BorderLayout.LINE_END);
		mainPanel.add(infoPanel, BorderLayout.PAGE_END);
		
	}

	private void createTabbedPane(JPanel mainPanel) {
		JTabbedPane tabbedPane = new JTabbedPane();
		mainPanel.add(tabbedPane, BorderLayout.CENTER);
		
		TestingPanel testingPanel = new TestingPanel();
		tabbedPane.add("Testy", testingPanel);
		
		DataKontroler dataKontroler = new DataKontroler();
		dataPanel = new DataPanel(dataKontroler);
		tabbedPane.add("Pojęcia", dataPanel);
	}

	@Override
	public void dataAmount(Integer numer) {
		infoLabel.setText("Zebrano już " + numer + " pojęć!");
		
	}
	
	@Override
	public void dataIsRead(List<DataRow[]> lista) {

		this.lista = lista;
		TableModel dataModel = new AbstractTableModel() {

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Object pojecie = lista.get(rowIndex)[columnIndex];
				return pojecie;
			}

			@Override
			public int getRowCount() {
				return lista.size();
			}

			@Override
			public int getColumnCount() {
				return 4;
			}

			@Override
			public String getColumnName(int column) {
				switch (column) {
				case 0:
					return "Polska Nazwa";
				case 1:
					return "Łacińska Nazwa";
				case 2:
					return "Dział";
				case 3:
					return "Błędy";
				}
				return null;
			}

		};
		dataPanel.dataTable.setModel(dataModel);

	}

}

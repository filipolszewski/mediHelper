package mediHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class ListWindow extends JFrame {

	private static final String TITLE = "Przeglądanie listy";
	private List<Object[]> lista;
	private JTable dataTable;

	public ListWindow(List<Object[]> lista) {
		this.lista = lista;
		setSize(300, 350);
		setLocationRelativeTo(null);
		setTitle(TITLE);
		createMainPanel();
		fillTable(lista);
		
	}

	private void createMainPanel() {
		JPanel mainPanel = (JPanel) getContentPane();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		createTablePanel(mainPanel);
	}

	private void createTablePanel(JPanel mainPanel) {
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		dataTable = new JTable(lista.size(), 2);
		dataTable.setRowSelectionAllowed(false);
		dataTable.setFillsViewportHeight(true);
		dataTable.setBackground(Color.pink);
		
		JScrollPane scrollPane = new JScrollPane(dataTable);
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(tablePanel, BorderLayout.CENTER);
	}
	
	private void fillTable(List<Object[]> lista) {

		this.lista = lista;
		TableModel dataModel = new AbstractTableModel() {

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Object wyp = lista.get(rowIndex)[columnIndex];
				return wyp;
			}

			@Override
			public int getRowCount() {
				return lista.size();
			}

			@Override
			public int getColumnCount() {
				return 2;
			}

			@Override
			public String getColumnName(int column) {
				switch (column) {
				case 0:
					return "Nazwa Polska";
				case 1:
					return "Nazwa Łacińska";
				}
				return null;
			}

		};
		dataTable.setModel(dataModel);

	}

	
}

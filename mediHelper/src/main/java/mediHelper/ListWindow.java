package mediHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class ListWindow extends JFrame implements TableModelListener {

	private static final String TITLE = "Przeglądanie listy";
	private List<Object[]> lista;
	private JTable dataTable;
	private MediKontroler controler;
	private TableModel dataModel;

	public ListWindow(List<Object[]> lista, MediKontroler controler) {
		this.controler = controler;
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
		dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dataTable.getModel().addTableModelListener(ListWindow.this);
		

		JScrollPane scrollPane = new JScrollPane(dataTable);
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(tablePanel, BorderLayout.CENTER);
	}

	private void fillTable(List<Object[]> lista) {

		this.lista = lista;
		dataModel = new AbstractTableModel() {

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Object data = lista.get(rowIndex)[columnIndex];
				return data;
			}

			@Override
			public int getRowCount() {
				return lista.size();
			}

			@Override
			public int getColumnCount() {
				return 2;
			}

			public boolean isCellEditable(int row, int col) {
				return true;
			}

			public void setValueAt(Object value, int row, int col) {
		        fireTableCellUpdated(row, col);
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

	};dataTable.setModel(dataModel);

	}

	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
        int column = e.getColumn();
        Object[] d = lista.get(row);
        controler.doEditData(d, column);
        
	}

}

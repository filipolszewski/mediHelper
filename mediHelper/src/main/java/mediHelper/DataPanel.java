package mediHelper;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import mediHelper.entities.Dane;
import mediHelper.entities.Dzial;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class DataPanel extends JPanel implements DatabaseListener{

	private JTable dataTable;
	private DataKontroler kontroler;
	private List<Dane> lista;
	
	public DataPanel() {
		setLayout(new BorderLayout(5, 5));
		createComponents();
		kontroler = new DataKontroler(this);
		kontroler.doStart();
	}

	private void createComponents() {
		createTopPanel();
		createTable();	
	}

	private void createTable() {
		
		dataTable = new JTable(40, 4);
		dataTable.setRowSelectionAllowed(true);
		dataTable.setFillsViewportHeight(true);
		dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(dataTable);
		scrollPane.setViewportView(dataTable);
		add(scrollPane, BorderLayout.CENTER);
	}

	

	private void createTopPanel() {
		JPanel topPanel = new JPanel(new MigLayout());
		add(topPanel, BorderLayout.PAGE_START);
		
		JButton addBtn = new JButton("Dodaj");
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				kontroler.doAddData();
				
			}
		});
		JButton editBtn = new JButton("Edytuj");
		editBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dataTable.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Wybierz wiersz przed edycją");
				} else
					kontroler.doEditData(lista.get(dataTable.getSelectedRow()).getId());
				
			}
		});
		JButton deleteBtn = new JButton("Usuń");
		JComboBox<Dzial> comboBox = new JComboBox<>();
		JTextField searchField = new JTextField(25);
		JButton searchBtn = new JButton("Szukaj");
		
		topPanel.add(addBtn);
		topPanel.add(editBtn);
		topPanel.add(deleteBtn);
		topPanel.add(comboBox, "gap left 20");
		topPanel.add(searchField, "gap left 130");
		topPanel.add(searchBtn, "align right");
	}
	
	public JTable getDataTable() {
		return dataTable;
	}
	
	@Override
	public void dataIsRead(List<Dane> lista) {

		this.lista = lista;
		dataTable.setModel(new AbstractTableModel() {

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				
				Object value = "??";
				Dane dataRow = lista.get(rowIndex);
				switch (columnIndex) {
				case 0:
					value = dataRow.getNazwapolska();
					break;
				case 1:
					value = dataRow.getNazwalacinska();
					break;
				case 2:
					value = dataRow.getDzial();
					break;
				case 3:
					value = dataRow.getBledy();
					break;
				}
				return value;
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
		});
        TableColumn columnA = dataTable.getColumn("Błędy");
        columnA.setMinWidth(40);
        columnA.setMaxWidth(40);
	}

	@Override
	public void dataAmount(Integer numer) {}

}

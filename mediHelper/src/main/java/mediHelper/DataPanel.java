package mediHelper;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import mediHelper.entities.Dane;
import mediHelper.entities.Dzial;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class DataPanel extends JPanel implements DatabaseListener {

	private JTable dataTable;
	private Kontroler kontroler;
	private List<Dane> lista;
	private JComboBox<Dzial> comboBox;
	private JTextField searchField;

	public DataPanel(Kontroler kontroler) {
		this.kontroler = kontroler;
		setLayout(new BorderLayout(5, 5));
		createComponents();
	}

	public void startControler() {
		kontroler.doStartDataPanel(this);
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
		scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		scrollPane.setViewportView(dataTable);
		add(scrollPane, BorderLayout.CENTER);
	}

	private void createTopPanel() {
		JPanel topPanel = new JPanel(new BorderLayout(5, 5));
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
		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if ((Integer) dataTable.getSelectedRow() >= 0) {
					int reply = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz usunąć to pojęcie?");
					if (reply == JOptionPane.YES_OPTION) {
						Integer id = (Integer) lista.get(dataTable.getSelectedRow()).getId();
						kontroler.doDelete(id);
					}
				}
			}
		});
		createComboBox();
		searchField = new JTextField(20);
		JButton searchBtn = new JButton("Szukaj");
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				kontroler.doGetData(searchField.getText(), (Dzial) comboBox.getSelectedItem());
			}
		});

		JPanel searchPanel = new JPanel(new MigLayout());
		JPanel btnPanel = new JPanel(new MigLayout());
		btnPanel.add(addBtn);
		btnPanel.add(editBtn);
		btnPanel.add(deleteBtn);
		btnPanel.add(comboBox, "gap left 20");
		searchPanel.add(searchField, "align right");
		searchPanel.add(searchBtn, "align right");
		topPanel.add(searchPanel, BorderLayout.LINE_END);
		topPanel.add(btnPanel, BorderLayout.LINE_START);
	}

	private JComboBox<Dzial> createComboBox() {
		comboBox = new JComboBox<Dzial>(new Vector<Dzial>(kontroler.getListaDzial()));
		comboBox.insertItemAt(null, 0);
		comboBox.setSelectedIndex(0);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				kontroler.doGetData(searchField.getText(), (Dzial) comboBox.getSelectedItem());
			}
		});
		return comboBox;
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
	public void dataAmount(Integer numer) {
	}

	@Override
	public void categoryChange() {
		comboBox.setModel(new DefaultComboBoxModel<Dzial>(new Vector<Dzial>(kontroler.getListaDzial())));
		comboBox.insertItemAt(null, 0);
		comboBox.setSelectedIndex(0);
		this.revalidate();
		this.repaint();
	}

}

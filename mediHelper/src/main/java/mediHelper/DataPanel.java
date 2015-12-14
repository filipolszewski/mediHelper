package mediHelper;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import mediHelper.entities.Dzial;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class DataPanel extends JPanel{

	public JTable dataTable;

	public DataPanel(DataKontroler kontroler) {
		setLayout(new BorderLayout(5, 5));
		createComponents();
		kontroler.doStart();
	}

	private void createComponents() {
		createTopPanel();
		createTable();	
	}

	private void createTable() {
		
		dataTable = new JTable(40, 4);
		dataTable.setRowSelectionAllowed(false);
		dataTable.setFillsViewportHeight(true);
		dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(dataTable);
		add(scrollPane, BorderLayout.CENTER);
	}

	private void createTopPanel() {
		JPanel topPanel = new JPanel(new MigLayout());
		add(topPanel, BorderLayout.PAGE_START);
		
		JButton addBtn = new JButton("Dodaj");
		JButton editBtn = new JButton("Edytuj");
		JButton deleteBtn = new JButton("Usuń");
		JComboBox<Dzial> comboBox = new JComboBox<>();
		JTextField searchField = new JTextField(35);
		JButton searchBtn = new JButton("Szukaj");
		
		topPanel.add(addBtn);
		topPanel.add(editBtn);
		topPanel.add(deleteBtn);
		topPanel.add(comboBox, "gap left 20");
		topPanel.add(searchField, "gap left 80");
		topPanel.add(searchBtn, "align right");
	}

}

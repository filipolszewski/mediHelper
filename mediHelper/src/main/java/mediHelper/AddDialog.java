package mediHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import mediHelper.entities.Dzial;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class AddDialog extends JDialog {

	private static final String TITLE = "Dodawanie pojęć";
	private static final Font FONT = new Font("Verdana", Font.BOLD, 11);
	
	private JTextField polishNameField;
	private JTextField latinaNameField;
	private JComboBox<Dzial> dzialComboBox;
	
	private List<Dzial> listaDzialow;
	private MediKontroler controler;
	private JTextField newDzialField;
	private JCheckBox checkBox;

	public AddDialog(MediKontroler controler, List<Dzial> listaDzialow) {
		this.controler = controler;
		this.listaDzialow = listaDzialow;
		setSize(350, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(TITLE);
		createComponents();
	}

	private void createComponents() {
		JPanel mainPanel = (JPanel) getContentPane();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		createFormPanel(mainPanel);
		createButtonPanel(mainPanel);
	}


	private void createFormPanel(JPanel mainPanel) {
		JPanel formPanel = new JPanel(new MigLayout());
		formPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		createAreasAndFiels(formPanel);
		mainPanel.add(formPanel, BorderLayout.CENTER);
		
	}
	private void createAreasAndFiels(JPanel formPanel) {
		addJLabelToPanel(formPanel, "Nazwa Polska:");
		polishNameField = new JTextField(15);
		formPanel.add(polishNameField, "wrap, span");

		addJLabelToPanel(formPanel, "Nazwa Łacińska:");
		latinaNameField = new JTextField(15);
		formPanel.add(latinaNameField, "wrap, span");
		
		addJLabelToPanel(formPanel, "Dział:");
		dzialComboBox = new JComboBox<Dzial>(new Vector<Dzial>(listaDzialow));
		dzialComboBox.setSize(40, 15);
		formPanel.add(dzialComboBox, "wrap");
		
		addJLabelToPanel(formPanel, "Nowy Dział:");
		newDzialField = new JTextField(15);
		newDzialField.setEnabled(false);
		newDzialField.setBackground(Color.DARK_GRAY);
		checkBox = new JCheckBox();
		checkBox.setToolTipText("Odblokuj pole nowego działu");
		checkBox.setBorderPaintedFlat(true);
		checkBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkBox.isSelected()) {
					newDzialField.setEnabled(true);
					newDzialField.setBackground(Color.WHITE);
					dzialComboBox.setEnabled(false);
				} else {
					newDzialField.setEnabled(false);
					newDzialField.setBackground(Color.DARK_GRAY);
					dzialComboBox.setEnabled(true);
				}
			}
		});
		formPanel.add(newDzialField);
		formPanel.add(checkBox, "wrap");
		
	}

	private void addJLabelToPanel(JPanel panel, String stringName) {
		JLabel label = new JLabel(stringName);
		label.setFont(FONT);
		panel.add(label);
	}

	private void createButtonPanel(JPanel mainPanel) {
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JButton addButton = new JButton("Dodaj");
		addButton.setFont(FONT);
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object dzial;
				if (checkBox.isSelected()) {
					dzial = newDzialField.getText();
				} else {
					dzial = dzialComboBox.getItemAt(dzialComboBox.getSelectedIndex()).getId_dzial();
				}
				controler.doAddData(polishNameField.getText(), latinaNameField.getText(), dzial);
				AddDialog.this.dispose();
			}
		});
		buttonPanel.add(addButton, BorderLayout.LINE_START);
		
		JButton cancelButton = new JButton("Anuluj");
		cancelButton.setFont(FONT);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddDialog.this.dispose();
			}
		});
		buttonPanel.add(cancelButton, BorderLayout.LINE_END);
		
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		
	}
}

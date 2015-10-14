package mediHelper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import mediHelper.entities.Dzial;

@SuppressWarnings("serial")
public class ListChoiceDialog extends JDialog {

	private static final String TITLE = "Wybór listy";
	private DefaultListModel<Dzial> listaModel;
	private JList<Dzial> listDzial;
	private MediKontroler controler;
	private JButton showButton;

	public ListChoiceDialog(MediKontroler controler) {

		this.controler = controler;
		setSize(350, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(TITLE);
		createComponents();
	}

	private void createComponents() {
		JPanel mainPanel = (JPanel) getContentPane();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		createListPanel(mainPanel);
		createButtonPanel(mainPanel);
	}

	private void createButtonPanel(JPanel mainPanel) {
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		showButton = new JButton("Pokaż listę");
		showButton.setEnabled(false);
		showButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer idDzial = listaModel.getElementAt(listDzial.getSelectedIndex()).getId_dzial();
				controler.doShowListWindow(idDzial);
				ListChoiceDialog.this.dispose();
			}
		});
		buttonPanel.add(showButton, BorderLayout.PAGE_START);

		JButton cancelButton = new JButton("Powrót");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ListChoiceDialog.this.dispose();

			}
		});
		buttonPanel.add(cancelButton, BorderLayout.PAGE_END);

		mainPanel.add(buttonPanel, BorderLayout.LINE_END);
	}

	private void createListPanel(JPanel mainPanel) {
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		listaModel = controler.getModelListaDzial();
		listDzial = new JList<Dzial>(listaModel);
		listDzial.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				showButton.setEnabled(true);
			}
		});

		JScrollPane scrollPaneList = new JScrollPane(listDzial);
		scrollPaneList.setPreferredSize(new Dimension(180, 200));
		listPanel.add(scrollPaneList, BorderLayout.CENTER);

		mainPanel.add(listPanel, BorderLayout.LINE_START);
	}

}

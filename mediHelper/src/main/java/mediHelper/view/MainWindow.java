package mediHelper.view;

//TODO Create and add Category Panel (Deleting categories + statistics)


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import mediHelper.controler.Kontroler;
import mediHelper.entities.Dane;
import mediHelper.listener.DatabaseListener;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements DatabaseListener {

	private static final String TITLE = "MediHelper";
	private Kontroler controler;
	private JLabel infoLabel;
	private static final Font infoLblFont = new Font("Verdana", Font.BOLD, 12);
	private DataPanel dataPanel;

	public MainWindow() {
		setSize(700, 450);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(TITLE);
		controler = new Kontroler(this);
		createComponents();
		controler.doStartMainWindow();
		dataPanel.startControler();
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

		dataPanel = new DataPanel(controler);
		tabbedPane.add("Pojęcia", dataPanel);
	}

	@Override
	public void dataAmount(Integer numer) {
		infoLabel.setText("Zebrano już " + numer + " pojęć!");

	}

	@Override
	public void dataIsRead(List<Dane> lista) {
	}

	@Override
	public void categoryChange() {}

	@Override
	public void nextQuestionGiven(Dane dane) {}
}

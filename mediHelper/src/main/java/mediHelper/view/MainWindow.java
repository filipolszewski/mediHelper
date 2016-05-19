package mediHelper.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import mediHelper.controler.MainWindowController;
import mediHelper.entities.Dane;
import mediHelper.entities.Dzial;
import mediHelper.entities.Stats;
import mediHelper.listener.DatabaseListener;
import net.miginfocom.swing.MigLayout;

// Okno Główne. Do rozdzielenia 3 głównych paneli używa JTabbedPane. Posiada także licznik ilości zebranych danych.

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements DatabaseListener {

	// Stałe
	private static final String TITLE = "MediHelper";
	private static final Font infoLblFont = new Font("Verdana", Font.BOLD, 12);
	private static final Font statsLblFont = new Font("Verdana", Font.PLAIN, 14);

	// Obiekty Swing'a
	private JTabbedPane tabbedPane;
	private JLabel dataAmountInfoLabel; // aktualizowana metodą Listenera
										// dataAmountDelivered()
	private DataPanel dataPanel;

	// Kontroler
	private MainWindowController controler;
	private JLabel averageAccuracyDataLabel;
	private JLabel allMistakesDataLabel;
	private JLabel lastTestDataLabel;
	private JLabel lastTestAccuracyDataLabel;
	private JLabel allTestsDataLabel;

	public MainWindow() {
		// Parametry okna
		setSize(700, 450);
		setLocationRelativeTo(null); // ustawia na środku ekranu
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(TITLE);

		controler = new MainWindowController();

		createComponents();
		// Start kontrolerów
		controler.doStartMainWindow(this);
		dataPanel.startControler();
	}

	// metoda tworząca wszystkie Swing'owe obiekty
	private void createComponents() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(mainPanel);
		createTabbedPane(mainPanel);
		createInfoPanel(mainPanel);
	}

	// metoda tworząca panel z informacją o ilości danych
	private void createInfoPanel(JPanel mainPanel) {
		JPanel infoPanel = new JPanel(new BorderLayout());

		dataAmountInfoLabel = new JLabel();
		dataAmountInfoLabel.setFont(infoLblFont);
		dataAmountInfoLabel.setForeground(Color.DARK_GRAY);
		infoPanel.add(dataAmountInfoLabel, BorderLayout.LINE_END);
		mainPanel.add(infoPanel, BorderLayout.PAGE_END);

	}

	// metoda tworząca panel łączący panele Tests, Data i Stats
	private void createTabbedPane(JPanel mainPanel) {
		tabbedPane = new JTabbedPane();
		mainPanel.add(tabbedPane, BorderLayout.CENTER);

		TestingPanel testingPanel = new TestingPanel();
		tabbedPane.add("Testy", testingPanel);

		dataPanel = new DataPanel(controler);
		tabbedPane.add("Pojęcia", dataPanel);

		createStatsPanel(mainPanel);
	}

	// metoda tworząca panel Stats - uwaga - użycie frameworka MigLayout
	private void createStatsPanel(JPanel mainPanel) {

		JPanel statsPanel = new JPanel(new MigLayout("", "", "[]30[]"));
		tabbedPane.add("Statystyki", statsPanel);
		statsPanel.setBorder(new EmptyBorder(30, 30, 10, 30));

		// ComboBox z kategoriami
		JComboBox<Dzial> categoryComboBox = createComboBox();
		statsPanel.add(categoryComboBox, "wrap");

		// Pary labeli Napis - Wartość - tworzenie, ustawienie fontów, dodanie
		// do Panelu
		JLabel allTestsLabel = new JLabel("Testów Łącznie:");
		allTestsDataLabel = new JLabel();
		allTestsLabel.setFont(statsLblFont);
		allTestsDataLabel.setFont(statsLblFont);

		statsPanel.add(allTestsLabel, "gapleft 20, w 200!");
		statsPanel.add(allTestsDataLabel, "wrap");

		JLabel averageAccuracyLabel = new JLabel("Średni wynik:");
		averageAccuracyDataLabel = new JLabel();
		averageAccuracyLabel.setFont(statsLblFont);
		averageAccuracyDataLabel.setFont(statsLblFont);

		statsPanel.add(averageAccuracyLabel, "gapleft 20, w 200!");
		statsPanel.add(averageAccuracyDataLabel, "wrap");

		JLabel allMistakesLabel = new JLabel("Błędów Łącznie:");
		allMistakesDataLabel = new JLabel();
		allMistakesLabel.setFont(statsLblFont);
		allMistakesDataLabel.setFont(statsLblFont);

		statsPanel.add(allMistakesLabel, "gapleft 20, w 200!");
		statsPanel.add(allMistakesDataLabel, "wrap");

		JLabel lastTestLabel = new JLabel("Ostatnio Testowano:");
		lastTestDataLabel = new JLabel();
		lastTestLabel.setFont(statsLblFont);
		lastTestDataLabel.setFont(statsLblFont);

		statsPanel.add(lastTestLabel, "gapleft 20, w 200!");
		statsPanel.add(lastTestDataLabel, "wrap");

		JLabel lastTestAccuracyLabel = new JLabel("Wynik ostatniego testu:");
		lastTestAccuracyDataLabel = new JLabel();
		lastTestAccuracyLabel.setFont(statsLblFont);
		lastTestAccuracyDataLabel.setFont(statsLblFont);

		statsPanel.add(lastTestAccuracyLabel, "gapleft 20, w 200!");
		statsPanel.add(lastTestAccuracyDataLabel, "wrap");
	}

	// tworzenie w osobnej metodzie ComboBox, dodawanie ActionListener
	private JComboBox<Dzial> createComboBox() {

		JComboBox<Dzial> categoryComboBox = new JComboBox<Dzial>();
		categoryComboBox.setModel(new DefaultComboBoxModel<Dzial>(new Vector<Dzial>(controler.getListOfCategories())));
		categoryComboBox.insertItemAt(null, 0);
		categoryComboBox.setSelectedIndex(0);
		categoryComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Dzial category = (Dzial) categoryComboBox.getSelectedItem();
				if (category != null) {
					controler.getStatsForCategory((Dzial) categoryComboBox.getSelectedItem());
				} else {
					clearStatsPanel();
				}

			}
		});
		return categoryComboBox;
	}

	// czyszczenie widoku statystyk
	private void clearStatsPanel() {
		allTestsDataLabel.setText("");
		averageAccuracyDataLabel.setText("");
		allMistakesDataLabel.setText("");
		lastTestDataLabel.setText("");
		lastTestAccuracyDataLabel.setText("");
	}

	// Aktualizacja widoku nowymi statystykami (po wyborze nowego działu)
	@Override
	public void onStatsDelivered(Stats stats) {
		allTestsDataLabel.setText("" + stats.getTestCount());
		averageAccuracyDataLabel.setText("" + stats.getOverallAccuracy() + "%");
		allMistakesDataLabel.setText("" + stats.getFailCount());
		lastTestDataLabel.setText(stats.getLastTestDate());
		lastTestAccuracyDataLabel.setText("" + stats.getLastTestAccuracy() + "%");
	}

	@Override
	public void dataAmountDelivered(Integer numer) {
		dataAmountInfoLabel.setText("Zebrano już " + numer + " pojęć!");
	}

	// MainWindow implementuje interfejs, ale nie reaguje na te 'powiadomienia'
	// z DAO, więc ciała metod sa puste.
	@Override
	public void dataIsRead(List<Dane> lista) {
	}

	@Override
	public void categoryChange() {
	}

	@Override
	public void nextQuestionGiven(Dane dane) {
	}

}

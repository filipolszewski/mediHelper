package mediHelper.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import mediHelper.controler.TestingPanelKontroler;
import mediHelper.entities.Dane;
import mediHelper.entities.Dzial;
import mediHelper.entities.Stats;
import mediHelper.listener.DatabaseListener;
import mediHelper.testing.AnswerResult;
import mediHelper.testing.TestStats;
import net.miginfocom.swing.MigLayout;

// klasa panelu testowania dziedzicząca po JPanel

@SuppressWarnings("serial")
public class TestingPanel extends JPanel implements DatabaseListener {

	private static final Font MediumFont = new Font("Verdana", Font.PLAIN, 12);
	private static final Font BigFont = new Font("Verdana", Font.PLAIN, 16);
	private static final Font SmallFont = new Font("Verdana", Font.PLAIN, 10);
	private JComboBox<Dzial> categoryBox;
	private JComboBox<String> modeBox;
	private TestingPanelKontroler kontroler;
	private JLabel modeLabel;
	private JLabel valueLabel1;
	private JLabel valueLabel2;
	private JLabel valueLabel3;
	private JLabel valueLabel4;
	private JLabel testingLabel;
	private Dane currentQuestion;
	private JButton submitButton;
	private JLabel answerResultLabel;

	public TestingPanel() {
		setLayout(new BorderLayout(5, 5));
		createControler();
		createComponents();
	}

	private void createControler() {
		kontroler = new TestingPanelKontroler(this);
	}

	// tworzenie całego widoku, paneli, obiektów Swing'owych
	private void createComponents() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(mainPanel);

		createOptionsPanel(mainPanel);
		createTestingPanel(mainPanel);
		createStatsPanel(mainPanel);
	}

	private void createStatsPanel(JPanel mainPanel) {
		JPanel statsPanel = new JPanel(new MigLayout("fillx", "10[]10", "10[]10"));
		mainPanel.add(statsPanel, BorderLayout.LINE_END);
		statsPanel.setBorder(new LineBorder(Color.GRAY));

		JLabel titleLabel = new JLabel("  Statystyki  ");
		titleLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
		titleLabel.setFont(BigFont);
		titleLabel.setForeground(Color.DARK_GRAY);
		statsPanel.add(titleLabel, "gapleft 10, gapright 10, wrap");

		createStatLabels(statsPanel);

	}

	private void createTestingPanel(JPanel mainPanel) {
		JPanel testingPanel = new JPanel(new BorderLayout(2, 2));
		mainPanel.add(testingPanel, BorderLayout.CENTER);

		answerResultLabel = new JLabel();
		answerResultLabel.setHorizontalAlignment(JLabel.CENTER);
		answerResultLabel.setFont(MediumFont);
		testingPanel.add(answerResultLabel, BorderLayout.PAGE_START);

		testingLabel = new JLabel("Wybierz opcje i naciśnij START");
		testingLabel.setHorizontalAlignment(JLabel.CENTER);
		testingLabel.setFont(BigFont);
		testingLabel.setForeground(Color.DARK_GRAY);
		testingPanel.add(testingLabel, BorderLayout.CENTER);

		JPanel answerPanel = new JPanel(new BorderLayout(5, 5));
		testingPanel.add(answerPanel, BorderLayout.PAGE_END);

		submitButton = new JButton("OK");
		submitButton.setEnabled(false);

		JTextField answerField = new JTextField(35);
		answerPanel.add(answerField, BorderLayout.CENTER);

		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AnswerResult aRes = kontroler.registerAnswer(currentQuestion, answerField.getText());
				setStats(aRes.getStats());
				if (aRes.isAnswerCorrect()) {
					answerResultLabel.setForeground(Color.GREEN);
					answerResultLabel.setText("Dobrze!");
				} else {
					answerResultLabel.setForeground(Color.RED);
					answerResultLabel.setText("<html>" + currentQuestion.getNazwapolska() + "<BR>"
							+ currentQuestion.getNazwalacinska() + "</html>");
				}
				kontroler.getNextQuestion();
				answerField.setText("");
			}
		});
		answerPanel.add(submitButton, BorderLayout.LINE_END);

	}

	private void createOptionsPanel(JPanel mainPanel) {
		JPanel optionsPanel = new JPanel(new MigLayout("wrap 1, fillx"));
		mainPanel.add(optionsPanel, BorderLayout.LINE_START);
		optionsPanel.setBorder(new LineBorder(Color.GRAY));

		createOptModePanel(optionsPanel);
		createOptCategoryPanel(optionsPanel);
		createOptLaunchPanel(optionsPanel);

	}

	private void createOptModePanel(JPanel optionsPanel) {

		JPanel modePanel = new JPanel(new MigLayout("fillx"));
		modePanel.setBorder(new EmptyBorder(15, 0, 15, 0));
		optionsPanel.add(modePanel);

		modeLabel = new JLabel("Wybierz Tryb");
		modeLabel.setFont(BigFont);
		modeLabel.setForeground(Color.DARK_GRAY);
		modePanel.add(modeLabel, "wrap");

		modeBox = new JComboBox<>();
		modeBox.setFont(MediumFont);
		modeBox.setForeground(Color.BLUE);
		modeBox.addItem("Polski -> Łacina");
		modeBox.addItem("Łacina -> Polski");
		modePanel.add(modeBox, "align center");
	}

	private void createOptCategoryPanel(JPanel optionsPanel) {

		JPanel catPanel = new JPanel(new MigLayout("fillx"));
		catPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
		optionsPanel.add(catPanel);

		JLabel categoryLabel = new JLabel("Wybierz Dział");
		catPanel.add(categoryLabel, "wrap");
		categoryLabel.setForeground(Color.DARK_GRAY);
		categoryLabel.setFont(BigFont);

		categoryBox = new JComboBox<Dzial>(new Vector<Dzial>(kontroler.getListaDzial()));
		categoryBox.setMaximumSize(new Dimension(130, 100));
		categoryBox.setForeground(Color.BLUE);
		categoryBox.setFont(SmallFont);
		catPanel.add(categoryBox);
	}

	private void createOptLaunchPanel(JPanel optionsPanel) {

		JPanel launchPanel = new JPanel(new MigLayout("fillx"));
		launchPanel.setBorder(new EmptyBorder(15, 30, 15, 30));
		optionsPanel.add(launchPanel);

		JButton launchButton = new JButton("Start");
		launchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (launchButton.getText().equals("Start")) {
					kontroler.startSession((String) modeBox.getSelectedItem(), (Dzial) categoryBox.getSelectedItem());
					kontroler.getNextQuestion();
					submitButton.setEnabled(true);
					setStats(new TestStats());
					modeBox.setEnabled(false);
					categoryBox.setEnabled(false);
					launchButton.setText("Stop");
				} else {
					kontroler.stopSession();
					launchButton.setText("Start");
					modeBox.setEnabled(true);
					categoryBox.setEnabled(true);
					submitButton.setEnabled(false);
					testingLabel.setText("Wybierz opcje i naciśnij START");
					answerResultLabel.setText("");
				}

			}
		});
		launchButton.setForeground(Color.DARK_GRAY);
		launchButton.setFont(BigFont);
		launchPanel.add(launchButton, "alignx center");

	}

	// aktualizacja panelu statystyk tstu
	protected void setStats(TestStats testStats) {
		valueLabel1.setText("" + testStats.getMainCount());
		valueLabel2.setText("" + testStats.getCorrectCount());
		valueLabel3.setText("" + testStats.getWrongCount());
		valueLabel4.setText("" + testStats.getPercentage().intValue());
	}

	private void createStatLabels(JPanel statsPanel) {

		JLabel statLabel1 = new JLabel("Ogółem: ");
		statLabel1.setFont(MediumFont);
		statsPanel.add(statLabel1, "split");

		valueLabel1 = new JLabel("--");
		valueLabel1.setFont(MediumFont);
		statsPanel.add(valueLabel1, "wrap");

		JLabel statLabel2 = new JLabel("Prawidłowo: ");
		statLabel2.setFont(MediumFont);
		statsPanel.add(statLabel2, "split");

		valueLabel2 = new JLabel("--");
		valueLabel2.setFont(MediumFont);
		statsPanel.add(valueLabel2, "wrap");

		JLabel statLabel3 = new JLabel("Błędy: ");
		statLabel3.setFont(MediumFont);
		statsPanel.add(statLabel3, "split");

		valueLabel3 = new JLabel("--");
		valueLabel3.setFont(MediumFont);
		statsPanel.add(valueLabel3, "wrap");

		JLabel statLabel4 = new JLabel("Poprawność: ");
		statLabel4.setFont(MediumFont);
		statsPanel.add(statLabel4, "split");

		valueLabel4 = new JLabel("--");
		valueLabel4.setFont(MediumFont);
		statsPanel.add(valueLabel4, "wrap");
	}

	// aktualizacja widoku po otrzymaniu kolejnych danych do testowania
	@Override
	public void nextQuestionGiven(Dane dane) {
		currentQuestion = dane;
		if (modeBox.getSelectedItem().toString().equals("Polski -> Łacina")) {
			testingLabel.setText(dane.getNazwapolska());
		} else {
			testingLabel.setText(dane.getNazwalacinska());
		}
	}

	@Override
	public void dataAmountDelivered(Integer numer) {
	}

	@Override
	public void dataIsRead(List<Dane> lista) {
	}

	@Override
	public void categoryChange() {
	}

	@Override
	public void onStatsDelivered(Stats stats) {
	}

}

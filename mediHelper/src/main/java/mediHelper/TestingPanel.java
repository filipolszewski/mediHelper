package mediHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import mediHelper.entities.Dzial;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class TestingPanel extends JPanel {
	
	private static final Font MediumFont = new Font("SansSerif", Font.BOLD, 12);
	private static final Font BigFont = new Font("SansSerif", Font.BOLD, 16);
	private static final Font SmallFont = new Font("SansSerif", Font.BOLD, 10);
	private JComboBox<Dzial> categoryBox;
	private JComboBox<String> modeBox;
	private TestingPanelKontroler kontroler;

	public TestingPanel() {
		setLayout(new BorderLayout(5, 5));
		createControler();
		createComponents();
	}

	private void createControler() {
		kontroler = new TestingPanelKontroler(this);
	}

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
		testingPanel.setBorder(new LineBorder(Color.GRAY));
		mainPanel.add(testingPanel, BorderLayout.CENTER);
		
		JLabel testingLabel = new JLabel("Wybierz opcje i naciśnij START");
		testingLabel.setHorizontalAlignment(JLabel.CENTER);
		testingLabel.setFont(BigFont);
		testingLabel.setForeground(Color.DARK_GRAY);
		testingPanel.add(testingLabel, BorderLayout.CENTER);
		
		JPanel answerPanel = new JPanel(new BorderLayout(5, 5));
		testingPanel.add(answerPanel, BorderLayout.PAGE_END);
		
		JButton submitButton = new JButton("OK");
		answerPanel.add(submitButton, BorderLayout.LINE_END);
		
		JTextField answerField = new JTextField(35);
		answerPanel.add(answerField, BorderLayout.CENTER);
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

		JLabel modeLabel = new JLabel("Wybierz Tryb");
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
		launchPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
		optionsPanel.add(launchPanel);

		JButton launchButton = new JButton("Start");
		launchButton.setForeground(Color.DARK_GRAY);
		launchButton.setFont(BigFont);
		launchPanel.add(launchButton, "alignx center");

	}
	
	private void createStatLabels(JPanel statsPanel) {
		
		JLabel statLabel1 = new JLabel("Ogółem: ");
		statLabel1.setFont(MediumFont);
		statsPanel.add(statLabel1, "split");
		
		JLabel valueLabel1 = new JLabel("27");
		valueLabel1.setFont(MediumFont);
		statsPanel.add(valueLabel1, "wrap");
		
		JLabel statLabel2 = new JLabel("Prawidłowo: ");
		statLabel2.setFont(MediumFont);
		statsPanel.add(statLabel2, "split");
		
		JLabel valueLabel2 = new JLabel("23");
		valueLabel2.setFont(MediumFont);
		statsPanel.add(valueLabel2, "wrap");
		
		JLabel statLabel3 = new JLabel("Błędy: ");
		statLabel3.setFont(MediumFont);
		statsPanel.add(statLabel3, "split");
		
		JLabel valueLabel3 = new JLabel("4");
		valueLabel3.setFont(MediumFont);
		statsPanel.add(valueLabel3, "wrap");
		
		JLabel statLabel4 = new JLabel("Poprawność: ");
		statLabel4.setFont(MediumFont);
		statsPanel.add(statLabel4, "split");
		
		JLabel valueLabel4 = new JLabel("85%");
		valueLabel4.setFont(MediumFont);
		statsPanel.add(valueLabel4, "wrap");
	}

}

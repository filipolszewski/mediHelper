package mediHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements DatabaseListener {

	private static final String TITLE = "MediHelper";
	private MediKontroler controler;
	private JLabel infoLabel;

	public MainWindow() {
		setSize(250, 215);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(TITLE);
		createComponents();
		createControler();
	}

	private void createControler() {
		controler = new MediKontroler(this);
		controler.doStart();
	}

	private void createComponents() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		createPanels(mainPanel);
		add(mainPanel);
	}

	private void createPanels(JPanel mainPanel) {
		createWelcomePanel(mainPanel);
		createButtonPanel(mainPanel);
		createInfoPanel(mainPanel);
	}

	private void createInfoPanel(JPanel mainPanel) {
		JPanel infoPanel = new JPanel(new BorderLayout());
		infoPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		infoLabel = new JLabel("");
		infoLabel.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));
		infoPanel.add(infoLabel, BorderLayout.CENTER);
		
		mainPanel.add(infoPanel, BorderLayout.PAGE_END);
	}

	private void createWelcomePanel(JPanel mainPanel) {
		JPanel welcomePanel = new JPanel(new BorderLayout());

		JLabel welcomeLabel = new JLabel("Witaj w MediHelper!", JLabel.CENTER);
		Font font = welcomeLabel.getFont();
		Font boldFont = new Font(font.getFontName(), Font.BOLD, 20);
		welcomeLabel.setFont(boldFont);
		welcomeLabel.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));

		welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

		mainPanel.add(welcomePanel, BorderLayout.PAGE_START);
	}

	private void createButtonPanel(JPanel mainPanel) {
		JPanel buttonPanel = new JPanel(new MigLayout("fillx"));

		buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JButton listButton = new JButton("Listy");
		listButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.showListChoice();
			}
		});
		buttonPanel.add(listButton, "w 50%, wrap, alignx center");

		JButton testButton = new JButton("Testy");
		buttonPanel.add(testButton, "w 50%, wrap, alignx center");

		JButton infiniteButton = new JButton("Tryb Infinite");
		buttonPanel.add(infiniteButton, "w 50%, wrap, alignx center");

		JButton addButton = new JButton("Dodaj Do Bazy");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.doAddDialog();
			}
		});
		buttonPanel.add(addButton, "w 50%, wrap, alignx center");

		mainPanel.add(buttonPanel, BorderLayout.CENTER);
	}

	@Override
	public void dataAmount(Integer numer) {
		if (numer < 5) {
			infoLabel.setText("Posiadam już " + numer + " pojęcia!");
		} else {
		infoLabel.setText("Posiadam już " + numer + " pojęć!");
		}
		
	}
}

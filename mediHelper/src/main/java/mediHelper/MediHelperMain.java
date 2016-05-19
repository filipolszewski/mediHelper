package mediHelper;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import mediHelper.view.MainWindow;

// Klasa Main. Tworzy główne okno i dbając o bezpieczeństwo wątków pokazuje je na ekranie.

public class MediHelperMain {
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		final MainWindow mainWindow = new MainWindow();
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				mainWindow.setVisible(true);
			}
		});
		
	}

}

package Main;

import javax.swing.SwingUtilities;
import View.MainWindow;

public class Main {
		
	public static void main(String[] args) {		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(); 
			}
		});
	}
}

package Main;

import javax.swing.SwingUtilities;

import Logic.AlgoritmoGenetico;
import View.MainWindow;

public class Main {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(new AlgoritmoGenetico()); 
			}
		});
	}
}

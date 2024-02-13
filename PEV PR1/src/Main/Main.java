package Main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import View.ControlPanel;
import View.MainWindow;

public class Main {
	
	/*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Java Project");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(new ControlPanel());
            frame.setVisible(true);
        });
    }*/
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(); 
			}
		});
	}
}

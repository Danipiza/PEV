/**
 * 
 */
package main;

import javax.swing.SwingUtilities;
import view.MainWindow;

/**
 * @author DavidSG & DannyP39
 
 * ENG: Class for Main
 * ESP: Clase para Main
 */

public class Main {
	
	/**
	 * 
	 * @param args
	
	 * ENG: Method for executing the program.
	 * ESP: Funcion para ejecutar el programa.
	 */
	public static void main(String[] args) {		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(); 
			}
		});
	}
}


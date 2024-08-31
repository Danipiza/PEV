/**
 * 
 */
package view;

import javax.swing.JFrame;

/**
 * @author DavidSG & DannyP39
 
 * ENG: Class for Main Window.
 * ESP: Clase para la ventana principal.
 */
public class MainWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	
	 * ENG: Class constructor.
	 * ESP: Constructor de la clase. Crea el panel
	 */
	public MainWindow() {			
		// ENG: Writes the name of the GUI.
		// ESP: Escribe el nombre de la interfaz.
		super("Algoritmos Geneticos");
		
		// ENG: Allows you to close the interface.
		// ESP: Permite cerrar la interfaz.
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		// ENG: Set the interface size.
		// ESP: Configura el tamaño de la interfaz.
	    this.setSize(800, 600);
	    // ENG: Creates the controles of the main panel.
		// ESP: Crea el controlador del panel principal.
	    this.add(new ControlPanel());
	    
		// ENG: Set to visible the interface.
		// ESP: Configura para que se vea la interfaz.
	    this.setVisible(true);			
    }	
	
	
}


package View;

import javax.swing.JFrame;

public class MainWindow extends JFrame{

	private static final long serialVersionUID = 1L;
			
	ControlPanel ctrl_p;

	
	public MainWindow() {		
		super("Algoritmos Geneticos");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(1200, 600);
	    this.add(new ControlPanel());
	    this.setVisible(true);
	    
	}
	
	@SuppressWarnings("unused")
	private void initGUI() {		
		ctrl_p= new ControlPanel();
		this.add(ctrl_p);		
		
		this.pack();
		this.setVisible(true);
	}
	
}

package View;



import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
//import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;




public class MainWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	//private Border border = BorderFactory.createLineBorder(Color.black, 3);
		
	ControlPanel ctrl_p;

	
	public MainWindow() {
		/*super("Algoritmos Geneticos");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set the jframe size and location, and make it visible
	    super.setPreferredSize(new Dimension(1500, 1000));
	    super.pack();
	    super.setLocationRelativeTo(null);
	    super.setVisible(true);*/
		
		
		super("Algoritmos Geneticos");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(800, 600);
	    this.add(new ControlPanel());
	    this.setVisible(true);
	    
		
		//initGUI();
	}
	
	private void initGUI() {
		
		/*JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);		
		
		mainPanel.add(new ControlPanel(), BorderLayout.PAGE_START);
		
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);*/
		
		
		///JPanel main_panel=new JPanel(new GridBagLayout());
		///this.setContentPane(main_panel);	
		///GridBagConstraints c = new GridBagConstraints();
		///c.insets = new Insets(5, 5, 5, 5); // Padding
		///c.anchor = GridBagConstraints.CENTER; // Align components to the left
		
		//ctrl_p= new ControlPanel(new GridBagLayout());
		ctrl_p= new ControlPanel();
		//this.getContentPane().add(ctrl_p, BorderLayout.WEST); // Align panel to the left
		
		///main_panel.add(ctrl_p, c);
		this.add(ctrl_p);
		
		
		/*c.anchor = GridBagConstraints.EAST; // Align components to the left
		main_panel.add(new ControlPanel(), c);*/
		
		//JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		//main_panel.add(viewsPanel, BorderLayout.CENTER);		
		
		
		/*main_panel=new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5); // Padding
		c.anchor = GridBagConstraints.NORTHWEST; // Align components to the left*/
		
		//AG.ejecuta(ctrl_p.getValores());
		
		//setValores(ctrl_p.getValores());
		
		this.pack();
		this.setVisible(true);
	}
	
}

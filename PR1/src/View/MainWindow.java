package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Logic.AlgoritmoGenetico;



public class MainWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	//private Border border = BorderFactory.createLineBorder(Color.black, 3);
	
	AlgoritmoGenetico AG;
	
	
	
	

	
	public MainWindow(AlgoritmoGenetico AG) {
		//super("Algoritmos Geneticos");
		this.AG=AG;
		
		
		
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);		
		
		// BOTON DE RUN
		/*JButton run = new JButton("Normal");
		run.setBounds(735, 850, 90, 64);
		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//run
			}
		});;
		run.setOpaque(true);
		run.setContentAreaFilled(false);
		run.setBorderPainted(false);
		run.setIcon(loadImage("resources/Games/run.png"));
		tablero.add(run);*/
		
		
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
}

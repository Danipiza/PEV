package View;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class ControlPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JButton run_button;
	
	private JTextField tam_poblacion;
	private JTextField generaciones;
	private JTextField prob_cruce;
	private JTextField prob_mut;
	private JTextField precision;
	
	
	private JSpinner spinner;
	
	public ControlPanel() {
		initGUI();
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		JToolBar mainP = new JToolBar();
		this.add(mainP);	
		
		JToolBar frame = new JToolBar();
		mainP.add(frame); 
		
		
		run_button = new JButton();
		run_button.setToolTipText("Run button"); 
		run_button.setIcon(loadImage("resources/icons/open.png"));
		run_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {										
				// RUN
			}
		});	
		
		JLabel label=new JLabel("");
		
		/*
		// IDENTIFIER PANEL (PARA QUE QUEDE BIEN COMO EN LA FOTO) --------------------------------
		JPanel identifierPanel = new JPanel();
		identifierPanel.setAlignmentX(LEFT_ALIGNMENT);
		mainPanel.add(identifierPanel);
		
		
		JLabel identifier = new JLabel("Identifier:");
		label.setAlignmentX(LEFT_ALIGNMENT);
		identifierPanel.add(identifier);
		
		vehicles2 = new JTextField(10);
		
		// vehiclesModel = new DefaultComboBoxModel<>();
		// vehicles = new JComboBox<>(vehiclesModel);
		
		JLabel identifierSpace = new JLabel("                 ");
		
		identifierPanel.add(identifierSpace);
		
		identifierPanel.add(vehicles2);
		*/
		
		
		
		frame.add(run_button);	
		
	}
	
	protected ImageIcon loadImage(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path));
	}

}

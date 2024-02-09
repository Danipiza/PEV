/*package View;


import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class ValoresDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private int estado; // 0 o 1	
	
	private int num;	
	
	private JButton confirm_button;
	private JButton cancel_button;
	
	private JTextField[] valores_text;
	
	public ValoresDialog(int num) {		
		super();		
		this.num=num;
		valores_text=new JTextField[num];	
		for(int i=0;i<num;i++) {
			valores_text[i]= new JTextField(10);
		}
		initGUI();		
	}
	
	private void initGUI() {
		estado = 0;		
		
		//this.setSize(5000, 5000);
		setTitle("Add vals");
		JPanel main_panel=new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5); // Padding
		c.anchor = GridBagConstraints.NORTH; // Align components to the left
				
		this.add(main_panel);
		
		
		
		

		confirm_button = new JButton();
		confirm_button.setToolTipText("Confirm button"); 
		confirm_button.setIcon(loadImage("resources/icons/confirm.png"));
		confirm_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				estado = 1;
				ValoresDialog.this.setVisible(false);
				// Enviar datos
			}
		});
		
		cancel_button = new JButton();
		cancel_button.setToolTipText("Cancel button"); 
		cancel_button.setIcon(loadImage("resources/icons/cancel.png"));
		cancel_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				estado = 0;
				ValoresDialog.this.setVisible(false);
			}
		});
		
		
		c.gridx = 0; c.gridy = 0;	
		for(int i=0;i<num;i++) {
			main_panel.add(new JLabel("Valor"+(i+1)+": "), c);	
			c.gridy++;
		}		

		c.gridx++; c.gridy = 0;
		for(int i=0;i<num;i++) {
			main_panel.add(valores_text[i], c);
			c.gridy++;
		}
		
		c.gridy++; main_panel.add(confirm_button, c);
		c.gridx++; main_panel.add(cancel_button, c);
		
		

		setPreferredSize(new Dimension(300, 160+40*(num-1)));
		pack();
		setResizable(false);
		setVisible(false);
		
	}
	
	public int open(int num) {
		setLocation(getParent().getLocation().x + 300, getParent().getLocation().y + 360);

		setVisible(true);
		return estado;
	}
	
	
	protected ImageIcon loadImage(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path));
	}

}
*/
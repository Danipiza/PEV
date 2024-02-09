package View;


import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Logic.AlgoritmoGenetico;
import Logic.Valores;

import java.awt.GridBagConstraints;

public class ControlPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	AlgoritmoGenetico AG;
	
	JPanel main_panel;	
	
	private JButton run_button;
	private JButton valores_button;
	
	private JTextField tam_poblacion;
	private JTextField generaciones;
	private JTextField prob_cruce;
	private JTextField prob_mut;
	private JTextField precision;
	
	private JComboBox<String> funcion_CBox;
	private JComboBox<String> seleccion_CBox;
	private JComboBox<String> cruce_CBox;
	private JComboBox<String> mutacion_CBox;
	
	private Valores valores;
	
	public ControlPanel() {
		tam_poblacion = new JTextField(15);
		generaciones = new JTextField(15);
        prob_cruce = new JTextField(15);
        prob_mut = new JTextField(15);
        precision = new JTextField(15);
        
        AG = new AlgoritmoGenetico();
        
		initGUI();
	}
	
	private void initGUI() {
		
		main_panel=new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5); // Padding
		c.anchor = GridBagConstraints.WEST; // Align components to the left
				
		this.add(main_panel);
		
		String[] funciones = {"F1: Calibracion y Prueba", 
							"F2: Mishra Bird", 
							"F3: Holder table",
							"F4: Michalewicz (Binaria)",
							"F5: Michalewicz (Real)"
							};
		String[] seleccion = {"Ruleta", 
							"Torneo Deterministico", 
							"Torneo Probabilistico",
							"Estocastico Universal",
							"Truncamiento",
							"Restos",
							};
		String[] cruce= {"Mono-Punto",
						"Uniforme"
						};
		String[] mutacion = {"Básica"
							};
		
		funcion_CBox = new JComboBox<>(funciones);	
		seleccion_CBox = new JComboBox<>(seleccion);	
		cruce_CBox = new JComboBox<>(cruce);	
		mutacion_CBox = new JComboBox<>(mutacion);	
		
		
		run_button = new JButton();
		run_button.setToolTipText("Run button"); 
		run_button.setIcon(loadImage("resources/icons/run.png"));
		run_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				run();				
			}
		});
		
		valores_button = new JButton();
		valores_button.setToolTipText("Valores button"); 
		//valores_button.setIcon(loadImage("resources/icons/run.png"));
		valores_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				// dialog		
			}
		});
		
		
		
				
		
		c.gridx = 0; c.gridy = 0;		
		main_panel.add(new JLabel("Tam. Poblacion:"), c);		
		c.gridy++; main_panel.add(new JLabel("Num. Generaciones:"), c);
		c.gridy++; main_panel.add(new JLabel("Metodo de Seleccion:"), c);
		c.gridy++; main_panel.add(new JLabel("Metodo de Cruce:"), c);
		c.gridy++; main_panel.add(new JLabel("Prob. Cruce:"), c);		
		c.gridy++; main_panel.add(new JLabel("Metodo de Mutacion:"), c);
		c.gridy++; main_panel.add(new JLabel("Prob. Mutacion:"), c);		
		c.gridy++; main_panel.add(new JLabel("Precision:"), c);
		c.gridy++; main_panel.add(new JLabel("Funcion:"), c);

			

		c.gridx++; c.gridy = 0;
		main_panel.add(tam_poblacion, c);
		c.gridy++; main_panel.add(generaciones, c);
		c.gridy++; main_panel.add(seleccion_CBox, c);
		c.gridy++; main_panel.add(cruce_CBox, c);
		c.gridy++; main_panel.add(prob_cruce, c);
		c.gridy++; main_panel.add(mutacion_CBox, c);
		c.gridy++; main_panel.add(prob_mut, c);
		c.gridy++; main_panel.add(precision, c);
		c.gridy++; main_panel.add(funcion_CBox, c);
		
		c.anchor = GridBagConstraints.SOUTH; // Align components to the left
		c.gridy++; main_panel.add(run_button, c);
		c.gridy++; main_panel.add(valores_button, c);
	}
	
	private void run() {
		setValores();
		AG.ejecuta(valores);		
	}
	
	
	
	private void setValores() {		
		valores=new Valores(Integer.parseInt(tam_poblacion.getText()),
							Integer.parseInt(generaciones.getText()),
							seleccion_CBox.getSelectedIndex(),
							cruce_CBox.getSelectedIndex(),
							Double.parseDouble(prob_cruce.getText()),
							mutacion_CBox.getSelectedIndex(),
							Double.parseDouble(prob_mut.getText()),
							Double.parseDouble(precision.getText()),
							funcion_CBox.getSelectedIndex());		
	}
	
	public Valores getValores() {
		return valores;
	}
	
	/*private void funcion(int x) {
		switch (x) {
		case 1:
			funcion1Class();
			break;
		case 2:
			funcion2Class();	
			break;
		case 3:
			funcion3Class();
			break;
		case 4:
			funcion4Class();
			break;
		case 5:
			funcion5Class();
			break;
		default:
			break;
		}
	}
	
	protected void funcion1Class() {
		
	}
	
	protected void funcion2Class() {
		
	}

	protected void funcion3Class() {
		
	}
	
	protected void funcion4Class() {
		
	}
	
	protected void funcion5Class() {
		
	}*/
	
	protected ImageIcon loadImage(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path));
	}

}

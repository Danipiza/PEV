package View;

import org.math.plot.*;

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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import Logic.AlgoritmoGenetico;
import Model.Valores;
import Model.ValoresIndividuosGrafico;
import Utils.Pair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

public class ControlPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	AlgoritmoGenetico AG;
		
	private JButton run_button;
	
	private JTextField tam_poblacion;
	private JTextField generaciones;
	private JTextField prob_cruce;
	private JTextField prob_mut;
	private JTextField precision;
	
	private JComboBox<String> funcion_CBox;
	private JComboBox<String> seleccion_CBox;
	private JComboBox<String> cruce_CBox;
	private JComboBox<String> mutacion_CBox;
	
	private JSpinner genes_spinner;
	
	private Plot2DPanel plot2D;
	private Plot3DPanel plot3D;
	
	
	private Valores valores;
	
	public ControlPanel() {
		tam_poblacion = new JTextField(15);
		generaciones = new JTextField(15);
        prob_cruce = new JTextField(15);
        prob_mut = new JTextField(15);
        precision = new JTextField(15);
        genes_spinner = new JSpinner();
        
        AG = new AlgoritmoGenetico(this); // MEJORAR IMPLEMENTACION
        
		initGUI();
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel2D();        

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
	}
	
	private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        leftPanel.setPreferredSize(new Dimension(325, 600));
		
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
 
											 // Initial value, min, max, step
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(2, 1, 10, 1); 
        genes_spinner.setModel(spinnerModel);		
		
		run_button = new JButton();
		run_button.setToolTipText("Run button"); 
		run_button.setIcon(loadImage("resources/icons/run.png"));
		run_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				run();				
			}
		});		
							
		gbc.anchor=GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0; // Espacio antes de las JLabels para que se vea mejor la GUI
        leftPanel.add(new JLabel("  Tam. Poblacion:"), gbc);		
		gbc.gridy++; leftPanel.add(new JLabel("  Num. Generaciones:"), gbc);
		gbc.gridy++; leftPanel.add(new JLabel("  Metodo de Seleccion:"), gbc);
		gbc.gridy++; leftPanel.add(new JLabel("  Metodo de Cruce:"), gbc);
		gbc.gridy++; leftPanel.add(new JLabel("  Prob. Cruce:"), gbc);		
		gbc.gridy++; leftPanel.add(new JLabel("  Metodo de Mutacion:"), gbc);
		gbc.gridy++; leftPanel.add(new JLabel("  Prob. Mutacion:"), gbc);		
		gbc.gridy++; leftPanel.add(new JLabel("  Precision:"), gbc);
		gbc.gridy++; leftPanel.add(new JLabel("  Funcion:"), gbc);
		gbc.gridy++; leftPanel.add(new JLabel("  d:"), gbc);
			

		gbc.gridx++; gbc.gridy = 0;
		leftPanel.add(tam_poblacion, gbc);
		gbc.gridy++; leftPanel.add(generaciones, gbc);
		gbc.gridy++; leftPanel.add(seleccion_CBox, gbc);
		gbc.gridy++; leftPanel.add(cruce_CBox, gbc);
		gbc.gridy++; leftPanel.add(prob_cruce, gbc);
		gbc.gridy++; leftPanel.add(mutacion_CBox, gbc);
		gbc.gridy++; leftPanel.add(prob_mut, gbc);
		gbc.gridy++; leftPanel.add(precision, gbc);
		gbc.gridy++; leftPanel.add(funcion_CBox, gbc);
		gbc.gridy++; leftPanel.add(genes_spinner, gbc);
		
		gbc.anchor = GridBagConstraints.SOUTH; // Align components to the left
		gbc.gridy++; leftPanel.add(run_button, gbc);
        
              
        return leftPanel;
    }

    private JPanel createRightPanel3D() {
    	JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        rightPanel.setPreferredSize(new Dimension(475, 600));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1.0; // Mas espacio horizontal 
        gbc.weighty = 1.0; // // Mas espacio vertical
        gbc.fill = GridBagConstraints.BOTH; // Rellena el espacio disponible 
    	
        plot3D = new Plot3DPanel();    	
    	
        plot3D.getAxis(0).setLabelText("X1");
        plot3D.getAxis(1).setLabelText("X2");
        plot3D.getAxis(2).setLabelText("Fitness");
        //plot.removeAllPlots();        
        rightPanel.add(plot3D, gbc);
        
        return rightPanel;
    }
    
    private JPanel createRightPanel2D() {
    	JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        rightPanel.setPreferredSize(new Dimension(475, 600));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1.0; // Mas espacio horizontal 
        gbc.weighty = 1.0; // // Mas espacio vertical
        gbc.fill = GridBagConstraints.BOTH; // Rellena el espacio disponible 
    	
        plot2D = new Plot2DPanel();    	
    	
        plot2D.getAxis(0).setLabelText("Generacion");
        plot2D.getAxis(1).setLabelText("Fitness");
    	
        //plot.removeAllPlots();        
        rightPanel.add(plot2D, gbc);
        
        return rightPanel;
    }
	
    public void actualiza_Grafico(double[][] vals, Pair<Double,Double> interval) {
    	plot2D.removeAllPlots();
    	
    	double[] x = new double[vals[0].length];
    	for(int i=0;i<vals[0].length;i++) {
    		x[i]=i;
    	}
        //double[] y1 = {2, 3, 4, 5, 6};
        //double[] y2 = {1, 4, 3, 2, 5};
        //double[] y3 = {3, 2, 5, 4, 1};

        // Add the lines to the plot with different colors
        plot2D.addLinePlot("Mejor Absoluto", x, vals[0]);
        plot2D.addLinePlot("Mejor de la Generacion", x, vals[1]);
        plot2D.addLinePlot("Media", x, vals[2]);

        // Customize the plot (optional)
        plot2D.setAxisLabels("X Axis", "Y Axis");
        plot2D.setAxisLabel(0, "X Axis");
        plot2D.setAxisLabel(1, "Y Axis");
        plot2D.setFixedBounds(1, interval.getKey(), interval.getValue()); // Fix Y-axis bounds
        
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
							funcion_CBox.getSelectedIndex(),
							(int)genes_spinner.getValue());		
	}
	
	public Valores getValores() { return valores; }	
	
	
	
	
	protected ImageIcon loadImage(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path));
	}

	
	/*protected void valoresClass() {		
		int num=(int)valores_spinner.getValue();		
		ValoresDialog valores_dialog = new ValoresDialog(num); // (Frame) this.getTopLevelAncestor()		
		
		int estado = valores_dialog.open(num);		
		if (estado == 1) {			
			
		} 
	}*/
}

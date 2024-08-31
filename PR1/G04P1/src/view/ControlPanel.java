/**
 * 
 */
package view;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import logic.AlgoritmoGenetico;
import model.Individuo;
import model.Valores;
//import utils.BooleanSwitch;
import utils.Pair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

/**
 * @author DavidSG & DannyP39
 
 * ENG: Class for controlling the main panel.
 * ESP: Clase para el controlador del panel principal.
 */
public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// ENG: Variable with the main loop and algorithm configuration.
	// ESP: Variable con el bucle principal y la configuracion del algoritmo.
	private AlgoritmoGenetico AG;

	// ENG: Button to run the program.
	// ESP: Buton para ejecutar el programa.
	private JButton run_button;

	// ENG: Text fields to configure variables.
	// ESP: Campos de texto para configurar las variables.
	private JTextField tam_poblacion;
	private JTextField generaciones;
	private JTextField prob_cruce;
	private JTextField prob_mut;
	private JTextField precision;
	private JTextField elitismo;

	// ENG: Combo boxes to select the methods.
	// ESP: Cuadros combinados para seleccionar los metodos.
	private JComboBox<String> funcion_CBox;
	private JComboBox<String> seleccion_CBox;
	private JComboBox<String> cruce_CBox;
	private JComboBox<String> mutacion_CBox;

	// ENG: Spinner to select the number of genes.
	// ESP: Spinner para seleccionar el numero de genes.
	private JSpinner genes_spinner;
	
	// ENG: Text area where the results or execution errors are shown.
	// ESP: Area de texto donde muestran los resultados o errores de ejecucion.
	private JTextArea text_area;
	
	// ENG: Two dimensional graph with the results.
	// ESP: Grafico de dos dimensiones con los resultados.
	private Plot2DPanel plot2D;
	
	
	// ENG: Class with the values ​​of the variables.
	// ESP: Clase con los valores de las variables.
	private Valores valores;

	/**
	 * 
	
	 * ENG: Class constructor.
	 * ESP: Constructor de la clase.
	 */
	public ControlPanel() {
		this.tam_poblacion	=new JTextField("100", 15);
		this.generaciones 	=new JTextField("100", 15);
		this.prob_cruce 	=new JTextField("0.6", 15);
		this.prob_mut 		=new JTextField("0.05", 15);
		this.precision 		=new JTextField("0.001", 15);
		this.elitismo 		=new JTextField("0", 15);
		this.genes_spinner 	=new JSpinner();

		AG=new AlgoritmoGenetico(this); 
		
		init_GUI();
	}

	/**
	 * 
	
	 * ENG: Method to initialize the interface sub-panels.
	 * ESP: Funcion para inicializar los sub-paneles de la interfaz.
	 */
	private void init_GUI() {
		setLayout(new BorderLayout());
		
		// ENG: The left panel contains the execution variables.
		// 		The right one contains the graph of the results.
		// ESP: El panel de la izquierda contiene las variables de ejecucion.
		//		El de la derecha contiene la grafica de los resultados.
		JPanel leftPanel =crea_panel_izquiedo();
		JPanel rightPanel=crea_panel_derecho();
		
		// ENG: Adds the subpanels to the main panel.
		// ESP: Añade los subpaneles al panel principal.
		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);
	}
	
	/**
	 * 
	 * @return JPanel
	
	 * ENG: Method for creating the left panel.
	 * ESP: Funcion para crear el panel de la izquierda.
	 */
	private JPanel crea_panel_izquiedo() {
		JPanel leftPanel=new JPanel(new GridBagLayout());
		leftPanel.setPreferredSize(new Dimension(335, 600));
		
		// ENG: GridBag to align all components efficiently.
		// ESP: GridBag para alinear todos los componentes de manera eficiente.
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.insets=new Insets(5, 5, 5, 5);
		
		
		// ENG: Texts of the combo boxes.
		// ESP: Textos de los cuadros combinados.
		String[] funciones={"F1: Calibracion y Prueba",
							"F2: Mishra Bird",
							"F3: Holder table",
							"F4: Michalewicz (Binaria)",
							"F5: Michalewicz (Real)"
		};
		String[] seleccion={"Ruleta",
							"Torneo Deterministico",
							"Torneo Probabilistico",
							"Estocastico Universal",
							"Truncamiento",
							"Ranking",
							"Restos",
		};
		String[] cruce={"Mono-Punto",
						"Uniforme",
						"Aritmetico",
						"BLX"
		};
		String[] mutacion = { "Básica"
		};

		
		// ENG: Initializes the combo boxes.
		// ESP: Inicializa los cuadros combinados.
		funcion_CBox	=new JComboBox<>(funciones);
		seleccion_CBox	=new JComboBox<>(seleccion);
		cruce_CBox		=new JComboBox<>(cruce);
		mutacion_CBox	=new JComboBox<>(mutacion);
		
		// ENG: Initializes the text area.
		// ESP: Inicializa el area de texto.
		text_area=new JTextArea(2, 2);
		text_area.append("Esperando una ejecucion...");

		// ENG: Initialize the gene number spinner.
		// ESP: Inicializa el spinner de numero de genes.
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(2, 1, 10, 1);
		genes_spinner.setModel(spinnerModel);
		
		// ENG: Initializes and adds the run button action.
		// ESP: Inicializa y añade la accion del buton de ejecucion.
		run_button=new JButton();
		run_button.setToolTipText("Run button");
		// ENG: Button image.
		// ESP: Imagen del boton.
		run_button.setIcon(load_image("resources/icons/run.png")); 
		// ENG: Button action.
		// ESP: Accion del buton.
		run_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tmp=Integer.parseInt(elitismo.getText());
				// ENG: Executes the algorithm if the elitism percentage is valid.
				// ESP: Ejecuta el algoritmo si el porcentaje de elitismo es valido.
				if(tmp<0||tmp>100) actualiza_fallo("Elitismo porcentaje");
				else run();
			}
		});
		
		// ENG: Initializes the GridBag values.
		// ESP: Inicializa los valores del GridBag.
		gbc.anchor=GridBagConstraints.WEST;
		gbc.gridx=0;
		gbc.gridy=0; 
		
		
		// ENG: Adds the components to the GridBag.
		// ESP: Añade los componentes al GridBag.
		leftPanel.add(new JLabel("  Tam. Poblacion:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Num. Generaciones:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Metodo de Seleccion:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Metodo de Cruce:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Prob. Cruce:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Metodo de Mutacion:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Prob. Mutacion:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Precision:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Funcion:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  d:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Elitismo:"), gbc);
		gbc.anchor=GridBagConstraints.EAST;
		gbc.gridy++;
		leftPanel.add(new JLabel("Valor optimo:  "), gbc);
		gbc.anchor=GridBagConstraints.WEST;

		gbc.gridx++;
		gbc.gridy=0;
		
		leftPanel.add(tam_poblacion, gbc);
		gbc.gridy++;
		leftPanel.add(generaciones, gbc);
		gbc.gridy++;
		leftPanel.add(seleccion_CBox, gbc);
		gbc.gridy++;
		leftPanel.add(cruce_CBox, gbc);
		gbc.gridy++;
		leftPanel.add(prob_cruce, gbc);
		gbc.gridy++;
		leftPanel.add(mutacion_CBox, gbc);
		gbc.gridy++;
		leftPanel.add(prob_mut, gbc);
		gbc.gridy++;
		leftPanel.add(precision, gbc);
		gbc.gridy++;
		leftPanel.add(funcion_CBox, gbc);
		gbc.gridy++;
		leftPanel.add(genes_spinner, gbc);
		gbc.gridy++;
		leftPanel.add(elitismo, gbc); 
		gbc.gridy++;
		leftPanel.add(text_area, gbc);		
		
		gbc.anchor=GridBagConstraints.SOUTH; 
		gbc.gridy++;
		leftPanel.add(run_button, gbc);

		return leftPanel;
	}

	
	/**
	 * 
	 * @return
	
	 * ENG: Method for creating the right panel.
	 * ESP: Funcion para crear el panel de la derecha.
	 */
	private JPanel crea_panel_derecho() {
		JPanel rightPanel=new JPanel(new GridBagLayout());
		rightPanel.setPreferredSize(new Dimension(465, 600));
		
		// ENG: GridBag to align all components efficiently.
		// ESP: GridBag para alinear todos los componentes de manera eficiente.
		GridBagConstraints gbc = new GridBagConstraints();
		// ENG: Initializes the GridBag values.
		// ESP: Inicializa los valores del GridBag.
		gbc.gridx=0;
		gbc.gridy=0;
		// ENG: More horizontal and vertical space.
		// ESP: Mas espacio horizontal y vertical
		gbc.weightx=1.0; 
		gbc.weighty=1.0; 
		// ENG: Fill the available space.
		// ESP: Rellena el espacio disponible.
		gbc.fill=GridBagConstraints.BOTH; 
		
		// ENG: Initialize the panel graph.
		// ESP: Inicializa el grafico del panel.
		plot2D=new Plot2DPanel();
		// ENG: Add the names of the axes.
		// ESP: Añade los nombres de los ejes.
		plot2D.getAxis(0).setLabelText("Generacion");
		plot2D.getAxis(1).setLabelText("Fitness");


		rightPanel.add(plot2D, gbc);
		return rightPanel;
	}
	
	/**
	 * 
	 * @param vals
	 * @param interval
	 * @param mejor_ind
	
	 * ENG: Method for updating the graphic.
	 * ESP: Funcion para actualizar el grafico.
	 */
	public void actualiza_grafico(double[][] vals, Pair<Double, Double> interval, Individuo mejor_ind) {
		plot2D.removeAllPlots();

		double[] x=new double[vals[0].length];
		for (int i=0;i<vals[0].length;i++) x[i]=i;
		
		// ENG: Add the result functions.
		// ESP: Añade las funciones resultados.
		plot2D.addLinePlot("Mejor Absoluto", x, vals[0]);
		plot2D.addLinePlot("Mejor de la Generacion", x, vals[1]);
		plot2D.addLinePlot("Media", x, vals[2]);

		// ENG: Configure the axis names again.
		// ESP: Configura nuevamente los nombres de los ejes.
		plot2D.getAxis(0).setLabelText("Generacion");
		plot2D.getAxis(1).setLabelText("Fitness");
		plot2D.setFixedBounds(1, interval.get_first(), interval.get_second()); // Fix Y-axis bounds

		// ENG: Add the legend in the south of the graph.
		// ESP: Añade la leyenda en el sur del grafico.
		plot2D.addLegend("SOUTH");
		
		// ENG: Change the output text.
		// ESP: Cambia el texto de salida.
		String texto_salida="Fitness: "+mejor_ind.fitness+"\n";
		int cont=1;
		for(double cromosoma: mejor_ind.fenotipo) {
			texto_salida+="Variable "+(cont++)+": "+cromosoma + "\n";
		}
		
		text_area.setText(texto_salida);

	}
	
	/**
	 * 
	 * @param s
	
	 * ENG: Method for updating the graph in case of failure.
	 * ESP: Funcion para actualizar el grafico en caso de fallo.
	 */
	public void actualiza_fallo(String s) {
		plot2D.removeAllPlots();
		
		// ENG: Change the result text to the error obtained.
		// ESP: Cambia el texto resultado al error obtenido.
		text_area.setText(s);
	}
	
	/**
	 * 
	
	 * ENG: Method for executing the algorithm.
	 * ESP: Funcion para ejecutar el algoritmo.
	 */
	private void run() {
		set_valores();
		AG.ejecuta(valores);
	}
	
	
	
	/**
	 * 
	 * @param path
	 * @return
	
	 * ENG: Method for loading an image.
	 * ESP: Funcion para cargar una imagen.
	 */
	protected ImageIcon load_image(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path));
	}
	
	/**
	 * 
	
	 * ENG: Method for storing the values ​​marked in the left panel.
	 * ESP: Funcion para almacenar los valores marcados en el panel izquierdo.
	 */
	private void set_valores() {
		valores = new Valores(Integer.parseInt(tam_poblacion.getText()),
				Integer.parseInt(generaciones.getText()),
				seleccion_CBox.getSelectedIndex(),
				cruce_CBox.getSelectedIndex(),
				Double.parseDouble(prob_cruce.getText()),
				mutacion_CBox.getSelectedIndex(),
				Double.parseDouble(prob_mut.getText()),
				Double.parseDouble(precision.getText()),
				funcion_CBox.getSelectedIndex(),
				(int) genes_spinner.getValue(),
				Integer.parseInt(elitismo.getText()));
	}
		
	/**
	 * 
	 * @return
	
	 * ENG: Method for getting the values.
	 * ESP: Funcion para conseguir los valores.
	 */
	public Valores get_valores() { return valores; }
	
}


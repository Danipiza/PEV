package View;

import org.math.plot.*;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import Logic.AlgoritmoGenetico;
import Logic.Funcion;
import Model.Individuo;
import Model.Valores;
//import Utils.BooleanSwitch;
import Utils.Pair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	AlgoritmoGenetico AG;

	private JButton run_button;

	//private BooleanSwitch elitismo_button;

	private JTextField tam_poblacion;
	private JTextField generaciones;	
	private JTextField prob_cruce;
	private JTextField prob_mut;
	private JTextField elitismo;
	private JTextField filas_text;
	private JTextField columnas_text;
	
	private JSpinner profundidad;

	private JComboBox<String> ini_CBox;
	private JComboBox<String> seleccion_CBox;
	private JComboBox<String> cruce_CBox;
	private JComboBox<String> mutacion_CBox;


	private JTextArea text_area;
	private JTextArea text_area2;

	private Plot2DPanel plot2D;

	private Valores valores;
	
	
	
	GridBagConstraints gbc;
	
	private int filas;
	private int columnas;

	public ControlPanel() {
		tam_poblacion = new JTextField("100", 11);
		generaciones = new JTextField("100", 11);
		prob_cruce = new JTextField("0.6", 11);
		prob_mut = new JTextField("0.3", 11);
		elitismo = new JTextField("0", 11);
		filas_text = new JTextField("8", 11);
		columnas_text= new JTextField("8", 11);
		
		
		SpinnerModel spinnerModel = new SpinnerNumberModel(4, 2, 5, 1); // Default value: 4, Min: 2, Max: 5, Step: 1
        profundidad = new JSpinner(spinnerModel);
		
		gbc = new GridBagConstraints();
		
		// TODO
		this.filas=8;
		this.columnas=8;

		AG = new AlgoritmoGenetico(this); // MEJORAR IMPLEMENTACION
		initGUI();
	}

	
	private void initGUI() {
	    setLayout(new GridBagLayout());

	    JPanel leftPanel = createLeftPanel();
	    JPanel rightPanel = createRightPanel2D();
	    int[][] M=new int[filas][columnas];
	    this.filas=8;
	    this.columnas=8;
	    JPanel matrixPanel = createMatrixPanel(M);

	    
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.weightx = 0.125; // Make the left panel smaller
	    gbc.weighty = 1.0; // Equal vertical weight for all panels
	    gbc.fill = GridBagConstraints.BOTH; // Fill both horizontal and vertical space

	    add(leftPanel, gbc);

	    gbc.gridx = 1;
	    gbc.weightx = 0.4; // Make the center panel larger
	    add(rightPanel, gbc);

	    gbc.gridx = 2;
	    gbc.weightx = 0.4; // Make the right panel larger
	    add(matrixPanel, gbc);
	}
	
	private JPanel createLeftPanel() {
		JPanel leftPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		leftPanel.setPreferredSize(new Dimension(300, 600));

		String[] inicializacions={"Completa",
								  "Creciente",
								  "Ramped & Half"};
		
		String[] seleccion = { 	"Ruleta",
								"T. Deterministico",
								"T. Probabilistico",
								"Estocastico Univ",
								"Truncamiento",
								"Restos",
								"Ranking"};
		
		String[] cruce = { 		"Intercambio"};
		
		String[] mutacion = { 	"Terminal",
								"Funcional",
								"Arbol",
								"Permutacion",
								"Subarbol",
								"Hoist",
								"Contraccion",
								"Espansion"};

		//elitismo_button = new BooleanSwitch();

		ini_CBox = new JComboBox<>(inicializacions);
		seleccion_CBox = new JComboBox<>(seleccion);
		cruce_CBox = new JComboBox<>(cruce);
		mutacion_CBox = new JComboBox<>(mutacion);
		
		text_area = new JTextArea(2, 2);
		text_area.append("Esperando una ejecucion...");
		text_area2 = new JTextArea(2, 2);
		text_area2.append("Esperando una ejecucion...");

		run_button = new JButton();
		run_button.setToolTipText("Run button");
		run_button.setIcon(loadImage("resources/icons/run.png"));
		run_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tmp=Integer.parseInt(elitismo.getText());
				if(tmp<0||tmp>100) actualiza_fallo("Elitismo porcentaje");
				else run();
			}
		});
		
				gbc.anchor = GridBagConstraints.WEST;

		gbc.gridx = 0;
		gbc.gridy = 0; // Espacio antes de las JLabels para que se vea mejor la GUI

		leftPanel.add(new JLabel("  Tam. Poblacion:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Num. Generaciones:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Inicializacion:"), gbc);
		gbc.gridy++; 
		leftPanel.add(new JLabel("  Profundidad:"), gbc);
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
		leftPanel.add(new JLabel("  Num. Filas:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Num. Columnas:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Elitismo:"), gbc);
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridy++;
		leftPanel.add(new JLabel("Valor optimo:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("Arbol:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;

		gbc.gridx++;
		gbc.gridy = 0;
		leftPanel.add(tam_poblacion, gbc);
		gbc.gridy++;
		leftPanel.add(generaciones, gbc);
		gbc.gridy++;
		leftPanel.add(ini_CBox, gbc);
		gbc.gridy++;
		leftPanel.add(profundidad, gbc);
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
		leftPanel.add(filas_text, gbc);
		gbc.gridy++;
		leftPanel.add(columnas_text, gbc);
		gbc.gridy++;
		leftPanel.add(elitismo, gbc); //elitismo_button
		gbc.gridy++;
		leftPanel.add(text_area, gbc);
		gbc.gridy+=2;
		gbc.gridx--;
		leftPanel.add(text_area2, gbc);

		gbc.anchor = GridBagConstraints.SOUTH; // Align components to the left
		gbc.gridy++;
		leftPanel.add(run_button, gbc);

		return leftPanel;
	}

	

	private JPanel createRightPanel2D() {
		JPanel rightPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		rightPanel.setPreferredSize(new Dimension(450, 600));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0; // Mas espacio horizontal
		gbc.weighty = 1.0; // // Mas espacio vertical
		gbc.fill = GridBagConstraints.BOTH; // Rellena el espacio disponible

		plot2D = new Plot2DPanel();

		plot2D.getAxis(0).setLabelText("Generacion");
		plot2D.getAxis(1).setLabelText("Fitness");

		// plot.removeAllPlots();
		rightPanel.add(plot2D, gbc);

		return rightPanel;
	}
	
	
	
	private JPanel createMatrixPanel(int[][] M) {
		int gap=2;
		int borderGap = 10;
		JPanel gridPanel = new JPanel(new GridLayout(this.filas, this.columnas, gap, gap));
        gridPanel.setBorder(new EmptyBorder(borderGap, borderGap, borderGap, borderGap)); // Add empty border
        gridPanel.setPreferredSize(new Dimension(450, 600));
        Color lightGreen = Color.decode("#00CC66"); // Light green color
        Color darkGreen = Color.decode("#028A46"); // Dark green color

        
        
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                JPanel square = new JPanel();
                //square.setPreferredSize(new Dimension(50, 50)); // Adjust size as needed

                // Alternate colors for the squares
                if (M[i][j]==1) {
                    square.setBackground(lightGreen);
                } else {
                    square.setBackground(darkGreen);
                }
                gridPanel.add(square);
            }
        }
        return gridPanel;
	}

	public void actualiza_Grafico(double[][] vals, Funcion f,Individuo mejor_individuo, int filas, int columnas) {
		plot2D.removeAllPlots();
		
		this.filas=filas;
		this.columnas=columnas;
		
		double[] x = new double[vals[0].length];
		for (int i = 0; i < vals[0].length; i++) {
			x[i] = i;
			
		}
		

		// Add the lines to the plot with different colors
		plot2D.addLinePlot("Mejor Absoluto", x, vals[0]);
		plot2D.addLinePlot("Mejor de la Generacion", x, vals[1]);
		plot2D.addLinePlot("Media", x, vals[2]);
		plot2D.addLinePlot("Presion Selectiva Generacion", Color.BLACK, x, vals[3]);
		

		// Customize the plot (optional)
		plot2D.getAxis(0).setLabelText("Generacion");
		plot2D.getAxis(1).setLabelText("Fitness");
		plot2D.setFixedBounds(1, 0,filas*columnas); // Fix Y-axis bounds

		//plot2D.addLegend("Mejor Absoluto");
		plot2D.addLegend("SOUTH");
		
		// TODO
		// Actualiza la matriz
		
		int[][] M=f.matrix(mejor_individuo);
		
		JPanel matrixPanel = createMatrixPanel(M);
		
		gbc.gridx = 2;
	    gbc.weightx = 0.4; // Make the right panel larger
	    add(matrixPanel, gbc);
		
		
		
		/*String cromosoma="";
		int i=0;
		for(int a: mejor_individuo.gen.v) {
			cromosoma+=a+1 + " ";
			i++;
			if(i%10==0) cromosoma+="\n";
		}*/
		text_area.setText("" + mejor_individuo.fitness);
		
		
			
	}

	public void actualiza_fallo(String s) {
		plot2D.removeAllPlots();

		text_area.setText(s);
	}

	private void run() {
		setValores();
		//AG.ejecuta_calculo_medias(valores);
		AG.ejecuta(valores);
	}

	private void setValores() {
		valores = new Valores(
				Integer.parseInt(tam_poblacion.getText()),
				Integer.parseInt(generaciones.getText()),
				ini_CBox.getSelectedIndex(),
				(int) profundidad.getValue(),
				seleccion_CBox.getSelectedIndex(),
				cruce_CBox.getSelectedIndex(),
				Double.parseDouble(prob_cruce.getText()),
				mutacion_CBox.getSelectedIndex(),
				Double.parseDouble(prob_mut.getText()),
				Integer.parseInt(filas_text.getText()),
				Integer.parseInt(columnas_text.getText()),
				Integer.parseInt(elitismo.getText()));
	}

	public Valores getValores() {
		return valores;
	}

	protected ImageIcon loadImage(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path));
	}

	
	public void resultadosClass() {
		//ResultadosDialog resultadosDialog = new ResultadosDialog((Frame) this.getTopLevelAncestor(), num_pistas);					
		
		//resultadosDialog.open(mejor_individuo);					
	}
	
}
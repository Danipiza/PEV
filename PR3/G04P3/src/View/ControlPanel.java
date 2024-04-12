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
import Model.IndividuoArbol;
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
	private JButton run_button2;
	private JButton corta_button;

	//private BooleanSwitch elitismo_button;

	private JTextField tam_poblacion;
	private JTextField generaciones;	
	private JTextField prob_cruce;
	private JTextField prob_mut;		
	private JTextField filas_text;
	private JTextField columnas_text;
	private JTextField elitismo;
	private JTextField ticks;
	
	private JTextField tiempo_animacion;

	private JComboBox<String> seleccion_CBox;
	
	
	private JComboBox<String> ini_CBox;	
	private JComboBox<String> cruceA_CBox;
	private JComboBox<String> mutacionA_CBox;
	
	private JSpinner profundidad;
	
	
	private JTextField tam_cromosoma;
	
	private JComboBox<String> cruceG_CBox;
	private JComboBox<String> mutacionG_CBox;
	
	
	
	
	
	private JTextArea text_area;
	private JTextArea text_area2;

	private Plot2DPanel plot2D;

	private Valores valores;
	
	
	GridBagConstraints gbc;
	
	private int filas;
	private int columnas;
	
	
	
	private JPanel matrixPanel;	
	private JPanel squares[][]; 	
	private Individuo mejor_individuo;

	public ControlPanel() {
		tam_poblacion = new JTextField("100", 11);
		generaciones = new JTextField("100", 11);
		prob_cruce = new JTextField("0.6", 11);
		prob_mut = new JTextField("0.3", 11);
		elitismo = new JTextField("0", 11);
		filas_text = new JTextField("8", 11);
		columnas_text= new JTextField("8", 11);
		tam_cromosoma= new JTextField("10", 11);
		ticks= new JTextField("100", 11);
		tiempo_animacion=new JTextField("0.25", 5);
		
		
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
	    
	    createMatrixPanel(M);

	    
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
		
		String[] cruceA = { 	"Intercambio"};
		
		String[] mutacionA = { 	"Terminal",
								"Funcional",
								//"Arbol",
								"Permutacion",
								//"Subarbol",
								"Hoist",
								"Contraccion"
								//"Espansion"
							};
		
		String[] cruceG = { 	"MonoPunto" };
		
		String[] mutacionG = { 	"Basica" };

		//elitismo_button = new BooleanSwitch();

		ini_CBox = new JComboBox<>(inicializacions);
		seleccion_CBox = new JComboBox<>(seleccion);
		cruceA_CBox = new JComboBox<>(cruceA);
		mutacionA_CBox = new JComboBox<>(mutacionA);
		
		cruceG_CBox = new JComboBox<>(cruceG);
		mutacionG_CBox = new JComboBox<>(mutacionG);
		
		text_area = new JTextArea(2, 2);
		text_area.append("Esperando una ejecucion...");
		text_area2 = new JTextArea(2, 4);
		text_area2.append("Esperando una ejecucion...");

		run_button = new JButton();
		run_button.setToolTipText("Run button");
		run_button.setIcon(loadImage("resources/icons/run.png"));
		run_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tmp=Integer.parseInt(elitismo.getText());
				if(tmp<0||tmp>100) actualiza_fallo("Elitismo porcentaje");
				else runArbol();
			}
		});
		
		// TODO
		run_button2 = new JButton();
		run_button2.setToolTipText("Run button");
		run_button2.setIcon(loadImage("resources/icons/run.png"));
		run_button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tmp=Integer.parseInt(elitismo.getText());
				if(tmp<0||tmp>100) actualiza_fallo("Elitismo porcentaje");
				else runGramatica();
			}
		});
		
		corta_button = new JButton();
		corta_button.setToolTipText("Run button");
		corta_button.setIcon(loadImage("resources/icons/Corta.png"));
		corta_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				/*System.out.println("Corta");
				Color lightGreen = Color.decode("#00CC66"); // Light green color
		        Color darkGreen = Color.decode("#028A46"); // Dark green color
		        squares[0][0].setBackground(lightGreen);*/
		        if(mejor_individuo!=null) {
		        	Color darkGreen = Color.decode("#028A46");
		        	// REINICIA
		            for (int i = 0; i < filas; i++) {
		                for (int j = 0; j < columnas; j++) {
		                	squares[i][j].setBackground(darkGreen);                
		                }
		            }
		            matrixPanel.repaint();
		        	
		        	animacionMatrixPanel(mejor_individuo,
		        			Double.parseDouble(tiempo_animacion.getText()),
			        		Integer.parseInt(ticks.getText()));
		        }
			}
		});
		
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.anchor = GridBagConstraints.WEST;

		gbc.gridx = 0;
		gbc.gridy = 0; // Espacio antes de las JLabels para que se vea mejor la GUI

		leftPanel.add(new JLabel("  Tam. Poblacion:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Num. Generaciones:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Metodo de Seleccion:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Prob. Cruce:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Prob. Mutacion:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Num. Filas:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Num. Columnas:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Elitismo:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Ticks:"), gbc);
		gbc.gridy++;
		
		leftPanel.add(new JLabel(""), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel(""), gbc);
		gbc.gridy++;	
		
		leftPanel.add(new JLabel("  Parte 1. Arbol:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Inicializacion:"), gbc);
		gbc.gridy++; 
		leftPanel.add(new JLabel("  Profundidad:"), gbc);
		gbc.gridy++;				
		leftPanel.add(new JLabel("  Metodo de Cruce:"), gbc);
		gbc.gridy++;		
		leftPanel.add(new JLabel("  Metodo de Mutacion:"), gbc);
		gbc.gridy++;
		
		gbc.anchor = GridBagConstraints.SOUTH; // Align components to the left
		//gbc.gridy++;
		gbc.gridwidth=2;
		leftPanel.add(run_button, gbc);
		gbc.gridwidth=1;
		gbc.anchor = GridBagConstraints.WEST;
		
		leftPanel.add(new JLabel(""), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel(""), gbc);
		gbc.gridy++;	
		
		leftPanel.add(new JLabel("  Parte 2. Gramatica:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Tam. Cromosoma:"), gbc);
		gbc.gridy++;				
		leftPanel.add(new JLabel("  Metodo de Cruce:"), gbc);
		gbc.gridy++;		
		leftPanel.add(new JLabel("  Metodo de Mutacion:"), gbc);
		gbc.gridy++;
		
		gbc.anchor = GridBagConstraints.SOUTH; // Align components to the left
		//gbc.gridy++;
		gbc.gridwidth=2;
		leftPanel.add(run_button2, gbc);
		gbc.gridwidth=1;
		//leftPanel.add(new JLabel(""), gbc);
		//gbc.gridy++;
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridy++;
		leftPanel.add(new JLabel("Valor optimo:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("Mejor Individuo:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;

		gbc.gridx++;
		gbc.gridy = 0;
		leftPanel.add(tam_poblacion, gbc);
		gbc.gridy++;
		leftPanel.add(generaciones, gbc);
		gbc.gridy++;
		leftPanel.add(seleccion_CBox, gbc);
		gbc.gridy++;
		leftPanel.add(prob_cruce, gbc);
		gbc.gridy++;
		leftPanel.add(prob_mut, gbc);
		gbc.gridy++;
		leftPanel.add(filas_text, gbc);
		gbc.gridy++;
		leftPanel.add(columnas_text, gbc);
		gbc.gridy++;
		leftPanel.add(elitismo, gbc); //elitismo_button
		gbc.gridy++;
		leftPanel.add(ticks, gbc); //elitismo_button
		gbc.gridy++;
		
		gbc.gridy++;
		gbc.gridy++;
		gbc.gridy++;
		
		leftPanel.add(ini_CBox, gbc);
		gbc.gridy++;
		leftPanel.add(profundidad, gbc);
		gbc.gridy++;			
		leftPanel.add(cruceA_CBox, gbc);
		gbc.gridy++;		
		leftPanel.add(mutacionA_CBox, gbc);
		gbc.gridy++;
		
		gbc.gridy++;		
		gbc.gridy++;
		gbc.gridy++;
		
		leftPanel.add(tam_cromosoma, gbc);
		gbc.gridy++;			
		leftPanel.add(cruceG_CBox, gbc);
		gbc.gridy++;		
		leftPanel.add(mutacionG_CBox, gbc);
		gbc.gridy++;
		
		gbc.gridy++;
		
		leftPanel.add(text_area, gbc);
		gbc.gridy+=2;
		gbc.gridx--;
		gbc.gridwidth=2;
		gbc.fill = GridBagConstraints.BOTH;
		leftPanel.add(text_area2, gbc);
		gbc.gridy++;
		gbc.gridwidth=1;
		
		leftPanel.add(corta_button, gbc);
		gbc.gridx++;
		leftPanel.add(tiempo_animacion, gbc);
		

		/*gbc.anchor = GridBagConstraints.SOUTH; // Align components to the left
		gbc.gridy++;
		leftPanel.add(run_button, gbc);*/

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
	
	
	
	private void createMatrixPanel(int[][] M) {
		int gap=2;
		int borderGap = 10;
		matrixPanel = new JPanel(new GridLayout(this.filas, this.columnas, gap, gap));
		matrixPanel.setBorder(new EmptyBorder(borderGap, borderGap, borderGap, borderGap)); // Add empty border
		matrixPanel.setPreferredSize(new Dimension(450, 600));
        Color lightGreen = Color.decode("#00CC66"); // Light green color
        Color darkGreen = Color.decode("#028A46"); // Dark green color
        
        
        squares=new JPanel[filas][columnas];
        
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
            	squares[i][j] = new JPanel();
                
                if (M[i][j]==1) squares[i][j].setBackground(lightGreen);
                else squares[i][j].setBackground(darkGreen);
                matrixPanel.add(squares[i][j]);
            }
        }
	}
	
	private void updateMatrixPanel(int[][] M) {
        Color lightGreen = Color.decode("#00CC66"); // Light green color
        Color darkGreen = Color.decode("#028A46"); // Dark green color
                       
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                if (M[i][j]==1) squares[i][j].setBackground(lightGreen);
                else squares[i][j].setBackground(darkGreen);                
            }
        }
	}
	
	private void animacionMatrixPanel(Individuo ind, double tiempo, int ticks) {
        Color lightGreen = Color.decode("#00CC66"); // Light green color
        Color darkGreen = Color.decode("#028A46"); // Dark green color
        
        // REINICIA
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
            	squares[i][j].setBackground(darkGreen);                
            }
        }
        matrixPanel.repaint();
        double fitness=0.0;
		if(ind.operaciones.size()==0) return;
		
		int[][] M=new int[filas][columnas];
		int[][] direccionAvanza={{-1,0},{0,-1},{1,0},{0,1}};
		int[][] direccionSalta ={{-1,1},{1,-1},{1,1},{1,1}};
		
		// Posicion inicial
		int x=4;
		int y=4;
		
		int dir=0;
		
		int cont=0;	
		int n=ind.operaciones.size();
		char[][] ops=new char[n][3];
		for(String tmp: ind.operaciones) {
			ops[cont++]=tmp.toCharArray();
		}
		cont=0;
		
		// Tick suma cuando se gira a la izquierda, salta o avanza
		
		int t=0;
		while(t<ticks) {
			try { Thread.sleep((long) (tiempo*1000));
			} catch (InterruptedException e) { e.printStackTrace(); } 
			
			// Gira a la izquierda
			if(ops[cont][0]=='I') {
				dir+=(dir+1)%4;
				//System.out.println("Izquierda");
			}
			else if(ops[cont][0]=='A') {
				//System.out.println("Avanza");
				x=(x+direccionAvanza[dir][0])%filas;
				y=(y+direccionAvanza[dir][1])%columnas;
				if(x<0) x=filas+x;
				if(y<0) y=columnas+y;
				if(M[x][y]==0) {
					fitness++;
					M[x][y]=1;
					squares[x][y].setBackground(lightGreen);
				}
			}
			else {
				//System.out.println("Salta");
				x=(x+direccionSalta[dir][0]*ops[cont][1]-'0')%filas;
				y=(y+direccionSalta[dir][1]*ops[cont][2]-'0')%columnas;
				if(x<0) x=filas+x;
				if(y<0) y=columnas+y;
				if(M[x][y]==0) {
					fitness++;
					M[x][y]=1;
					squares[x][y].setBackground(lightGreen);
				}
			}
			matrixPanel.repaint();
			t++;
			cont=(cont+1)%n;
		}		
		
        
        
	}
	
	

	public void actualiza_Grafico(double[][] vals, Funcion f,Individuo mejor_individuo, int filas, int columnas) {
		plot2D.removeAllPlots();
		
		this.mejor_individuo=mejor_individuo;
		
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
		
		//JPanel matrixPanel = updateMatrixPanel(M);
		updateMatrixPanel(M);
		/*gbc.gridx = 2;
	    gbc.weightx = 0.4; // Make the right panel larger
	    add(matrixPanel, gbc);*/
		
		
		
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

	private void runArbol() {
		setValores(0);
		AG.ejecutaArbol(valores);
	}
	
	private void runGramatica() {
		setValores(1);
		AG.ejecutaGramatica(valores);
		
	}

	private void setValores(int modo) {
		int prof_O_longCrom=(int) profundidad.getValue();
		if(modo==1) prof_O_longCrom=Integer.parseInt(tam_cromosoma.getText());
			
		valores = new Valores(
				Integer.parseInt(tam_poblacion.getText()),
				Integer.parseInt(generaciones.getText()),
				ini_CBox.getSelectedIndex(), // PARA GRAMATICA NO IMPORTA
				prof_O_longCrom,
				seleccion_CBox.getSelectedIndex(),
				cruceA_CBox.getSelectedIndex(),
				Double.parseDouble(prob_cruce.getText()),
				mutacionA_CBox.getSelectedIndex(),
				Double.parseDouble(prob_mut.getText()),
				Integer.parseInt(filas_text.getText()),
				Integer.parseInt(columnas_text.getText()),
				Integer.parseInt(elitismo.getText()),
				Integer.parseInt(ticks.getText()),
				modo);
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

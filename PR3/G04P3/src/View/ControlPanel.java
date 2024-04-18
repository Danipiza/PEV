package View;

import org.math.plot.*;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

	private GridBagConstraints gbc;
	
	private AlgoritmoGenetico AG;	
	private Valores valores;
	
	private int filas;
	private int columnas;

	

	//private BooleanSwitch elitismo_button;

	private JTextField tam_poblacion;
	private JTextField generaciones;	
	private JTextField prob_cruce;
	private JTextField prob_mut;		
	private JTextField filas_text;
	private JTextField columnas_text;
	private JTextField elitismo;
	private JTextField ticks;	

	private JComboBox<String> seleccion_CBox;	
	private JComboBox<String> bloating_CBox;
	
	// PARTE 1. PROGRAMACION GENETICA
	
	private JComboBox<String> ini_CBox;	
	private JComboBox<String> cruceA_CBox;
	private JComboBox<String> mutacionA_CBox;
	
	
	private JSpinner profundidad;
	
	private JButton run_button;
	
	
	// PARTE 2. GRAMATICAS GENETICAS
	
	private JTextField tam_cromosoma;
	
	private JComboBox<String> cruceG_CBox;
	private JComboBox<String> mutacionG_CBox;
	
	private JButton run_button2;
	
	// ANIMACION 
	
	private JButton corta_button;
	private JTextField tiempo_animacion;
	
	private JPanel matrixPanel;	
	private JPanel squares[][]; 	
	private Individuo mejor_individuo;
	
	private Color lightGreen = Color.decode("#00CC66"); // Light green color
	private Color darkGreen = Color.decode("#028A46"); // Dark green color
    private boolean finAnimacion=false;
	
    
    
    // IMPRIME RESULTADOS
	private JTextArea text_area;
	private JTextArea text_area2;
	private JTextArea text_area3;

	private Plot2DPanel plot2D;

		
	// OPCIONAL
	private JCheckBox[] checkBoxes;
    private JButton opcional_button;
    private boolean[] selectedCheckboxes;
	

	public ControlPanel() {
		this.gbc = new GridBagConstraints();
		this.AG = new AlgoritmoGenetico(this);
		this.filas=AG.getFilas();
		this.columnas=AG.getColumnas();

		this.tam_poblacion = new JTextField("100", 11);
		this.generaciones = new JTextField("100", 11);
		this.prob_cruce = new JTextField("0.6", 11);
		this.prob_mut = new JTextField("0.3", 11);
		this.elitismo = new JTextField("0", 11);
		this.filas_text = new JTextField("8", 11);
		this.columnas_text= new JTextField("8", 11);
		this.tam_cromosoma= new JTextField("100", 11);
		this.ticks= new JTextField("100", 11);
		this.tiempo_animacion=new JTextField("0.25", 5);
		
		// Valor por defecto: 4, min: 2, max: 5, Pasos: 1
		SpinnerModel spinnerModel = new SpinnerNumberModel(4, 2, 5, 1); 
		this.profundidad = new JSpinner(spinnerModel);
		
		
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
								"Arbol",
								"Permutacion",
								"Hoist",
								"Contraccion",
								"Expansion"};
		
		String[] cruceG = { 	"MonoPunto" };	
		
		String[] mutacionG = { 	"Basica" };
		
		String[] bloating = { 	"Sin",
								"Tarpeian",
								"Poli and McPhee"};


		ini_CBox = new JComboBox<>(inicializacions);
		seleccion_CBox = new JComboBox<>(seleccion);
		cruceA_CBox = new JComboBox<>(cruceA);
		mutacionA_CBox = new JComboBox<>(mutacionA);
		bloating_CBox = new JComboBox<>(bloating);
		
		cruceG_CBox = new JComboBox<>(cruceG);
		mutacionG_CBox = new JComboBox<>(mutacionG);
		
		
		checkBoxes = new JCheckBox[7]; 
		checkBoxes[0] = new JCheckBox("Obstaculos");
		checkBoxes[1] = new JCheckBox("OP. Derecha");
        checkBoxes[2] = new JCheckBox("OP. Retrocede");
        checkBoxes[3] = new JCheckBox("OP. IF_Dirty(A,B)");
        checkBoxes[4] = new JCheckBox("OP. Repeat(A)");
        checkBoxes[5] = new JCheckBox("OP. Salto_casilla(A)");
        checkBoxes[6] = new JCheckBox("OP. Mueve_Primer");
        
        selectedCheckboxes = new boolean[checkBoxes.length];

        opcional_button = new JButton("Opcional	");
        opcional_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDialog();
            }
        });

		run_button = new JButton();
		run_button.setToolTipText("Run button");
		run_button.setIcon(loadImage("resources/icons/run.png"));
		run_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				finAnimacion=true;
				int tmp=Integer.parseInt(elitismo.getText());
				if(tmp<0||tmp>100) actualiza_fallo("Elitismo porcentaje");
				else runArbol();
			}
		});
		
		
		run_button2 = new JButton();
		run_button2.setToolTipText("Run button");
		run_button2.setIcon(loadImage("resources/icons/run.png"));
		run_button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try { 
            		finAnimacion=true;
    				Thread.sleep((long) (100));
    				finAnimacion=false;
    			} catch (InterruptedException e1) { e1.printStackTrace(); } 
				int tmp=Integer.parseInt(elitismo.getText());
				if(tmp<0||tmp>100) actualiza_fallo("Elitismo porcentaje");
				else runGramatica();
			}
		});
		
		corta_button = new JButton();
		corta_button.setToolTipText("Run button");
		corta_button.setIcon(loadImage("resources/icons/CortaE.png"));
		corta_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				finAnimacion=true;
				updateMatrixPanel(new int[filas][columnas]);  
				
				animacionMatrixPanel(mejor_individuo,
	        			Double.parseDouble(tiempo_animacion.getText()),
		        		Integer.parseInt(ticks.getText()));					
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
		leftPanel.add(new JLabel("  Bloating:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Opcional:"), gbc);
		gbc.gridy++;
		
		// PARTE 1. PROGRAMACION GENETICA
		
		gbc.gridwidth=2;
		leftPanel.add(new JLabel("--------------------------------------------------------------------"), gbc);
		gbc.gridy++;
		gbc.gridwidth=1;
		
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
		gbc.gridy++;
		
		// PARTE 2. GRAMATICAS GENETICAS
		
		gbc.gridwidth=2;
		leftPanel.add(new JLabel("--------------------------------------------------------------------"), gbc);
		gbc.gridy++;
		gbc.gridwidth=1;	
		
		leftPanel.add(new JLabel("  Parte 2. Gramatica:"), gbc);
		gbc.gridy++;
		leftPanel.add(new JLabel("  Tam. Cromosoma:"), gbc);
		gbc.gridy++;				
		leftPanel.add(new JLabel("  Metodo de Cruce:"), gbc);
		gbc.gridy++;		
		leftPanel.add(new JLabel("  Metodo de Mutacion:"), gbc);
		gbc.gridy++;
		
		
		// ANIMACION
		
		gbc.anchor = GridBagConstraints.SOUTH; // Align components to the left

		gbc.gridwidth=2;
		leftPanel.add(run_button2, gbc);
		gbc.gridwidth=2;
		gbc.gridy++;
		leftPanel.add(new JLabel("--------------------------------------------------------------------"), gbc);
		gbc.gridy++;
		gbc.gridwidth=1;
		leftPanel.add(new JLabel("  Animacion:"), gbc);
		gbc.gridy++;
		
		
		
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
		leftPanel.add(elitismo, gbc); 
		gbc.gridy++;
		leftPanel.add(ticks, gbc); 
		gbc.gridy++;
		leftPanel.add(bloating_CBox, gbc); 
		gbc.gridy++;
		leftPanel.add(opcional_button, gbc); 
		gbc.gridy++;
		
		gbc.gridy++;
		gbc.gridy++;
		
		// PARTE 1. PROGRAMACION GENETICA			
		
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
		
		// PARTE 2. GRAMATICAS GENETICAS
		
		leftPanel.add(tam_cromosoma, gbc);
		gbc.gridy++;			
		leftPanel.add(cruceG_CBox, gbc);
		gbc.gridy++;		
		leftPanel.add(mutacionG_CBox, gbc);
		gbc.gridy++;
		
		gbc.gridy++;
		
		// ANIMACION
		
		gbc.gridy++;
		gbc.gridy++;
		gbc.gridx=0;
		leftPanel.add(corta_button, gbc);
		gbc.gridx++;
		leftPanel.add(tiempo_animacion, gbc);
		


		return leftPanel;
	}

	
	private JPanel createRightPanel2D() {
		JPanel rightPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		rightPanel.setPreferredSize(new Dimension(450, 600));

		// plot2D ocupa mas
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0; // Espacio horizontal
		gbc.weighty = 2.5; // Espacio vertical 
		gbc.fill = GridBagConstraints.BOTH; // Rellena el espacio

		plot2D = new Plot2DPanel();
		plot2D.getAxis(0).setLabelText("Generacion");
		plot2D.getAxis(1).setLabelText("Fitness");

		gbc.gridheight = 6; // Numero de filas que ocupa		
		rightPanel.add(plot2D, gbc);

		// text_areas		
		gbc.weighty = 0.1; // Menos espacio vertical 
		gbc.gridheight = 1; // Restablece filas
		gbc.gridy = 6;

		text_area = new JTextArea(2, 2);
		text_area.append("  Esperando una ejecucion...");
		text_area2 = new JTextArea(2, 2);
		text_area2.append("  Esperando una ejecucion...");
		text_area3 = new JTextArea(2, 2);
		text_area3.append("  Esperando una ejecucion...");

		rightPanel.add(text_area, gbc);
		gbc.gridy++;
		gbc.weighty = 0.5; // Mas espacio para imprimir el individuo
		rightPanel.add(text_area2, gbc);
		gbc.gridy++;
		rightPanel.add(text_area3, gbc);
		
		return rightPanel;
	}
	
		
	private void createMatrixPanel(int[][] M) {
		int gap=2;
		int borderGap = 10;		        
				
		
		matrixPanel = new JPanel(new GridLayout(filas, columnas, gap, gap)); 

		// borde que act�a como espacio entre las celdas
		matrixPanel.setBorder(new EmptyBorder(borderGap, borderGap, borderGap, borderGap));
		matrixPanel.setPreferredSize(new Dimension(450, 600));
			
		
		matrixPanel.removeAll();
		
        squares= new JPanel[filas][columnas]; 
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
            	squares[i][j] = new JPanel(); 
            	squares[i][j].setLayout(new BorderLayout()); 
            	squares[i][j].setOpaque(true);
            	squares[i][j].setBackground(darkGreen);

            	// Borde bloancon entre cada celda
            	squares[i][j].setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
                matrixPanel.add(squares[i][j]);
            }
        }
	}
	
	// Con matriz
	private void updateMatrixPanel(int[][] M) {                              
		int gap=2;
		int borderGap = 10;		   
		
		//matrixPanel = new JPanel(new GridLayout(filas, columnas, gap, gap)); 
		//matrixPanel.resize(new GridLayout(filas, columnas, gap, gap));
		
		matrixPanel.setLayout(new GridLayout(filas, columnas, gap, gap));	
		
		matrixPanel.removeAll();
		matrixPanel.repaint();
		
        squares= new JPanel[filas][columnas]; 
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
            	squares[i][j] = new JPanel(); 
            	squares[i][j].setLayout(new BorderLayout()); 
            	squares[i][j].setOpaque(true);
            	
            	if (M[i][j]==1) squares[i][j].setBackground(lightGreen);
                else squares[i][j].setBackground(darkGreen);  

            	// Borde bloancon entre cada celda
            	squares[i][j].setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
                matrixPanel.add(squares[i][j]);
            }
        }
		
		
	}
	
	// SImula el recorrido del individuo
	private void animacionMatrixPanel(Individuo ind, double tiempo, int ticks)  {                            
		
		Thread thread = new Thread(new Runnable() {
			
            @Override
            public void run() {
            	try { 
            		finAnimacion=true;
    				Thread.sleep((long) (tiempo*1000));
    				finAnimacion=false;
    			} catch (InterruptedException e) { e.printStackTrace(); } 
        		
            	if(ind.operaciones.size()==0) return;
        		
        		int[][] M=new int[filas][columnas];
        		int[][] direccionAvanza={{-1,0},{0,-1},{1,0},{0,1}};
        		int[][] direccionSalta ={{-1,1},{-1,-1},{1,-1},{1,1}};
        		String[] dirCorta= {"N.png","O.png","S.png","E.png"};
        		
        		String path="resources/icons/Corta";
        		
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
        		
        		String operaciones[] = new String[n];
        		
        		for(int i=0;i<n;i++) {
        			if(ops[i][0]=='A') operaciones[i]="AVANZA";
        			else if(ops[i][0]=='I') operaciones[i]="IZQUIERDA";
        			else operaciones[i]="SALTA "+ops[i][1]+ ", "+ops[i][2];
        		}
        		
        		
        		// Cortacesped en la casilla inicial
        		squares[x][y].add(new JLabel(loadImage(path+dirCorta[dir])));
				squares[x][y].revalidate();
				squares[x][y].repaint();
				
				
				
        		int t=0;
        		while(t<ticks) {
        			
        			
        			text_area3.setText("\n     Operacion: "+operaciones[cont]);
        			try { 
        				Thread.sleep((long) (tiempo*1000));
        				if(finAnimacion) break;
        			} catch (InterruptedException e) { e.printStackTrace(); } 
        			
        			// Quita el cortacesped actual, porque va a pasar a la siguiente
        			squares[x][y].removeAll();
        			squares[x][y].revalidate();                                
        			squares[x][y].repaint();
        			// Gira a la izquierda
        			if(ops[cont][0]=='I') dir=(dir+1)%4;
        			else if(ops[cont][0]=='A') {
        				x=(x+direccionAvanza[dir][0])%filas;
        				y=(y+direccionAvanza[dir][1])%columnas;
        				// Toroidal
        				if(x<0) x=filas+x;
        				if(y<0) y=columnas+y;
        				
        				if(M[x][y]==0) {
        					M[x][y]=1;
        					squares[x][y].setBackground(lightGreen);
        				}        				
        			}
        			else {
        				x=(x+direccionSalta[dir][0]*ops[cont][1]-'0')%filas;
        				y=(y+direccionSalta[dir][1]*ops[cont][2]-'0')%columnas;
        				// Toroidal
        				if(x<0) x=filas+x;
        				if(y<0) y=columnas+y;
        				
        				if(M[x][y]==0) {
        					M[x][y]=1;
        					squares[x][y].setBackground(lightGreen);
        				}
        			}
        			matrixPanel.repaint();        			
        			squares[x][y].removeAll();
    				squares[x][y].add(new JLabel(loadImage(path+dirCorta[dir])));
    				squares[x][y].revalidate();
    				squares[x][y].repaint();
    				
    				t++;
        			cont=(cont+1)%n;
        		}	
        		finAnimacion=false;
            }
        });
        thread.start();
		
		
						
	}
		

	public void actualiza_Grafico(double[][] vals, Funcion f,Individuo mejor_individuo, int filas, int columnas) {
		plot2D.removeAllPlots();
		
		this.mejor_individuo=mejor_individuo;
		
		this.filas=filas;
		this.columnas=columnas;
		
		// Numero de generaciones para el eje X
		double[] x = new double[vals[0].length];
		for (int i = 0; i < vals[0].length; i++) {
			x[i] = i;			
		}
		

		// Lineas
		plot2D.addLinePlot("Mejor Absoluto", x, vals[0]);
		plot2D.addLinePlot("Mejor de la Generacion", x, vals[1]);
		plot2D.addLinePlot("Media", x, vals[2]);
		plot2D.addLinePlot("Presion Selectiva Generacion", Color.BLACK, x, vals[3]);
		

		// Ejes 
		plot2D.getAxis(0).setLabelText("Generacion");
		plot2D.getAxis(1).setLabelText("Fitness");
		plot2D.setFixedBounds(1, 0,filas*columnas); // Intervalo del fitness

		plot2D.addLegend("SOUTH");
		
		
		int[][] M=f.matrix(mejor_individuo);
		
		updateMatrixPanel(new int[filas][columnas]);
		
		animacionMatrixPanel(mejor_individuo,
    			0.0,
        		Integer.parseInt(ticks.getText()));	
		
		int n=mejor_individuo.operaciones.size();
		String acciones="";
		
		for(int i=0;i<n-1;i++) {
			acciones+=mejor_individuo.operaciones.get(i)+" - ";
		}
		acciones+=mejor_individuo.operaciones.get(n-1);
		   
		
		text_area.setText( "\n     Fitness Optimo:" + mejor_individuo.fitness);
		text_area2.setLineWrap(true);
		text_area2.setWrapStyleWord(true);
		text_area2.setText("     Representacion:\n" + mejor_individuo.toString()+
						   "\n\n     Acciones: "+ acciones);
		
		
						
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
				modo,
				bloating_CBox.getSelectedIndex(),
				selectedCheckboxes);
	}
	
	

	public Valores getValores() {
		return valores;
	}

	protected ImageIcon loadImage(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path));
	}

	
	private void openDialog() {
		boolean[] aux = selectedCheckboxes;
		selectedCheckboxes = new boolean[checkBoxes.length];
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(checkBoxes.length, 1));
        for (int i = 0; i < checkBoxes.length; i++) {
            panel.add(checkBoxes[i]);
        }
        

        int result = JOptionPane.showConfirmDialog(null, panel, "Select Options", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            for (int i = 0; i < checkBoxes.length; i++) {
                selectedCheckboxes[i] = checkBoxes[i].isSelected();
            }
        }
        else {
        	selectedCheckboxes=aux;
        	for(int i=0;i<checkBoxes.length;i++) {        	
                checkBoxes[i].setSelected(selectedCheckboxes[i]);                
        	}
        }
    }
	
}

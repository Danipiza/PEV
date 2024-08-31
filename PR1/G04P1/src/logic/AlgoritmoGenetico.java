/**
 * 
 */
package logic;

import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Comparator;

import model.Valores;
import model.Individuo;
import model.IndividuoBin;
import model.IndividuoReal;

import utils.Node;

import view.ControlPanel;


/**
 * @author DavidSG & DannyP39 
 
 * ENG: Class for GeneteicAlgorithm
 * ESP: Clase para AlgoritmoGenetico
 */
public class AlgoritmoGenetico {


	/**
	 * ENG: GUI variables
	 * ESP: Variables interfaz
	 */
	private int tam_poblacion;
	private int generaciones;
	private int seleccion_idx;
	private int cruce_idx;
	private double prob_cruce;
	private int mut_idx;
	private double prob_mut;
	private double precision;
	private int funcion_idx;
	private int num_genes;
	
	

	/**
	 * ENG: Init function variables
	 * ESP: Variables de la funcion de inicializacion
	 */
	@SuppressWarnings("unused")
	private int tam_individuo;
	private int[] tam_genes;
	private Individuo[] poblacion;

	/**
	 * ENG: Evaluation function variables
	 * ESP: Variables de la funcion de evaluacion
	 */
	private double fitness_total;
	private double[] prob_seleccion;
	private double[] prob_seleccionAcum;

	/**
	 * ENG: Selected methods variables
	 * ESP: Variables de seleccion de metodos
	 */
	private Evaluacion funcion;
	private Seleccion seleccion;
	private Cruce cruce;
	private Mutacion mutacion;

	/**
	 * ENG: Graphics variables
	 * ESP: Variables de los graficos
	 */
	private int generacionActual;
	private double[][] progreso_generaciones;
	private double mejor_total;
	private Individuo mejor_ind;
	
	/**
	 * ENG: Elitism variables
	 * ESP: Variables de elitismo
	 */
	private int elitismo;
	private int tam_elite;
	PriorityQueue<Node> elitQ;

	private int decimales;

	private ControlPanel ctrl;
	
	/**
	 * 
	 * @param ctrl
	
	 * ENG: Class constructor
	 * ESP: Constructor de la clase
	 */
	public AlgoritmoGenetico(ControlPanel ctrl) {
		this.ctrl = ctrl;
	}
	
	/**
	 * 
	 * @param valores. 	
	 *
	
	 Description/Descripcion:	 
	 * ENG: Method for setting the values of all the variables. 
	 * ESP: Funcion para configurar los valores de todas las variables.
	 */	
	private void set_valores(Valores valores) {
		
		// ENG: Setted values in the GUI.
		// ESP:	Valores establecidos en la interfaz.
		this.tam_poblacion 	= valores.tam_poblacion;
		this.generaciones 	= valores.generaciones;
		this.seleccion_idx 	= valores.seleccion_idx;
		this.cruce_idx 		= valores.cruce_idx;
		this.prob_cruce 	= valores.prob_cruce;
		this.mut_idx 		= valores.mut_idx;
		this.prob_mut 		= valores.prob_mut;
		this.precision 		= valores.precision;
		this.funcion_idx 	= valores.funcion_idx;
		this.num_genes 		= valores.num_genes;
		this.elitismo 		= valores.elitismo;
		
		// ENG: If the selected evaluation function in the GUI is not the 4 or 5,
		//		the number of genes is always 2.
		// ESP: Si la funcion de evaluacion elegida en la interfaz no es la 4 o 5, 
		//		el numero de genes siempre es 2.
		if(funcion_idx<3) num_genes=2;
		
		// ENG: Calculate the number of decimals with the selected precision.
		// ESP: Calcula el numero de decimales con la precision seleccionada.
		double tmp=this.precision*10;
		this.decimales=10;
		while (tmp!=1) {
			tmp*=10;
			decimales*=10;
		}
		
		// ENG: Calculate the number of the best individuals that survive between generations.
		// ESP: Calcula el numero de los mejores individuos que sobreviven entre generaciones.
		tam_elite=(int) (tam_poblacion*(elitismo/100.0));

		// ENG: Select the functions for the algorithm methods
		// ESP: Selecciona las funciones para los metodos del algoritmo
		seleccionar_funcion();
		seleccion	= new Seleccion(tam_poblacion, funcion.opt, funcion_idx);
		cruce		= new Cruce(prob_cruce, funcion_idx,tam_elite);
		mutacion	= new Mutacion(prob_mut,tam_elite);
		
		// ENG: Calculate the size of each gen if binary individuals are used
		// ESP: Calcula el tamaño de cada gen si se usan individuos binarios 
		if (funcion_idx<4) tam_genes=tam_genes();
		

		// ENG: Initialize the value of the best individual to the minimum/maximum value
		// ESP: Inicializa el valor del mejor individuo al minimo/maximo valor
		mejor_total=(funcion.opt?Double.MIN_VALUE:Double.MAX_VALUE);
	}
	
	/**
	 * 
	 * @param valores
	 *
	
	 * ENG: Method for executing the main loop of the algorithm.
	 * ESP: Funcion para ejecutar el bucle principal del algoritmo.
	 */
	public void ejecuta(Valores valores) {
		Individuo[] selec=null;
				
		// ENG: Set the values of variables settled in the GUI
		//		and check for errors.
		// ESP: Configura las variables con los valores establecidos 
		//		en la interfaz y comprueba errores.		
		set_valores(valores);		
		if(!comprueba_valores()) return;
		
		// ENG: Priority queue used for the elitism
		// ESP: Cola de prioridad usada para el elitismo
		Comparator<Node> comparator=Comparator.comparingDouble(Node::get_valor);
		if(funcion_idx!=0) 
			 elitQ=new PriorityQueue<>(Collections.reverseOrder(comparator));
		else elitQ=new PriorityQueue<>(comparator);
		
		// ENG: Variables to print the graphic
		// ESP: Variables para imprimir el grafico
		progreso_generaciones=new double[3][generaciones + 1];
		generacionActual=0;
		
		// ENG: Main loop
		// ESP: Bucle principal		 
		inicializar_poblacion(); // init
		evaluar_poblacion(); // evaluate
		
		while (generaciones-- != 0) {
			selec=seleccionar_poblacion(); 		// select
			
			poblacion=cruzar_poblacion(selec); 	// cross
			poblacion=mutar_poblacion(); 		// mutate
			
			// elitism
			while(elitQ.size()!=0) {
				poblacion[tam_poblacion-elitQ.size()]=elitQ.poll().get_individuo();
			}

			evaluar_poblacion(); 				// evaluate
		}
		
		ctrl.actualiza_grafico(progreso_generaciones, funcion.intervalosGrafico, mejor_ind);
	}
	
	/**
	 * 
	 * @return boolean
	
	 * ENG: Method for checking values errors in the variables.
	 * ESP: Funcion para comprobar errores en los valores de las variables.
	 */
	private boolean comprueba_valores() {
		if(funcion_idx!=4) {
			if(cruce_idx==2) {
				// There is not Arithmetic cross in Binary individuals.
				ctrl.actualiza_fallo("No hay Cruce\n- Aritmetico\nen individuos Binarios"); 
				return false;
			}
			if(cruce_idx==3) {
				// There is not BLX cross in Binary individuals.
				ctrl.actualiza_fallo("No hay Cruce \n- BLX\nen individuos Reales");
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 
	 * @return int[]
	 *
	
	 * ENG: Method for setting the genes sizes
	 * ESP: Funcion para configurar el tamaño de los genes
	 */
	private int[] tam_genes() {
		int ret[]=new int[num_genes];
		
		// ENG: For each gen calls the tam_gen() function to calculate the size.
		// ESP:	Para cada gen llama a la funcion tam_gen() para calcular su tamaño.
		for (int i=0;i<num_genes;i++) {
			tam_individuo+=ret[i]=tam_gen(precision, funcion.minimos[i], funcion.maximos[i]);
		}

		return ret;
	}
	
	/**
	 * 
	 * @return
	 *
	
	 * ENG: Method for setting a gen size
	 * ESP: Funcion para configurar el tamaño de un gen
	 */
	private int tam_gen(double precision, double min, double max) {		
		return (int) Math.ceil((Math.log10(((max-min)/precision)+1)/Math.log10(2)));
	}

	/**
	 * 
	
	 * ENG: Method to initialize the population.
	 * ESP: Funcion para inicializar la poblacion.
	 */
	private void inicializar_poblacion() {
		poblacion=new Individuo[tam_poblacion];

		
		if (funcion_idx<4) { // Binary individuals / Individuos binarios
			for (int i=0;i<tam_poblacion;i++) {
				poblacion[i]=new IndividuoBin(num_genes, tam_genes, funcion.maximos, funcion.minimos);
			}
		} else { // Real individuals / Individuos reales
			for (int i=0;i<tam_poblacion;i++) {
				poblacion[i]=new IndividuoReal(num_genes, decimales);
			}
		}
	}
	
	/**
	 *
	
	 * ENG: Method to evaluate the population.
	 * ESP: Funcion para evaluar la poblacion
	 */
	private void evaluar_poblacion() {
		fitness_total=0;
		prob_seleccion=new double[tam_poblacion];
		prob_seleccionAcum=new double[tam_poblacion];

		double mejor_generacion=(funcion.opt?Double.MIN_VALUE:Double.MAX_VALUE);
		double peor_generacion=(funcion.opt?Double.MAX_VALUE:Double.MIN_VALUE);
		Individuo mejor_generacionInd=null;
		

		// ENG: If they are binary individuals, the real value is calculated.
		// ESP:	Si son individuos binarios se calcula el valor real.
		if (funcion_idx<4) {
			for (int i=0;i<tam_poblacion;i++) 
				poblacion[i].calcular_fenotipo(funcion.maximos, funcion.minimos);
		}
		
		// ENG: Evaluation of each individual, elitism, 
		//		and store the best and worst individual.
		// ESP:	Evaluacion para cada individuo, elitismo, 
		//		y almacenar el mejor y peor individuo.
		double fit;
		for (int i = 0; i < tam_poblacion; i++) {
			fit=funcion.fitness(poblacion[i].fenotipo);
			poblacion[i].fitness=fit;
			fitness_total+=fit;
			
			// ENG: Store the current individual in the elitism queue
			//		(if it is better than the current worst of best individual)
			// ESP:	Almacena el individuo actual en la cola de elitismo 
			//		(si es mejor que el actual peor mejor individuo).
			if(elitQ.size()<tam_elite) elitQ.add(new Node(fit,poblacion[i]));
			else if(tam_elite!=0&&funcion.cmp(elitQ.peek().get_valor(), fit)==fit) {
				elitQ.poll();
				elitQ.add(new Node(fit,poblacion[i]));
			}

			// ENG: Check if it is the best individual
			// ESP:	Coprueba si es el mejor individuo
			if(funcion.cmpBool(fit, mejor_generacion)) {
				mejor_generacion=fit;	
				mejor_generacionInd=poblacion[i];
			}
			peor_generacion = funcion.cmpPeor(peor_generacion, fit);
		}
		
		// ENG: Check if the best individual if the generation is the global best.
		// ESP:	Comprobar si el mejor individuo de la generacion es el mejor global.
		if(funcion.cmpBool(mejor_generacion, mejor_total)) {
			mejor_total=mejor_generacion;	
			mejor_ind=mejor_generacionInd;
		}
		
		// ENG: Graphic
		//	1st line is the global best, 2nd is the generation best, 3rd is the mean.
		// ESP:	Grafico. 
		//	1ra linea es el mejor global, 2da es el mejor de la generacion. 3ra es la media
		progreso_generaciones[0][generacionActual]=mejor_total; // Mejor Absoluto
		progreso_generaciones[1][generacionActual]=mejor_generacion; // Mejor Local
		progreso_generaciones[2][generacionActual++]=fitness_total / tam_poblacion; // Media
		
		
		// ENG: Displacement for removing negative fitness values.
		// ESP:	Desplazamiento para eliminar valores fitness negativos.
		double acum=0;
		if (peor_generacion<0) peor_generacion*=-1;
		
		
		if (!funcion.opt) { // Minimization problem / Problema de minimizacion 
			fitness_total=tam_poblacion*1.05*peor_generacion-fitness_total;
			for (int i=0;i<tam_poblacion;i++) {
				prob_seleccion[i]=1.05*peor_generacion-poblacion[i].fitness;
				prob_seleccion[i]/=fitness_total;
				acum+=prob_seleccion[i];
				prob_seleccionAcum[i]=acum;
			}
		} else { // Maximization problem / Problema de maximizacion
			fitness_total=tam_poblacion*1.05*peor_generacion+fitness_total;
			for (int i=0;i<tam_poblacion;i++) {
				prob_seleccion[i]=1.05*peor_generacion+poblacion[i].fitness;
				prob_seleccion[i]/=fitness_total;
				acum+=prob_seleccion[i];
				prob_seleccionAcum[i]=acum;
			}
		}

	}
	
	/**
	 * 
	
	 * ENG: Method for selecting the evaluating function
	 * ESP: Funcion para seleccionar la funcion de evaluacion
	 */
	private void seleccionar_funcion() {
		switch (funcion_idx) {
			case 0: { funcion=new Evaluacion1(); break; }
			case 1: { funcion=new Evaluacion2(); break; }
			case 2: { funcion=new Evaluacion3(); break; }
			case 3: { funcion=new Evaluacion4(num_genes); break; }
			case 4: { funcion=new Evaluacion5(num_genes); break; }
			default: break;
		}
	}

	/**
	 * 
	 * @return Individuo[]
	 *
	
	 * ENG: Method to select individuals from the population.
	 * ESP: Funcion para seleccionar individuos de la poblacion.
	 */
	private Individuo[] seleccionar_poblacion() {
		Individuo[] ret = null;

		switch (seleccion_idx) {
			case 0:
				ret=seleccion.ruleta(poblacion, prob_seleccionAcum, tam_poblacion-tam_elite);
				break;
			case 1:
				ret=seleccion.torneoDeterministico(poblacion, 3, tam_poblacion-tam_elite);
				break;
			case 2:
				ret=seleccion.torneoProbabilistico(poblacion, 3, 0.9, tam_poblacion-tam_elite);
				break;
			case 3:
				ret=seleccion.estocasticoUniversal1(poblacion, prob_seleccionAcum, tam_poblacion-tam_elite);
				break;
			case 4:
				ret=seleccion.truncamiento(poblacion, prob_seleccion, 0.5, tam_poblacion-tam_elite);
				break;
			case 5:
				ret=seleccion.ranking(poblacion, prob_seleccion, tam_poblacion - tam_elite, 2);
				break;
			case 6:
				ret=seleccion.restos(poblacion, prob_seleccion, prob_seleccionAcum, tam_poblacion-tam_elite);
				break;
			
			default:;
				break;
		}
		return ret;
	}
	
	/**
	 * 
	 * @param selec
	 * @return
	 *
	
	 * ENG: Method to cross a population.
	 * ESP: Funcion para cruzar una poblacion.
	 */
	private Individuo[] cruzar_poblacion(Individuo[] selec) {
		Individuo[] ret = null;

		switch (cruce_idx) {
			case 0:
				if (funcion_idx<4) 	ret=cruce.cruce_monopuntoBin(selec);
				else 				ret=cruce.cruce_monopuntoReal(selec, num_genes);
				break;
			case 1:
				if (funcion_idx<4) 	ret=cruce.cruce_uniformeBin(selec);
				else				ret=cruce.cruce_uniformeReal(selec, num_genes);
				break;
			case 2:
				ret=cruce.cruce_aritmetico(selec, num_genes, 0.6);
				break;
			case 3:
				ret=cruce.cruce_BLX(selec, num_genes, 0.6);
				break;
			default:
				break;
		}

		return ret;
	}
	
	/**
	 * 
	 * @return
	 *
	
	 * ENG: Method to mutated a population
	 * ESP: Funcion para mutar una poblacion
	 */
	private Individuo[] mutar_poblacion() {
		Individuo[] ret = null;

		switch (mut_idx) {
			case 0:
				if (funcion_idx<4) 	ret=mutacion.mut_basicaBin(poblacion);
				else 				ret=mutacion.mut_Real(poblacion, decimales);
				break;
			default: break;
		}
		return ret;
	}

	@SuppressWarnings("unused")
	/**
	 * 
	
	 * ENG: Method for printing the population
	 * ESP: Funcion para imprimir la poblacion
	 */
	private void imprimir_poblacion() {
		int cont=0;
		for (Individuo ind: poblacion) {
			ind.print_individuo();
		}
	}

}


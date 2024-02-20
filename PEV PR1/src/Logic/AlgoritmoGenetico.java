package Logic;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import Model.Gen;
import Model.Individuo;
import Model.IndividuoBin;
import Model.IndividuoReal;
import Model.Valores;
import Utils.FuncionException;
import View.ControlPanel;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("unused")
public class AlgoritmoGenetico {

	// Parametros interfaz
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

	// Init
	private int tam_individuo;
	private int[] tam_genes;
	private Individuo[] poblacion;
	
	//	Evaluacion
	private double fitness_total;
	private double[] prob_seleccion;
	private double[] prob_seleccionAcum;

	// Funciones seleccionadas
	private Funcion funcion;
	private Seleccion seleccion;
	private Cruce cruce;
	private Mutacion mutacion;

	// Grafico
	private int generacionActual;
	private double[][] progreso_generaciones;
	private double mejor_total;
	
	private boolean elitismo;
	
	private int decimales;
	

	private ControlPanel ctrl;

	public AlgoritmoGenetico(ControlPanel ctrl) {
		this.ctrl = ctrl;
	}

	private void setValores(Valores valores) {
		this.tam_poblacion = valores.tam_poblacion;
		this.generaciones = valores.generaciones;
		this.seleccion_idx = valores.seleccion_idx;
		this.cruce_idx = valores.cruce_idx;
		this.prob_cruce = valores.prob_cruce;
		this.mut_idx = valores.mut_idx;
		this.prob_mut = valores.prob_mut;
		this.precision = valores.precision;
		this.funcion_idx = valores.funcion_idx;
		this.num_genes = valores.num_genes;
		this.elitismo= valores.elitismo;
		
		this.decimales=1;
		while(precision!=1) {
			precision*=10;
			decimales*=10;
		}

		funcionSelector();
		seleccion = new Seleccion(tam_poblacion, funcion.opt,funcion_idx);
		cruce = new Cruce(prob_cruce, funcion_idx);
		mutacion = new Mutacion(prob_mut);

		tam_genes = tamGenes();

		mejor_total = (funcion.opt ? Double.MIN_VALUE : Double.MAX_VALUE);
	}

	public void ejecuta(Valores valores) {
		Individuo[] selec=null;
		String fallo ="";
		setValores(valores);

		// valores_inds=new double[tam_poblacion*(generaciones+1)][3];
		progreso_generaciones = new double[3][generaciones + 1];
		generacionActual = 0;

		init_poblacion();
		evaluacion_poblacion();

		//printPoblacion();
		//System.out.println("-------------------------------------------------------------");

		while (generaciones-- != 0) {
			selec = seleccion_poblacion();
			try {
				poblacion = cruce_poblacion(selec);
				poblacion = mutacion_poblacion(); 
			}
			catch (Exception e) {
				fallo = e.getMessage();
				break;
			}
			evaluacion_poblacion();
			//printPoblacion();
			//System.out.println("-------------------------------------------------------------");
		}
		
		if(fallo.equals("")) { // TERMINA LA EJECUCION, MANDA LOS VALORES CALCULADOS AL GRAFICO
			ctrl.actualiza_Grafico(progreso_generaciones, funcion.intervalosGrafico);
		}
		else {
			ctrl.actualiza_fallo(fallo);
		}
	}

	

	private int[] tamGenes() {
		int ret[] = new int[num_genes];
		for (int i = 0; i < num_genes; i++) {
			tam_individuo += ret[i] = tamGen(precision, funcion.minimos[i], funcion.maximos[i]);
		}

		return ret;
	}

	// TODO preguntar si es con ceil
	private int tamGen(double precision, double min, double max) {
		return (int) Math.ceil((Math.log10(((max - min) / precision) + 1) / Math.log10(2)));
	}

	private void init_poblacion() {
		poblacion = new Individuo[tam_poblacion];
		
		if(funcion_idx<4) { 
			for (int i = 0; i < tam_poblacion; i++) {
				poblacion[i] = new IndividuoBin(num_genes, tam_genes, funcion.maximos, funcion.minimos);
			}
		}
		else {
			for (int i = 0; i < tam_poblacion; i++) {
				poblacion[i] = new IndividuoReal(num_genes, decimales);
			}
		}
	}

	private void evaluacion_poblacion() {
		fitness_total = 0;
		prob_seleccion = new double[tam_poblacion];
		prob_seleccionAcum = new double[tam_poblacion];

		double mejor_generacion = (funcion.opt ? Double.MIN_VALUE : Double.MAX_VALUE);
		// double mejor_generacion=0;
		double tmp;
		// if (selected_function != null) // NO HACE FALTA
		
		if(funcion_idx<4) {
			for (int i = 0; i < tam_poblacion; i++) {
				poblacion[i].calcular_fenotipo(funcion.maximos, funcion.minimos);
			}
		}
		
		
		for (int i = 0; i < tam_poblacion; i++) {				
			poblacion[i].fitness = funcion.fitness(poblacion[i].fenotipo);
			fitness_total += poblacion[i].fitness;

			mejor_generacion = funcion.cmp(mejor_generacion, poblacion[i].fitness);
			/*
			 * GRAFICO 3D
			 * valores_inds[pos_valores][0]=poblacion[i].fenotipo[0];
			 * valores_inds[pos_valores][1]=poblacion[i].fenotipo[1];
			 * valores_inds[pos_valores++][2]=fitness[i];
			 */
		}
		
		
		
		mejor_total = funcion.cmp(mejor_total, mejor_generacion);

		progreso_generaciones[0][generacionActual] = mejor_total; // Mejor Absoluto
		progreso_generaciones[1][generacionActual] = mejor_generacion; // Mejor Local
		progreso_generaciones[2][generacionActual++] = fitness_total / tam_poblacion; // Media

		double acum = 0;
		for (int i = 0; i < tam_poblacion; i++) {
			tmp = poblacion[i].fitness / fitness_total;
			prob_seleccion[i] = tmp;
			acum += tmp;
			prob_seleccionAcum[i] = acum;
		}
	}

	private void funcionSelector() {
		switch (funcion_idx) {
			case 0:
				funcion = new Funcion1();
				break;
			case 1:
				funcion = new Funcion2();
				break;
			case 2:
				funcion = new Funcion3();
				break;
			case 3:
				funcion = new Funcion4(num_genes);
				break;
			case 4:
				funcion = new Funcion5();
				break;

			default:
				break;
		}
	}

	private Individuo[] seleccion_poblacion() {
		Individuo[] ret = null;

		switch (seleccion_idx) {
			case 0:
				ret = seleccion.ruleta(poblacion, prob_seleccion, tam_poblacion);
				break;
			case 1:
				ret = seleccion.torneoDeterministico(poblacion, prob_seleccionAcum, 3, tam_poblacion);
				break;
			case 2:
				ret = seleccion.torneoProbabilistico(poblacion, prob_seleccionAcum, 3, 0.9, tam_poblacion);
				break;
			case 3:
				ret = seleccion.estocasticoUniversal1(poblacion, prob_seleccionAcum, tam_poblacion);
				break;
			case 4:
				ret = seleccion.estocasticoUniversal2(poblacion, prob_seleccionAcum, tam_poblacion);
				break;
				
			case 5:
				ret = seleccion.truncamiento(poblacion, prob_seleccion, 0.5, tam_poblacion);
				break;
				
			case 6:
				ret = seleccion.restos(poblacion, prob_seleccion, prob_seleccionAcum, tam_poblacion);
				break;
			
			default:
				break;
		}
		return ret;
	}

	private Individuo[] cruce_poblacion(Individuo[] selec) {
		Individuo[] ret = null;

		switch (cruce_idx) {
			case 0:
				if(funcion_idx<4) ret = cruce.cruce_monopuntoBin(selec);
				else ret = cruce.cruce_monopuntoReal(selec, num_genes);
				break;
			case 1:
				if(funcion_idx<4) ret = cruce.cruce_uniformeBin(selec);
				else ret=cruce.cruce_uniformeReal(selec, num_genes);
				break;
			case 2:
				if(funcion_idx<4) throw new FuncionException("No hay C. Arit en Bin");
				else ret = cruce.cruce_aritmetico(selec);
				break;
			case 3:
				if(funcion_idx<4) throw new FuncionException("No hay C. BLX en Bin");
				else ret = cruce.cruce_BLX(selec);
				break;
			default:
				break;
		}

		return ret;
	}

	private Individuo[] mutacion_poblacion() {
		Individuo[] ret = null; 
		
		switch (mut_idx) {
			case 0:
				if(funcion_idx<4) ret=mutacion.mut_basicaBin(poblacion);
				else ret=mutacion.mut_basicaReal(poblacion, decimales);
				break;
			default:
				break;
		}
		// System.out.println("Proceso de mutacion terminado: ");
		// System.out.println();
		// printPoblacion();
		return ret;
	}

	private void printPoblacion() {
		int cont = 0;
		for (Individuo ind : poblacion) {
			ind.printIndividuo();
		}
	}

}

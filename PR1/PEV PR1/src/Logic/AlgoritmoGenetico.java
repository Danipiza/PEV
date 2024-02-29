package Logic;

import java.util.List;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.Function;
import Utils.Pair;

import Model.Gen;
import Model.Individuo;
import Model.IndividuoBin;
import Model.IndividuoReal;
import Model.Valores;
import Utils.FuncionException;
import Utils.Node;
import View.ControlPanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

	// Evaluacion
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

	private int elitismo;
	private int tam_elite;
	PriorityQueue<Node> elitQ;

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
		this.elitismo = valores.elitismo;
		if(funcion_idx<3)num_genes=2;

		double tmp = precision * 10;
		this.decimales = 10;
		while (tmp != 1) {
			tmp *= 10;
			decimales *= 10;
		}
		
		tam_elite=(int) (tam_poblacion*(elitismo/100.0));

		funcionSelector();
		seleccion = new Seleccion(tam_poblacion, funcion.opt, funcion_idx);
		cruce = new Cruce(prob_cruce, funcion_idx,tam_elite);
		mutacion = new Mutacion(prob_mut,tam_elite);

		if (funcion_idx < 4) tam_genes = tamGenes();

		mejor_total = (funcion.opt ? Double.MIN_VALUE : Double.MAX_VALUE);
	}

	public void ejecuta(Valores valores) {
		Individuo[] selec = null;
				
		
		String fallo = "";
		setValores(valores);
		
		Comparator<Node> comparator = Comparator.comparingDouble(Node::getValue);
		if(funcion_idx!=0) {
			elitQ = new PriorityQueue<>(Collections.reverseOrder(comparator));
		}
		else {
			elitQ = new PriorityQueue<>(comparator);
		}
		
		// valores_inds=new double[tam_poblacion*(generaciones+1)][3];
		progreso_generaciones = new double[3][generaciones + 1];
		generacionActual = 0;

		init_poblacion();
		evaluacion_poblacion();

		// printPoblacion();
		// System.out.println("-------------------------------------------------------------");

		while (generaciones-- != 0) {
			selec = seleccion_poblacion();
			try {
				poblacion = cruce_poblacion(selec);
				poblacion = mutacion_poblacion();
				while(elitQ.size()!=0) {
					//elitQ.poll();
					poblacion[tam_poblacion-elitQ.size()]=elitQ.poll().getId();
				}
			} catch (Exception e) {
				fallo = e.getMessage();
				break;
			}
			evaluacion_poblacion();
			// printPoblacion();
			// System.out.println("-------------------------------------------------------------");
		}

		if (fallo.equals("")) { // TERMINA LA EJECUCION, MANDA LOS VALORES CALCULADOS AL GRAFICO
			ctrl.actualiza_Grafico(progreso_generaciones, funcion.intervalosGrafico);
		} else {
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

	private int tamGen(double precision, double min, double max) {
		return (int) Math.ceil((Math.log10(((max - min) / precision) + 1) / Math.log10(2)));
	}

	private void init_poblacion() {
		poblacion = new Individuo[tam_poblacion];

		if (funcion_idx < 4) {
			for (int i = 0; i < tam_poblacion; i++) {
				poblacion[i] = new IndividuoBin(num_genes, tam_genes, funcion.maximos, funcion.minimos);
			}
		} else {
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
		double peor_generacion = (funcion.opt ? Double.MAX_VALUE : Double.MIN_VALUE);

		double tmp;

		if (funcion_idx < 4) {
			for (int i = 0; i < tam_poblacion; i++) {
				poblacion[i].calcular_fenotipo(funcion.maximos, funcion.minimos);
			}
		}

		double fitnessTotalAdaptado = 0;
		double fit;
		for (int i = 0; i < tam_poblacion; i++) {
			fit=funcion.fitness(poblacion[i].fenotipo);
			poblacion[i].fitness = fit;
			fitness_total += fit;
			
			if(elitQ.size()<tam_elite) elitQ.add(new Node(fit,poblacion[i]));
			else if(tam_elite!=0&&funcion.cmp(elitQ.peek().getValue(), fit)==fit) {
				elitQ.poll();
				elitQ.add(new Node(fit,poblacion[i]));
			}

			mejor_generacion = funcion.cmp(mejor_generacion, fit);
			peor_generacion = funcion.cmpPeor(peor_generacion, fit);
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
		if (peor_generacion < 0)
			peor_generacion *= -1;

		if (!funcion.opt) {
			fitness_total = tam_poblacion * 1.05 * peor_generacion - fitness_total;
			for (int i = 0; i < tam_poblacion; i++) {
				prob_seleccion[i] = 1.05 * peor_generacion - poblacion[i].fitness;
				prob_seleccion[i] /= fitness_total;
				acum += prob_seleccion[i];
				prob_seleccionAcum[i] = acum;
			}
		} else {
			fitness_total = tam_poblacion * 1.05 * peor_generacion + fitness_total;
			for (int i = 0; i < tam_poblacion; i++) {
				prob_seleccion[i] = 1.05 * peor_generacion + poblacion[i].fitness;
				prob_seleccion[i] /= fitness_total;
				acum += prob_seleccion[i];
				prob_seleccionAcum[i] = acum;
			}
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
				funcion = new Funcion5(num_genes);
				break;

			default:
				break;
		}
	}

	private Individuo[] seleccion_poblacion() {
		Individuo[] ret = null;

		switch (seleccion_idx) {
			case 0:
				ret = seleccion.ruleta(poblacion, prob_seleccion, tam_poblacion-tam_elite);
				break;
			case 1:
				ret = seleccion.torneoDeterministico(poblacion, 3, tam_poblacion-tam_elite);
				break;
			case 2:
				ret = seleccion.torneoProbabilistico(poblacion, 3, 0.9, tam_poblacion-tam_elite);
				break;
			case 3:
				ret = seleccion.estocasticoUniversal1(poblacion, prob_seleccionAcum, tam_poblacion-tam_elite);
				break;
			case 4:
				ret = seleccion.estocasticoUniversal2(poblacion, prob_seleccionAcum, tam_poblacion-tam_elite);
				break;
			case 5:
				ret = seleccion.truncamiento(poblacion, prob_seleccion, 0.5, tam_poblacion-tam_elite);
				break;
			case 6:
				ret = seleccion.restos(poblacion, prob_seleccion, prob_seleccionAcum, tam_poblacion-tam_elite);
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
				if (funcion_idx < 4)
					ret = cruce.cruce_monopuntoBin(selec);
				else
					ret = cruce.cruce_monopuntoReal(selec, num_genes);
				break;
			case 1:
				if (funcion_idx < 4)
					ret = cruce.cruce_uniformeBin(selec);
				else
					ret = cruce.cruce_uniformeReal(selec, num_genes);
				break;
			case 2:
				if (funcion_idx < 4)
					throw new FuncionException("No hay C. Arit en Bin");
				else
					ret = cruce.cruce_aritmetico(selec, num_genes, 0.6);
				break;
			case 3:
				if (funcion_idx < 4)
					throw new FuncionException("No hay C. BLX en Bin");
				else
					ret = cruce.cruce_BLX(selec, num_genes, 0.6);
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
				if (funcion_idx < 4)
					ret = mutacion.mut_basicaBin(poblacion);
				else
					ret = mutacion.mut_Real(poblacion, decimales);
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

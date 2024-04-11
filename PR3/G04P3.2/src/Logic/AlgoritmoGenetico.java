package Logic;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.Function;
import Utils.Pair;

import Model.Individuo;
import Model.Valores;
import Utils.FuncionException;
import Utils.Node;
import View.ControlPanel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
	private Individuo mejor_individuo;

	private int elitismo;
	private int tam_elite;
	PriorityQueue<Node> elitQ;

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
		this.funcion_idx = valores.funcion_idx;
		this.elitismo = valores.elitismo;

		tam_elite = (int) (tam_poblacion * (elitismo / 100.0));

		seleccion = new Seleccion(tam_poblacion, tam_elite);
		cruce = new Cruce(prob_cruce, tam_elite);
		mutacion = new Mutacion(prob_mut, funcion);


		mejor_total = Double.MAX_VALUE;
	}

	public void ejecuta(Valores valores) {
		Individuo[] selec = null;

		String fallo = "";
		setValores(valores);

		// ELITISMO
		Comparator<Node> comparator = Comparator.comparingDouble(Node::getValue);
		elitQ = new PriorityQueue<>(Collections.reverseOrder(comparator));

		// valores_inds=new double[tam_poblacion*(generaciones+1)][3];
		progreso_generaciones = new double[4][generaciones + 1];
		generacionActual = 0;

		//init_poblacion();

		//evaluacion_poblacion();

		int cont=1;
		while (generaciones-- != 0) {
			
			/*selec = seleccion_poblacion();
			try {
				poblacion = cruce_poblacion(selec);
				poblacion = mutacion_poblacion();

				// ELITISMO
				while (elitQ.size() != 0) {
					poblacion[tam_poblacion - elitQ.size()] = elitQ.poll().getId();
				}
			} catch (Exception e) {
				fallo = e.getMessage();
				break;
			}
			evaluacion_poblacion();*/
			cont++;
		}

		if (fallo.equals("")) { // TERMINA LA EJECUCION, MANDA LOS VALORES CALCULADOS AL GRAFICO
			ctrl.actualiza_Grafico(progreso_generaciones, funcion, mejor_individuo);
		} else {
			ctrl.actualiza_fallo(fallo);
		}
	}
	
	/*private Individuo[] reinicia(int porcentaje) {
		Individuo[] ret= new Individuo[tam_poblacion];
		int tam=tam_poblacion/porcentaje;
		for (int i = tam; i < tam_poblacion; i++) {
			ret[i] = new Individuo(num_vuelos);
		}
		Comparator<Node> comparator = Comparator.comparingDouble(Node::getValue);
		PriorityQueue<Node> Q = new PriorityQueue<>(Collections.reverseOrder(comparator));
		
		
		for(Individuo ind: poblacion) {
			if(Q.size()<tam) Q.add(new Node(ind.fitness, ind));
			else if (funcion.cmp(Q.peek().getValue(), ind.fitness) == ind.fitness) {
				Q.poll();
				Q.add(new Node(ind.fitness, ind));
			}
		}
		
		for(int i=0;i<tam;i++) {
			ret[i]=Q.peek().getId();
		}
		
		
		
		return ret;
	}*/
	
	private void init_poblacion() {
		poblacion = new Individuo[tam_poblacion];
		for (int i = 0; i < tam_poblacion; i++) {
			
		}

	}

	private void evaluacion_poblacion() {
		fitness_total = 0;
		prob_seleccion = new double[tam_poblacion];
		prob_seleccionAcum = new double[tam_poblacion];

		double mejor_generacion = (Double.MAX_VALUE);
		double peor_generacion = (Double.MIN_VALUE);

		double tmp;

		double fitnessTotalAdaptado = 0;
		double fit;
		int indexMG = 0;
		for (int i = 0; i < tam_poblacion; i++) {
			fit = 0;//funcion.fitness(poblacion[i].gen.v); // TODO
			poblacion[i].fitness = fit;
			fitness_total += fit;

			if (elitQ.size() < tam_elite)
				elitQ.add(new Node(fit, poblacion[i]));
			else if (tam_elite != 0 && funcion.cmp(elitQ.peek().getValue(), fit) == fit) {
				elitQ.poll();
				elitQ.add(new Node(fit, poblacion[i]));
			}

			if (mejor_generacion > fit) {
				mejor_generacion = fit;
				indexMG = i;
			}
			// mejor_generacion = funcion.cmp(mejor_generacion, fit);
			peor_generacion = funcion.cmpPeor(peor_generacion, fit);
		}

		if (mejor_total > mejor_generacion) {
			mejor_total = mejor_generacion;
			// mejor_individuo=poblacion[indexMG];
			mejor_individuo = new Individuo(poblacion[indexMG]);
		}
		// mejor_total = funcion.cmp(mejor_total, mejor_generacion);
		
		
		progreso_generaciones[0][generacionActual] = mejor_total; // Mejor Absoluto
		progreso_generaciones[1][generacionActual] = mejor_generacion; // Mejor Local
		progreso_generaciones[2][generacionActual] = fitness_total / tam_poblacion; // Media

		double acum = 0;
		if (peor_generacion < 0)
			peor_generacion *= -1;
		
		// No usamos porque cambia a peor los resultados
		double P=2;
		double media=fitness_total/tam_poblacion;
		double a=((P-1)*media)/(peor_generacion-media);
		double b=(1-a)*media;
		
		fitness_total=0;
		for (int i = 0; i < tam_poblacion; i++) {
			//fit= a*poblacion[i].fitness+b;
			fit= poblacion[i].fitness;
			poblacion[i].fitness=fit;
			fitness_total += fit;
			
			if (elitQ.size() < tam_elite)
				elitQ.add(new Node(fit, poblacion[i]));
			else if (tam_elite != 0 && funcion.cmp(elitQ.peek().getValue(), fit) == fit) {
				elitQ.poll();
				elitQ.add(new Node(fit, poblacion[i]));
			}

			if (mejor_generacion > fit) {
				mejor_generacion = fit;
				indexMG = i;
			}
			peor_generacion = funcion.cmpPeor(peor_generacion, fit);
		}
		
		
		// Desplazamiento
		fitness_total = tam_poblacion * 1.05 * peor_generacion - fitness_total;
		for (int i = 0; i < tam_poblacion; i++) {
			prob_seleccion[i] = 1.05 * peor_generacion - poblacion[i].fitness;
			prob_seleccion[i] /= fitness_total;
			acum += prob_seleccion[i];
			prob_seleccionAcum[i] = acum;
		}
		
		
		// Escalado lineal
		
		
		progreso_generaciones[3][generacionActual++] = tam_poblacion*prob_seleccion[indexMG]; // Media

	}

	private Individuo[] seleccion_poblacion() {
		Individuo[] ret = null;

		switch (seleccion_idx) {
			case 0:
				ret = seleccion.ruleta(poblacion, prob_seleccionAcum, tam_poblacion - tam_elite);
				break;
			case 1:
				ret = seleccion.torneoDeterministico(poblacion, 20, tam_poblacion - tam_elite);
				break;
			case 2:
				ret = seleccion.torneoProbabilistico(poblacion, 3, 0.9, tam_poblacion - tam_elite);
				break;
			case 3:
				ret = seleccion.estocasticoUniversal1(poblacion, prob_seleccionAcum, tam_poblacion - tam_elite);
				break;
			case 4:
				ret = seleccion.estocasticoUniversal2(poblacion, prob_seleccionAcum, tam_poblacion - tam_elite);
				break;
			case 5:
				ret = seleccion.truncamiento(poblacion, prob_seleccion, 0.5, tam_poblacion - tam_elite);
				break;
			case 6:
				ret = seleccion.restos(poblacion, prob_seleccion, prob_seleccionAcum, tam_poblacion - tam_elite);
				break;
			case 7:
				ret = seleccion.ranking(poblacion, prob_seleccion, tam_poblacion - tam_elite, 2);
				break;
			default:
				break;
		}
		return ret;
	}

	private Individuo[] cruce_poblacion(Individuo[] selec) {
		Individuo[] ret = null;

		ret=cruce.intercambio(selec);

		return ret;
	}

	private Individuo[] mutacion_poblacion() {
		Individuo[] ret = null;

		switch (mut_idx) {
			case 0:
				ret = mutacion.terminal(poblacion);
				break;
			case 1:
				ret = mutacion.funcional(poblacion);
				break;
			case 2:
				ret = mutacion.arbol(poblacion);
				break;
			case 3:
				ret = mutacion.permutacion(poblacion);
				break;
			case 4:
				// TODO MAS?
				break;
			default:
				break;
		}
		return ret;
	}

	private void printPoblacion() {
		int cont = 0;
		for (Individuo ind : poblacion) {
			ind.printIndividuo();
		}
	}
	
	
	
	public void ejecuta_calculo_medias(Valores valores) {
		
		
	}


}
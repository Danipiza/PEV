package Logic;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.Function;
import Utils.Pair;

import Model.Individuo;
import Model.MejorIndividuo;
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

	// NUEVO
	private int num_vuelos;
	private int num_pistas;

	private String[] vuelos_id;

	private int[][] TEL;
	private int[] tipo_avion;

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

		lee_archivos();
		seleccion = new Seleccion(tam_poblacion, tam_elite, funcion_idx);
		cruce = new Cruce(prob_cruce, num_vuelos, tam_elite);
		mutacion = new Mutacion(prob_mut, num_vuelos, tam_elite, funcion);

		ctrl.set_valores(num_vuelos, num_pistas, vuelos_id, TEL, tipo_avion);

		mejor_total = Double.MAX_VALUE;
	}

	public void ejecuta(Valores valores) {
		Individuo[] selec = null;

		String fallo = "";
		setValores(valores);

		// ELITISMO
		Comparator<Node> comparator = Comparator.comparingDouble(Node::getValue);
		if (funcion_idx != 0) {
			elitQ = new PriorityQueue<>(Collections.reverseOrder(comparator));
		} else
			elitQ = new PriorityQueue<>(comparator);

		// valores_inds=new double[tam_poblacion*(generaciones+1)][3];
		progreso_generaciones = new double[4][generaciones + 1];
		generacionActual = 0;

		init_poblacion();

		evaluacion_poblacion();


		while (generaciones-- != 0) {
			selec = seleccion_poblacion();
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
			evaluacion_poblacion();
		}

		if (fallo.equals("")) { // TERMINA LA EJECUCION, MANDA LOS VALORES CALCULADOS AL GRAFICO
			ctrl.actualiza_Grafico(progreso_generaciones, funcion.intervalosGrafico, funcion, mejor_individuo);
		} else {
			ctrl.actualiza_fallo(fallo);
		}
	}
	
	public void ejecuta_calculo_medias(Valores valores) {
		double media=0.0;
		String[] cruce = { 	"PMX",
				"OX",
				"OX-PP",
				"CX",
				"CO",
				"Custom..."};

		String[] mutacion = { 	"Insercion",
							"Intercambio",
							"Inversion",
							"Heuristica",
							"Custom..."};
		
		System.out.println("Insercion");
		
		while(valores.cruce_idx!=6) {	
			media=0.0;
			for(int i=0;i<20;i++) {
				Individuo[] selec = null;
		
				
				String fallo = "";
				setValores(valores);
				
				// ELITISMO
				Comparator<Node> comparator = Comparator.comparingDouble(Node::getValue);
				if (funcion_idx != 0) {
					elitQ = new PriorityQueue<>(Collections.reverseOrder(comparator));
				} else
					elitQ = new PriorityQueue<>(comparator);
		
				// valores_inds=new double[tam_poblacion*(generaciones+1)][3];
				progreso_generaciones = new double[4][generaciones + 1];
				generacionActual = 0;
		
				init_poblacion();
				evaluacion_poblacion();
				while (generaciones-- != 0) {
					selec = seleccion_poblacion();
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
					evaluacion_poblacion();
				}
		
				if (fallo.equals("")) { 
					media+=mejor_total;
				} else {
					System.out.println("Fallo");
					System.exit(0);
				}
			}
			media/=20;
			System.out.println("\t"+mutacion[valores.mut_idx]+", media de: " + media);
			valores.mut_idx++;
			if(valores.mut_idx==5) {
				valores.mut_idx=0;
				valores.cruce_idx++;
				if(valores.cruce_idx!=6) System.out.println(cruce[valores.cruce_idx]);
			}
		}
	}

	private void lee_archivos() {
		String vuelos_txt = "data/", TEL_txt = "data/";

		// LEE DE LOS TXT
		if (funcion_idx == 0) {
			this.num_vuelos = 12;
			this.num_pistas = 3;
			vuelos_txt += "vuelos1.txt";
			TEL_txt += "TEL1.txt";
		} else if(funcion_idx==1) {
			this.num_vuelos = 25;
			this.num_pistas = 5;
			vuelos_txt += "vuelos2.txt";
			TEL_txt += "TEL2.txt";
		}
		else {
			this.num_vuelos = 100;
			this.num_pistas = 10;
			vuelos_txt += "vuelos3.txt";
			TEL_txt += "TEL3.txt";
		}
		int i = 0, j;
		vuelos_id = new String[num_vuelos];
		tipo_avion = new int[num_vuelos];
		TEL = new int[num_pistas][num_vuelos];
		try {
			// Open the file
			BufferedReader vuelos_reader = new BufferedReader(new FileReader(vuelos_txt));
			BufferedReader TEL_reader = new BufferedReader(new FileReader(TEL_txt));

			String line;
			while ((line = vuelos_reader.readLine()) != null) {
				String[] tokens = line.split("\\s+");
				vuelos_id[i] = tokens[0];
				if (tokens[1].equals("W"))
					tipo_avion[i] = 0;
				else if (tokens[1].equals("G"))
					tipo_avion[i] = 1;
				else
					tipo_avion[i] = 2;
				i++;
			}
			i = 0;
			while ((line = TEL_reader.readLine()) != null) {
				String[] tokens = line.split("\t");
				j = 0;
				for (String t : tokens) {
					TEL[i][j++] = Integer.parseInt(t);
				}
				i++;
			}

			vuelos_reader.close();
			TEL_reader.close();
		} catch (IOException e) {
			System.err.println("Error al leer archivos: " + e.getMessage());
		}
		funcion = new Funcion(num_vuelos, num_pistas, tipo_avion, TEL);
		
		
	}

	private void init_poblacion() {
		poblacion = new Individuo[tam_poblacion];
		for (int i = 0; i < tam_poblacion; i++) {
			poblacion[i] = new Individuo(num_vuelos);
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
			fit = funcion.fitness(poblacion[i].gen.v);
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

		switch (cruce_idx) {
			case 0:
				ret = cruce.PMX(selec);
				break;
			case 1:
				ret = cruce.OX(selec);
				break;
			case 2:
				ret = cruce.OX_PP(selec, 3);
				break;
			case 3:
				ret = cruce.CX(selec);
				break;
			case 4:
				ret = cruce.CO(selec);
				break;
			case 5:
				ret = cruce.custom(selec);
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
				ret = mutacion.insercion(poblacion);
				break;
			case 1:
				ret = mutacion.intercambio(poblacion);
				break;
			case 2:
				ret = mutacion.inversion(poblacion);
				break;
			case 3:
				ret = mutacion.heuristica(poblacion, 3);
				break;
			case 4:
				ret = mutacion.custom(poblacion);
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

}

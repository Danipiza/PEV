package Logic;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.Function;
import Utils.Pair;

import Model.IndividuoReal;
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
	private IndividuoReal[] poblacion;

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

	private ControlPanel ctrl;
	
	
	// NUEVO
	private int num_vuelos;
	private int num_pistas;
	
	private String[] vuelos_id;
	private Map<Integer, String> vuelos_map; 
	
	private int[][] TEL;
	

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

		tam_elite=(int) (tam_poblacion*(elitismo/100.0));

		seleccion = new Seleccion(tam_poblacion, funcion_idx);
		cruce = new Cruce(prob_cruce, funcion_idx,tam_elite);
		mutacion = new Mutacion(prob_mut,tam_elite);
		lee_archivos();
		

		// TODO
		mejor_total = (true ? Double.MIN_VALUE : Double.MAX_VALUE);
	}

	public void ejecuta(Valores valores) {
		IndividuoReal[] selec = null;				
		
		String fallo = "";
		setValores(valores);
		
		// ELITISMO
		/* Comparator<Node> comparator = Comparator.comparingDouble(Node::getValue);
		if(funcion_idx!=0) {
			elitQ = new PriorityQueue<>(Collections.reverseOrder(comparator));
		}
		else elitQ = new PriorityQueue<>(comparator);*/
		
		// valores_inds=new double[tam_poblacion*(generaciones+1)][3];
		progreso_generaciones = new double[3][generaciones + 1];
		generacionActual = 0;

		
		init_poblacion();
		/*evaluacion_poblacion();


		while (generaciones-- != 0) {
			selec = seleccion_poblacion();
			try {
				poblacion = cruce_poblacion(selec);
				poblacion = mutacion_poblacion();
				
				// ELITISMO
				/*while(elitQ.size()!=0) {
					poblacion[tam_poblacion-elitQ.size()]=elitQ.poll().getId();
				}
			} catch (Exception e) {
				fallo = e.getMessage();
				break;
			}
			evaluacion_poblacion();
		}

		if (fallo.equals("")) { // TERMINA LA EJECUCION, MANDA LOS VALORES CALCULADOS AL GRAFICO
			ctrl.actualiza_Grafico(progreso_generaciones, funcion.intervalosGrafico);
		} else {
			ctrl.actualiza_fallo(fallo);
		}*/
	}

	
	private void lee_archivos() {
		String vuelos_txt= "data/", TEL_txt="data/";
		
		// LEE DE LOS TXT
		if (funcion_idx==0) {
			this.num_vuelos=12;
			this.num_pistas=3;
			vuelos_txt+="vuelos1.txt";
			TEL_txt+="TEL1.txt";
		}
		else {
			this.num_vuelos=25;
			this.num_pistas=5;
			vuelos_txt+="vuelos2.txt";
			TEL_txt+="TEL2.txt";
		}
		int i=0,j;
		vuelos_id=new String[num_vuelos];
		vuelos_map=new HashMap<Integer, String>();
		TEL=new int[num_pistas][num_vuelos];
		try {
            // Open the file
            BufferedReader vuelos_reader = new BufferedReader(new FileReader(vuelos_txt));
            BufferedReader TEL_reader = new BufferedReader(new FileReader(TEL_txt));
            
            String line;
            while ((line = vuelos_reader.readLine()) != null) {
                String[] tokens = line.split("\\s+");
            	vuelos_map.put(i,tokens[1]);
            	vuelos_id[i++]=tokens[0];
            }
            i=0; 
            while ((line = TEL_reader.readLine()) != null) {
                String[] tokens = line.split("\t");
                j=0;
                for(String t: tokens) {
                	TEL[i][j++]=Integer.parseInt(t);
                }
                i++;
            }

            
            vuelos_reader.close();
            TEL_reader.close();
        } catch (IOException e) {
            System.err.println("Error al leer archivos: " + e.getMessage());
        }
		
		/*for(String v:vuelos_id) {
			System.out.println(v);
		}*/
		
		for(i=0;i<num_pistas;i++) {
			for(j=0;j<num_vuelos;j++) {
				System.out.print(TEL[i][j] + " ");
			}
			System.out.println();
		}
	}

	

	private void init_poblacion() {
		poblacion = new IndividuoReal[tam_poblacion];
		
		
	}

	private void evaluacion_poblacion() {
		/*fitness_total = 0;
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
	 	*/
	}

	
	private IndividuoReal[] seleccion_poblacion() {
		IndividuoReal[] ret = null;

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
			case 7:
				ret = seleccion.ranking(poblacion, prob_seleccion, prob_seleccionAcum, tam_poblacion-tam_elite);
				break;
			default:
				break;
		}
		return ret;
	}

	private IndividuoReal[] cruce_poblacion(IndividuoReal[] selec) {
		IndividuoReal[] ret = null;

		switch (cruce_idx) {
			case 0:				
				ret = cruce.aritmetico(selec, num_genes, 0.6);
				break;
			case 1:
				ret = cruce.BLX(selec, num_genes, 0.6);
				break;
			case 2:				
				ret = cruce.PMX(selec);
				break;
			case 3:				
				ret = cruce.OX(selec);
				break;
			case 4:				
				ret = cruce.OX_PP(selec);	
				break;
			case 5:				
				ret = cruce.CX(selec);
				break;
			case 6:				
				ret = cruce.CO(selec);
				break;
			case 7:				
				ret = cruce.custom(selec);
				break;
			default:
				break;
		}

		return ret;
	}

	private IndividuoReal[] mutacion_poblacion() {
		IndividuoReal[] ret = null;

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
				ret = mutacion.heuristica(poblacion);				
				break;
			case 4:				
				ret = mutacion.custom(poblacion);				
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
		for (IndividuoReal ind : poblacion) {
			ind.printIndividuo();
		}
	}

	
	/*private void funcionSelector() {
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
}*/

}

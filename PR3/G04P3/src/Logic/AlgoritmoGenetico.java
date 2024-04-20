package Logic;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import Utils.Pair;
import Model.Individuo;
import Model.IndividuoArbol;
import Model.IndividuoGramatica;
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
	private int ini_idx;
	private int seleccion_idx;
	private int cruce_idx;
	private double prob_cruce;
	private int mut_idx;
	private double prob_mut;	
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

	private int ticks;
	
	private ControlPanel ctrl;
	
	// Nuevos
	private int modo;	
	
	private int profundidad;
	private int filas;
	private int columnas;	
	
	
	private int long_cromosoma;
	
	private int bloating_idx;
	
	private boolean[] opcs;
	private int numOPopcT;
	private int numOPopcF;
	
	private double mediaTam;
	private int nGen;
	private int nGenElim;

	public AlgoritmoGenetico(ControlPanel ctrl) {
		this.ctrl = ctrl;
	}

	private void setValores(Valores valores) {
		this.tam_poblacion = valores.tam_poblacion;
		this.generaciones = valores.generaciones;
		this.ini_idx = valores.ini_idx;
		this.seleccion_idx = valores.seleccion_idx;
		this.cruce_idx = valores.cruce_idx;
		this.prob_cruce = valores.prob_cruce;
		this.mut_idx = valores.mut_idx;
		this.prob_mut = valores.prob_mut;
		
		this.profundidad=valores.prof_O_longCrom;
		long_cromosoma=valores.prof_O_longCrom;
		
		this.modo=valores.modo;
		
		this.filas=valores.filas;
		this.columnas=valores.columnas;
		this.elitismo = valores.elitismo;
		this.ticks= valores.ticks;
		this.opcs=valores.opcs;
		
		this.nGen=valores.nGen;
		this.nGenElim=valores.nGenElim;
		if(this.nGen==0) nGen=-1;
		if(this.nGenElim==0) nGenElim=-1;
		
		
		this.numOPopcT=0;
		this.numOPopcF=0;
		int opcN=opcs.length-2;
		for(int i=1;i<4;i++) {
			if(opcs[i]) numOPopcT++;
		}
		for(int i=4;i<opcN;i++) {
			if(opcs[i]) numOPopcF++;
		}

		tam_elite = (int) (tam_poblacion * (elitismo / 100.0));

		funcion = new Funcion(filas, columnas, ticks, opcs[0]);
		
		seleccion = new Seleccion(tam_poblacion, tam_elite, modo);
		cruce = new Cruce(prob_cruce, tam_elite);
		mutacion = new Mutacion(prob_mut, tam_elite, funcion,filas, columnas,opcs,numOPopcF,numOPopcT);		
		
		
		bloating_idx=valores.bloating_idx;

		mejor_total = Double.MIN_VALUE;
	}

	// TODO JUNTAR LOS EJECUTA EN 1 FUNCION
	public void ejecutaArbol(Valores valores) {
		Individuo[] selec = null;

		String fallo = "";
		setValores(valores);

		// ELITISMO
		Comparator<Node> comparator = Comparator.comparingDouble(Node::getValue);
		elitQ = new PriorityQueue<>(comparator);

		progreso_generaciones = new double[4][generaciones + 1];
		generacionActual = 0;
			
		
		init_poblacion(0);

		evaluacion_poblacion(1);

		int cont=1;
		while (generaciones-- != 0) {
			if(nGen!=-1&&cont%nGen==0) {
				reset(0);
				evaluacion_poblacion(0);
			}
			if(nGenElim!=-1&&cont%nGenElim==0) {
				resetElim(0);
				evaluacion_poblacion(0);
			}
			
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
			
			
			evaluacion_poblacion(1);
			cont++;
		}

		if (fallo.equals("")) { // TERMINA LA EJECUCION, MANDA LOS VALORES CALCULADOS AL GRAFICO
			ctrl.actualiza_Grafico(progreso_generaciones, funcion, mejor_individuo, filas,columnas);
		} else {
			ctrl.actualiza_fallo(fallo);
		}
	}
	
	public void ejecutaGramatica(Valores valores) {
		Individuo[] selec = null;

		String fallo = "";
		setValores(valores);

		// ELITISMO
		Comparator<Node> comparator = Comparator.comparingDouble(Node::getValue);
		elitQ = new PriorityQueue<>(comparator);

		progreso_generaciones = new double[4][generaciones + 1];
		generacionActual = 0;
		
		init_poblacionG(0);

		evaluacion_poblacion(1);

		int cont=1;
		while (generaciones-- != 0) {
			if(nGen!=-1&&cont%nGen==0) {
				reset(1);
				evaluacion_poblacion(0);
			}
			if(nGenElim!=-1&&cont%nGenElim==0) {
				resetElim(1);
				evaluacion_poblacion(0);
			}
			
			selec = seleccion_poblacion();
			
			try {
				poblacion = cruce_poblacionG(selec, long_cromosoma);
				poblacion = mutacion_poblacionG();

				// ELITISMO
				while (elitQ.size() != 0) {
					poblacion[tam_poblacion - elitQ.size()] = elitQ.poll().getId();
				}
			} catch (Exception e) {
				fallo = e.getMessage();
				break;
			}
			evaluacion_poblacion(1);
			cont++;
		}

		if (fallo.equals("")) { // TERMINA LA EJECUCION, MANDA LOS VALORES CALCULADOS AL GRAFICO
			ctrl.actualiza_Grafico(progreso_generaciones, funcion, mejor_individuo, filas,columnas);
		} else {
			ctrl.actualiza_fallo(fallo);
		}
	}
	
	public Individuo[] cruce_poblacionG(Individuo[] selec, int d) {
		int n = selec.length;
		Individuo[] ret = new Individuo[n+tam_elite];
		//n-=tam_elite;
		
		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}

		int corte_maximo = d - 1;
		
		Random random=new Random();

		int i = 0, j = 0;
		IndividuoGramatica ind1, ind2;
		int corte, tmp;
		while (i < n) {
			ind1 = (IndividuoGramatica) selec[i];
			ind2 = (IndividuoGramatica) selec[i+1];
			if (Math.random() < prob_cruce) {
				corte = (int) (Math.random() * (corte_maximo)) + 1;

				for (j = 0; j < corte; j++) {
					tmp = ind1.cromosoma[j];
					ind1.cromosoma[j] = ind2.cromosoma[j];
					ind2.cromosoma[j] = tmp;
				}				
				
			}
			ret[i++]= new IndividuoGramatica(ind1.cromosoma, filas, columnas, opcs, numOPopcF,numOPopcT);
			ret[i++] = new IndividuoGramatica(ind2.cromosoma, filas, columnas, opcs, numOPopcF,numOPopcT);
		}
		return ret;
	}
	
	public Individuo[] mutacion_poblacionG() {				
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		Random random=new Random();
		
		IndividuoGramatica act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=(IndividuoGramatica)poblacion[i];
			
			if(Math.random()<prob_mut) {
				int rand=random.nextInt(long_cromosoma);
				act.cromosoma[rand]=random.nextInt(256);
			}
			ret[i]=new IndividuoGramatica(act.cromosoma, filas, columnas, opcs, numOPopcF,numOPopcT);
			
		}
		
		return ret;
	}
	
	private void reset(int modo) {
		if(modo==0) init_poblacion(tam_elite);
		else init_poblacionG(tam_elite);
	}
	
	private void resetElim(int modo) {
		for(int i=0;i<tam_poblacion;i++) {
			if(mediaTam<poblacion[i].nodos) {
				if(modo==0) poblacion[i]=new IndividuoArbol(0, profundidad,filas, columnas, opcs, numOPopcF,numOPopcT);
				else poblacion[i]=new IndividuoGramatica(long_cromosoma, filas, columnas, opcs, numOPopcF,numOPopcT);
			}
		}
	}
		
	private void init_poblacion(int x) {
		poblacion = new IndividuoArbol[tam_poblacion];
		int cont=0;
		if(ini_idx==2) { // RAMPED & HALF			
			int D=profundidad-1;
			int tam=(tam_poblacion-x)/D;
			int mod=(tam_poblacion-x)%D;	
			int mod2=tam%2;
			
			
			
			if(mod!=0) {
				mod2=(tam+1)%2;
				for (int d=profundidad;d>profundidad-mod;d--) {
					for(int i=0;i<((tam+1)/2);i++) {						
						poblacion[cont++]=new IndividuoArbol(0, d,filas, columnas, opcs, numOPopcF,numOPopcT);
						poblacion[cont++]=new IndividuoArbol(1, d,filas, columnas, opcs, numOPopcF,numOPopcT);
					}
					if(mod2==1) poblacion[cont++]=new IndividuoArbol(1, d,filas, columnas, opcs, numOPopcF,numOPopcT);
				}
				mod2=tam%2;
				for (int d=profundidad-mod;d>=2;d--) {
					for(int i=0;i<tam/2;i++) {						
						poblacion[cont++]=new IndividuoArbol(0, d,filas, columnas, opcs, numOPopcF,numOPopcT);
						poblacion[cont++]=new IndividuoArbol(1, d,filas, columnas, opcs, numOPopcF,numOPopcT);
					}
					if(mod2==1) poblacion[cont++]=new IndividuoArbol(1, d,filas, columnas, opcs, numOPopcF,numOPopcT);
				}
			}
			else {				
				for (int d=2;d<=profundidad;d++) {					
					for(int i=0;i<tam/2;i++) {						
						poblacion[cont++]=new IndividuoArbol(0, d,filas, columnas, opcs, numOPopcF,numOPopcT);
						poblacion[cont++]=new IndividuoArbol(1, d,filas, columnas, opcs, numOPopcF,numOPopcT);
					}
					if(mod2==1) poblacion[cont++]=new IndividuoArbol(1, d,filas, columnas, opcs, numOPopcF,numOPopcT);
				}
			}
			
		}
		else { // COMPLETO o CRECIENTE			
			for (int i = 0; i < tam_poblacion-x; i++) {
				poblacion[i]=new IndividuoArbol(ini_idx, profundidad,filas, columnas, opcs, numOPopcF,numOPopcT);
			}
		}	
		
		if(x!=0) {
			while (elitQ.size() != 0) {
				poblacion[tam_poblacion - elitQ.size()] = new IndividuoArbol((IndividuoArbol) elitQ.poll().getId());
			}
		}
	}
	
	
	private void init_poblacionG(int x) {
		poblacion = new IndividuoGramatica[tam_poblacion];
		for(int i=0;i<tam_poblacion-x;i++) {
			poblacion[i]= new IndividuoGramatica(long_cromosoma, filas, columnas, opcs, numOPopcF,numOPopcT);
		}
		if(x!=0) {
			while (elitQ.size() != 0) {
				poblacion[tam_poblacion - elitQ.size()] = new IndividuoGramatica((IndividuoGramatica) elitQ.poll().getId());
			}
		}
	}

	private void evaluacion_poblacion(int modo) {
		fitness_total = 0;
		prob_seleccion = new double[tam_poblacion];
		prob_seleccionAcum = new double[tam_poblacion];

		double mejor_generacion = (Double.MIN_VALUE);
		double peor_generacion = (Double.MAX_VALUE);

		double tmp;

		mediaTam=0.0;
		
		double fitnessTotalAdaptado = 0;
		double fit;
		int indexMG = 0;
		for (int i = 0; i < tam_poblacion; i++) {
			fit = funcion.fitness(poblacion[i]);
			//fit=poblacion[i].operaciones.size(); // prueba gramatica
			poblacion[i].fitness = fit;
			fitness_total += fit;
			mediaTam+=poblacion[i].nodos;
			
			if (elitQ.size() < tam_elite)
				elitQ.add(new Node(fit, poblacion[i]));
			else if (tam_elite != 0 && funcion.cmp(elitQ.peek().getValue(), fit) == fit) {
				elitQ.poll();
				elitQ.add(new Node(fit, poblacion[i]));
			}

			if (mejor_generacion < fit) {
				mejor_generacion = fit;
				indexMG = i;
			}
			// mejor_generacion = funcion.cmp(mejor_generacion, fit);
			peor_generacion = funcion.cmpPeor(peor_generacion, fit);
		}

		if (mejor_total < mejor_generacion) {
			mejor_total = mejor_generacion;
			// mejor_individuo=poblacion[indexMG];
			// TODO
			mejor_individuo = poblacion[indexMG];//new Individuo(poblacion[indexMG]);
		}
		mediaTam/=tam_poblacion;
		double media=fitness_total/tam_poblacion;
		
		
		
		if(modo==1) {
			progreso_generaciones[0][generacionActual] = mejor_total; // Mejor Absoluto
			progreso_generaciones[1][generacionActual] = mejor_generacion; // Mejor Local
			progreso_generaciones[2][generacionActual] = fitness_total / tam_poblacion; // Media
		}
		
		
		
		double acum = 0;
		if (peor_generacion < 0)
			peor_generacion *= -1;
		
		// Desplazamiento
		fitness_total = tam_poblacion * 1.05 * peor_generacion - fitness_total;
		for (int i = 0; i < tam_poblacion; i++) {
			prob_seleccion[i] = 1.05 * peor_generacion - poblacion[i].fitness;
			prob_seleccion[i] /= fitness_total;
			acum += prob_seleccion[i];
			prob_seleccionAcum[i] = acum;
		}
		
		/*
		// No usamos porque cambia a peor los resultados (ESTA MAL IMPLEMENTADO)
		double P=2;
		
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
		}*/
		
		if(bloating_idx==1) {
			double pElim=1/tam_poblacion;
			for (int i = 0; i < tam_poblacion; i++) {		
				if(poblacion[i].nodos>mediaTam && Math.random()<pElim) { // Elimina					
					poblacion[i]=new IndividuoArbol(modo, profundidad, filas, columnas, opcs, numOPopcF,numOPopcT);
				}
			}
		}
		else if(bloating_idx==2) {
			double k=0.0;
			double covarianza=0.0;
			double varianza=0.0;
			// Covarianza(l,f) l = tama�o, f = fitness
			// Varianza(l)
			for(int i=0;i<tam_poblacion;i++) {
				tmp=poblacion[i].nodos-mediaTam;
				covarianza+=(tmp)*(poblacion[i].fitness-media);
				varianza+=Math.pow(tmp, 2);
			}
			covarianza/=tam_poblacion;
			varianza/=tam_poblacion;
			
			k=covarianza/varianza; // BLOATING
			
			// Nuevos valores
			fitness_total=0;
			for (int i = 0; i < tam_poblacion; i++) {		
				poblacion[i].fitness-=(k*poblacion[i].nodos);				
				fitness_total+=poblacion[i].fitness;
			}
			
			if(modo==1) {
				progreso_generaciones[0][generacionActual] = mejor_total; // Mejor Absoluto
				progreso_generaciones[1][generacionActual] = mejor_generacion; // Mejor Local
				progreso_generaciones[2][generacionActual] = fitness_total / tam_poblacion; // Media
			}
			
		
		}
		
		// Escalado lineal
		
		if(modo==1) {
			progreso_generaciones[3][generacionActual++] = tam_poblacion*prob_seleccion[indexMG]; // Media
		}

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
				ret = seleccion.truncamiento(poblacion, prob_seleccion, 0.5, tam_poblacion - tam_elite);
				break;
			case 5:
				ret = seleccion.restos(poblacion, prob_seleccion, prob_seleccionAcum, tam_poblacion - tam_elite);
				break;
			case 6:
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
				ret = mutacion.hoist(poblacion);
				break;
			case 5:
				ret = mutacion.contraccion(poblacion);
				break;
			case 6:
				ret = mutacion.expansion(poblacion);
				break;
			default:
				break;
		}
		return ret;
	}

	
	
	
	
	public void ejecuta_calculo_mediasA(Valores valores) {
		double media=0.0, mediaTamaño=0.0;
		
		String[] seleccion = { 	"Ruleta ",
				"T. Deterministico ",
				"T. Probabilistico ",
				"Estocastico Univ ",
				"Truncamiento ",
				"Restos ",
				"Ranking "};
		
		String[] bloating = { 	"Sin",
				"Tarpeian",
				"Poli and McPhee"};
		
		
		String[] cruce = { 	"Intercambio"};
		//String[] cruce = { 	"MonoPunto" };
		
		String[] inicializacions={"Completa",
				  "Creciente",
				  "Ramped & Half"};

		
		
			
		
		String[] mutacion = { 	"Terminal",
						"Funcional",
						"Arbol",
						"Permutacion",
						"Hoist",
						"Contraccion",
						"Expansion"};
		
		
		
		//String[] mutacionG = { 	"Basica" };
		
		
		
		
		

		
		System.out.println(seleccion[valores.seleccion_idx] + "----------------------------------------------------------------------");
		while(true) {	
			
			media=0.0;
			mediaTamaño=0.0;
			long startTime = System.nanoTime();
			for(int i=0;i<20;i++) {
				Individuo[] selec = null;
		
				
				String fallo = "";
				setValores(valores);
				
				// ELITISMO
				Comparator<Node> comparator = Comparator.comparingDouble(Node::getValue);
				
				elitQ = new PriorityQueue<>(Collections.reverseOrder(comparator));
	
				// valores_inds=new double[tam_poblacion*(generaciones+1)][3];
				progreso_generaciones = new double[4][generaciones + 1];
				generacionActual = 0;
		
				init_poblacion(0);
				evaluacion_poblacion(1);
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
					evaluacion_poblacion(1);
				}
		
				if (fallo.equals("")) { 
					media+=mejor_total;
					mediaTamaño+=mejor_individuo.nodos;
				} else {
					System.out.println("Fallo");
					System.exit(0);
				}
			}
			media/=20;
			mediaTamaño/=20;
			long endTime = System.nanoTime();
			double tiempo = (endTime - startTime) / 1e9;
			System.out.println("- "+mutacion[valores.mut_idx]+"\tFitness medio: " + media +
							   "\n\t\tNum Nodos medio: "+mediaTamaño+" Tiempo de ejecucion: "+ tiempo+"s");
			valores.mut_idx++;
			if(valores.mut_idx==5) {
				valores.mut_idx=0;
				valores.seleccion_idx++;
				if(valores.seleccion_idx==7) break;
				System.out.println("\n"+seleccion[valores.seleccion_idx] + "----------------------------------------------------------------------");
			}
		}
	}
	
	public void ejecuta_calculo_mediasG(Valores valores) {
		double media=0.0, mediaTamaño=0.0;
		
		String[] seleccion = { 	"Ruleta ",
				"T. Deterministico ",
				"T. Probabilistico ",
				"Estocastico Univ ",
				"Truncamiento ",
				"Restos ",
				"Ranking "};
		
		String[] bloating = { 	"Sin",
				"Tarpeian",
				"Poli and McPhee"};
		
		
		
		String[] cruce = { 	"MonoPunto" };
		
						
		
		
		String[] mutacionG = { 	"Basica" };
		
		
		int[] tamsCromosoma = {10,25,50,75,100,255};
		int tamsCromosomaIdx=0;
		

		
		System.out.println(seleccion[valores.seleccion_idx] + "----------------------------------------------------------------------");
		while(true) {	
			valores.prof_O_longCrom=tamsCromosoma[tamsCromosomaIdx];
			media=0.0;
			mediaTamaño=0.0;
			long startTime = System.nanoTime();
			for(int i=0;i<20;i++) {
				Individuo[] selec = null;
		
				
				String fallo = "";
				setValores(valores);
				
				// ELITISMO
				Comparator<Node> comparator = Comparator.comparingDouble(Node::getValue);
				
				elitQ = new PriorityQueue<>(Collections.reverseOrder(comparator));
	
				// valores_inds=new double[tam_poblacion*(generaciones+1)][3];
				progreso_generaciones = new double[4][generaciones + 1];
				generacionActual = 0;
		
				init_poblacionG(0);
				evaluacion_poblacion(1);
				while (generaciones-- != 0) {
					selec = seleccion_poblacion();
					try {
						poblacion = cruce_poblacionG(selec, long_cromosoma);
						poblacion = mutacion_poblacionG();
		
						// ELITISMO
						while (elitQ.size() != 0) {
							poblacion[tam_poblacion - elitQ.size()] = elitQ.poll().getId();
						}
					} catch (Exception e) {
						fallo = e.getMessage();
						break;
					}
					evaluacion_poblacion(1);
				}
		
				if (fallo.equals("")) { 
					media+=mejor_total;
					mediaTamaño+=mejor_individuo.nodos;
				} else {
					System.out.println("Fallo");
					System.exit(0);
				}
			}
			media/=20;
			mediaTamaño/=20;
			long endTime = System.nanoTime();
			double tiempo = (endTime - startTime) / 1e9;
			System.out.println("- Tam. Cromosoma: "+tamsCromosoma[tamsCromosomaIdx]+"\tFitness medio: " + media +
							   "\n\t\t\tNum Nodos medio: "+mediaTamaño+" Tiempo de ejecucion: "+ tiempo+"s");
			
			tamsCromosomaIdx++;
			if(tamsCromosomaIdx==6) {
				tamsCromosomaIdx=0;				
				valores.seleccion_idx++;			
				if(valores.seleccion_idx==7) break;
				System.out.println("\n"+seleccion[valores.seleccion_idx] + "----------------------------------------------------------------------");				
			}
			
		}
	}

	
	public int getFilas() { return this.filas; }
	public int getColumnas() { return this.columnas; }
	
}

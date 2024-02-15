package Logic;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import Model.Gen;
import Model.Individuo;
import Model.Valores;
import View.ControlPanel;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("unused")
public class AlgoritmoGenetico {
	
	private int tam_poblacion;	
	private Individuo[] poblacion;	
		
	private int generaciones;
	
	private int tam_individuo;
	private int num_genes;
	private int[] tam_genes;
	
	private double precision;	
	
	private double fitness_total;
	private double[] fitness;
	private double[] prob_seleccion;
	private double[] prob_seleccionAcum;
		
	private Seleccion seleccion;
	private int seleccion_idx;
	
	private Cruce cruce;
	private int cruce_idx;
	private double prob_cruce;
	
	private Mutacion mutacion;
	private int mut_idx;
	private double prob_mut;	
		
	private Funciones funcion;
	private int funcion_idx;
	
	private double[] maximos;
	private double[] minimos;
	
		
	private int tam_torneo;
		
	private int pos_valores;
	//private double[][] valores_inds;	
	//private ValoresIndividuosGrafico[] valores_inds;
	
	
	private double[][] progreso_generaciones;
	private double mejor_total;
	//private Individuo best;
	//private int pos_best;
	
	private ControlPanel ctrl;
	
	public AlgoritmoGenetico(ControlPanel ctrl) { 
		this.ctrl=ctrl;
	}
		
	private void setValores(Valores valores) {
		this.tam_poblacion=valores.tam_poblacion;
		this.generaciones=valores.generaciones;
		this.seleccion_idx=valores.seleccion_idx;
		this.cruce_idx=valores.cruce_idx;
		this.prob_cruce=valores.prob_cruce;
		this.mut_idx=valores.mut_idx;
		this.prob_mut=valores.prob_mut;
		this.precision=valores.precision;
		this.funcion_idx=valores.funcion_idx;
		this.num_genes=valores.num_genes;
		
		funcion=new Funciones(funcion_idx);
		seleccion=new Seleccion(tam_poblacion);
		cruce=new Cruce(prob_cruce);
		mutacion=new Mutacion();
		
		this.maximos=funcion.maximos(num_genes);
		this.minimos=funcion.minimos(num_genes);
		
		tam_genes=tamGenes();		
	}
	
	
	public void ejecuta(Valores valores) {
		Individuo[] selec=null;
		
		setValores(valores);
		
		//valores_inds=new double[tam_poblacion*(generaciones+1)][3];
		progreso_generaciones=new double[3][generaciones+1];
		pos_valores=0;
		
		init_poblacion();

		//mutacion_poblacion();
		
		/*evaluacion_poblacion();
		printPoblacion();		
		selec=seleccion_poblacion();
		cruce.cruce_uniforme(selec, poblacion);	
		System.out.println("Cruce realizado");
		printPoblacion();*/
		
		
		evaluacion_poblacion();
		
		
		//printPoblacion();
		//System.out.println("-------------------------------------------------------------");
		
		
		while(generaciones--!=0) {
			selec=seleccion_poblacion(); 		
			cruce_poblacion(selec);  			
			mutacion_poblacion(); 				
			evaluacion_poblacion();	
			
			//printPoblacion();
			//System.out.println("-------------------------------------------------------------");
		}
		

		// TERMINA LA EJECUCION, MANDA LOS VALORES CALCULADOS AL GRAFICO
		ctrl.actualiza_Grafico(progreso_generaciones);
	}
	
	
	
	private int[] tamGenes() {
		int ret[] = new int[num_genes];
		for(int i=0;i<num_genes;i++) {
			tam_individuo+=ret[i]=tamGen(precision, minimos[i],maximos[i]);
		}
		
		return ret;
	}
	
	// TODO preguntar si es con ceil
	private int tamGen(double precision, double min, double max) {
		return (int) Math.ceil((Math.log10(((max - min) / precision) + 1) / Math.log10(2)));
	}
			
	private void init_poblacion() {		
		poblacion = new Individuo[tam_poblacion];		
		for(int i=0;i<tam_poblacion;i++) {
			poblacion[i]=new Individuo(num_genes, tam_genes, maximos, minimos);
		}
	}	
				
	private void evaluacion_poblacion() {
		fitness_total=0;
		fitness=new double[tam_poblacion];
		prob_seleccion=new double[tam_poblacion];
		prob_seleccionAcum=new double[tam_poblacion];
		
		// This functional interface takes a double array as parameter and returns a Double.
		Function<double[], Double> selected_function = null;
		
		switch (funcion_idx) {
		case 0:
			selected_function = funcion::f1;
			break;
		case 1:
			selected_function = funcion::f2;	
			break;
		case 2:
			selected_function = funcion::f3;
			break;
		case 3:
			selected_function = funcion::f4;
			break;
		case 4:
			selected_function = funcion::f5;
			break;

		default:
			break;
		}
		
		double mejor_generacion=0;
		//if (selected_function != null) // NO HACE FALTA
        for (int i=0;i<tam_poblacion;i++) {
            //fitness[i] = selected_function.apply(new double[]{poblacion[i].fenotipo});
        	// TODO CAMBIAR, PARA QUE SOLO ESTE EN LA CLASE Individuo, QUITAR ARRAY GLOBAL fitness[]
        	fitness[i]=selected_function.apply(poblacion[i].fenotipo); 
        	poblacion[i].fitness=fitness[i];
            fitness_total += fitness[i];
            
            if(mejor_generacion<fitness[i]) mejor_generacion=fitness[i];
            
            // GRAFICO
            poblacion[i].calcular_fenotipo(maximos, minimos);
            /*valores_inds[pos_valores][0]=poblacion[i].fenotipo[0];
            valores_inds[pos_valores][1]=poblacion[i].fenotipo[1];
            valores_inds[pos_valores++][2]=fitness[i];*/            
        }
        if(mejor_generacion>mejor_total) mejor_total=mejor_generacion;
        
        /*plot2D.addLinePlot("Mejor Absoluto", x, vals[0]);
        plot2D.addLinePlot("Mejor", x, vals[1]);
        plot2D.addLinePlot("Media", x, vals[2]);*/
        
        progreso_generaciones[0][pos_valores]=mejor_total; // Mejor Absoluto
		progreso_generaciones[1][pos_valores]=mejor_generacion; // Mejor Local
		progreso_generaciones[2][pos_valores++]=fitness_total/tam_poblacion; // Media
        
        double acum=0;
        for (int i=0;i<tam_poblacion;i++) {           	
        	prob_seleccion[i]=fitness[i]/fitness_total;
        	acum+=prob_seleccion[i];
        	prob_seleccionAcum[i]=acum;
        }		
	}
		
	private Individuo[] seleccion_poblacion() {
		Individuo[] ret = new Individuo[tam_poblacion];		
		
		
		switch (funcion_idx) {
		case 0:
			ret=seleccion.ruleta(poblacion, prob_seleccionAcum);
			break;
		case 1:			
			ret=seleccion.torneoDeterministico(poblacion, 3);
			break;
		case 2:			
			ret=seleccion.torneoProbabilistico(poblacion, 3, 0.2);
			break;
		case 3:
			ret=seleccion.estocasticoUniversal1(poblacion, prob_seleccionAcum);
			break;
		case 4:
			ret=seleccion.estocasticoUniversal2(poblacion, prob_seleccionAcum);
			break;
		case 5:
			ret=seleccion.truncamiento(poblacion, prob_seleccionAcum);
			break;
		case 6:
			ret=seleccion.restos(poblacion, prob_seleccionAcum);
			break;

		default:
			break;
		}
		
		
		
		return ret;
	}
	
	private /*Individuo[]*/ void cruce_poblacion(Individuo[] selec) {
		//Individuo[] ret = null;
		
		switch (cruce_idx) {
		case 0:
			/*ret=*/cruce.cruce_monopunto(selec, poblacion);
			break;
		case 1:
			/*ret=*/cruce.cruce_uniforme(selec, poblacion);
			break;
		default:
			break;
		}
		
		//return ret;
	}
	
	
	private void mutacion_poblacion() {
		//printPoblacion();
		switch (mut_idx) {
		case 0:
			mutacion.mut_basica(poblacion, prob_mut);
			break;
		default:
			break;
		}
		//System.out.println("Proceso de mutacion terminado: ");
		//System.out.println();
		//printPoblacion();
	}
	
	
	private void printPoblacion() {
		for (Individuo ind: poblacion) {			
			for(Gen c: ind.genes) {
				for (int a: c.v) {
					System.out.print(a + " ");
				}
				System.out.print("| ");
			}
			System.out.println(" Fitness: " + ind.fitness + 
					"f1: " + ind.fenotipo[0] + " f2: " + ind.fenotipo[1]);
		}
	}
				
	
}

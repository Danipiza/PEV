package Logic;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("unused")
public class AlgoritmoGenetico {
	
	private int tam_poblacion;	
	private Individuo[] poblacion;	
	private int tam_gen;
	
	private double[] fitness;
	
	private int generaciones;
	
	private int seleccion_idx;
	
	private int cruce_idx;
	private double prob_cruce;
	
	private int mut_idx;
	private double prob_mut;	
	
	private double precision;
	
	private Funciones funcion;
	private int funcion_idx;	
	
	private int tam_torneo;
	
	private Individuo best;
	private int pos_best;	
	
	private int[] maximos;
	private int[] minimos;
	
	public AlgoritmoGenetico() { }
		
	public void ejecuta(Valores valores) {
		setValores(valores);
		System.out.println("Valores guardados.");
		System.out.println(tam_poblacion + " " + generaciones + " " + 
						   prob_cruce + " " + prob_mut + " " + precision + " " + funcion_idx);
		System.out.println("Ejecutando...");
		
		// INIT
		while(generaciones--!=0) {
			// EVALUACION
			// SELECCION
			// CRUCE
			// MUTACION						
		}
	}
	
	private void setValores(Valores valores) {
		this.tam_poblacion=valores.tam_poblacion;
		this.generaciones=valores.generaciones;
		this.prob_cruce=valores.prob_cruce;
		this.prob_mut=valores.prob_mut;
		this.precision=valores.precision;
		this.funcion_idx=valores.funcion_idx;
		
		/*this.xMin=10;
		this.xMax=10;*/
		
		// TODO
		//this.tam_gen=tamGen(xMin,xMax)+tamGen(xMin,xMax);
	}
	
	
	
	private void init_poblacion() {		
		poblacion = new Individuo[tam_poblacion];		
		for(int i=0;i<tam_poblacion;i++) {
			//int num, double precision, int[] maximos, int[] minimos
			//poblacion[i]=new Individuo(tam_gen);
		}
	}	
				
		
	private Individuo[] seleccion_poblacion(int n) {
		Individuo[] ret = new Individuo[n];
		
		
		
		return ret;
	}
	
	
	
	private void mutar_poblacion(int n) {
		List<Individuo> cruzados = new ArrayList<Individuo>();
		for (int i = 0; i < tam_poblacion;i++) {
			// TODO MUT
		}
	}
	
	
		
				
	
}

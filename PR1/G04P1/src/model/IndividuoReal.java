/**
 * 
 */
package model;

import java.util.Random;

/**
 * @author DavidSG & DannyP39
 
 * ENG: Class for creating a Real Individual.
 * ESP: Clase para crear un Individuo Real.
 */
public class IndividuoReal extends Individuo {
	
	
	/**
	 * 
	 * @param num
	 * @param prec
	
	 * ENG: Class constructor. Random initialization.
	 * ESP: Constructor de la clase. Inicializacion aleatoria.
	 */
	public IndividuoReal(int num, int prec) {			
		fenotipo=new double[num];
		fitness=0;
		
		// ENG: Each gene is generated randomly.
		// ESP: Cada gen se genera de manera aleatoria.
		for (int i=0;i<num;i++) fenotipo[i]=gen_aleatorio(prec);
	}
	
	/**
	 * 
	 * @param ind
	
	 * ENG: Class constructor. Initialization with a given Individual.
	 * ESP: Constructor de la clase. Inicializacion con un Individuo dado.
	 */
	public IndividuoReal(Individuo ind) {
		int num=ind.fenotipo.length;
		fenotipo=new double[num];
		fitness=0;
		
		// ENG: Each gene is copied from the given Individual.
		// ESP: Cada gen se copia del Individuo dado.
		for (int i=0;i<num;i++) fenotipo[i]=ind.fenotipo[i];
	 	
	}
	
	/**
	 * 
	 * @param prec
	 * @return
	
	 * ENG: Method for generating a random gene.
	 * ESP: Funcion para generar un gen de manera aleatoria.
	 */
	public double gen_aleatorio(double prec) {
		Random rand=new Random();
				 
		// ENG: Generates the real value with 2 decimal places of precision.
		// ESP: Genera el valor real con 2 decimales de precision.
		double ret=rand.nextDouble()*Math.PI;  
		ret=Math.round(ret*prec)/prec;
		
		return ret;
	}

	

	/**
	 * 
	
	 * ENG: Method for printing the Individual.
	 * ESP: Funcion para imprimir el Individuo.
	 */
	@Override
	public void print_individuo() {
		for (double g: fenotipo) System.out.print(g+" ");
		System.out.println();		
	}

	/**
	 * 
	 * @param maximos
	 * @param minimos
	
	 * ENG: It is not necessary to calculate the phenotype, since they are already real values.
	 * ESP: No hace falta calcular el fenotipo, pues ya son valores reales.
	 */
	@Override
	public void calcular_fenotipo(double[] maximos, double[] minimos) {		
	}

	


}

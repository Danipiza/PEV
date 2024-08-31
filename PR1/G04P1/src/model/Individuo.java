/**
 * 
 */
package model;

/**
 * @author DavidSG & DannyP39
 
 * ENG: Class for creating Individuals.
 * ESP: Clase para crear Individuos.
 */

public abstract class Individuo {
	
	// ENG: Variable with the genes.
	// ESP: Variable con los genes.
	public Gen[] genes;
	
	// ENG: Optimal variable.
	// ESP: Variable de optimalidad
	public double fitness;
	
	// ENG: Variable with the phenotypes of each gene.
	// ESP: Variable con los fenotipos de cada gen.
	public double[] fenotipo;	
	

	/**
	 * 
	
	 * ENG: Method for printing an individual.
	 * ESP: Funcion para imprimir un individuo.
	 */
	public abstract void print_individuo();
	
	
	/**
	 * 
	 * @param maximos
	 * @param minimos
	
	 * ENG: Method for calculating the phenotype of the Individual.
	 * ESP: Funcion para calcular el fenotipo del Individuo.
	 */
	public abstract void calcular_fenotipo(double[] maximos, double[] minimos);
}
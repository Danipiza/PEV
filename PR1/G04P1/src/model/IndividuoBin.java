/**
 * 
 */
package model;

/**
 * @author DavidSG & DannyP39
 
 * ENG: Class for
 * ESP: Clase para
 */

public class IndividuoBin extends Individuo {
	
	/**
	 * 
	 * @param num
	 * @param tam_genes
	 * @param xMax
	 * @param xMin
	
	 * ENG: Class constructor. Random initialization.
	 * ESP: Constructor de la clase. Inicializacion aleatoria.
	 */
	public IndividuoBin(int num, int[] tam_genes, double xMax[], double[] xMin) {
		genes=new Gen[num];
		fenotipo=new double[num];
		fitness=0;
		
		// ENG: Each gene is generated randomly.
		// ESP: Cada gen se genera de manera aleatoria.
		for (int i=0;i<num;i++) genes[i]=new Gen(tam_genes[i]);
	}
	
	/**
	 * 
	 * @param poblacion
	
	 * ENG: Class constructor. Initialization with a given Individual.
	 * ESP: Constructor de la clase. Inicializacion con un Individuo dado.
	 */
	public IndividuoBin(Individuo poblacion) {
		int num=poblacion.genes.length;	
		genes=new Gen[num];
		fenotipo=new double[num];
		fitness=0;
		
		// ENG: Each gene is copied from the given Individual.
		// ESP: Cada gen se copia del Individuo dado.
		for (int i=0;i<num;i++) genes[i]=new Gen(poblacion.genes[i]);	
	}
	
	/**
	 * 
	 * @param gen
	
	 * ENG: Method for transforming an integer binary number into a real number.
	 * ESP: Funcion para trasformar un numero binario entero a real.
	 */
	private int bin_to_real(Gen gen) {
		int ret=0;
		
		int cont=1;
		
		// ENG: Start with the least significant bit and multiply by two 
		// 		until the most significant one.
		// ESP: Empieza por el bit menos significativo y multiplica por dos 
		//		hasta llegar al mas significativo.
		for (int i=gen.v.length-1;i>=0;i--) {
			if (gen.v[i]==1) ret+=cont;
			cont*=2;
		}
		
		return ret;
	}
	
	/**
	 * 	
	 * @param xMax
	 * @param xMin

	 * ENG: Method for calculating the phenotype.
	 * ESP: Funcion para calcular el fenotipo.
	 */
	public void calcular_fenotipo(double[] xMax, double[] xMin) {
		
		// ENG: For each gene, calculate its real value.
		// ESP: Para cada gen calcula el su valor real.
		for (int i=0;i<genes.length;i++) {
			fenotipo[i]=calcular_valor_real(genes[i], xMax[i], xMin[i]);
		}
	}
	
	/**
	 * 
	 * @param ind
	 * @param xMax
	 * @param xMin
	 * @return
	
	 * ENG: Method for calculating the ral value of a Gene.
	 * ESP: Funcion para calcular el valor real de un Gen.
	 */
	private double calcular_valor_real(Gen ind, double xMax, double xMin) {
		return xMin+bin_to_real(ind)*((xMax-xMin)/(Math.pow(2, ind.v.length)-1));
	}
	
	/**
	 * 
	
	 * ENG: Method for printing an individual.
	 * ESP: Funcion para imprimir un individuo.
	 */
	public void print_individuo() {
		for (Gen c: genes) {
			for (int a: c.v) System.out.print(a + " ");
		}
	
		System.out.println(" fenotipo x1: "+fenotipo[0]+" fenotipo x2: "+fenotipo[1]);
	}



}

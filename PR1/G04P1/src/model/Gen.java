/**
 * 
 */
package model;

/**
 * @author DavidSG & DannyP39
 
 * ENG: Class for creating a Gene.
 * ESP: Clase para crear un Gen.
 */
public class Gen {
	
	public int[] v;
	
	/**
	 * 
	 * @param l
	
	 * ENG: Class constructor. Random initialization.
	 * ESP: Constructor de la clase. Inicializacion aleatoria.
	 */
	public Gen(int l) {
		v=new int[l];
		init(l);
	}
	
	/**
	 * 
	 * @param gen
	
	 * ENG: Class constructor. Initialization with a given Gene.
	 * ESP: Constructor de la clase. Inicializacion con un Gen dado.
	 */
	public Gen(Gen gen) {
		v=new int[gen.v.length];
		// ENG: Copy the bits.
		// ESP: Copia los bits.
		for (int i=0;i<gen.v.length;i++) {
			v[i]=gen.v[i];
		}
	}

	/**
	 * 
	 * @param l
	
	 * ENG: Method to initialize a Gene randomly.
	 * ESP: Funcion para inicializar un Gen de manera aletoria.
	 */
	private void init(int l) {
		// ENG: With a given length, generates all the alleles of the Gene.
		// ESP: Con una longitud dada, genera todos los alelos del Gen.
		for (int i=0;i<l;i++) {
			v[i]=(Math.random()<=0.5? 1:0);
		}
	}

}

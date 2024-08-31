/**
 * 
 */
package logic;


import java.util.Arrays;
import java.util.Comparator;

import model.Individuo;
import model.IndividuoBin;
import model.IndividuoReal;
import utils.Pair;

/**
 * @author DavidSG & DannyP39
 
 * ENG: Class for Selection.
 * ESP: Clase para Seleccion.
 */

public class Seleccion {

	private int tam_poblacion;
	private boolean opt;
	private boolean bin;
	
	/**
	 * 
	 * @param tam_poblacion
	 * @param opt
	 * @param funcion_idx
	
	 * ENG: Class constructor
	 * ESP: Constructor de la clase
	 */
	public Seleccion(int tam_poblacion, boolean opt, int funcion_idx) {
		this.tam_poblacion=tam_poblacion;
		
		this.opt=opt;		
		this.bin=(funcion_idx<4?true:false);
	}

	
	/**
	 * 
	 * @param x
	 * @param prob_acumulada
	 * @return
	
	 * ENG: Binary search to reduce the search time.
	 * ESP: Busqueda binaria para reducir el tiempo de busqueda.
	 */
	protected int busqueda_binaria(double x, double[] prob_acumulada) {
		int i=0, j=tam_poblacion-1;
		int m=0;
		
		while (i<j) {
			m=(j+i)/2;
			
			// ENG: Search value is greater than the actual one. RIGHT side.
			// ESP: Valor buscado es mayor que el actual. Parte DERECHA.
			if (x>prob_acumulada[m]) i=m+1;		 
			// ENG: Search value is lower than the actual one. LEFT side.
			// ESP: Valor buscado es menor que el actual. Parte IZQUIERDA.
			else if (x < prob_acumulada[m]) j=m;
			// ENG: Found
			// ESP: Encontrado
			else return m;
		}

		return i;
	}
	
	/**
	 * 
	 * @param poblacion
	 * @param prob_acumulada
	 * @param tam_seleccionados
	 * @return
	
	 * ENG: Selection by roulette. Probability in proportion to their fitness.
	 * ESP: Seleccion por ruleta. Probabilidad en proporcion de sus fitness.
	 */
	public Individuo[] ruleta(Individuo[] poblacion, double[] prob_acumulada, int tam_seleccionados) {
		Individuo[] seleccionados=new Individuo[tam_seleccionados];

		double rand;
		
		// ENG: Main loop.
		// ESP: Bucle principal.
		for (int i=0;i<tam_seleccionados;i++) {
			// ENG: Random number.
			// ESP: Numero aleatorio
			rand=Math.random();
			
			// ENG: Binary search with the random number to select the individual.
			// ESP: Busqueda binaria con el numero aleatorio para seleccionar el individuo.
			if(bin) seleccionados[i]=new IndividuoBin(poblacion[busqueda_binaria(rand, prob_acumulada)]);
			else seleccionados[i]	=new IndividuoReal(poblacion[busqueda_binaria(rand, prob_acumulada)]);
		}

		return seleccionados;
	}
	
	/**
	 * 
	 * @param poblacion
	 * @param k
	 * @param tam_seleccionados
	 * @return
	
	 * ENG: Selection by deterministic tournament. 
	 * 		A random group of k individuals is chosen and the best one is chosen.
	 * ESP: Seleccion por torneo determinista. 
	 * 		Se escoge un grupo aleatorio de k individuos y se elige al mejor.
	 */
	public Individuo[] torneoDeterministico(Individuo[] poblacion, int k, int tam_seleccionados) {
		Individuo[] seleccionados=new Individuo[tam_seleccionados];

		double randomFitness;
		int indexMax, indexMin;
		double max, min;
		
		// ENG: Main loop.
		// ESP: Bucle principal.
		for (int i=0;i<tam_seleccionados;i++) {
			max=Double.NEGATIVE_INFINITY;
			min=Double.MAX_VALUE;
			indexMax=-1;
			indexMin=-1;
			
			// ENG: Randomly select the k individuals.
			// ESP: Selecciona de manera aleatoria a los k individuos. 
			for (int j=0;j<k;j++) {
				int randomIndex=(int) (Math.random() * tam_poblacion);
				randomFitness=poblacion[randomIndex].fitness;
				if (randomFitness>max) {
					max=randomFitness;
					indexMax=randomIndex;
				} 
				if (randomFitness<min) {
					min=randomFitness;
					indexMin=randomIndex;
				}
			}	
			 
			// ENG: Select the best individual. If it is minimization, the smallest, otherwise the largest.
			// ESP: Selecciona el mejor individuo. Si es de minimizacion el menor en caso contrario el mayor.
			if(bin) seleccionados[i]=new IndividuoBin((opt ? poblacion[indexMax] : poblacion[indexMin]));
			else seleccionados[i]	=new IndividuoReal((opt ? poblacion[indexMax] : poblacion[indexMin]));
		}

		return seleccionados;
	}
	
	/**
	 * 
	 * @param poblacion
	 * @param k
	 * @param p
	 * @param tam_seleccionados
	 * @return
	
	 * ENG: Selection by probabilistic tournament. 
	 * 		A random group of k individuals is chosen and with a probability
	 * 		the best or worst is chosen.
	 * ESP: Seleccion por torneo probabilistico. 
	 * 		Se escoge un grupo aleatorio de k individuos y con una probabilidad
	 * 		se elige al mejor o peor.
	 */
	public Individuo[] torneoProbabilistico(Individuo[] poblacion, int k, double p, int tam_seleccionados) {
		Individuo[] seleccionados = new Individuo[tam_seleccionados];

		double randomFitness;
		int indexMax, indexMin;
		double max, min;
		
		// ENG: Main loop.
		// ESP: Bucle principal .
		for (int i=0;i<tam_seleccionados;i++) {
			max=Double.NEGATIVE_INFINITY;
			min=Double.MAX_VALUE;
			indexMax=-1;
			indexMin=-1;
			
			// ENG: Randomly select the k individuals.
			// ESP: Selecciona de manera aleatoria a los k individuos. 
			for (int j=0;j<k;j++) {
				int randomIndex=(int) (Math.random() * poblacion.length);
				randomFitness=poblacion[randomIndex].fitness;
				if (randomFitness>max) {
					max=randomFitness;
					indexMax=randomIndex;
				} 
				if (randomFitness<min) {
					min=randomFitness;
					indexMin=randomIndex;
				}
			}	
			
			// ENG: Select the best or worst individual, depending on the probability. 
			// ESP: Selecciona el mejor o pero individuo, dependiendo de la probabilidad. 
			if(bin) seleccionados[i]=new IndividuoBin((opt && Math.random()<=p||!opt && Math.random()>p ? 
					poblacion[indexMax] : poblacion[indexMin]));
			else seleccionados[i]=new IndividuoReal((opt && Math.random()<=p||!opt && Math.random()>p ? 
					poblacion[indexMax] : poblacion[indexMin]));			
		}

		return seleccionados;
	}
	
	/**
	 * 
	 * @param poblacion
	 * @param prob_acumulada
	 * @param tam_seleccionados
	 * @return
	
	 * ENG: Universal stochastic selection. 
	 * 		Individuals are chosen with a random number. Said number is
	 * 		incremented in each iteration
	 * ESP: Seleccion estocastica universal. 
	 * 		Con un numero aleatorio se escogen los individuos. Dicho numero
	 * 		aleatorio se incrementa en cada iteracion.
	 */
	public Individuo[] estocasticoUniversal1(Individuo[] poblacion, double[] prob_acumulada, int tam_seleccionados) {
		Individuo[] seleccionados=new Individuo[tam_seleccionados];
		
		// ENG: Generates the random number and the increment value.
		// ESP: Genera el numero aleatorio y el valor de incremento.
		double incr=1.0/tam_seleccionados, rand = Math.random()*incr;
		
		// ENG: Main loop.
		// ESP: Bucle principal.
		for (int i=0;i<tam_seleccionados;i++) {
			// ENG: Binary search with the number.
			// ESP: Busqueda binaria con el numero.
			if(bin) seleccionados[i]=new IndividuoBin(poblacion[busqueda_binaria(rand, prob_acumulada)]);
			else seleccionados[i]	=new IndividuoReal(poblacion[busqueda_binaria(rand, prob_acumulada)]);

			// ENG: Increments the number.
			// ESP: Incrementa el numero.
			rand+=incr;
		}

		return seleccionados;
	}
	
	/*// Another way of executing universal stochastic	
	public Individuo[] estocasticoUniversal2(Individuo[] poblacion, double[] prob_acumulada, int tam_seleccionados) {
		Individuo[] seleccionados=new Individuo[tam_seleccionados];
		
		double distMarca=1.0/tam_seleccionados, rand = Math.random() * distMarca;
		for (int i=0;i<tam_seleccionados;i++) {
			double x=(rand+i)/tam_seleccionados;
			
			if(bin) seleccionados[i]=new IndividuoBin(poblacion[busqueda_binaria(x, prob_acumulada)]);
			else seleccionados[i]	=new IndividuoReal(poblacion[busqueda_binaria(x, prob_acumulada)]);
		}

		return seleccionados;
	}*/
	
	/**
	 * 
	 * @param poblacion
	 * @param prob_seleccion
	 * @param trunc
	 * @param tam_seleccionados
	 * @return
	
	 * ENG: Selection by truncation. 
	 * 		Individuals are ordered by fitness and those who are 
	 * 		that exceed a certain threshold, x times. With x being the 
	 * 		division between threshold and probability.
	 * ESP: Seleccion por truncamiento. 
	 * 		Se ordenan los individuos por fitness y se eligen a los que 
	 * 		que superen cierto umbral, x veces. Siendo x la division entre el
	 * 		umbral y los probabilidad.
	 */
	@SuppressWarnings("unchecked")
	public Individuo[] truncamiento(Individuo[] poblacion, double[] prob_seleccion, double trunc, int tam_seleccionados) {
		Individuo[] seleccionados=new Individuo[tam_seleccionados];
		
		// ENG: Sort the individuals by the selection probability.
		// ESP: Ordena a los individuos por la probabilidad de seleccion.
		Pair<Individuo, Double>[] pairs=new Pair[tam_seleccionados];
		for (int i=0;i<tam_seleccionados;i++) {
			pairs[i]=new Pair<>(poblacion[i], prob_seleccion[i]);
		}
		Arrays.sort(pairs, Comparator.comparingDouble(p -> p.get_second()));
		
		// ENG: 
		// ESP: 
		int x=0, num=(int) (1.0/trunc);
		int n=pairs.length-1;
		
		// ENG: Main loop.
		// ESP: Bucle principal
		for (int i=0;i<(tam_seleccionados)*trunc;i++) {		
			// ENG: Select num times the current individual.
			// ESP: Selecciona num veces el individuo actual.
			for (int j = 0; j < num && x<tam_seleccionados; j++) {
				if(bin) seleccionados[x++]	=new IndividuoBin(pairs[n-i].get_first());
				else seleccionados[x++]		=new IndividuoReal(pairs[n-i].get_first());
			}
		}
		
		return seleccionados;
	}
	
	/**
	 * 
	 * @param poblacion
	 * @param prob_seleccion
	 * @param prob_acumulada
	 * @param tam_seleccionados
	 * @return
	
	 * ENG: Selection by leftovers.
	 * 		The accumulated probabilities are multiplied by the size of the selection,
	 * 		and select this number rounded down times.
	 * 		The leftovers individuals are chosen with another method.
	 * ESP: Seleccion por restos.
	 * 		Las probabilidades acumuladas se multiplican por el tamaño de la seleccion,
	 * 		y se seleccionan este número redondeado para abajo veces.
	 * 		Los individuos restantes se eligen con otro método.
	 */
	public Individuo[] restos(Individuo[] poblacion, double[] prob_seleccion, double[] prob_acumulada, int tam_seleccionados) {
		Individuo[] seleccionados=new Individuo[tam_seleccionados];

		int x=0, num;
		double aux;		
		
		// ENG: Main loop.
		// ESP: Bucle principal.
		for (int i=0;i<tam_seleccionados;i++) {
			// ENG: Calculates the value of num by multiplying its probability with the selection size.
			// ESP: Calcula el valor de num al multiplicar su probabilidad con el tamaño de seleccion.
			aux=prob_seleccion[i]*(tam_seleccionados);
			num=(int) aux;
			
			// ENG: Select the current individual num times.
			// ESP: Selecciona num veces el individuo actual.
			for (int j=0;j<num;j++) {
				if(bin) seleccionados[x++]=new IndividuoBin(poblacion[i]);
				else seleccionados[x++]	  =new IndividuoReal(poblacion[i]);
			}

		}
		
		// ENG: The leftover set is calculated with a random method.
		// ESP: El conjunto restante se calcula con un metodo aleatorio.
		Individuo[] resto=null;
		int func=(int) (Math.random()*5);
		switch (func) { 

		case 0:
			resto=ruleta(poblacion, prob_acumulada, tam_seleccionados-x);
			break;
		case 1:
			resto=torneoDeterministico(poblacion, 3, tam_seleccionados-x);	
			break;
		case 2:
			resto=torneoProbabilistico(poblacion, 3, 0.9, tam_seleccionados-x);
			break;
		case 3:
			resto=estocasticoUniversal1(poblacion, prob_acumulada, tam_seleccionados-x);
			break;
		case 4:
			resto=truncamiento(poblacion, prob_acumulada, 0.5, tam_seleccionados-x);
			break;
		case 5:
			resto=ranking(poblacion, prob_seleccion, tam_poblacion-x, 2);			
			break;

		default:
			break;
		}

		
		int i=0;
		// ENG: The leftover set is added to the selection.
		// ESP: Se añade a la seleccion el conjunto restante.
		while (x<tam_seleccionados) seleccionados[x++]=resto[i++];

		return seleccionados;
	}
	
	/**
	 * 
	 * @param poblacion
	 * @param prob_seleccion
	 * @param tam_seleccionados
	 * @param beta
	 * @return
	
	 * ENG: Selection by ranking.
	 *		The probabilities of each individual depend only on their position in the ranking 
	 * 		and not its original probability.
	 * ESP: Seleccion por ranking.
	 * 		Las probabilidades de cada individuo dependen solo de su posición en el ranking 
	 * 		y no de su probabilidad original.
	 */
	@SuppressWarnings("unchecked")
	public Individuo[] ranking(Individuo[] poblacion, double[] prob_seleccion, int tam_seleccionados, double beta) {
		Individuo[] seleccionados=new Individuo[tam_seleccionados];
		
		// ENG: Sort the individuals by the selection probability.
		// ESP: Ordena a los individuos por la probabilidad de seleccion.
		Pair<Individuo, Double>[] pairs=new Pair[tam_seleccionados];
		for (int i=0;i<tam_seleccionados;i++) {
			pairs[i]=new Pair<>(poblacion[i], prob_seleccion[i]);
		}
		Arrays.sort(pairs, Comparator.comparingDouble(p -> ((Pair<Individuo, Double>) p).get_second()).reversed());
		
		double val=0.0, acum=0.0;
		double prob_acumulada[]=new double[tam_seleccionados];
		
		// ENG: Modifies the probabilities ​​to adapt them to the conditions.
		// ESP: Modifica las probabilidades para adaptarlos a las condiciones.
		for(int i=1;i<=tam_seleccionados;i++) {
			val=(beta-(2*(beta-1)*((i-1)/(tam_seleccionados-1.0))))/tam_seleccionados;
			acum+=val;
			prob_acumulada[i-1]=acum;
		}
		
		double rand;
		
		// ENG: Main loop.
		// ESP: Bucle principal.		
		for (int i=0;i<tam_seleccionados;i++) {
			// ENG: Random number.
			// ESP: Numero aleatorio
			rand=Math.random();
			
			// ENG: Binary search with the random number to select the individual.
			// ESP: Busqueda binaria con el numero aleatorio para seleccionar el individuo.
			
			if(bin) seleccionados[i]=new IndividuoBin(poblacion[i]);
			else seleccionados[i]	=new IndividuoReal(pairs[busqueda_binaria(rand, prob_acumulada)].get_first());
		}
		
		
		return seleccionados;
	}
}


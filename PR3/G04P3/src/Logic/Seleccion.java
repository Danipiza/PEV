package Logic;

import java.util.Arrays;
import java.util.Comparator;

import Model.Individuo;
import Model.IndividuoArbol;
import Model.IndividuoGramatica;
import Model.Individuo;
import Utils.Pair;

public class Seleccion {
	private int tam_poblacion;
	private int tam_elite;
	private int ind_modo;
	
	public Seleccion(int _tam_poblacion, int tam_elite, int ind_modo) {
		this.tam_poblacion = _tam_poblacion;
		this.tam_elite = tam_elite;
		this.ind_modo=ind_modo;
	}

	
	protected int busquedaBinaria(double x, double[] prob_acumulada) {
		int i = 0, j = tam_poblacion-tam_elite - 1;
		int m = 0;
		while (i < j) {
			m = (j + i) / 2;

			if (x > prob_acumulada[m]) {
				i = m + 1;
			} else if (x < prob_acumulada[m]) {
				j = m;
			} else
				return m;
		}

		return i;
	}

	public Individuo[] ruleta(Individuo[] poblacion, double[] prob_acumulada, int tam_seleccionados) {
		Individuo[] seleccionados = new Individuo[tam_seleccionados];

		double rand;
		for (int i = 0; i < tam_seleccionados; i++) {
			rand = Math.random();		
			
			if(ind_modo==0) seleccionados[i] = new IndividuoArbol((IndividuoArbol) poblacion[busquedaBinaria(rand, prob_acumulada)]);
			else seleccionados[i]=new IndividuoGramatica((IndividuoGramatica) poblacion[busquedaBinaria(rand, prob_acumulada)]);
		}

		return seleccionados;
	}

	public Individuo[] torneoDeterministico(Individuo[] poblacion, int k, int tam_seleccionados) {
		Individuo[] seleccionados=new Individuo[tam_seleccionados];
		
		/*if(ind_modo==0) seleccionados= new IndividuoArbol[tam_seleccionados];
		else seleccionados= new IndividuoArbol[tam_seleccionados];*/
		
		double randomFitness;
		int indexMax;
		double max;
		for (int i = 0; i < tam_seleccionados; i++) {
			max = Double.NEGATIVE_INFINITY;
			indexMax = -1;
			for (int j = 0; j < k; j++) {
				int randomIndex = (int) (Math.random() * tam_poblacion);
				randomFitness = poblacion[randomIndex].fitness;
				if (randomFitness > max) {
					max = randomFitness;
					indexMax = randomIndex;
				} 
			}	
			 
			
			if(ind_modo==0) seleccionados[i] = new IndividuoArbol((IndividuoArbol) poblacion[indexMax]);
			else seleccionados[i] = new IndividuoGramatica((IndividuoGramatica) poblacion[indexMax]);			
		}

		return seleccionados;
	}
	
	public Individuo[] torneoProbabilistico(Individuo[] poblacion, int k, double p, int tam_seleccionados) {
		Individuo[] seleccionados = new Individuo[tam_seleccionados];

		double randomFitness;
		int indexMax, indexMin;
		double max, min;
		for (int i = 0; i < tam_seleccionados; i++) {
			max = Double.NEGATIVE_INFINITY;
			min = Double.MAX_VALUE;
			indexMax = -1;
			indexMin = -1;
			for (int j = 0; j < k; j++) {
				int randomIndex = (int) (Math.random() * poblacion.length);
				randomFitness = poblacion[randomIndex].fitness;
				if (randomFitness > max) {
					max = randomFitness;
					indexMax = randomIndex;
				} 
				if (randomFitness < min) {
					min = randomFitness;
					indexMin = randomIndex;
				}
			}	
			 
			int index=indexMin;
			if(Math.random()<=p) index=indexMax;
			
			//seleccionados[i] = new IndividuoReal((opt && Math.random() <= p || !opt && Math.random() > p ? poblacion[indexMax] : poblacion[indexMin]));
			if(ind_modo==0) seleccionados[i] = new IndividuoArbol((IndividuoArbol) poblacion[index]);
			else seleccionados[i] = new IndividuoGramatica((IndividuoGramatica) poblacion[index]);
		}

		return seleccionados;
	}
	
	public Individuo[] estocasticoUniversal1(Individuo[] poblacion, double[] prob_acumulada, int tam_seleccionados) {
		Individuo[] seleccionados = new Individuo[tam_seleccionados];
		
		double incr = 1.0 / tam_seleccionados, rand = Math.random() * incr;
		for (int i = 0; i < tam_seleccionados; i++) {
			if(ind_modo==0) seleccionados[i] = new IndividuoArbol((IndividuoArbol) poblacion[busquedaBinaria(rand, prob_acumulada)]);
			else seleccionados[i] = new IndividuoGramatica((IndividuoGramatica) poblacion[busquedaBinaria(rand, prob_acumulada)]);
			
			rand += incr;
		}

		return seleccionados;
	}
	
	public Individuo[] estocasticoUniversal2(Individuo[] poblacion, double[] prob_acumulada, int tam_seleccionados) {
		Individuo[] seleccionados = new Individuo[tam_seleccionados];

		double distMarca = 1.0 / tam_seleccionados, rand = Math.random() * distMarca;
		for (int i = 0; i < tam_seleccionados; i++) {
			double x = (rand + i) / tam_seleccionados;
			
			if(ind_modo==0) seleccionados[i] = new IndividuoArbol((IndividuoArbol) poblacion[busquedaBinaria(x, prob_acumulada)]);
			else seleccionados[i] = new IndividuoGramatica((IndividuoGramatica) poblacion[busquedaBinaria(x, prob_acumulada)]);
		}

		return seleccionados;
	}
	
	@SuppressWarnings("unchecked")
	public Individuo[] truncamiento(Individuo[] poblacion, double[] prob_seleccion, double trunc, int tam_seleccionados) {
		Individuo[] seleccionados = new Individuo[tam_seleccionados];

		Pair<Individuo, Double>[] pairs = new Pair[tam_seleccionados];
		for (int i = 0; i < tam_seleccionados; i++) {
			pairs[i] = new Pair<>(poblacion[i], prob_seleccion[i]);
		}

		Arrays.sort(pairs, Comparator.comparingDouble(p -> p.getValue()));
		

		int x = 0, num = (int) (1.0 / trunc);
		int n=pairs.length-1;
	
		for (int i = 0; i < (tam_seleccionados) * trunc; i++) {			
			for (int j = 0; j < num && x<tam_seleccionados; j++) {
				if(ind_modo==0) seleccionados[x++] = new IndividuoArbol((IndividuoArbol)pairs[n-i].getKey());
				else seleccionados[x++] = new IndividuoGramatica((IndividuoGramatica) pairs[n-i].getKey());
			}
		}
		
		return seleccionados;
	}

	public Individuo[] restos(Individuo[] poblacion, double[] prob_seleccion, double[] prob_acumulada, int tam_seleccionados) {
		Individuo[] seleccionados = new Individuo[tam_seleccionados];

		int x = 0, num;
		double aux;		
		for (int i = 0; i < tam_seleccionados; i++) {
			aux = prob_seleccion[i] * (tam_seleccionados);
			num = (int) aux;
			for (int j = 0; j < num; j++) {
				if(ind_modo==0) seleccionados[x++] = new IndividuoArbol((IndividuoArbol) poblacion[i]);
				else seleccionados[x++] = new IndividuoGramatica((IndividuoGramatica) poblacion[i]);
			}

		}
		
		
		Individuo[] resto = null;
		int func=(int) (Math.random()*5);
		switch (func) { // SE SUMA LA PARTE DE elitismo PORQUE SINO SE RESTA 2 VECES, 
						// EN LA PARTE DE restos() Y LA FUNCION RANDOM
		case 0:
			resto = ruleta(poblacion, prob_acumulada, tam_seleccionados - x);
			break;
		case 1:
			resto = torneoDeterministico(poblacion, 3, tam_seleccionados - x);	
			break;
		case 2:
			resto = torneoProbabilistico(poblacion, 3, 0.9, tam_seleccionados - x);
			break;
		case 3:
			resto = estocasticoUniversal1(poblacion, prob_acumulada, tam_seleccionados - x);
			break;
		case 4:
			resto = estocasticoUniversal2(poblacion, prob_acumulada, tam_seleccionados - x);
			break;
		case 5:
			resto = truncamiento(poblacion, prob_acumulada, 0.5, tam_seleccionados - x);
			break;

		default:
			break;
		}

		
		int i = 0;
		while (x < tam_seleccionados) {
			seleccionados[x++] = resto[i++];
		}

		return seleccionados;
	}

	public Individuo[] ranking(Individuo[] poblacion, double[] prob_seleccion, int tam_seleccionados, double beta) {
		Individuo[] seleccionados = new Individuo[tam_seleccionados];

		Pair<Individuo, Double>[] pairs=new Pair[tam_seleccionados];
		for (int i=0;i<tam_seleccionados;i++) {
			pairs[i]=new Pair<>(poblacion[i], prob_seleccion[i]);
		}

		//Arrays.sort(pairs, Comparator.comparingDouble(p -> p.getValue()));
		Arrays.sort(pairs, Comparator.comparingDouble(p -> ((Pair<Individuo, Double>) p).getValue()).reversed());
		
		double val=0.0, acum=0.0;
		double prob_acumulada[]=new double[tam_seleccionados];
		//double probs[]=new double[tam_seleccionados];
		
		
		for(int i=1;i<=tam_seleccionados;i++) {
			val=(beta-(2*(beta-1)*((i-1)/(tam_seleccionados-1.0))))/tam_seleccionados;
			acum+=val;
			prob_acumulada[i-1]=acum;
			//probs[i-1]=val;
		}
		
		double rand;
		for (int i = 0; i < tam_seleccionados; i++) {
			rand = Math.random();		
			
			if(ind_modo==0) seleccionados[i] = new IndividuoArbol((IndividuoArbol) pairs[busquedaBinaria(rand, prob_acumulada)].getKey());
			else seleccionados[i] = new IndividuoGramatica((IndividuoGramatica) pairs[busquedaBinaria(rand, prob_acumulada)].getKey());
		}
		
		
		return seleccionados;
	}
}
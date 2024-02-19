package Logic;

import java.util.Arrays;
import java.util.Comparator;

import Model.Individuo;
import Model.IndividuoBin;
import Utils.Pair;

public class Seleccion {
	int tam_poblacion;
	boolean opt;
	
	public Seleccion(int _tam_poblacion, boolean _opt) {
		this.tam_poblacion = _tam_poblacion;
		this.opt = _opt;
	}


	protected int busquedaBinaria(double x, double[] prob_acumulada) {
		int i = 0, j = tam_poblacion - 1;
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
			seleccionados[i] = new IndividuoBin(poblacion[busquedaBinaria(rand, prob_acumulada)]);
		}

		return seleccionados;
	}

	public Individuo[] torneoDeterministico(Individuo[] poblacion, double[] prob_acumulada, int k, int tam_seleccionados) {
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
				int randomIndex = (int) (Math.random() * tam_poblacion);
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
			 
			seleccionados[i] = new IndividuoBin((opt ? poblacion[indexMax] : poblacion[indexMin]));
		}

		return seleccionados;
	}

	public Individuo[] torneoProbabilistico(Individuo[] poblacion, double[] prob_acumulada, int k, double p, int tam_seleccionados) {
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
			 
			seleccionados[i] = new IndividuoBin((opt && Math.random() <= p || !opt && Math.random() > p ? poblacion[indexMax] : poblacion[indexMin]));
			
		}

		return seleccionados;
	}

	public Individuo[] estocasticoUniversal1(Individuo[] poblacion, double[] prob_acumulada, int tam_seleccionados) {
		Individuo[] seleccionados = new Individuo[tam_seleccionados];
		
		double incr = 1.0 / tam_seleccionados, rand = Math.random() * incr;
		for (int i = 0; i < tam_seleccionados; i++) {
			seleccionados[i] = new IndividuoBin(poblacion[busquedaBinaria(rand, prob_acumulada)]);
			rand += incr;
		}

		return seleccionados;
	}

	public Individuo[] estocasticoUniversal2(Individuo[] poblacion, double[] prob_acumulada, int tam_seleccionados) {
		Individuo[] seleccionados = new Individuo[tam_seleccionados];

		double distMarca = 1.0 / tam_seleccionados, rand = Math.random() * distMarca;
		for (int i = 0; i < tam_seleccionados; i++) {
			double x = (rand + i) / tam_seleccionados;
			seleccionados[i] = new IndividuoBin(poblacion[busquedaBinaria(x, prob_acumulada)]);
		}

		return seleccionados;
	}

	public Individuo[] truncamiento(Individuo[] poblacion, double[] prob_seleccion, double trunc, int tam_seleccionados) {
		Individuo[] seleccionados = new Individuo[tam_seleccionados];

		Pair<Individuo, Double>[] pairs = new Pair[tam_poblacion];
		for (int i = 0; i < tam_poblacion; i++) {
			pairs[i] = new Pair<>(poblacion[i], prob_seleccion[i]);
		}

		Arrays.sort(pairs, new Comparator<Pair<Individuo, Double>>() {
			public int compare(Pair<Individuo, Double> a, Pair<Individuo, Double> b) {
				if (a.getValue() > b.getValue())
					return -1;
				else
					return 1;
			}
		});

		int x = 0, num = (int) (1.0 / trunc);
		for (int i = 0; i < tam_seleccionados * trunc; i++) {
			for (int j = 0; j < num; j++) {
				seleccionados[x++] = new IndividuoBin(poblacion[i]);
			}
		}

		return seleccionados;
	}

	public Individuo[] restos(Individuo[] poblacion, double[] prob_seleccion, double[] prob_acumulada, int tam_seleccionados) {
		Individuo[] seleccionados = new Individuo[tam_seleccionados];

		int x = 0;
		for (int i = 0; i < tam_seleccionados; i++) {
			double aux = prob_seleccion[i] * tam_seleccionados;
			int num = (int) aux;
			for (int j = 0; j < num; j++) {
				seleccionados[x++] = new IndividuoBin(poblacion[i]);
			}

		}
		
		// random de otros metodos
		Individuo[] resto = ruleta(poblacion, prob_acumulada, tam_seleccionados - x);
		int i = 0;
		while (x < tam_seleccionados) {
			seleccionados[x++] = resto[i++];
		}

		return seleccionados;
	}
}

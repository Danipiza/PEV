package Logic;

import java.util.Arrays;
import java.util.Comparator;

import Model.IndividuoReal;
import Utils.Pair;

public class Seleccion {
	private int tam_poblacion;
	
	public Seleccion(int _tam_poblacion, int funcion_idx) {
		this.tam_poblacion = _tam_poblacion;
		//this.tam_elite=tam_elite;
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

	public IndividuoReal[] ruleta(IndividuoReal[] poblacion, double[] prob_acumulada, int tam_seleccionados) {
		IndividuoReal[] seleccionados = new IndividuoReal[tam_seleccionados];

		double rand;
		for (int i = 0; i < tam_seleccionados; i++) {
			rand = Math.random();
			
			
			seleccionados[i] = new IndividuoReal(poblacion[busquedaBinaria(rand, prob_acumulada)]);
		}

		return seleccionados;
	}

	public IndividuoReal[] torneoDeterministico(IndividuoReal[] poblacion, int k, int tam_seleccionados) {
		IndividuoReal[] seleccionados = new IndividuoReal[tam_seleccionados];

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
			 
			//seleccionados[i] = new IndividuoReal((opt ? poblacion[indexMax] : poblacion[indexMin]));
			seleccionados[i] = new IndividuoReal(poblacion[indexMin]);
		}

		return seleccionados;
	}
	
	public IndividuoReal[] torneoProbabilistico(IndividuoReal[] poblacion, int k, double p, int tam_seleccionados) {
		IndividuoReal[] seleccionados = new IndividuoReal[tam_seleccionados];

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
			 
			//seleccionados[i] = new IndividuoReal((opt && Math.random() <= p || !opt && Math.random() > p ? poblacion[indexMax] : poblacion[indexMin]));
			seleccionados[i] = new IndividuoReal(poblacion[indexMin]);	
		}

		return seleccionados;
	}
	
	public IndividuoReal[] estocasticoUniversal1(IndividuoReal[] poblacion, double[] prob_acumulada, int tam_seleccionados) {
		IndividuoReal[] seleccionados = new IndividuoReal[tam_seleccionados];
		
		double incr = 1.0 / tam_seleccionados, rand = Math.random() * incr;
		for (int i = 0; i < tam_seleccionados; i++) {
			seleccionados[i] = new IndividuoReal(poblacion[busquedaBinaria(rand, prob_acumulada)]);
			
			rand += incr;
		}

		return seleccionados;
	}
	
	public IndividuoReal[] estocasticoUniversal2(IndividuoReal[] poblacion, double[] prob_acumulada, int tam_seleccionados) {
		IndividuoReal[] seleccionados = new IndividuoReal[tam_seleccionados];

		double distMarca = 1.0 / tam_seleccionados, rand = Math.random() * distMarca;
		for (int i = 0; i < tam_seleccionados; i++) {
			double x = (rand + i) / tam_seleccionados;
			
			seleccionados[i] = new IndividuoReal(poblacion[busquedaBinaria(x, prob_acumulada)]);
		}

		return seleccionados;
	}
	
	@SuppressWarnings("unchecked")
	public IndividuoReal[] truncamiento(IndividuoReal[] poblacion, double[] prob_seleccion, double trunc, int tam_seleccionados) {
		IndividuoReal[] seleccionados = new IndividuoReal[tam_seleccionados];

		Pair<IndividuoReal, Double>[] pairs = new Pair[tam_seleccionados];
		for (int i = 0; i < tam_seleccionados; i++) {
			pairs[i] = new Pair<>(poblacion[i], prob_seleccion[i]);
		}

		//Arrays.sort(pairs, Comparator.comparingDouble(Pair<IndividuoReal, Double>::getKey));
		Arrays.sort(pairs, Comparator.comparingDouble(p -> p.getValue()));
		//Arrays.sort(pairs, Comparator.comparingDouble(p -> ((Pair<IndividuoReal, Double>) p).getValue()).reversed());
		/*Arrays.sort(pairs, new Comparator<Pair<IndividuoReal, Double>>() {
			public int compare(Pair<IndividuoReal, Double> a, Pair<IndividuoReal, Double> b) {
				if (a.getValue() > b.getValue())
					return -1;
				else
					return 1;
			}
		});*/

		int x = 0, num = (int) (1.0 / trunc);
		
		for (int i = 0; i < (tam_seleccionados) * trunc; i++) {
			for (int j = 0; j < num && x<tam_seleccionados; j++) {
				seleccionados[x++] = new IndividuoReal(poblacion[i]);
			}
		}
		
		return seleccionados;
	}

	public IndividuoReal[] restos(IndividuoReal[] poblacion, double[] prob_seleccion, double[] prob_acumulada, int tam_seleccionados) {
		IndividuoReal[] seleccionados = new IndividuoReal[tam_seleccionados];

		int x = 0, num;
		double aux;		
		for (int i = 0; i < tam_seleccionados; i++) {
			aux = prob_seleccion[i] * (tam_seleccionados);
			num = (int) aux;
			for (int j = 0; j < num; j++) {
				seleccionados[x++] = new IndividuoReal(poblacion[i]);
			}

		}
		
		
		IndividuoReal[] resto = null;
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
	
	// TODO
	public IndividuoReal[] ranking(IndividuoReal[] poblacion, double[] prob_seleccion, double[] prob_acumulada, int tam_seleccionados) {
		IndividuoReal[] seleccionados = new IndividuoReal[tam_seleccionados];

		

		return seleccionados;
	}
}

package Logic;

import java.util.Arrays;
import java.util.Comparator;

import Utils.Pair;

public class Seleccion {
	
	int tam_poblacion;
	
	public Seleccion(int tam_poblacion) {
		this.tam_poblacion=tam_poblacion;
	}
	
	
	private int busquedaBinaria(double x, double[] prob_acumulada) {
		int ret=0;
		int i=0, j=prob_acumulada.length-1;
		int m=0;		
		while (i <= j) {
            m=i+(j-i)/2;

            if (prob_acumulada[m]>=x) {
                ret = m;
                j = m - 1;
            } 
            else i=m+1;
        }
		
		return ret;
	}
	public Individuo[] ruleta(Individuo[] poblacion, double[] prob_acumulada) {
		Individuo[] seleccionados = new Individuo[tam_poblacion];
		
		double rand;
		for(int i=0;i<tam_poblacion;i++) {
			rand=Math.random();
			seleccionados[i]=poblacion[busquedaBinaria(rand, prob_acumulada)];
		}	
		
		// TODO QUITAR
		for(Individuo ind:seleccionados) {
			for(double f: ind.fenotipo) {
				System.out.print(f + " ");				
			}
			System.out.println();								
		}
		
		
		return seleccionados;
	}	
	public Individuo[] torneoDeterministico(Individuo[] poblacion, int k) {
		Individuo[] seleccionados = new Individuo[poblacion.length];
		Individuo[] torneo = new Individuo[k];
		for (int i = 0; i < poblacion.length; i++) {
			for (int j = 0; j < k; j++) {
				int randomIndex = (int) (Math.random() * poblacion.length);
				torneo[j] = poblacion[randomIndex];
			}
			
			double max = 0; int index = 0;
			for (int j = 0; j < 3; j++) {
				if (torneo[j].fitness > max) {
					max = torneo[j].fitness;
					index = j;
				}
			}
			seleccionados[i] = torneo[index];
		}
			
		return seleccionados;
	}
	public Individuo[] torneoProbabilistico(Individuo[] poblacion, int k, double p) {
		Individuo[] seleccionados = new Individuo[poblacion.length];
		Individuo[] torneo = new Individuo[k];
		for (int i = 0; i < poblacion.length; i++) {
			for (int j = 0; j < k; j++) {
				int randomIndex = (int) (Math.random() * poblacion.length);
				torneo[j] = poblacion[randomIndex];
			}
			
			double max = 0, min = Double.MAX_VALUE; int index = 0;
			if (Math.random() > p) {
				for (int j = 0; j < 3; j++) {
					if (torneo[j].fitness > max) {
						max = torneo[j].fitness;
						index = j;
					}
				}
			}
			else {
				for (int j = 0; j < 3; j++) {
					if (torneo[j].fitness < min) {
						min = torneo[j].fitness;
						index = j;
					}
				}
			}
			
			seleccionados[i] = torneo[index];
		}
			
		return seleccionados;
	}
	public Individuo[] estocasticoUniversal1(Individuo[] poblacion, double[] prob_acumulada) {
		Individuo[] seleccionados = new Individuo[tam_poblacion];
		
		double incr = 1/poblacion.length, rand = Math.random() * incr;
		for(int i=0;i<tam_poblacion;i++) {
			seleccionados[i]=poblacion[busquedaBinaria(rand, prob_acumulada)];
			rand += incr;
		}	
		
		return seleccionados;
	}

	public Individuo[] estocasticoUniversal2(Individuo[] poblacion, double[] prob_acumulada) {
		Individuo[] seleccionados = new Individuo[tam_poblacion];
		
		double distMarca = 1/poblacion.length, rand = Math.random() * distMarca;
		for(int i=0;i<tam_poblacion;i++) {
			seleccionados[i]=poblacion[busquedaBinaria((rand + i)/distMarca, prob_acumulada)];
		}	
		
		return seleccionados;
	}

	public Individuo[] truncamiento(Individuo[] poblacion, double[] prob_acumulada) {
		Individuo[] seleccionados = new Individuo[tam_poblacion];
		
		Pair<Individuo, Double>[] pairs = new Pair[tam_poblacion];
        for (int i = 0; i < tam_poblacion; i++) {
            pairs[i] = new Pair<>(poblacion[i], prob_acumulada[i]);
        }

		Arrays.sort(pairs, new Comparator<Pair<Individuo, Double>>() {
			public int compare(Pair<Individuo, Double> a, Pair<Individuo, Double> b) {
			   if (a.getValue() > b.getValue()) return -1;
			   else return 1;
			}
		});

		double trunc = 0.5; int x = 0, num = (int) (1/trunc); 
		for(int i = 0; i < tam_poblacion * trunc; i++) {
			for (int j = 0; j < num; j++) {
				seleccionados[x] = poblacion[i];
			}
		}		
		
		return seleccionados;
	}



	public Individuo[] restos(Individuo[] poblacion, double[] prob_acumulada) {
		Individuo[] seleccionados = new Individuo[tam_poblacion];
		
		int x = 0;
		for(int i = 0;i < tam_poblacion; i++) {
			int num = (int) prob_acumulada[i] * tam_poblacion;
			for (int j = 0; j < num; j++) {
				seleccionados[x] = poblacion[i];
			}
			
		}	

		while(x < tam_poblacion ) {
			seleccionados[x] = poblacion[busquedaBinaria(Math.random() * tam_poblacion, prob_acumulada)];
			x++;
		}	
		
		
		return seleccionados;
	}
	

	
}
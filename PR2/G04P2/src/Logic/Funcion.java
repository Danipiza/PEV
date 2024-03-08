package Logic;

import java.util.Stack;

import Utils.Pair;

public class Funcion {
	public Pair<Double, Double> intervalosGrafico;
	public boolean opt = false;

	public int aviones = 12;
	public int pistas = 3;

	public int[] tipoAvion = {
		0, 1, 0, 0, 2, 0, 1, 0, 0, 2, 0, 1
	};

	public int[][] tel = {
		{11, 15, 6, 6, 9, 7, 15, 6, 6, 9, 7, 9},
		{10, 17, 7, 7, 12, 6, 17, 7, 7, 12, 6, 7},
		{9, 19, 8, 8, 15, 5, 19, 8, 8, 15, 5, 5}
	};
	
	// 0: Pesado (W)
	// 1: Grande
	// 2: Pequeño
	public double[][] sep = {
		{1, 1.5, 2},
		{1, 1.5, 1.5},
		{1, 1, 1}
	};
	

	public double fitness(int[] avion) {
		Stack<Pair<Integer, Double>>[] tla = new Stack[pistas];
		for (int i = 0; i < pistas; i++) {
			tla[i] = new Stack<>();
			tla[i].push(new Pair<>(2, 0.0)); 
		}

		double fitness = 0.0;
		for (int i = 0; i < aviones; i++) {
			double newTla, menor_tla = 24.0;
			int indexPista = 0;
			for (int j = 0; j < pistas; j++) {
				newTla = Math.max(tla[j].peek().getValue() + sep[tla[j].peek().getKey()][tipoAvion[avion[i]]], tel[j][avion[i]]);
				if (newTla < menor_tla) {
					menor_tla = newTla;
					indexPista = j;
				}
			}

			tla[indexPista].push(new Pair<>(tipoAvion[avion[i]], menor_tla));
			
			double menor_tel = 24.0;
			for (int j = 0; j < pistas; j++) {
				if (tel[j][avion[i]] < menor_tel) menor_tel = tel[j][avion[i]];
			}

			fitness += Math.pow(menor_tla - menor_tel, 2);
		}
		return fitness;
	}


	public double cmp(double a, double b) {
		if (a < b)
			return a;
		else
			return b;
	}

	public double cmpPeor(double a, double b) {
		if (a > b)
			return a;
		else
			return b;
	}

	
}

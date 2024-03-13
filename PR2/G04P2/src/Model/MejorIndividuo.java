package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Logic.Funcion;
import Utils.Pair;

public class MejorIndividuo {
	
	public int num_vuelos;
	public int num_pistas;
	public double fitness;
	
	public int[] fenotipo;
	
	public List<FilaInd> l[];
	
	@SuppressWarnings("unchecked")
	public MejorIndividuo(Funcion f, int num_pistas, int num_vuelos, Individuo mejor,
			int[] tipo_avion, int[][] TEL, double[][] sep, String[] vuelos_id) {
		this.num_pistas=num_pistas;
		this.num_vuelos=num_vuelos;
		
		l=new ArrayList[num_pistas];		
		for (int i=0;i<num_pistas;i++) l[i] = new ArrayList<>();		
		
		this.fenotipo=mejor.gen.v;
		String cromosoma="";		
		for(int a: fenotipo) {
			cromosoma+=a+1 + " ";
		}
		System.out.println(cromosoma);
		int[] avion=mejor.gen.v;
		System.out.println(f.fitness(avion));
		
		
		
		Stack<Pair<Integer, Double>>[] tla = new Stack[num_pistas];
		for (int i = 0; i < num_pistas; i++) {
			tla[i] = new Stack<>();
			tla[i].push(new Pair<>(2, 0.0)); 
		}

		
		fitness = 0.0;
		for (int i = 0; i < f.aviones; i++) {
			double newTla = 0, menor_tla = 24.0;
			int indexPista = 0;
			for (int j = 0; j < num_pistas; j++) {
				newTla = Math.max(tla[j].peek().getValue() + sep[tla[j].peek().getKey()][tipo_avion[avion[i]]], f.TEL[j][avion[i]]);
				if (newTla < menor_tla) {
					menor_tla = newTla;
					indexPista = j;
				}
			}

			tla[indexPista].push(new Pair<>(tipo_avion[avion[i]], menor_tla));
			
			double menor_tel = 24.0;
			for (int j = 0; j < num_pistas; j++) {
				if (f.TEL[j][avion[i]] < menor_tel) menor_tel = f.TEL[j][avion[i]];
			}
			
			fitness += Math.pow(menor_tla - menor_tel, 2);
		}
		
		System.out.println(fitness);
		
	}
	
	
}

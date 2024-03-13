package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Utils.Pair;

public class MejorIndividuo {
	
	public int num_vuelos;
	public int num_pistas;
	public double fitness;
	
	public int[] fenotipo;
	
	public List<FilaInd> l[];
	
	@SuppressWarnings("unchecked")
	public MejorIndividuo(int num_pistas, int num_vuelos, Individuo mejor,
			int[] tipo_avion, int[][] TEL, double[][] sep, String[] vuelos_id) {
		
		this.num_pistas=num_pistas;
		this.num_vuelos=num_vuelos;
		
		l=new ArrayList[num_pistas];		
		for (int i=0;i<num_pistas;i++) l[i] = new ArrayList<>();
		
		Stack<Pair<Integer, Double>>[] tla = new Stack[num_pistas];
		for (int i = 0; i < num_pistas; i++) {
			tla[i] = new Stack<>();
			tla[i].push(new Pair<>(2, 0.0)); 
		}
		this.fenotipo=mejor.gen.v;
		

		fitness = 0.0;
		double newTla=0.0, menor_tla=0.0;
		for (int i = 0; i < num_vuelos; i++) {
			menor_tla=24.0;
			int indexPista = 0;
			for (int j = 0; j < num_pistas; j++) {
				newTla = Math.max(tla[j].peek().getValue() + sep[tla[j].peek().getKey()][tipo_avion[mejor.gen.v[i]]], TEL[j][mejor.gen.v[i]]);
				if (newTla < menor_tla) {
					menor_tla = newTla;
					indexPista = j;
				}
			}

			tla[indexPista].push(new Pair<>(tipo_avion[mejor.gen.v[i]], menor_tla));
			
			//int vuelo, String Nombre, double TEL, double TLA, double RET
			FilaInd e=new FilaInd(mejor.gen.v[i],vuelos_id[mejor.gen.v[i]],
									TEL[indexPista][mejor.gen.v[i]],menor_tla,
									menor_tla-TEL[indexPista][mejor.gen.v[i]]);
			l[indexPista].add(e);
			
			double menor_tel = 24.0;
			for (int j = 0; j < num_pistas; j++) {
				if (TEL[j][mejor.gen.v[i]] < menor_tel) menor_tel = TEL[j][mejor.gen.v[i]];
			}
			
			//System.out.println("Avion: " + (i+1));
			//System.out.println("Menor_tla: "+ menor_tla);
			//System.out.println("Menor_tel: " + menor_tel);
			fitness += Math.pow(menor_tla - menor_tel, 2);
		}
		
		System.out.println(fitness);
		
	}
	
	
}

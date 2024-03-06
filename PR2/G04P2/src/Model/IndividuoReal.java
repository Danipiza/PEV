package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IndividuoReal {
	
	public double fitness;
	public double[] genes;	
	private int num_genes;
	
	public IndividuoReal(int num_genes) {		
		this.num_genes=num_genes;
		genes = new double[num_genes];
		fitness = 0;
		individuoAleatorio();
	}
	
	public IndividuoReal(IndividuoReal ind) {
		int num = ind.genes.length;
		genes = new double[num];
		fitness = 0;
		
		for (int i = 0; i < num; i++) {
			genes[i] = ind.genes[i];
		}
	 	
	}
	
	public void individuoAleatorio() {
		Random rand = new Random();
		List<Integer> vuelos=new ArrayList<Integer>();
		for(int i=0;i<num_genes;i++) vuelos.add(i);
		
		int i=0;
		while(!vuelos.isEmpty()) genes[i]=rand.nextInt(num_genes-i++);
	}

	

	public void printIndividuo() {
		for (double g : genes) {			
			System.out.print(g + " ");		
		}
		System.out.println();
		
	}


}

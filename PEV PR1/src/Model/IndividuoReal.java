package Model;

import java.util.Random;

public class IndividuoReal extends Individuo {
	
	//public double[] genes;
	
	public IndividuoReal(int num, int prec) {
		
		
		fenotipo = new double[num];
		fitness = 0;
		for (int i=0;i<num;i++) {					
			fenotipo[i]=genAleatorio(prec);
		}
	}
	
	public double genAleatorio(double prec) {
		Random rand = new Random();
				 
		
		double ret= rand.nextDouble()*Math.PI;  
		ret = Math.round(ret*prec)/prec; // Redondear a 2 decimales de precisión
		return ret;
	}

	public IndividuoReal(Individuo ind) {
		int num = ind.fenotipo.length;
		fenotipo = new double[num];
		fitness = 0;
		
		for (int i = 0; i < num; i++) {
			fenotipo[i] = ind.fenotipo[i];
		}
	 	
	}

	@Override
	public void printIndividuo() {
		for (double g : fenotipo) {			
			System.out.print(g + " ");		
		}
		System.out.println();
		
	}

	@Override
	public void calcular_fenotipo(double[] maximos, double[] minimos) {		
	}

	


}

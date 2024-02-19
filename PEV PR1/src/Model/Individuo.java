package Model;


public abstract class Individuo {
	
	public Gen[] genes;
	public double fitness;
	public double[] fenotipo;	
	
	public abstract void printIndividuo();

	public abstract void calcular_fenotipo(double[] maximos, double[] minimos);
}






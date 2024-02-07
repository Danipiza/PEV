package Logic;

public class AlgoritmoGenetico {
	
	private int tam_poblacion;
	private Individuo[] poblacion;
	
	private double[] fitness;
	
	private int max_generaciones;
	
	private double prob_cruce;
	private double prob_mut;
	
	//private int tam_torneo;
	
	private Individuo best;
	private int pos_best;
	
	private Funciones funcion;
	
	private int precision;
	
	public AlgoritmoGenetico() {
		
	}
	
	public void ejecutar() {
		
	}
		
	private int tamGen(double valorError, double min, double max) {
		return (int) (Math.log10(((max - min) / precision) + 1) / Math.log10(2));
	}
	
		
	
	
}

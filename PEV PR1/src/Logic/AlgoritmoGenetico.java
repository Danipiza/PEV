package Logic;

@SuppressWarnings("unused")
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
	private int funcion_idx;
	
	private double precision;
	
	public AlgoritmoGenetico() {
		
	}
	
	public void ejecutar() {
		
	}
	
	public void ejecuta(Valores valores) {
		setValores(valores);
		System.out.println("Valores guardados.");
		System.out.println(tam_poblacion + " " + max_generaciones + " " + 
						   prob_cruce + " " + prob_mut + " " + precision + " " + funcion_idx);
		System.out.println("Ejecutando...");
	}
	
	private void setValores(Valores valores) {
		this.tam_poblacion=valores.tam_poblacion;
		this.max_generaciones=valores.generaciones;
		this.prob_cruce=valores.prob_cruce;
		this.prob_mut=valores.prob_mut;
		this.precision=valores.precision;
		this.funcion_idx=valores.funcion_idx;
	}
		
	private int tamGen(double valorError, double min, double max) {
		return (int) (Math.log10(((max - min) / precision) + 1) / Math.log10(2));
	}
	
		
	
	
}

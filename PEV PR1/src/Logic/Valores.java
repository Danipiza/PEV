package Logic;


public class Valores {
	public int tam_poblacion;
	public int  generaciones;
	public double prob_cruce;
	public double prob_mut;
	public double precision;	
	public int funcion_idx;
	
	public Valores(int tam_poblacion,int generaciones,
			double prob_cruce, double prob_mut, 
			double precision, int funcion_idx) {
		
		this.tam_poblacion=tam_poblacion;
		this.generaciones=generaciones;
		this.prob_cruce=prob_cruce;
		this.prob_mut=prob_mut;
		this.precision=precision;
		this.funcion_idx=funcion_idx;
	}
}

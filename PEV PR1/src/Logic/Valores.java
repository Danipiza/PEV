package Logic;


public class Valores {
	public int tam_poblacion;
	public int generaciones;
	public int seleccion_idx;
	public int cruce_idx;	
	public double prob_cruce;
	public int mut_idx;
	public double prob_mut;
	public double precision;	
	public int funcion_idx;
	public int cromosomas;
	
	public Valores(int tam_poblacion, int generaciones, int seleccion_idx,
			int cruce_idx, double prob_cruce, int mut_idx, double prob_mut, 
			double precision, int funcion_idx, int cromosomas) {
		
		this.tam_poblacion=tam_poblacion;
		this.generaciones=generaciones;
		this.seleccion_idx=seleccion_idx;
		this.cruce_idx=cruce_idx;
		this.prob_cruce=prob_cruce;
		this.mut_idx=mut_idx;
		this.prob_mut=prob_mut;
		this.precision=precision;
		this.funcion_idx=funcion_idx;
		this.cromosomas=cromosomas;
	}
}

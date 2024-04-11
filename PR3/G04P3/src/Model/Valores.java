package Model;


public class Valores {
	public int tam_poblacion;
	public int generaciones;
	public int ini_idx;
	public int profundidad;
	public int seleccion_idx;
	public int cruce_idx;	
	public double prob_cruce;
	public int mut_idx;
	public double prob_mut;
	public int filas;	
	public int columnas;	
	public int elitismo;
	
	public Valores(int tam_poblacion, int generaciones, 
			int ini_idx, int profundidad, int seleccion_idx,
			int cruce_idx, double prob_cruce, int mut_idx, double prob_mut, 
			int filas, int columnas, int elitismo) {
		
		this.tam_poblacion=tam_poblacion;
		this.generaciones=generaciones;
		this.ini_idx=ini_idx;
		this.profundidad=profundidad;
		this.seleccion_idx=seleccion_idx;
		this.cruce_idx=cruce_idx;
		this.prob_cruce=prob_cruce;
		this.mut_idx=mut_idx;
		this.prob_mut=prob_mut;	
		this.filas=filas;
		this.columnas=columnas;
		this.elitismo=elitismo;
	}
}

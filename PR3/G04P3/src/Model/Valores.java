package Model;


public class Valores {
	public int tam_poblacion;
	public int generaciones;
	public int ini_idx;
	public int prof_O_longCrom;
	public int seleccion_idx;
	public int cruce_idx;	
	public double prob_cruce;
	public int mut_idx;
	public double prob_mut;
	public int filas;	
	public int columnas;	
	public int elitismo;
	public int ticks;
	public int modo;
	public int bloating_idx;
	public boolean[] opcs;
	public int nGen;
	public int nGenElim;
	
	public Valores(int tam_poblacion, int generaciones, 
			int ini_idx, int prof_O_longCrom, int seleccion_idx,
			int cruce_idx, double prob_cruce, int mut_idx, double prob_mut, 
			int filas, int columnas, int elitismo, int ticks, int modo, int bloating_idx,
			boolean[] opcs,int nGen, int nGenElim) {
		
		this.tam_poblacion=tam_poblacion;
		this.generaciones=generaciones;
		this.ini_idx=ini_idx;
		this.prof_O_longCrom=prof_O_longCrom;
		this.seleccion_idx=seleccion_idx;
		this.cruce_idx=cruce_idx;
		this.prob_cruce=prob_cruce;
		this.mut_idx=mut_idx;
		this.prob_mut=prob_mut;	
		this.filas=filas;
		this.columnas=columnas;
		this.elitismo=elitismo;
		this.ticks=ticks;
		this.modo=modo;
		this.bloating_idx=bloating_idx;
		this.opcs=opcs;
		this.nGen=nGen;
		this.nGenElim=nGenElim;
	}
}

package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.Simbolos.Exp;


public class IndividuoGramatica extends Individuo {
	
	private int tam_cromosoma;
	private int cromosoma[]; // Cromosoma con los Codones
	
	public String gramatica;
	//public List<String> operaciones;
	private int cont;
	
	private int filas;
	private int columnas;
	
	private Random random;
	
	

	/* GRAMATICA
	<start> := progn2(<op>, <op>) | salta(<op>) | suma(<op>, <op>)
	
	<op> := progn2(<op>, <op>) | salta(<op>) | suma(<op>, <op>) 
		  	| avanza | constante(<num>, <num>) | izquierda
		  	
  	<num> := 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 // O PONER RANDOM Y ASI NO USAR CODONES
	
	*/
	
	public IndividuoGramatica(int tam_cromosoma, int filas, int columnas) {		
		this.cromosoma=new int[tam_cromosoma];
		this.tam_cromosoma=tam_cromosoma;
		this.cont=0;
		this.filas=filas;
		this.columnas=columnas;
		this.gramatica="";
		this.fitness=0;
		
		this.random=new Random();
		
		this.operaciones=new ArrayList<String>();
		
		init();
	}
	
	public IndividuoGramatica(IndividuoGramatica individuo) {	
		this.tam_cromosoma=individuo.tam_cromosoma;
		this.cromosoma=new int[tam_cromosoma];
		this.fitness=individuo.fitness;
		this.cont=0;
		
		
		for(int i=0;i<this.tam_cromosoma; i++) {
			this.cromosoma[i]=individuo.cromosoma[i];
		}
	}
	
	private void init() {
		Random random = new Random();        
        
		for (int i=0;i<tam_cromosoma;i++) {
			cromosoma[i]=random.nextInt(256); // [0-255]
		}
		
		String act="";
		int tamHijos=2;
		int x=cromosoma[0]%3;
		if(x==0) act="progn2";
		else if(x==1) {
			act="salta";
			tamHijos=1;
		}
		else act="suma";
		cont=1;
		gramatica(act,tamHijos);
	}
	
	private void gramatica(String act, int tam) {
		gramatica+=act;
		
		
		
		if(tam!=0) gramatica+="(";
		for(int i=0;i<tam;i++) {			
			String hijo;
			int tamHijos=2;
			
			int x=cromosoma[cont]%6;
			if(x==0) {
				hijo="progn2";
				tamHijos=2;
			}
			else if(x==1) {
				hijo="salta";
				tamHijos=1;
			}
			else if(x==2) {
				hijo="suma";
				tamHijos=2;
			}
			else if(x==3) {
				hijo="avanza";
				tamHijos=0;
				//this.cont=(cont+1)%tam_cromosoma;
				//return;
			}
			else if(x==4) {
				hijo="constante("+random.nextInt(filas)+","
									   +random.nextInt(columnas)+")";
				tamHijos=0;
				//this.cont=(cont+1)%tam_cromosoma;
				//return;
			}
			else {
				hijo="izquierda";
				tamHijos=0;
				//this.cont=(cont+1)%tam_cromosoma;
				//return;
			}
			this.cont=(cont+1)%tam_cromosoma;
			
			gramatica(hijo,tamHijos);
			if(tam==2&&i==0) gramatica+=", ";
		}		
		if(tam!=0)  gramatica+=")";
	}
	
	
	public void printIndividuo() {		
		for(int x: this.cromosoma) {
			System.out.print(x + " ");
		}
		System.out.println();		
	}
	
	// TODO
	public String toString() {
		return "";
	}

	
	
}






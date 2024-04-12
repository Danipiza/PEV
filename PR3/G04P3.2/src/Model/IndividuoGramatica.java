package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.Simbolos.Exp;


public class IndividuoGramatica extends Individuo {
	
	private int tam_cromosoma;
	public int cromosoma[]; // Cromosoma con los Codones
	
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
		this.fitness=0;
		
		this.random=new Random();	
		
		
		init();
	}
	
	public IndividuoGramatica(IndividuoGramatica individuo) {	
		this.tam_cromosoma=individuo.tam_cromosoma;
		this.filas=individuo.filas;
		this.columnas=individuo.columnas;
		
		this.fitness=individuo.fitness;
		this.cont=0;		
		
		this.gramatica=individuo.gramatica;		
		
		this.operaciones=new ArrayList<String>();
		for(String l: individuo.operaciones) {
			this.operaciones.add(l);
		}
		
		this.cromosoma=new int[tam_cromosoma];
		for(int i=0;i<this.tam_cromosoma; i++) {
			this.cromosoma[i]=individuo.cromosoma[i];
		}
	}
	
	public void init() {
		Random random = new Random();        
        
		
		
		boolean aux=actualiza();
		while(!aux){
			System.out.println("Elimina");
			for (int i=0;i<tam_cromosoma;i++) {
				cromosoma[i]=random.nextInt(256); // [0-255]
			}
			aux=actualiza();
		}		
	}
	
	public boolean actualiza() {
		this.operaciones=new ArrayList<String>();
		this.gramatica="";
		
		String act="";
		int tamHijos=2;
		int x=cromosoma[0]%3;
		if(x==0) act="PROGN";
		else if(x==1) {
			act="SALTA";
			tamHijos=1;
		}
		else act="SUMA";
		cont=1;
		 
		boolean ret=gramatica(act,tamHijos,1);
		return ret;
	}
	
	private boolean gramatica(String act, int tam, int altura) {
		gramatica+=act;
		
		if(altura==100) return false;
		
		if(tam!=0) gramatica+="(";
		for(int i=0;i<tam;i++) {			
			String hijo;
			int tamHijos=2;
			
			
			
			int c=cromosoma[cont]%6;
			if(c==0) {
				hijo="PROGN";
				tamHijos=2;
			}
			else if(c==1) {
				hijo="SALTA";
				tamHijos=1;
			}
			else if(c==2) {
				hijo="SUMA";
				tamHijos=2;
			}
			else if(c==3) {
				hijo="AVANZA";
				operaciones.add("A");
				tamHijos=0;
			}
			else if(c==4) {
				int x=random.nextInt(filas);
				int y=random.nextInt(columnas);
				hijo="CONSTANTE("+x+","+y+")";
				operaciones.add("S"+x+y);
				tamHijos=0;
			}
			else {
				hijo="IZQUIERDA";
				operaciones.add("I");
				tamHijos=0;
			}
			this.cont=(cont+1)%tam_cromosoma;
			
			if(!gramatica(hijo,tamHijos,altura+1)) return false;
			if(tam==2&&i==0) gramatica+=", ";
		}		
		if(tam!=0)  gramatica+=")";
		
		return true;
	}
	
	
	
	// TODO
	public String toString() {
		return gramatica;
	}

	
	
}






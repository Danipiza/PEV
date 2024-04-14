package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.Simbolos.Exp;


public class IndividuoGramatica extends Individuo {
	
	private int tam_cromosoma;
	public int cromosoma[]; // Cromosoma con los Codones
	
	public String gramatica;
	
	
	private int cont;
	
	public int filas;
	public int columnas;
	
	private Random random;
	
	protected class Coord{
		int x;
		int y;
		public Coord(int x, int y) {
			this.x=x;
			this.y=y;
		}
	}
	
	

	/* GRAMATICA
	<start> := progn2(<op>, <op>) | salta(<op>) | suma(<op>, <op>)
	
	<op> := progn2(<op>, <op>) | salta(<op>) | suma(<op>, <op>) 
		  	| avanza | constante(<num>, <num>) | izquierda
		  	
  	<num> := 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 // O PONER RANDOM Y ASI NO USAR CODONES
	
	*/
	
	public IndividuoGramatica(int tam_cromosoma, int filas, int columnas) {		
		this.cromosoma=new int[tam_cromosoma];
		this.tam_cromosoma=tam_cromosoma;
		this.filas=filas;
		this.columnas=columnas;	
					
		this.fitness=0;		
		this.random=new Random();	
		this.nodos=0; // Bloating
		
		init();
	}
	
	public IndividuoGramatica(IndividuoGramatica individuo) {	
		this.tam_cromosoma=individuo.tam_cromosoma;
		this.filas=individuo.filas;
		this.columnas=individuo.columnas;
		
		this.nodos=individuo.nodos; // Bloating
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
	
	public IndividuoGramatica(int[] cromosoma, int filas, int columnas) {
		this.tam_cromosoma=cromosoma.length;
		this.filas=filas;
		this.columnas=columnas;
		
		this.nodos=0; // Bloating
		this.fitness=0;
		this.cont=0;		
		
		this.cromosoma=new int[tam_cromosoma];
		for(int i=0;i<this.tam_cromosoma; i++) {
			this.cromosoma[i]=cromosoma[i];
		}
		
		this.random=new Random();	
		
		init();				
	}
	
	public void init() {
		Random random = new Random(); 
		
		this.cont=0;
		this.operaciones=new ArrayList<String>();
		this.gramatica="";
		
		for (int i=0;i<tam_cromosoma;i++) {
			cromosoma[i]=random.nextInt(256); // [0-255]
		}
		
		try {
			start();
		}
		catch(Exception e) {
			init();
		}
		
		
	}
	
	private void start() {
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
		
		op(act, tamHijos);
		//if(act.equals("SALTA")) operaciones.add("S"+aux.x+aux.y);
	}
	
	private Coord op(String act, int tam) {
		this.gramatica+=act;
		this.nodos++; //Bloating
		if(tam==0) {			
			if(act.equals("AVANZA")) return new Coord(0,0);
			else if(act.equals("IZQUIERDA")) return new Coord(0,0);
			else {
				Pattern pattern = Pattern.compile("\\d+");		      
		        Matcher matcher = pattern.matcher(act);
		        matcher.find();
		        String number1 = matcher.group();
		        matcher.find();
		        String number2 = matcher.group();
		        
				return new Coord(Integer.parseInt(number1),Integer.parseInt(number2));
			}
		}
		
		this.gramatica+="(";
		
		Coord[] coordsH= new Coord[tam];		
		for(int i=0;i<tam;i++) {
			String hijo="";
			int tamHijos=2;
			int c=cromosoma[cont++]%6;
			if(c==0) hijo="PROGN";
			else if(c==1) {
				hijo="SALTA";
				tamHijos=1;				
			}
			else if(c==2) hijo="SUMA";
			else if(c==3) {
				hijo="AVANZA";
				tamHijos=0;
				operaciones.add("A");
			}
			else if(c==4) {
				int tmpX=random.nextInt(filas);
				int tmpY=random.nextInt(columnas);
				hijo="CONSTANTE("+tmpX+","+tmpY+")";
				tamHijos=0;
			}
			else {
				hijo="IZQUIERDA";
				tamHijos=0;
				operaciones.add("I");
			}
			
			
			coordsH[i]=op(hijo,tamHijos);
			if(tam==2&&i==0) this.gramatica+=", ";
		}
		
		this.gramatica+=")";
		
		
		
		if(act.equals("PROGN")) return coordsH[1];
		else if(act.equals("SALTA")) {
			operaciones.add("S"+coordsH[0].x+coordsH[0].y);
			return coordsH[0];
		}
		else return new Coord((coordsH[0].x+coordsH[1].x)%filas, 
							  (coordsH[0].y+coordsH[1].y)%columnas);		
	}
	
	
		
	
	// TODO
	public String toString() {
		String aux="";
		for(String x: operaciones) {
			aux+=x+", ";
		}
		return gramatica;// +"\nOperaciones: " + aux;
	}

	
	
}






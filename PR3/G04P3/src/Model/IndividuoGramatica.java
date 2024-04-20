package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.Simbolos.Exp;
import Model.Simbolos.Terminales.Opc_Derecha;
import Model.Simbolos.Terminales.Opc_Mueve_Primer;
import Model.Simbolos.Terminales.Opc_Retrocede;


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
	
	public int numOPopcF;
	public int numOPopcT;
	public int OPopc;
	

	/* GRAMATICA SIN NO TERMINALES NI TERMINALES OPCIONALES
	<start> := progn(<op>, <op>) | salta(<op>) | suma(<op>, <op>)
	
	<op> := progn(<op>, <op>) | salta(<op>) | suma(<op>, <op>) 
		  	| avanza | constante(<num>, <num>) | izquierda
		  	
  	<num> := 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 // O PONER RANDOM Y ASI NO USAR CODONES
  	
  	NUEVO FUNCIONA PEOR:
  	<start> := <op>
	
	<op> := <no_terminal> | <terminal>
	
	<no_terminal> := progn(<op>, <op>) | salta(<op>) | suma(<op>, <op>) 
		  	
  	<terminal> := avanza | constante(<num>, <num>) | izquierda
	
	
	CON OPCIONALES:
	<start> := progn(<op>, <op>) | salta(<op>) | suma(<op>, <op>) | salta_a(<op>)
	
	<op> := progn(<op>, <op>) | salta(<op>) | suma(<op>, <op>) | salta_a(<op>)
		  	| avanza | constante(<num>, <num>) | izquierda | derecha | retrocede | mueve
		  	
  	<num> := 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 // O PONER RANDOM Y ASI NO USAR CODONES
	
	*/
	
	public IndividuoGramatica(int tam_cromosoma, int filas, int columnas, 
			boolean[] opcs, int numOPopcF, int numOPopcT) {		
		this.cromosoma=new int[tam_cromosoma];
		this.tam_cromosoma=tam_cromosoma;
		this.filas=filas;
		this.columnas=columnas;	
					
		this.fitness=0;		
		this.random=new Random();	
		this.nodos=0; // Bloating
		
		this.opcs=opcs;
		this.numOPopcF=numOPopcF;
		this.numOPopcT=numOPopcT;
		this.OPopc=(numOPopcF+numOPopcT>=1?1:0);
		
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
		
		this.opcs=individuo.opcs;
		this.numOPopcF=individuo.numOPopcF;
		this.numOPopcT=individuo.numOPopcT;
		this.OPopc=(numOPopcF+numOPopcT>=1?1:0);
		
		this.cromosoma=new int[tam_cromosoma];
		for(int i=0;i<this.tam_cromosoma; i++) {
			this.cromosoma[i]=individuo.cromosoma[i];
		}
	}
	
	public IndividuoGramatica(int[] cromosoma, int filas, int columnas,
			boolean[] opcs, int numOPopcF, int numOPopcT) {
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
		
		this.opcs=opcs;
		this.numOPopcF=numOPopcF;
		this.numOPopcT=numOPopcT;
		this.OPopc=(numOPopcF+numOPopcT>=1?1:0);
		
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
	
	/*private void start() {		
		nodos=0;
		cont=0;
		op();
	}
	
	private Coord op() {
		Coord ret;		
				
		int x=cromosoma[cont++]%2;
		if(x==0) ret=no_terminal();
		else ret=terminal();
		
		return ret;
	}
	
	private Coord no_terminal() {		
		Coord ret;

		this.nodos++;
		
		int x=cromosoma[cont++]%3;
		if(x==0) {
			this.gramatica+="PROGN(";			
			op();
			this.gramatica+=", ";
			ret = op();
			this.gramatica+=")";
			return ret;
		}
		if(x==1) {
			this.gramatica+="SALTA(";					
			ret=op();
			this.gramatica+=")";
			operaciones.add("S"+ret.x+ret.y);
		}
		else {
			this.gramatica+="SUMA";			
			op();
			this.gramatica+=", ";
			op();
			this.gramatica+=")";			
		}
		
		return new Coord(0, 0);
	}
	
	private Coord terminal() {
		this.nodos++;
		int x=cromosoma[cont++]%3;
		if(x==0) {
			this.gramatica+="AVANZA";
			operaciones.add("A");
		}
		if(x==1) {
			this.gramatica+="CONSTANTE";
			int tmpX=random.nextInt(filas);
			int tmpY=random.nextInt(columnas);
			return new Coord(tmpX, tmpY);
		}
		else {
			this.gramatica+="IZQUIERDA";
			operaciones.add("I");
		}        		
		
		
		return new Coord(0,0);
	}*/
	
	
	
	private void start() {
		String act="";
		int tamHijos=2;
				
		int x=cromosoma[0]%(3+(numOPopcF>=1?1:0));
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
			else if(act.equals("RETROCEDE")) return new Coord(0,0);
			else if(act.equals("DERECHA")) return new Coord(0,0);
			else if(act.equals("MUEVE")) return new Coord(0,0);
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
			int c=cromosoma[cont++]%(6+(numOPopcF+numOPopcT>0?1:0));
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
			else if(c==5){
				hijo="IZQUIERDA";
				tamHijos=0;
				operaciones.add("I");
			}
			else {
				c=cromosoma[cont-1]%(numOPopcF+numOPopcT);
				int cont=0;
				if(opcs[1]) {
					if(cont==c) {
						hijo="DERECHA";
						tamHijos=0;
						operaciones.add("D");
					}
					cont++;
				}
				if(opcs[2]) {
					if(cont==c) {
						hijo="MUEVE";
						tamHijos=0;
						operaciones.add("M");
					}
					cont++;
				}
				if(opcs[3]) {
					if(cont==c) {
						hijo="RETROCEDE";
						tamHijos=0;
						operaciones.add("R");
					}
					cont++;
				}
				if(opcs[4]) {
					if(cont==c) {
						hijo="SALTA_A";
						tamHijos=1;
					}
					cont++;
				}
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
		else if(act.equals("SALTA_A")) {
			operaciones.add("T"+coordsH[0].x+coordsH[0].y);
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






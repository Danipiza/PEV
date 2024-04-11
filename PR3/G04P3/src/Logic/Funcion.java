package Logic;

import Model.Individuo;

public class Funcion {

	private int filas;
	private int columnas;
	//							        N      O     S     E
	private int[][] direccionAvanza={{-1,0},{0,-1},{1,0},{0,1}};
	private int[][] direccionSalta ={{-1,1},{1,-1},{1,1},{1,1}};
	
	public Funcion(int filas, int columnas) {
		this.filas=filas;
		this.columnas=columnas;
	}
	
	// TODO
	public double fitness(Individuo ind) {
		double fitness=0.0;
		
		int[][] M=new int[filas][columnas];
		
		// Posicion inicial
		int x=4;
		int y=4;
		
		int dir=0;
		
		int cont=0;	
		int n=ind.operaciones.size();
		char[][] ops=new char[n][3];
		for(String tmp: ind.operaciones) {
			ops[cont++]=tmp.toCharArray();
		}
		cont=0;
		
		// Tick suma cuando se gira a la izquierda, salta o avanza
		int ticks=0;
		while(ticks<100) {
			// Gira a la izquierda
			if(ops[cont][0]=='I') {
				dir+=(dir+1)%4;
				//System.out.println("Izquierda");
			}
			else if(ops[cont][0]=='A') {
				//System.out.println("Avanza");
				x=(x+direccionAvanza[dir][0])%filas;
				y=(y+direccionAvanza[dir][1])%columnas;
				if(x<0) x=filas+x;
				if(y<0) y=columnas+y;
				if(M[x][y]==0) {
					fitness++;
					M[x][y]=1;
				}
			}
			else {
				//System.out.println("Salta");
				x=(x+direccionSalta[dir][0]*ops[cont][1]-'0')%filas;
				y=(y+direccionSalta[dir][1]*ops[cont][2]-'0')%columnas;
				if(x<0) x=filas+x;
				if(y<0) y=columnas+y;
				if(M[x][y]==0) {
					fitness++;
					M[x][y]=1;
				}
			}
			ticks++;
			cont=(cont+1)%n;
		}
		
		
		return fitness;
	}

	// AHORA ES MAXIMIZAR
	public double cmp(double a, double b) {
		if (a>b) return a;
		else return b;
	}

	public double cmpPeor(double a, double b) {
		if (a<b) return a;
		else return b;
	}

	
}

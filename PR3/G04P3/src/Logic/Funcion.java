package Logic;


import Model.Individuo;

public class Funcion {

	private int filas;
	private int columnas;
	private int ticks;
	//							        N      O     S     E
	private int[][] direccionAvanza={{-1,0},{0,-1},{1,0},{0,1}};
	private int[][] direccionSalta ={{-1,1},{-1,-1},{1,-1},{1,1}};
	// int[][] direccionSalta ={{-1,1},{-1,-1},{1,-1},{1,1}};
	
	public int[][] tablero;
	private int[][] M;
	
	private int tick;
	
	public Funcion(int filas, int columnas, int ticks, boolean obstaculos) {
		this.filas=filas;
		this.columnas=columnas;
		this.ticks=ticks;
		tablero=new int[filas][columnas];
		if(obstaculos) {
			for(int i=0;i<filas;i++) {
				for(int j=0;j<columnas;j++) {
					if(Math.random()<0.05) {
						tablero[i][j]=-1;
						//System.out.println("i: "+ i+", j: "+j);
					}
				}
			}
		}
	}
	
	// TODO
	public double fitness(Individuo ind) {
		double fitness=0.0;
		if(ind.operaciones.size()==0) return 0.0;
		
		//int[][] M=new int[filas][columnas];
		M=new int[filas][columnas];
		for(int i=0;i<filas;i++) {
			for(int j=0;j<columnas;j++) {
				if(tablero[i][j]==-1) M[i][j]=-1;//tablero[i][j]; // TODO
			}
		}
		
		// Posicion inicial
		int x=4;
		int y=4;
		
		int dir=0;
		
		int cont=0;	
		int n=ind.operaciones.size();
		char[] ops=new char[n];
		for(String tmp: ind.operaciones) {
			ops[cont++]=tmp.charAt(0);
		}
		cont=0;
		
		// Obstaculo
		int tmpX,tmpY;
		
		// Tick suma cuando se gira a la izquierda, salta o avanza		
		tick=0;
		while(tick<ticks) {
			// Gira a la izquierda
			if(ops[cont]=='I') {
				dir=(dir+1)%4;
			}			
			else if(ops[cont]=='A') {
				tmpX=(x+direccionAvanza[dir][0])%filas;
				tmpY=(y+direccionAvanza[dir][1])%columnas;				
				if(tmpX<0) tmpX=filas+tmpX;
				if(tmpY<0) tmpY=columnas+tmpY;
				if(M[tmpX][tmpY]!=-1) {
					x=tmpX; y=tmpY;
					if(M[x][y]==0) {
						fitness++;
						M[x][y]=1;
					}
				}
			}			
			else if(ops[cont]=='S'){
				
				tmpX=(x+direccionSalta[dir][0]*ind.operaciones.get(cont).charAt(1)-'0')%filas;
				tmpY=(y+direccionSalta[dir][1]*ind.operaciones.get(cont).charAt(2)-'0')%columnas;
				if(tmpX<0) tmpX=filas+tmpX;
				if(tmpY<0) tmpY=columnas+tmpY;
				if(M[tmpX][tmpY]!=-1) {
					x=tmpX; y=tmpY;
					if(M[x][y]==0) {
						fitness++;
						M[x][y]=1;
					}
				}
			}
			else if(ops[cont]=='D') {
				dir=(dir-1)%4;
				if(dir<0)dir=3;
			}			
			else if(ops[cont]=='M') {
				int[] mX= {-1,-1,-1,0,0,1,1,1};
				int[] mY= {-1,0,1,-1,1,-1,0,1};				
				for(int k =0;k<8;k++) {
					tmpX=(x+mX[k])%filas;
					tmpY=(y+mY[k])%columnas;	
					if(tmpX<0) tmpX=filas+tmpX;
					if(tmpY<0) tmpY=columnas+tmpY;
					if(M[tmpX][tmpY]==0) {
						x=tmpX;
						y=tmpY;
						fitness++;
						M[x][y]=1;
						break;
					}
				}				
				
			}
			else if(ops[cont]=='T'){
				tmpX=(ind.operaciones.get(cont).charAt(1)-'0')%filas;
				tmpY=(ind.operaciones.get(cont).charAt(2)-'0')%columnas;
				if(tmpX<0) tmpX=filas+tmpX;
				if(tmpY<0) tmpY=columnas+tmpY;
				if(M[tmpX][tmpY]!=-1) {
					x=tmpX; y=tmpY;
					if(M[x][y]==0) {
						fitness++;
						M[x][y]=1;
					}
				}
			}
			else if(ops[cont]=='R') {
				
				tmpX=(x-direccionAvanza[dir][0])%filas;
				tmpY=(y-direccionAvanza[dir][1])%columnas;				
				if(tmpX<0) tmpX=filas+tmpX;
				if(tmpY<0) tmpY=columnas+tmpY;
				if(M[tmpX][tmpY]!=-1) {
					x=tmpX; y=tmpY;
					if(M[x][y]==0) {
						fitness++;
						M[x][y]=1;
					}
				}				
			}
			/*else if(ops[cont]=='B') {
				fitness+=repeat(ind.operaciones.get(cont),x,y);				
			}*/
			tick++;
			cont=(cont+1)%n;
		}
		
		
		return fitness;
	}
	
	/*private int repeat(String op, int x, int y) {
		int ret=0;
		
		String[] tmp = op.split(";");
		String[] operationsR=new String[tmp.length-1];
		for(int i=1;i<tmp.length;i++) {
			operationsR[i-1]=tmp[i];
		}
		int m=operationsR.length;		
		if(m==1) return 0;
		if(m==0) {
			return 0;
		}
		int repeat = operationsR[m-1].charAt(1)-'0';
		
		int dir=0, cont=0;
		int tmpX,tmpY;
		while(tick<ticks&&repeat!=0) {
			if(operationsR[cont].charAt(0)=='I') {
				dir=(dir+1)%4;
			}			
			else if(operationsR[cont].charAt(0)=='A') {
				tmpX=(x+direccionAvanza[dir][0])%filas;
				tmpY=(y+direccionAvanza[dir][1])%columnas;				
				if(tmpX<0) tmpX=filas+tmpX;
				if(tmpY<0) tmpY=columnas+tmpY;
				if(M[tmpX][tmpY]!=-1) {
					x=tmpX; y=tmpY;
					if(M[x][y]==0) {
						ret++;
						M[x][y]=1;
					}
				}
			}			
			else if(operationsR[cont].charAt(0)=='S'){
				
				tmpX=(x+direccionSalta[dir][0]*operationsR[cont].charAt(1)-'0')%filas;
				tmpY=(y+direccionSalta[dir][1]*operationsR[cont].charAt(2)-'0')%columnas;
				if(tmpX<0) tmpX=filas+tmpX;
				if(tmpY<0) tmpY=columnas+tmpY;
				if(M[tmpX][tmpY]!=-1) {
					x=tmpX; y=tmpY;
					if(M[x][y]==0) {
						ret++;
						M[x][y]=1;
					}
				}
			}
			else if(operationsR[cont].charAt(0)=='D') {
				dir=(dir-1)%4;
				if(dir<0)dir=3;
			}			
			else if(operationsR[cont].charAt(0)=='M') {
				int[] mX= {-1,-1,-1,0,0,1,1,1};
				int[] mY= {-1,0,1,-1,1,-1,0,1};				
				for(int k =0;k<8;k++) {
					tmpX=(x+mX[k])%filas;
					tmpY=(y+mY[k])%columnas;	
					if(tmpX<0) tmpX=filas+tmpX;
					if(tmpY<0) tmpY=columnas+tmpY;
					if(M[tmpX][tmpY]==0) {
						x=tmpX;
						y=tmpY;
						ret++;
						M[x][y]=1;
						break;
					}
				}				
				
			}
			else if(operationsR[cont].charAt(0)=='T'){
				tmpX=(operationsR[cont].charAt(1)-'0')%filas;
				tmpY=(operationsR[cont].charAt(2)-'0')%columnas;
				if(tmpX<0) tmpX=filas+tmpX;
				if(tmpY<0) tmpY=columnas+tmpY;
				if(M[tmpX][tmpY]!=-1) {
					x=tmpX; y=tmpY;
					if(M[x][y]==0) {
						ret++;
						M[x][y]=1;
					}
				}
			}
			else if(operationsR[cont].charAt(0)=='R') {
				
				tmpX=(x-direccionAvanza[dir][0])%filas;
				tmpY=(y-direccionAvanza[dir][1])%columnas;				
				if(tmpX<0) tmpX=filas+tmpX;
				if(tmpY<0) tmpY=columnas+tmpY;
				if(M[tmpX][tmpY]!=-1) {
					x=tmpX; y=tmpY;
					if(M[x][y]==0) {
						ret++;
						M[x][y]=1;
					}
				}				
			}
			else if(operationsR[cont].charAt(0)=='B') {
				ret+=repeat(operationsR[cont],x,y);				
			}
			tick++;
			cont++;
			if(cont==m-1) {
				repeat--;
				cont=0;
			}
		}
		
		
		return ret;
	}*/
	
	public int[][] matrix(Individuo ind) {
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
		
		// Obstaculo
		int tmpX,tmpY;
		
		// Tick suma cuando se gira a la izquierda, salta o avanza		
		int ticks=0;
		while(ticks<this.ticks) {
			// Gira a la izquierda
			if(ops[cont][0]=='I') {
				dir=(dir+1)%4;
			}
			else if(ops[cont][0]=='A') {
				tmpX=(x+direccionAvanza[dir][0])%filas;
				tmpY=(y+direccionAvanza[dir][1])%columnas;				
				if(tmpX<0) tmpX=filas+tmpX;
				if(tmpY<0) tmpY=columnas+tmpY;
				if(M[tmpX][tmpY]!=-1) {
					x=tmpX; y=tmpY;
					if(M[x][y]==0) {
						fitness++;
						M[x][y]=1;
					}
				}
			}
			else if(ops[cont][0]=='S'){
				tmpX=(x+direccionSalta[dir][0]*ops[cont][1]-'0')%filas;
				tmpY=(y+direccionSalta[dir][1]*ops[cont][2]-'0')%columnas;
				if(tmpX<0) tmpX=filas+tmpX;
				if(tmpY<0) tmpY=columnas+tmpY;
				if(M[tmpX][tmpY]!=-1) {
					x=tmpX; y=tmpY;
					if(M[x][y]==0) {
						fitness++;
						M[x][y]=1;
					}
				}
			}
			else if(ops[cont][0]=='D') {
				dir=(dir-1)%4;
				if(dir<0)dir=3;
			}
			else if(ops[cont][0]=='R') {
				tmpX=(x-direccionAvanza[dir][0])%filas;
				tmpY=(y-direccionAvanza[dir][1])%columnas;				
				if(tmpX<0) tmpX=filas+tmpX;
				if(tmpY<0) tmpY=columnas+tmpY;
				if(M[tmpX][tmpY]!=-1) {
					x=tmpX; y=tmpY;
					if(M[x][y]==0) {
						fitness++;
						M[x][y]=1;
					}
				}
			}
			else if(ops[cont][0]=='M') {
				int[] mX= {-1,-1,-1,0,0,1,1,1};
				int[] mY= {-1,0,1,-1,1,-1,0,1};				
				for(int k =0;k<8;k++) {
					tmpX=(x+mX[k])%filas;
					tmpY=(y+mY[k])%columnas;	
					if(tmpX<0) tmpX=filas+tmpX;
					if(tmpY<0) tmpY=columnas+tmpY;
					if(M[tmpX][tmpY]==0) {
						x=tmpX;
						y=tmpY;
						fitness++;
						M[x][y]=1;
						break;
					}
				}				
				
			}
			else if(ops[cont][0]=='T'){
				tmpX=(ops[cont][1]-'0')%filas;
				tmpY=(ops[cont][2]-'0')%columnas;
				if(tmpX<0) tmpX=filas+tmpX;
				if(tmpY<0) tmpY=columnas+tmpY;
				if(M[tmpX][tmpY]!=-1) {
					x=tmpX; y=tmpY;
					if(M[x][y]==0) {
						fitness++;
						M[x][y]=1;
					}
				}
			}
			ticks++;
			cont=(cont+1)%n;
		}
		
		
		return M;
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

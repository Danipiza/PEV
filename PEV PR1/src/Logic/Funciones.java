package Logic;

public class Funciones {
	
	private int funcion_idx;
	private double izq, der; 	// TODO CAMBIAR HAY INTERVALOS PARA DOS VALORES NO SOLO 1
								//	EJEMPLO F2
	
	public Funciones(int funcion_idx) {
		this.funcion_idx=funcion_idx;
	}
	
	
	public double funcion(double x1, double x2) {
		double ret=0;
		switch (funcion_idx) {
		case 0:
			izq=-10; der=10;
			ret=Math.pow(x1, 2)+2*Math.pow(x2, 2);
			break;
		case 1:
			izq=-10; der=10;
			// f(x1,x2) = sin(x2) exp(1 − cos(x1)) 2 + cos(x1) exp(1 − sin(x2))2 + (x1−x2)2 
			//ret=Math.pow(x1, 2)+2*Math.pow(x2, 2);
			break;
		case 2:
			
			//ret=Math.pow(x1, 2)+2*Math.pow(x2, 2);			
			break;
		case 3:
			
			//ret=Math.pow(x1, 2)+2*Math.pow(x2, 2);			
			break;
		case 4:
			
			//ret=Math.pow(x1, 2)+2*Math.pow(x2, 2);			
			break;

		default:
			break;
		}
		
		return ret;
	}
	
	public double getIzq() { return izq; }
	public double getDer() { return der; }
	

}

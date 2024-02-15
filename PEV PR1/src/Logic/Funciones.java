package Logic;

import Utils.Pair;

public class Funciones {
	
	private int funcion_idx;
	
	public Funciones(int funcion_idx) {
		this.funcion_idx=funcion_idx;
	}
	
	public int optimizacion() {
		return (funcion_idx>=1?0:1);
	}
	
	public Pair<Double,Double> intervalosGrafico(){
		Pair<Double,Double> ret=new Pair(0,0);
		
		switch (funcion_idx) {
		case 0:
			ret.setKey(0.0);
			ret.setValue(300.0);
			break;
		case 1:
			ret.setKey(-110.0);
			ret.setValue(100.0);
			break;
		case 2:
			ret.setKey(-20.0);
			ret.setValue(0.0);
			break;
		case 3:
			/*ret.setKey(0.0);
			ret.setValue(310.0);*/
		case 4:
			/*ret.setKey(0.0);
			ret.setValue(310.0);*/
			break;

		default:
			break;
		}
		
		
		return ret;
	}
	
	public double[] maximos(int d) {
		int n = (funcion_idx<4?2:d);
		double ret[]=new double[n];
		
		switch (funcion_idx) {
		case 0:
			ret[0]=ret[1]=10;
			break;
		case 1:
			ret[0]=ret[1]=0;	
			break;
		case 2:
			ret[0]=ret[1]=10;
			break;
		case 3:
			for(int i=0;i<n;i++) {
				ret[i]=Math.PI;
			}			
			break;
		case 4:
			
			break;

		default:
			break;
		}
		
		
		return ret;
	}
	
	public double[] minimos(int d) {
		int n = (funcion_idx<4?2:d);
		double ret[]=new double[n];
		
		switch (funcion_idx) {
		case 0:
			ret[0]=ret[1]=-10;
			break;
		case 1:
			ret[0]=-10;
			ret[1]=-6.5;	
			break;
		case 2:
			ret[0]=ret[1]=-10;
			break;
		case 3:
			for(int i=0;i<n;i++) {
				ret[i]=0;
			}			
			break;
		case 4:
			
			break;

		default:
			break;
		}
		
		
		return ret;
	}
	
	public double f1(double[] nums) {
		return (Math.pow(nums[0], 2)+2*Math.pow(nums[1], 2));
	}	
	public double f2(double[] nums) {
		//f(x1,x2) = sin(x2) exp(1 − cos(x1)) 2 + cos(x1) exp(1 − sin(x2))2 + (x1−x2)2 
		return 	Math.sin(nums[1])*Math.exp(1-Math.cos(nums[0]))*2 + 
				Math.cos(nums[0])*Math.exp(1-Math.sin(nums[1]))*2 + 
				(nums[0]-nums[1])*2;
	}	
	public double f3(double[] nums) {
		return 0;
	}	
	public double f4(double[] nums) {
		return 0;
	}	
	public double f5(double[] nums) {
		return 0;
	}
	
		

}

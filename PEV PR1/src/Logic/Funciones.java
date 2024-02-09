package Logic;

public class Funciones {
	
	private int funcion_idx;
	
	public Funciones(int funcion_idx) {
		this.funcion_idx=funcion_idx;
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
		return 0;
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

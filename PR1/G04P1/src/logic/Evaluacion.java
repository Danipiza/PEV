/**
 * 
 */
package logic;


import utils.Pair;

/**
 * @author DavidSG & DannyP39
 
 * ENG: Class for Function
 * ESP: Clase para Evalua
 */

public abstract class Evaluacion {
	
	// ENG: They are initialized for each problem.
	// ESP: Se inicializan para cada problema.
	public Pair<Double, Double> intervalosGrafico;
	
	// ENG: Values for each problem.
	// ESP: Valores para cada problema.
	public double[] maximos;
	public double[] minimos;
	
	// ENG: Maximization (false) or minimization (true) problem.
	// ESP: Problema de maximizacion (false) o minimizacion (true).
	public Boolean opt;

	// ENG: Evaluation funciont. Different for each problem.
	// ESP: Funcion de evaluacion. Diferente para cada problema.
	public abstract double fitness(double[] nums);
	
	// ENG: Functions to compare individuals.
	//		Different in maximization and minimization problems.
	// ESP: Funciones para comparar individuos. 
	//		Diferentes en problemas de maximizacion y minimizacion.
	public abstract double cmp(double a, double b);
	public abstract boolean cmpBool(double a, double b);

	public abstract double cmpPeor(double a, double b);
	public abstract boolean cmpPeorBool(double a, double b);
}

class Evaluacion1 extends Evaluacion {
	public Evaluacion1() {
		intervalosGrafico=new Pair<>(0.0, 300.0); 
		
		maximos=new double[] { 10, 10 }; 
		minimos=new double[] { -10, -10 }; 
		
		opt = true;
	}

	@Override
	public double fitness(double[] nums) {
		return (Math.pow(nums[0], 2)+2*Math.pow(nums[1], 2));
	}

	@Override
	public double cmp(double a, double b) { return (a>b?a:b); }
	@Override
	public boolean cmpBool(double a, double b) { return (a>b?true:false); }
	
	@Override
	public double cmpPeor(double a, double b) { return (a<b?a:b); }
	@Override
	public boolean cmpPeorBool(double a, double b) { return (a<b?true:false); }
}

class Evaluacion2 extends Evaluacion {
	public Evaluacion2() {
		intervalosGrafico=new Pair<>(-110.0, 50.0); 
		
		maximos=new double[] { 0, 0 }; 
		minimos=new double[] { -10, -6.5 }; 
		
		opt=false;
	}

	@Override
	public double fitness(double[] nums) {
		return Math.sin(nums[1])*Math.pow(Math.exp(1-Math.cos(nums[0])),2) +
				Math.cos(nums[0])*Math.pow(Math.exp(1-Math.sin(nums[1])),2) +
				Math.pow((nums[0]-nums[1]), 2);
	}

	@Override
	public double cmp(double a, double b) { return (a<b?a:b); }
	@Override
	public boolean cmpBool(double a, double b) { return (a<b?true:false); }
	
	@Override
	public double cmpPeor(double a, double b) { return (a>b?a:b); }
	@Override
	public boolean cmpPeorBool(double a, double b) { return (a>b?true:false); }
}

class Evaluacion3 extends Evaluacion {
	public Evaluacion3() {
		intervalosGrafico=new Pair<>(-20.0, 0.0); 

		maximos=new double[] { 10, 10 }; 
		minimos=new double[] { -10, -10 }; 

		opt=false;
	}

	@Override
	public double fitness(double[] nums) {
		double exp=Math.abs(1-(Math.sqrt(Math.pow(nums[0], 2)+Math.pow(nums[1], 2)))/Math.PI);
		double ret=Math.sin(nums[0]) * Math.cos(nums[1]) * Math.exp(exp);
		return -Math.abs(ret);

	}

	@Override
	public double cmp(double a, double b) { return (a<b?a:b); }
	@Override
	public boolean cmpBool(double a, double b) { return (a<b?true:false); }
	
	@Override
	public double cmpPeor(double a, double b) { return (a>b?a:b); }
	@Override
	public boolean cmpPeorBool(double a, double b) { return (a>b?true:false); }
}

class Evaluacion4 extends Evaluacion {

	// ENG: Number of dimensions.
	// ESP: Numero de domensiones.
	int d; 

	public Evaluacion4(int d) {
		this.d=d;
		
		intervalosGrafico = new Pair<>(-15.0, 5.0); 
		maximos=new double[d]; 
		minimos=new double[d]; 
		for (int i=0;i<d;i++) {
			maximos[i]=Math.PI;
			minimos[i]=0;
		}
		
		opt=false;
	}

	@Override
	public double fitness(double[] nums) {
		double ret=0;
		for (int i=1;i<=d;i++) {
			double sin1=Math.sin(nums[i-1]);
			double radians=(i * Math.pow(nums[i-1],2))/Math.PI;
			double comp=Math.sin(radians);
			ret+=sin1*Math.pow(comp, 20);
		}
		return ret*-1;
	}
	
	@Override
	public double cmp(double a, double b) { return (a<b?a:b); }
	@Override
	public boolean cmpBool(double a, double b) { return (a<b?true:false); }
	
	@Override
	public double cmpPeor(double a, double b) { return (a>b?a:b); }
	@Override
	public boolean cmpPeorBool(double a, double b) { return (a>b?true:false); }
}

class Evaluacion5 extends Evaluacion {
	int d;

	public Evaluacion5(int d) {
		this.d=d;
		
		intervalosGrafico=new Pair<>(-10.0, 10.0); 
		maximos=new double[] { 10, 10 }; 
		minimos=new double[] { -10, -10 }; 
		
		opt=false;
	}

	@Override
	public double fitness(double[] nums) {
		double ret=0;
		for (int i=1;i<=d;i++) {
			double sin1=Math.sin(nums[i-1]);
			double radians=i*Math.pow(nums[i-1],2)/Math.PI;
			double comp=Math.sin(radians);
			ret+=sin1*Math.pow(comp, 20);
		}
		return ret * -1;
	}
	

	@Override
	public double cmp(double a, double b) { return (a<b?a:b); }
	@Override
	public boolean cmpBool(double a, double b) { return (a<b?true:false); }
	
	@Override
	public double cmpPeor(double a, double b) { return (a>b?a:b); }
	@Override
	public boolean cmpPeorBool(double a, double b) { return (a>b?true:false); }	
}

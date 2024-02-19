package Logic;

import Utils.Pair;

public abstract class Funcion {
	public Pair<Double, Double> intervalosGrafico;
	public double[] maximos;
	public double[] minimos;
	public Boolean opt;

	// Add an access modifier to the abstract method
	public abstract double fitness(double[] nums);

	public abstract double cmp(double a, double b);
}

class Funcion1 extends Funcion {
	public Funcion1() {
		intervalosGrafico = new Pair<>(0.0, 300.0); // Inicializar intervalosGrafico
		maximos = new double[] { 10, 10 }; // Inicializar array maximos
		minimos = new double[] { -10, -10 }; // Inicializar array minimos
		opt = true;
	}

	@Override
	public double fitness(double[] nums) {
		return (Math.pow(nums[0], 2) + 2 * Math.pow(nums[1], 2));
	}

	@Override
	public double cmp(double a, double b) {
		if (a > b)
			return a;
		else
			return b;
	}
}

class Funcion2 extends Funcion {
	public Funcion2() {
		intervalosGrafico = new Pair<>(-110.0, 50.0); // Inicializar intervalosGrafico
		maximos = new double[] { 0, 0 }; // Inicializar array maximos 
		minimos = new double[] { -10, -6.5 }; // Inicializar array minimos 
		opt = false;
	}

	@Override
	public double fitness(double[] nums) {
		return  Math.sin(nums[1]) * Math.pow(Math.exp(1 - Math.cos(nums[0])),2) +
				Math.cos(nums[0]) * Math.pow(Math.exp(1 - Math.sin(nums[1])),2) +
				Math.pow((nums[0] - nums[1]),2) ;
	}

	@Override
	public double cmp(double a, double b) {
		if (a < b)
			return a;
		else
			return b;
	}
}

class Funcion3 extends Funcion {
	public Funcion3() {
		intervalosGrafico = new Pair<>(-20.0, 0.0); // Inicializar intervalosGrafico
		maximos = new double[] { 10, 10 }; // Inicializar array maximos
		minimos = new double[] { -10, -10 }; // Inicializar array minimos
		opt = false;
	}

	@Override
	public double fitness(double[] nums) {
		double exp = Math.abs(1 - (Math.sqrt(Math.pow(nums[0], 2) + Math.pow(nums[1], 2))) / Math.PI);
		double ret = Math.sin(nums[0]) * Math.cos(nums[1]) * Math.exp(exp);
		return -Math.abs(ret);
		 
	}

	@Override
	public double cmp(double a, double b) {
		if (a < b)
			return a;
		else
			return b;
	}
}

class Funcion4 extends Funcion {
	int d;
	public Funcion4(int _d) {
		d = _d;
		intervalosGrafico = new Pair<>(-15.0, 5.0); // Inicializar intervalosGrafico
		maximos = new double[d]; // Inicializar array maximos 
		minimos = new double[d]; // Inicializar array minimos
		for (int i = 0; i < d; i++) {
			maximos[i] = Math.PI; minimos[i] = 0;
		}
		opt = false;
	}

	@Override
	public double fitness(double[] nums) {
		double ret = 0;
		for (int i = 1; i <= d; i++) {
			double sin1 = Math.sin(nums[i-1]);
			double radians = i*Math.pow(nums[i-1],2)/Math.PI;
			double comp = Math.sin(radians);
			ret += sin1*Math.pow(comp,20);
		}
		return ret*-1;
	}

	@Override
	public double cmp(double a, double b) {
		if (a < b)
			return a;
		else
			return b;
	}
}

class Funcion5 extends Funcion {
	public Funcion5() {
		intervalosGrafico = new Pair<>(-10.0, 10.0); // Inicializar intervalosGrafico
		maximos = new double[] { 10, 10 }; // Inicializar array maximos 
		minimos = new double[] { -10, -10 }; // Inicializar array minimos 
		opt = false;
	}

	@Override
	public double fitness(double[] nums) {
		return (Math.pow(nums[0], 2) + 2 * Math.pow(nums[1], 2));
	}

	@Override
	public double cmp(double a, double b) {
		if (a < b)
			return a;
		else
			return b;
	}
}

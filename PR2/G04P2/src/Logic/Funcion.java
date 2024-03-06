package Logic;

import Utils.Pair;

public class Funcion {
	public Pair<Double, Double> intervalosGrafico;
	public double[] maximos;
	public double[] minimos;

	// Add an access modifier to the abstract method
	public double fitness(double[] nums) {
		return 0;
	}

	public double cmp(double a, double b) {
		if (a < b) return a;
		else return b;
	}

	public double cmpPeor(double a, double b) {
		if (a > b) return a;
		else return b;
	}
}


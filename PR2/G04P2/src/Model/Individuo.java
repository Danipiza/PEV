package Model;


public class Individuo {
	
	public Gen gen;
	public double fitness;	
	
	public Individuo(int aviones) {
		gen = new Gen(aviones);
		fitness = 0;
	}
	
	public Individuo(Individuo individuo) {
		gen = new Gen(individuo.gen);
	}
	
	public Individuo(int[] cromosoma) {
		gen = new Gen(cromosoma);
	}
	
	public void printIndividuo() {
		for (int a : gen.v) {
			System.out.print(a + " ");
		}
		System.out.println("Fitness: " + fitness); 
	}
}






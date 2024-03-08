package Utils;

import Model.Individuo;

public class Node {
	
    private double value;
    private Individuo id;

    public Node(double value, Individuo id) {
        this.value = value;
        this.id = id;
    }

    public double getValue() { return value; }

    public Individuo getId() { return id; }
	
}

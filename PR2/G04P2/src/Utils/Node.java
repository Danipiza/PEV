package Utils;

import Model.IndividuoReal;

public class Node {
	
    private double value;
    private IndividuoReal id;

    public Node(double value, IndividuoReal id) {
        this.value = value;
        this.id = id;
    }

    public double getValue() { return value; }

    public IndividuoReal getId() { return id; }
	
}

/**
 * 
 */
package utils;

import model.Individuo;

/**
 * @author DavidSG & DannyP39
 
 * ENG: Class for sorting the elitism priority queue.
 * ESP: Clase para ordenar la cola de prioridad de elitismo.
 */
public class Node {
	
	// ENG: Variable with which it is ordered.
	// ESP: Variable con la cual se ordena.
    private double valor;
    
    // ENG: Pointer with the Individual.
    // ESP: Puntero con el Individuo.
    private Individuo individuo;
    
    /**
     * 
     * @param value
     * @param id
    
     * ENG: Class constructor.
     * ESP: Constructor de la clase.
     */
    public Node(double valor, Individuo individuo) {
        this.valor=valor;
        this.individuo=individuo;
    }

    /**
     * 
     * @return
    
     * ENG: Method for
     * ESP: Funcion para
     */
    public double get_valor() { return valor; }
    public Individuo get_individuo() { return individuo; }
	
}

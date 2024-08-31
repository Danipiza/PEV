/**
 * 
 */
package utils;

/**
 * @author DavidSG & DannyP39
 
 * ENG: Class for representing a pair of any type.
 * ESP: Clase para representar una pareja de cualquier tipo. 
 */
public class Pair<F, S> {
	
	// ENG: 
	// ESP: 
    private F first;
    private S second;

    public Pair(F first, S second) {
        this.first=first;
        this.second=second;
    }
    
    /**
     * 
     * @return
    
     * ENG: Method for getting the private variables.
     * ESP: Funcion para conseguir las variables privadas.
     */
    public F get_first() { return first; }
    public S get_second() { return second; }
    
    /**
     * 
     * @param val
    
     * ENG: Method for setting the values of the variables.
     * ESP: Funcion para cambiar el valor de las variables.
     */
    public void set_first(F val)  {  this.first=val; }   
    public void set_second(S val) { this.second=val; }
    
    /**
     * 
    
     * ENG: Method for printing the class.
     * ESP: Funcion para imprimir la clase.
     */
    @Override
    public String toString() { return "("+first+", "+second+")"; }
}
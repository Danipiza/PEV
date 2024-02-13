package Logic;

//import java.lang.Math.random;

public class Mutacion {	
	
	// TODO 
	public Mutacion() {
		
	}
	
	public void mut_basica(Individuo[] poblacion, double p) {
		int tam_poblacion=poblacion.length;
		//Individuo[] ret = new Individuo[tam_poblacion];
		
		for (int i=0;i<tam_poblacion;i++) {
			
			for(int c=0;c<poblacion[i].cromosoma.length;c++){
				for(int j=0;j<poblacion[i].cromosoma[c].v.length;j++) {
					if(Math.random()>p) {
						poblacion[i].cromosoma[c].v[j]=(poblacion[i].cromosoma[c].v[j]+1)%2;
					}
				}
			}
		}
	}
	
}

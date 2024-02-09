package Logic;

//import java.lang.Math.random;

public class Mutacion {	
	
	public void muta_poblacion(int i, double p, int tam_poblacion, 
			Individuo[] poblacion, int l) {		
		switch (i) {
		case 0: // BASICA
			mut_basica(tam_poblacion,p,poblacion,l);
			break;
		default:
			break;
		}
		
	}
	
	private void mut_basica(int tam_poblacion, double p,
			Individuo[] poblacion, int l) {
		
		Individuo[] ret = new Individuo[tam_poblacion];
		for (int i=0;i<tam_poblacion;i++) {
			
			for(int c=0;c<poblacion[i].cromosoma.length;c++){
				for(int j=0;j<poblacion[i].cromosoma[c].v.length;j++) {
					if(Math.random()>p) {
						ret[i].cromosoma[c].v[j]=(poblacion[i].cromosoma[c].v[j]+1)%2;
					}
				}
			}
		}
	}
	
}

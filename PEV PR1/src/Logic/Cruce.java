package Logic;

import java.util.ArrayList;
import java.util.List;

public class Cruce {
	
	
	public List<Individuo> cruce_poblacion(int i, int n) {
		List<Individuo> ret = null;
		switch (i) {
		case 0: // MONO_PUNTO
			//ret=cruce_monopunto(n);
			break;
		case 1: // UNIFORME
			ret=cruce_uniforme(n);
			break;
		default:
			break;
		}
		return ret;
	}
	
	private List<Individuo> cruce_monopunto(List<Individuo> individuos, int n) {
		List<Individuo> ret = new ArrayList<Individuo>();
		/*for (int i = 0; i < n/2;i++) {
			if(Math.random()>p) {
				for(int c=0;c<cromosomas;c++){
					//Individuo = individuos[2*i].cromosoma[0:rand] + individuos[2*i+1].cromosoma[rand:]
				}
			}
		}*/
		return ret;
	}
	
	private List<Individuo> cruce_uniforme(int n) {
		List<Individuo> ret = new ArrayList<Individuo>();
		for (int i = 0; i < n/2;i++) {
			
		}
		return ret;
	}
}

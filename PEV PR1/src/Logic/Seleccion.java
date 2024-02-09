package Logic;

public class Seleccion {
	
	int tam_poblacion;
	
	public Seleccion(int tam_poblacion) {
		this.tam_poblacion=tam_poblacion;
	}
	
	
	private int busquedaBinaria(double x, double[] prob_acumulada) {
		int ret=0;
		int i=0, j=prob_acumulada.length-1;
		int m=0;		
		while (i <= j) {
            m=i+(j-i)/2;

            if (prob_acumulada[m]>=x) {
                ret = m;
                j = m - 1;
            } 
            else i=m+1;
        }
		
		return ret;
	}
	public Individuo[] ruleta(Individuo[] poblacion, double[] prob_acumulada) {
		Individuo[] ret = new Individuo[tam_poblacion];
		
		double rand;
		for(int i=0;i<tam_poblacion;i++) {
			rand=Math.random();
			ret[i]=poblacion[busquedaBinaria(rand, prob_acumulada)];
		}	
		
		// TODO QUITAR
		for(Individuo ind:ret) {
			for(double f: ind.fenotipo) {
				System.out.print(f + " ");				
			}
			System.out.println();								
		}
		
		
		return ret;
	}	
	public Individuo[] torneoDeterministico(Individuo[] poblacion, double[] prob_acumulada) {
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		
		
		return ret;
	}
	public Individuo[] torneoProbabilistico(Individuo[] poblacion, double[] prob_acumulada) {
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		
		
		return ret;
	}
	public Individuo[] estocasticoUniversal(Individuo[] poblacion, double[] prob_acumulada) {
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		
		
		return ret;
	}
	public Individuo[] truncamiento(Individuo[] poblacion, double[] prob_acumulada) {
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		
		
		return ret;
	}
	public Individuo[] restos(Individuo[] poblacion, double[] prob_acumulada) {
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		
		
		return ret;
	}
	

	
}

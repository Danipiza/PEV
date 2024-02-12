package Logic;


public class Cruce {
	
	private double p;
	
	public Cruce(double p) { 
		this.p=p;
	}
	
	public Individuo[] cruce_monopunto(Individuo[] individuos) {
		int n=individuos.length;
		if(n%2==1) n--; // descarta al ultimo si es impar
		Individuo[] ret = new Individuo[n];
		
		int[] long_cromosomas=new int[individuos[0].cromosoma.length];
		int corte_maximo=-1, cont=0;		
		for(Cromosoma c: individuos[0].cromosoma) {
			corte_maximo+=c.v.length;
			long_cromosomas[cont++]=c.v.length;
		}
		//int l=corte_maximo+1;
		int i=0, j=0, k=0;
		Individuo ind1, ind2;
		int corte, tmp;
		while(i<n) {			
			ind1=individuos[i];
			ind2=individuos[i+1];
			//System.out.print("(ANTES)   Ind1: "); ind1.printIndividuo();
			//System.out.print("(ANTES)   Ind2: "); ind2.printIndividuo();
			if(Math.random()>p) {				
				corte=(int) (Math.random()*(corte_maximo))+1; // [1,corte_maximo]
				cont=0; j=0;
				//System.out.println("Corte en: " + corte + " ----------------------------------" );				
				for(k=0;k<corte;k++) {
					tmp=ind1.cromosoma[cont].v[j];
					ind1.cromosoma[cont].v[j]=ind2.cromosoma[cont].v[j];
					ind2.cromosoma[cont].v[j]=tmp;
					j++;
					if(j==long_cromosomas[cont]) {
						cont++;
						j=0;
					}					
				}
			}
			ret[i++]=ind1; ret[i++]=ind2;
			//System.out.print("(DESPUES) Ind1: "); ind1.printIndividuo();
			//System.out.print("(DESPUES) Ind2: "); ind2.printIndividuo();
			//System.out.println();
		}
		return ret;
	}
	
	public Individuo[] cruce_uniforme(Individuo[] individuos) {
		int n = individuos.length;
		if(n%2==1) n--; // descarta al ultimo si es impar
		Individuo[] ret = new Individuo[n];
		
		int[] long_cromosomas=new int[individuos[0].cromosoma.length];
		int cont=0, l=0;		
		for(Cromosoma c: individuos[0].cromosoma) {			
			l+=c.v.length;
			long_cromosomas[cont++]=c.v.length;
		}
		
		int i=0, j=0, k=0;
		Individuo ind1, ind2;
		int tmp;
		while(i<n) {			
			ind1=individuos[i];
			ind2=individuos[i+1];
			//System.out.print("(ANTES)   Ind1: "); ind1.printIndividuo();
			//System.out.print("(ANTES)   Ind2: "); ind2.printIndividuo();
			if(Math.random()>p) {	
				//System.out.println("Cruce");
				cont=0; j=0;								
				for(k=0;k<l;k++) {
					if(Math.random()<0.5) { 
						//System.out.print(k + " ");
						tmp=ind1.cromosoma[cont].v[j];
						ind1.cromosoma[cont].v[j]=ind2.cromosoma[cont].v[j];
						ind2.cromosoma[cont].v[j]=tmp;
					}
					j++;
					if(j==long_cromosomas[cont]) {
						cont++;
						j=0;
					}					
				}
				//System.out.println();
			}
			ret[i++]=ind1; ret[i++]=ind2;
			//System.out.print("(DESPUES) Ind1: "); ind1.printIndividuo();
			//System.out.print("(DESPUES) Ind2: "); ind2.printIndividuo();
			//System.out.println();
		}
		return ret;
	}
}

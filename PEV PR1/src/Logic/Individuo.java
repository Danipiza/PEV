package Logic;

public class Individuo {
	public Cromosoma[] cromosoma;
	
	public double[] fenotipo;
	
	public Individuo(int num, int[] tam_genes, double xMax[], double[] xMin){
		cromosoma=new Cromosoma[num];
		for(int i=0;i<num;i++) {
			cromosoma[i]=new Cromosoma(tam_genes[i]);
		}
		calcular_fenotipo(xMax,xMin);
	}	
	
	private int bin2dec(Cromosoma cromosoma) {
		int ret=0;
		int cont=1;
		for(int i=cromosoma.v.length;i>=0;i--) {
			if(cromosoma.v[i]==1)ret+=cont;
			cont*=2;
		}
		return ret;
	}
	
	
	private void calcular_fenotipo(double[] xMax, double[] xMin) {			
		for(int i=0;i<cromosoma.length;i++) {
			fenotipo[i]=calcular_fenotipoCromosoma(cromosoma[i], xMax[i], xMin[i]);
		}		
	}		
	
	
	private double calcular_fenotipoCromosoma(Cromosoma ind, double xMax, double xMin) {		
		return xMin + bin2dec(ind) *((xMax-xMin)/(Math.pow(2, ind.v.length)-1));
	}
	
	
	
	
	
	
	
	
	
	
	
	/*private double[] fenotipos_poblacion() {
		double[] ret = new double[tam_poblacion];
		for(int i=0;i<tam_poblacion;i++) {
			ret[i]=fenotipo(poblacion[i], xMin, xMax);
		}
		
		return ret;
	}
	*/
	
	
	
	
}

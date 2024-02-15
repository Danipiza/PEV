package Model;

public class Individuo {
	public Gen[] genes;
	
	public double[] fenotipo;
	
	public double fitness;
	
	public Individuo(int num, int[] tam_genes, double xMax[], double[] xMin){
		genes=new Gen[num];
		fenotipo=new double[num];
		for(int i=0;i<num;i++) {
			genes[i]=new Gen(tam_genes[i]);
		}
		calcular_fenotipo(xMax,xMin);
	}	
	
	private int bin2dec(Gen gen) {
		int ret=0;
		int cont=1;
		for(int i=gen.v.length-1;i>=0;i--) {
			if(gen.v[i]==1)ret+=cont;
			cont*=2;
		}
		return ret;
	}
	
	
	public void calcular_fenotipo(double[] xMax, double[] xMin) {			
		for(int i=0;i<genes.length;i++) {
			fenotipo[i]=calcular_fenotipoCromosoma(genes[i], xMax[i], xMin[i]);
		}		
	}		
	
	
	private double calcular_fenotipoCromosoma(Gen ind, double xMax, double xMin) {		
		return xMin + bin2dec(ind) *((xMax-xMin)/(Math.pow(2, ind.v.length)-1));
	}
	
	
	public void printIndividuo() {
		for(Gen c: genes) {
			for(int a: c.v) {
				System.out.print(a + " ");
			}
		}
		
		System.out.println(" fenotipo x1: " + fenotipo[0]+ " fenotipo x2: " + fenotipo[1]);
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

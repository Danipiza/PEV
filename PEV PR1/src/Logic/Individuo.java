package Logic;

public class Individuo {
	public Cromosoma[] cromosoma;
	
	public Individuo(int num, double precision, int[] maximos, int[] minimos){
		cromosoma=new Cromosoma[num];
		for(int i=0;i<num;i++) {
			cromosoma[i]=init(precision, maximos[i],minimos[i]);
		}
	}
	
	private Cromosoma init(double precision, int maximo, int minimo) {
		Cromosoma ret = new Cromosoma(tamGen(precision,maximo,minimo));
		for(int i=0;i<cromosoma.length;i++) {
			ret.v[i]=(Math.random()<=0.5?1:0);
		}
		return ret;
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
	
	private double fenotipo(Cromosoma ind, int xMax, int xMin) {
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
	
	private int tamGen(double precision, double min, double max) {
		return (int) (Math.log10(((max - min) / precision) + 1) / Math.log10(2));
	}
	
	
}

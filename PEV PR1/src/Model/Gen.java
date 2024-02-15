package Model;

public class Gen {
	public int[] v;
	
	public Gen(int l){
		v=new int[l];
		init(l);			
	}
	
	private void init(int l) {
		for(int i=0;i<l;i++) {
			v[i]=(Math.random()<=0.5?1:0);
		}
	}
	
	
	
	
}

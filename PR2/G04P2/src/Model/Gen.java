package Model;

public class Gen {
	public int[] v;

	public Gen(int l) {
		v = new int[l];
		init(l);
	}

	public Gen(Gen gen) {
		v = new int[gen.v.length];
		for (int i = 0; i < gen.v.length; i++) {
			v[i] = gen.v[i];
		}
	}

	private void init(int aviones) {
		v = new int[aviones];
		for (int i = 0; i < aviones; i++) {
			v[i] = i;
		}
		
		for (int i = aviones - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            
            int temp = v[i];
            v[i] = v[j];
            v[j] = temp;
        }
	}

}

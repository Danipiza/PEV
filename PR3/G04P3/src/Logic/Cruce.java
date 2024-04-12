package Logic;
import Model.Individuo;
import Model.Simbolos.Exp;

public class Cruce {

	private double p;

	private int tam_elite;


	public Cruce(double p, int tam_elite) {
		this.p = p;
		this.tam_elite = tam_elite;
	}

	// TODO
	public Individuo[] intercambio(Individuo[] selec) {
		int n = selec.length; // num_vuelos = selec[0].gen.v.length;
		Individuo[] ret = new Individuo[n + tam_elite];

		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}

		int i = 0;
		Individuo ind1, ind2;

		while (i < n) {
			//ind1 = new Individuo(selec[i]);
			//ind2 = new Individuo(selec[i+1]);
			ind1 = selec[i];
			ind2 = selec[i+1];
			if(Math.random()<p) {
				if(Math.random()<0.9) { // Funcional
					double drand1 = Math.random()*ind1.funcionales.size();
					double drand2 = Math.random()*ind2.funcionales.size();

					int rand1=(int)drand1;
					int rand2=(int)drand2;

					Exp hijo1 = ind1.funcionales.get(rand1).getKey().getHijo(ind1.funcionales.get(rand1).getValue());
					Exp hijo2 = ind2.funcionales.get(rand2).getKey().getHijo(ind2.funcionales.get(rand2).getValue());
					
					ind1.funcionales.get(rand1).getKey().setHijo(ind1.funcionales.get(rand1).getValue(), hijo2);
					ind2.funcionales.get(rand2).getKey().setHijo(ind2.funcionales.get(rand2).getValue(), hijo1);

					ind1.gen.raiz = ind1.funcionales.get(0).getKey().getHijo(0);
					ind2.gen.raiz = ind2.funcionales.get(0).getKey().getHijo(0);
				}
				else { // Terminal
					double drand1 = Math.random()*ind1.funcionales.size();
					double drand2 = Math.random()*ind2.funcionales.size();

					int rand1=(int)drand1;
					int rand2=(int)drand2;

					Exp hijo1 = ind1.terminales.get(rand1).getKey().getHijo(ind1.terminales.get(rand1).getValue());
					Exp hijo2 = ind2.terminales.get(rand2).getKey().getHijo(ind2.terminales.get(rand2).getValue());
					
					ind1.terminales.get(rand1).getKey().setHijo(ind1.terminales.get(rand1).getValue(), hijo2);
					ind2.terminales.get(rand2).getKey().setHijo(ind2.terminales.get(rand2).getValue(), hijo1);
				}
				ret[i++] = ind1;
				ret[i++] = ind2;
			}
		}
		
		return ret;
	}

}

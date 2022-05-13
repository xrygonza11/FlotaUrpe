package modelo;

public class Jota implements EgoeraState{

	@Override
	public void klik(Ontzia pOntzi, Arma pArma) {
		if (pArma instanceof Bonba) {
			
		}else if (pArma instanceof Misila) {
			pOntzi.setEgoera(new Urperatuta());
		}
		
	}

}

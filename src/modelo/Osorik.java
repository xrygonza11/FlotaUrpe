package modelo;

public class Osorik implements EgoeraState {

	@Override
	public void klik(Ontzia pOntzi, Arma pArma) {
		if (pArma instanceof Bonba) {
			pOntzi.setEgoera(new Jota());
		}else if (pArma instanceof Misila) {
			pOntzi.setEgoera(new Urperatuta());
		}else if (pArma instanceof Ezkutua) {
			pOntzi.gehituBizitza(100);
		}
		
	}

}

package modelo;


public class Armamentua {
	

	private Bonba bonba;
	private Misila misila;
	private Radarra radarra;
	private Ezkutua ezkutua;
	
	public Armamentua(int pKop, int pPrezioa) { // cuando haya tienda meter un kopuru
		this.bonba = new Bonba(100,0);
		this.misila = new Misila(pKop,pPrezioa);
		this.radarra = new Radarra(pKop,pPrezioa);
		this.ezkutua = new Ezkutua(pKop,pPrezioa);	
		
	}
	
	public Arma getArma(int zenb) {
		if(zenb == 1) {
			return(bonba);
		}else if(zenb == 2) {
			return(misila);
		}else if(zenb == 3) {
			return(radarra);
		}else {
			return(ezkutua);
		}
		
	}
	
	public int KopuruaJakin(Arma arma) {
		int kopurua = 0;
		if (arma instanceof Misila) {
			kopurua = this.misila.getKopurua();
		}else if (arma instanceof Radarra) {
			kopurua = this.radarra.getKopurua();
		}else if (arma instanceof Ezkutua) {
			kopurua = this.ezkutua.getKopurua();
		}
		return(kopurua);
	}
	
	
	public void kopuruaKendu(Arma arma) { //denda
		if (arma instanceof Misila) {
			 this.misila.batKendu();
		}else if (arma instanceof Radarra) {
			this.radarra.batKendu();
		}else if (arma instanceof Ezkutua) {
			this.ezkutua.batKendu();
		}
	}
	
	public void kopuruaGehitu(Arma arma) { //denda
		 if (arma instanceof Misila) {
			 this.misila.batGehitu();
		}else if (arma instanceof Radarra) {
			this.radarra.batGehitu();
		}else if (arma instanceof Ezkutua) {
			this.ezkutua.batGehitu();
		}
	}
}

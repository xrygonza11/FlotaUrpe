package modelo;

public abstract class Arma {
	protected int kopurua;
	protected int prezioa;

	public Arma(int pKop, int pPrezioa) {
		this.kopurua = pKop; 
		this.prezioa = pPrezioa;
	}
	
	protected void batGehitu() {
		this.kopurua++;
		
	}
	
	protected void batKendu() {
		this.kopurua--;
	}
	
	protected int getKopurua() {
		return(this.kopurua);
	}
}


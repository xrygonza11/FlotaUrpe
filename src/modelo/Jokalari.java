package modelo;

import java.util.Observable;

public abstract class Jokalari extends Observable{
	protected Zelaia jokalariZelaia;
	protected Zelaia tiroZelaia;
	protected Flota nireFlota;
	protected boolean txanda;
	
	
	public Jokalari () {
		this.txanda = false;
		this.jokalariZelaia = new Zelaia();
		this.tiroZelaia = new Zelaia();
		this.nireFlota = new Flota(new Armamentua(0, 100), 0);
				
	}
	
	public Egoera noraEmanDuTiroa(int x, int y) {
		return tiroZelaia.noraEmanDuTiroa(x,y);
	}
	
	public void txandaBukatu() {	
			txanda = false;
	}
	
	public boolean tiroEginDezake(int x, int y) {
		boolean tiro = true;
		if (noraEmanDuTiroa(x,y) == Egoera.URAJOTA || noraEmanDuTiroa(x,y) == Egoera.ONTZIJOTA ) {
			tiro = false;
		}
		return(tiro);
	}
	
	public boolean ontziaEzinDutKokatu(int x, int y, int noran , int tamaina) {
		return jokalariZelaia.ontziaEzinDutKokatu(x, y, noran, tamaina);
	}

	public void ontziaKokatu(int x, int y, int noran , int tamaina) {
		 jokalariZelaia.ontziaKokatu( x, y, noran, tamaina);
	}
	
	public boolean ontziakJarrita() {
		boolean jarrita = false;
		if(nireFlota.getOntziak().isEmpty()) {
			jarrita = true;			
		}
		return(jarrita);
	}
	
	public void okupatutaUrBihurtu() {
		if(ontziakJarrita() == true) {
			jokalariZelaia.okupatutaUrBihurtu(); 
		}
		
	}
	
	public void tiroEgin(int x, int y) {
		tiroZelaia.tiroEgin(x,y);
	}
	
	public void misilaBota(int x, int y) {
		nireFlota.getArmamentua().KopuruaJakin(nireFlota.getArmamentua().getArma(2));
		tiroZelaia.misilaBota(x,y);
	}
	
	public void radarraErabili(int x, int y) {
		nireFlota.getArmamentua().KopuruaJakin(nireFlota.getArmamentua().getArma(3));
		tiroZelaia.radarraErabili(x,y);
	}
	
	public void ezkutuaErabili(int x, int y) {
		nireFlota.getArmamentua().KopuruaJakin(nireFlota.getArmamentua().getArma(4));
		tiroZelaia.ezkutuaErabili(x,y);
	}
	
	public Zelaia getJokalariZelaia() {
		return jokalariZelaia;
	}
	
	public Zelaia getTiroZelaia() {
		return tiroZelaia;
	}
	
	public Flota getNireFlota() {
		return nireFlota;
	}
	
	
	public boolean gelaxkaLibreDago(int x, int y) {
		return jokalariZelaia.gelaxkaLibreDago(x,y);
	}
	
	public Egoera egoeraJakin(int x, int y) {
		return jokalariZelaia.egoeraJakin(x,y);
	}
	
	public boolean getTxanda() {
		return(this.txanda);
	}
	
	public void setTxanda(boolean txanda) {
		this.txanda = true;
	}
	
}

		 
package modelo;

import java.util.ArrayList;

public abstract class Ontzia {
	
//	private ArrayList<Gelaxka> ontzia;
	protected int tamaina;
	protected int noranzkoa;
	private EgoeraState egoera;
	protected int bizitza = 50;
	protected int konpoDiru;
	protected int urperDiru;
	protected String mota;
	
	
	
	public Ontzia() {
		this.konpoDiru = 100;
		this.urperDiru = 100000;
		this.egoera = new Osorik();
		
		
		
//		ontzia = new ArrayList<Gelaxka>();
//		tamaina = pTamaina;
//		for (int i = 0; i < tamaina; i++) {
//			ontzia.add(new Gelaxka(Egoera.ONTZIA));
//		}	
//		noranzkoa = pNoranzkoa;
//		egoera = Egoera.ONTZIA;
	}
	
	public int getNoranzkoa() {
		return noranzkoa;
	}
	
	public void setNoranzkoa(int noranzkoa) {
		this.noranzkoa = noranzkoa;
	}
	
	public int getTamaina() {
		return tamaina;
	}
	
	public EgoeraState getEgoera() {
		return egoera;
	}
	
	public void setEgoera(EgoeraState egoera) {
		this.egoera = egoera;
	}
	
	public void gehituBizitza(int pBizitza) {
		this.bizitza = this.bizitza+pBizitza;
	}
	
	
	
	
	
	
}

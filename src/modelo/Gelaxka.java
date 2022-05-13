package modelo;

import java.awt.Color;
import java.io.ObjectInputStream.GetField;

public class Gelaxka {
	//ATRIBUTUAK
	private Egoera egoera;
	private Ontzia ontzia;
		
	//ERAIKITZAILEA
	public Gelaxka(Egoera pEgoera) {
		this.egoera = pEgoera;
		this.ontzia = null;
	}
	
	public Egoera getEgoera() {
		return(this.egoera);
	}
	
	public void setEgoera(Egoera pEgoera) {
		this.egoera = pEgoera;		
	}
	
	public Ontzia getOntzia() {
		return this.ontzia;

	}
	
	public void setOntzia(Ontzia pOntzia) {
		this.ontzia = pOntzia;
	}
	
	public boolean ontziaDa() {
		if (ontzia == null) {
			return false;
		}else {
			return true;
		}
	}
	
	public EgoeraState zeinOntzi() {
		return ontzia.getEgoera();
	}
}

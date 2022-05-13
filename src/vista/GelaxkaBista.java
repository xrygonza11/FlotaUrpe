package vista;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;

import modelo.Egoera;


public class GelaxkaBista extends JLabel {
	//ATRIBUTUAK
	private Egoera egoera;
	private int Koordx;
	private int Koordy;
	
	//ERAIKITZAILEA
	public GelaxkaBista(int pX, int pY, Color pColor,Egoera pEgoera) {
		this.egoera = pEgoera;
		//this.koordenatua = new Koordenatua(pX, pY);
		this.Koordx =pX;
		this.Koordy = pY;
		this.setVisible(true);
		this.setOpaque(true);
		this.setBackground(pColor);
	}
	
	public int getKoordx() {
		return Koordx;
	}
	public int getKoordy() {
		return Koordy;
	}
	
	
	
	public GelaxkaBista getGelaxka() {
		return this;
	}
	

	public void setEgoera(Egoera pEgoera) {
		this.egoera = pEgoera;		
	}
	
	public Egoera getEgoera() {
		return egoera;
	}
}

package modelo;


import java.awt.im.InputMethodHighlight;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.midi.VoiceStatus;


public class Flota {
	private ArrayList<Ontzia> ontziLista;
    private Armamentua armamentu;
    private int ontzi1Kop;
    private int ontzi2Kop;
    private int ontzi3Kop;
    private int ontzi4Kop;
	private int dirua;

	
	public Flota(Armamentua pArmamentu, int pDirua) {
		this.ontziLista = new ArrayList<Ontzia>(10);
		this.armamentu = pArmamentu;
		this.dirua = 5000;
		
	}
	
	public void hasieratu() {
		sortuOntziLista();
	}
	
	private void sortuOntziLista() {
		OntziFactory oFactory = OntziFactory.getNOF();
		ontziLista.add(oFactory.createOntzia("Fragata"));
		ontziLista.add(oFactory.createOntzia("Fragata"));
		ontziLista.add(oFactory.createOntzia("Fragata"));
		ontziLista.add(oFactory.createOntzia("Fragata"));
		ontziLista.add(oFactory.createOntzia("Suntsitzailea"));
		ontziLista.add(oFactory.createOntzia("Suntsitzailea"));
		ontziLista.add(oFactory.createOntzia("Suntsitzailea"));
		ontziLista.add(oFactory.createOntzia("Itsaspeko"));
		ontziLista.add(oFactory.createOntzia("Itsaspeko"));
		ontziLista.add(oFactory.createOntzia("Hegazkinontzi"));		
	}

	public ArrayList<Ontzia> getOntziak() {
		return(ontziLista);
	}
	
	public Armamentua getArmamentua() {
		return(armamentu);
	}
	
	public int getDirua() {
		return(dirua);
	}
	
	public void ontziaKendu(Ontzia pOntzia) {
		ontziLista.remove(pOntzia);
		if (pOntzia instanceof Fragata) {
			ontzi1Kop--;
		}else if (pOntzia instanceof Suntsitzailea) {
			ontzi2Kop--;
		}else if (pOntzia instanceof Itsaspekoa) {
			ontzi3Kop--;
		}else if (pOntzia instanceof Hegazkinontzia) {
			ontzi4Kop--;
		}
		
//		Iterator<Ontzia> itr = ontziLista.iterator();/
//		while (itr.hasNext()) {
//			ontzia = itr.next();
//			if (ontzia.getMota() == pMota){
//				ontziLista.remove(ontzia);
//			}
//		}
	}
	
	
	public void flotaKudeatu(Armamentua armam, Arma arma){
		this.getArmamentua().kopuruaKendu(arma);
	}
	
	public int getOntziKop() {
		return ontzi1Kop+ontzi2Kop+ontzi3Kop+ontzi4Kop;
	}
	
	public int getOntzi1Kop() {
		return ontzi1Kop;
	}
	
	public int getOntzi2Kop() {
		return ontzi2Kop;
	}
	
	public int getOntzi3Kop() {
		return ontzi3Kop;
	}
	
	public int getOntzi4Kop() {
		return ontzi4Kop;
	}
	
	public boolean ontziGabe() {
		if (ontziLista.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	
		
}

package modelo;

public class OntziFactory {
	private static OntziFactory nOF;
	
	private OntziFactory() {}
	
	public static OntziFactory getNOF() {
		if (nOF == null){
			nOF = new OntziFactory();
		}return nOF;
	}
	
	public Ontzia createOntzia (String pMota) {
		switch (pMota) {
		case "Fragata": return new Fragata();		
		case "Suntsitzailea": return new Suntsitzailea();			
		case "Itsaspeko": return new Itsaspekoa();			
		case "Hegazkinontzi": return new Hegazkinontzia();
		default: return null;
		}
	}
}

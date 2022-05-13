package modelo;

public class JokalariArrunta extends Jokalari {
	
	private static JokalariArrunta nireJokalariArrunta = null;
	
	private JokalariArrunta() {
		super();
	}
	
	public static synchronized  JokalariArrunta getNireJokalariArrunta() {
		 if (nireJokalariArrunta == null) {
			 nireJokalariArrunta = new JokalariArrunta();	 
		 }
		 return(nireJokalariArrunta);
	}
	
	public void ontziakKokatuBot() {
		tiroZelaia.ontziakKokatuBot();
	}
	
	public boolean partidaAmaitu() {
		if(!(jokalariZelaia.ontziakGeratzenDira()) || !(tiroZelaia.ontziakGeratzenDira()) ) {
			return(true);
		}else {
			return(false);
		}
	}
	
	public boolean irabaziDu() {
		if(!tiroZelaia.ontziakGeratzenDira()) {
			return(true);
		}else {
			return(false);
		}
	}
	
	public boolean galduDu() {
		if(!jokalariZelaia.ontziakGeratzenDira()) {
			return(true);
		}else {
			return(false);
		}
	}
}

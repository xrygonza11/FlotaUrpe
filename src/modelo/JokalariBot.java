package modelo;


public class JokalariBot extends Jokalari {
	
	private static JokalariBot nireJokalariBot = null;
	
	private JokalariBot() {
		super();
	}
	
	public static JokalariBot getNireJokalariBot() {
		 if (nireJokalariBot == null) {
			 nireJokalariBot = new JokalariBot();	 
		 }
		 return(nireJokalariBot);
	}
	
	public void ontziakKokatuBot() {
		jokalariZelaia.ontziakKokatuBot();
	}
}

package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.OptionalInt;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import modelo.Egoera;
import modelo.Gelaxka;
import modelo.Jokalari;
import vista.GelaxkaBista;
import modelo.JokalariArrunta;
import modelo.JokalariBot;
import modelo.TimerKudeatu;


public class Jokoa extends JFrame implements Observer {


	private JPanel pnlBiltegia;
	private Zelaia pnlJokalariFlota;
	private Zelaia pnlOrdenagailuFlota;
	private JPanel pnlGoikoa;
	private JButton btnOntzi2;
	private JButton btnOntzi3;
	private JButton btnOntzi4;
	private JButton btnOntzi1;
	private JPanel pnlNagusia;
	public int aukeratutakoOntzia = 0;
	private int xKlikatuta;
	private int yJKlikatua;
	private Color jokalariZelaiaColor = new Color(116,195,247);
	private Color ordenagailuZelaiaColor = new Color(30,110,175);
	private int noranzkoa;	
	private ControlerGelaxka controlerGelaxka;
	private ControlerTiro controlerTiro;
	protected boolean sartuta;
	private JButton btnMisil;
	public int aukeratutakoArma;
	private JPanel pnlErdikoArmamentua;
	private JButton btnBonba;
	private JButton btnRadarra;
	private JButton btnEzkutua;
	private JPanel pnlErdikoOntziak;
	private boolean goikoaOkupatuta = false;
	private boolean eskuinekoaOkupatuta = false;
	private boolean behekoaOkupatuta = false;
	private boolean ezkerrekoaOkupatuta = false;
	private int ontzi1Kop = 4;
	private int ontzi2Kop = 3;
	private int ontzi3Kop = 2;
	private int ontzi4Kop = 1;
	protected boolean pressed = false;
	private int aurrekoNoranzkoa;
	private int noraRandom;
	private int xKoordRandom;
	private int yKoordRandom;
	private int tamainaOntziaRandom;
	private boolean pcZelaiaSortuta = false;
	private boolean jokalariTxanda = false;
	private boolean jokalariaTiroEginDu = false;
	private boolean ordenagailuaTiroEginDu = false;
	private int xJotzekoRandom;
	private int yJotzekoRandom;
	private boolean ontziakJarrita = false;
	private boolean taulabeteta = false;
	private GelaxkaBista lblKasillaOrdenagailu;
	private GelaxkaBista lblKasillaJokalari;
	private int armaRandom;
	private boolean arma1 = true;
	private boolean arma2 = true;
	private boolean arma3 = true;
	private boolean arma4 = true;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Jokoa frame = new Jokoa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Jokoa() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize();
		JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().addObserver(this);
		JokalariArrunta.getNireJokalariArrunta().getTiroZelaia().addObserver(this);
		TimerKudeatu.getKudeatzailea().addObserver(this);
		JokalariArrunta.getNireJokalariArrunta().addObserver(this);
		JokalariBot.getNireJokalariBot().addObserver(this);

		JokalariBot.getNireJokalariBot().getJokalariZelaia().ontziakKokatuBot();
		JokalariArrunta.getNireJokalariArrunta().getTiroZelaia().ontziakKokatuBot();
		JokalariArrunta.getNireJokalariArrunta().setTxanda(true);
		JokalariArrunta.getNireJokalariArrunta().getNireFlota().hasieratu();
		taulabeteta = true; 
	}


	private void initialize(){
		setResizable(false);
		setBounds(100, 100, 1200, 550);
		this.setContentPane(getPnlNagusia());
		setTitle("FLOTA URPERATU");
		setLocationRelativeTo(null);
		setVisible(true);
	}	


	private void matrizeaSortu(){
		for(int l = 0;l<10;l++) {
			for(int z=0;z<10;z++) {
				pnlJokalariFlota.add(getLblKasillaJokalari(l,z,jokalariZelaiaColor,Egoera.URA));
				pnlOrdenagailuFlota.add(getLblKasillaOrdenagailu(l,z,ordenagailuZelaiaColor));
			}
		}
	}

	private GelaxkaBista getLblKasillaJokalari(int x, int y, Color c, Egoera egoera){	 	
		lblKasillaJokalari  = new GelaxkaBista(x, y, c, egoera); 		
		lblKasillaJokalari.setOpaque(true);
		lblKasillaJokalari.setBackground(c);
		Border bordeBeltza = BorderFactory.createLineBorder(Color.BLACK,3);
		lblKasillaJokalari.setBorder(bordeBeltza);
		lblKasillaJokalari.addMouseListener(getControlerGelaxka());
		return lblKasillaJokalari;		
	}

	private boolean erabilita(int armaRand) {
		if (armaRand == 1 && arma2 == false) {
			return(false);
		}else if(armaRand == 2 && arma4 == false) {
			return (false);
		}else if(armaRand == 3 && arma3 == false) {
			return(false);
		}else {
			return(true);
		}
	}

	private void ordenagailuTxandaJolastu() {
		if (JokalariArrunta.getNireJokalariArrunta().ontziakJarrita()) {
			btnOntzi1.setBorder(new MatteBorder(null));
			btnOntzi2.setBorder(new MatteBorder(null));
			btnOntzi3.setBorder(new MatteBorder(null));
			btnOntzi4.setBorder(new MatteBorder(null));
			btnBonba.setBorder(new MatteBorder(null));
			btnMisil.setBorder(new MatteBorder(null));
			btnRadarra.setBorder(new MatteBorder(null));
			btnEzkutua.setBorder(new MatteBorder(null));
			JOptionPane.showMessageDialog(null, "Ordenagailuaren txanda da");
			Random arma= new Random();	
			Random x = new Random();
			Random y = new Random();
			Boolean ontziaDa = true;

			do {
				armaRandom = arma.nextInt(4);
			}
			while(!erabilita(armaRandom) && armaRandom != 0 );
			if (armaRandom==0){
				while (ontziaDa) {
					do {
						xJotzekoRandom =  x.nextInt(10);			
						yJotzekoRandom = y.nextInt(10);
					}while((JokalariBot.getNireJokalariBot().getTiroZelaia().egoeraJakin(xJotzekoRandom, yJotzekoRandom) == Egoera.URAJOTA)||(JokalariBot.getNireJokalariBot().getTiroZelaia().egoeraJakin(xJotzekoRandom, yJotzekoRandom) == Egoera.ONTZIJOTA));
					JOptionPane.showMessageDialog(null, "Eraso egingo dut, X:"+xJotzekoRandom+" Y:"+yJotzekoRandom+"kasillan");
					ImageIcon xIkonoa = new ImageIcon(Jokoa.class.getResource("/sources/xikonoa.png"));
					Image image = xIkonoa.getImage();
					Image newimage = image.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
					xIkonoa = new ImageIcon(newimage);
					if(JokalariBot.getNireJokalariBot().getTiroZelaia().egoeraJakin(xJotzekoRandom, yJotzekoRandom) == Egoera.URA || JokalariBot.getNireJokalariBot().getTiroZelaia().egoeraJakin(xJotzekoRandom, yJotzekoRandom) == Egoera.OKUPATUTA)  {
						JokalariBot.getNireJokalariBot().getTiroZelaia().tiroEgin(xJotzekoRandom,yJotzekoRandom);
						JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(xJotzekoRandom,yJotzekoRandom,Egoera.URAJOTA);
						((GelaxkaBista) pnlJokalariFlota.getComponent(xJotzekoRandom*10+yJotzekoRandom)).setIcon(null);
						((GelaxkaBista) pnlJokalariFlota.getComponent(xJotzekoRandom*10+yJotzekoRandom)).setIcon(xIkonoa);


						//JokalariBot.getNireJokalariBot().getNireFlota().flotaKudeatu(armam, Bon);
						ontziaDa = false;
					}else {
						JokalariBot.getNireJokalariBot().tiroEgin(xJotzekoRandom,yJotzekoRandom);
						JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(xJotzekoRandom,yJotzekoRandom,Egoera.ONTZIJOTA);
						((GelaxkaBista) pnlJokalariFlota.getComponent(xJotzekoRandom*10+yJotzekoRandom)).setIcon(null);
						((GelaxkaBista) pnlJokalariFlota.getComponent(xJotzekoRandom*10+yJotzekoRandom)).setIcon(xIkonoa);
						ontziaDa = true;
					}
				}
				JokalariArrunta.getNireJokalariArrunta().setTxanda(true);
				JOptionPane.showMessageDialog(null, "Zure txanda da");
				btnOntzi1.setBorder(new MatteBorder(null));
				btnOntzi2.setBorder(new MatteBorder(null));
				btnOntzi3.setBorder(new MatteBorder(null));
				btnOntzi4.setBorder(new MatteBorder(null));
				btnBonba.setBorder(new MatteBorder(null));
				btnMisil.setBorder(new MatteBorder(null));
				btnRadarra.setBorder(new MatteBorder(null));
				btnEzkutua.setBorder(new MatteBorder(null));

			}else if(armaRandom==1){
				while (ontziaDa){
					xJotzekoRandom = x.nextInt(10);			
					yJotzekoRandom = y.nextInt(10);
					while((JokalariBot.getNireJokalariBot().getTiroZelaia().egoeraJakin(xJotzekoRandom, yJotzekoRandom) == Egoera.URAJOTA)||(JokalariBot.getNireJokalariBot().getTiroZelaia().egoeraJakin(xJotzekoRandom, yJotzekoRandom) == Egoera.ONTZIJOTA));
					JOptionPane.showMessageDialog(null, "Misila botako dut, X:"+xJotzekoRandom+" Y:"+yJotzekoRandom+"kasillan");
					ImageIcon xIkonoa = new ImageIcon(Jokoa.class.getResource("/sources/xikonoa.png"));
					Image image = xIkonoa.getImage();
					Image newimage = image.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
					xIkonoa = new ImageIcon(newimage);
					if(JokalariBot.getNireJokalariBot().getTiroZelaia().egoeraJakin(xJotzekoRandom, yJotzekoRandom) == Egoera.URA || JokalariBot.getNireJokalariBot().getTiroZelaia().egoeraJakin(xJotzekoRandom, yJotzekoRandom) == Egoera.OKUPATUTA)  {
						JokalariBot.getNireJokalariBot().getTiroZelaia().tiroEgin(xJotzekoRandom,yJotzekoRandom);
						JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(xJotzekoRandom,yJotzekoRandom,Egoera.URAJOTA);
						((GelaxkaBista) pnlJokalariFlota.getComponent(xJotzekoRandom*10+yJotzekoRandom)).setIcon(null);
						((GelaxkaBista) pnlJokalariFlota.getComponent(xJotzekoRandom*10+yJotzekoRandom)).setIcon(xIkonoa);
						ontziaDa = false;
					}else{
						JokalariBot.getNireJokalariBot().misilaBota(xJotzekoRandom, yJotzekoRandom);
						boolean atera = false;
						int xJota = xJotzekoRandom;
						int yJota = yJotzekoRandom;
						if(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().egoeraJakin(xJota, yJota) == Egoera.ONTZIA){
							JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(xJota,yJota,Egoera.ONTZIJOTA);
							((GelaxkaBista) pnlJokalariFlota.getComponent(xJota*10+yJota)).setIcon(null);
							((GelaxkaBista) pnlJokalariFlota.getComponent(xJota*10+yJota)).setIcon(xIkonoa);
							if (yJota<9) { //eskuina
								yJota++;
								atera = false; 
								while(!((yJota>9) || (atera))) {
									if(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().egoeraJakin(xJota, yJota) == Egoera.ONTZIA){
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(xJota,yJota,Egoera.ONTZIJOTA);
										((GelaxkaBista) pnlJokalariFlota.getComponent(xJota*10+yJota)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(xJota*10+yJota)).setIcon(xIkonoa);

										yJota++;
									}else {
										atera = true;
									}
								}
							}
							xJota = xJotzekoRandom;
							yJota = yJotzekoRandom;
							if (!((xKlikatuta>9))) { //behera
								xJota++;
								atera = false;
								while(!((xJota>9)||(atera))) {
									if(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().egoeraJakin(xJota, yJota) == Egoera.ONTZIA){
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(xJota,yJota,Egoera.ONTZIJOTA);
										((GelaxkaBista) pnlJokalariFlota.getComponent(xJota*10+yJota)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(xJota*10+yJota)).setIcon(xIkonoa);
										xJota++;
									}else {
										atera = true;
									}
								}
							}
							xJota = xJotzekoRandom;
							yJota = yJotzekoRandom;
							if (yJota>0) { //ezkerra
								atera = false; 
								yJota--;
								while(!((yJota<0) || (atera))) {
									if(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().egoeraJakin(xJota, yJota) == Egoera.ONTZIA){
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(xJota,yJota,Egoera.ONTZIJOTA);
										((GelaxkaBista) pnlJokalariFlota.getComponent(xJota*10+yJota)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(xJota*10+yJota)).setIcon(xIkonoa);
										yJota--;
									}else {
										atera = true;
									}
								}
							}
							xJota = xJotzekoRandom;
							yJota = yJotzekoRandom;
							if (xJota>0) { //gora
								atera = false;
								xJota --;
								while(!((xJota<0) || (atera))) {
									if(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().egoeraJakin(xJota, yJota) == Egoera.ONTZIA){
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(xJota,yJota,Egoera.ONTZIJOTA);
										((GelaxkaBista) pnlJokalariFlota.getComponent(xJota*10+yJota)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(xJota*10+yJota)).setIcon(xIkonoa);
										xJota--;
									}else {
										atera = true;
									}
								}
							}
						}

						ontziaDa = true;

					}
				}
				arma2=false;
				JokalariArrunta.getNireJokalariArrunta().setTxanda(true);
				JOptionPane.showMessageDialog(null, "Zure txanda da");
				btnOntzi1.setBorder(new MatteBorder(null));
				btnOntzi2.setBorder(new MatteBorder(null));
				btnOntzi3.setBorder(new MatteBorder(null));
				btnOntzi4.setBorder(new MatteBorder(null));
				btnBonba.setBorder(new MatteBorder(null));
				btnMisil.setBorder(new MatteBorder(null));
				btnRadarra.setBorder(new MatteBorder(null));
				btnEzkutua.setBorder(new MatteBorder(null));

			}else if(armaRandom==3){
				xJotzekoRandom = x.nextInt(10);			
				yJotzekoRandom = y.nextInt(10);

				JOptionPane.showMessageDialog(null, "Radarra erabiliko dut, X:"+xJotzekoRandom+" Y:"+yJotzekoRandom+"kasillan");

				ImageIcon radara1con = new ImageIcon(Jokoa.class.getResource("/sources/a1con.png"));
				Image image = radara1con.getImage();
				Image newimage = image.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radara1con = new ImageIcon(newimage);

				ImageIcon radara2con = new ImageIcon(Jokoa.class.getResource("/sources/a2con.png"));
				Image image2 = radara2con.getImage();
				Image newimage2 = image2.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radara2con = new ImageIcon(newimage2);

				ImageIcon radara3con = new ImageIcon(Jokoa.class.getResource("/sources/a3con.png"));
				Image image3 = radara3con.getImage();
				Image newimage3 = image3.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radara3con = new ImageIcon(newimage3);

				ImageIcon radarb1con = new ImageIcon(Jokoa.class.getResource("/sources/b1con.png"));
				Image image4 = radarb1con.getImage();
				Image newimage4 = image4.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radarb1con = new ImageIcon(newimage4);

				ImageIcon radarb2con = new ImageIcon(Jokoa.class.getResource("/sources/b2con.png"));
				Image image5 = radarb2con.getImage();
				Image newimage5 = image5.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radarb2con = new ImageIcon(newimage5);

				ImageIcon radarb3con = new ImageIcon(Jokoa.class.getResource("/sources/b3con.png"));
				Image image6 = radarb3con.getImage();
				Image newimage6 = image6.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radarb3con = new ImageIcon(newimage6);

				ImageIcon radarc1con = new ImageIcon(Jokoa.class.getResource("/sources/c1con.png"));
				Image image7 = radarc1con.getImage();
				Image newimage7 = image7.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radarc1con = new ImageIcon(newimage7);

				ImageIcon radarc2con = new ImageIcon(Jokoa.class.getResource("/sources/c2con.png"));
				Image image8 = radarc2con.getImage();
				Image newimage8 = image8.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radarc2con = new ImageIcon(newimage8);

				ImageIcon radarc3con = new ImageIcon(Jokoa.class.getResource("/sources/c3con.png"));
				Image image9 = radarc3con.getImage();
				Image newimage9 = image9.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radarc3con = new ImageIcon(newimage9);

				ImageIcon radara1sin = new ImageIcon(Jokoa.class.getResource("/sources/a1sin.png"));
				Image image10 = radara1sin.getImage();
				Image newimage10 = image10.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radara1sin = new ImageIcon(newimage10);

				ImageIcon radara2sin = new ImageIcon(Jokoa.class.getResource("/sources/a2sin.png"));
				Image image11 = radara2sin.getImage();
				Image newimage11 = image11.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radara2sin = new ImageIcon(newimage11);

				ImageIcon radara3sin = new ImageIcon(Jokoa.class.getResource("/sources/a3sin.png"));
				Image image12 = radara3sin.getImage();
				Image newimage12 = image12.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radara3sin = new ImageIcon(newimage12);

				ImageIcon radarb1sin = new ImageIcon(Jokoa.class.getResource("/sources/b1sin.png"));
				Image image13 = radarb1sin.getImage();
				Image newimage13 = image13.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radarb1sin = new ImageIcon(newimage13);

				ImageIcon radarb2sin = new ImageIcon(Jokoa.class.getResource("/sources/b2sin.png"));
				Image image14 = radarb2sin.getImage();
				Image newimage14 = image14.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radarb2sin = new ImageIcon(newimage14);

				ImageIcon radarb3sin = new ImageIcon(Jokoa.class.getResource("/sources/b3sin.png"));
				Image image15 = radarb3sin.getImage();
				Image newimage15 = image15.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radarb3sin = new ImageIcon(newimage15);

				ImageIcon radarc1sin = new ImageIcon(Jokoa.class.getResource("/sources/c1sin.png"));
				Image image16 = radarc1sin.getImage();
				Image newimage16 = image16.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radarc1sin = new ImageIcon(newimage16);

				ImageIcon radarc2sin = new ImageIcon(Jokoa.class.getResource("/sources/c2sin.png"));
				Image image17 = radarc2sin.getImage();
				Image newimage17 = image17.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radarc2sin = new ImageIcon(newimage17);

				ImageIcon radarc3sin = new ImageIcon(Jokoa.class.getResource("/sources/c3sin.png"));
				Image image18 = radarc3sin.getImage();
				Image newimage18 = image18.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
				radarc3sin = new ImageIcon(newimage18);
				JokalariBot.getNireJokalariBot().radarraErabili(xJotzekoRandom, yJotzekoRandom);
				for(int i=xJotzekoRandom-1; i<=xJotzekoRandom+1; i++) {
					for(int j=yJotzekoRandom-1; j<=yJotzekoRandom+1; j++) {
						if(i>=0 && i<10 && j>=0 && j<10) {
							if (JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(i, j) != Egoera.ONTZIJOTA || JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(i, j) != Egoera.URAJOTA)	{
								if(JokalariBot.getNireJokalariBot().noraEmanDuTiroa(i, j) == Egoera.ONTZIAIKUSITA) {
									if(i==xJotzekoRandom-1 && j==yJotzekoRandom-1) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radara1con);

									}else if (i==xJotzekoRandom && j == yJotzekoRandom -1) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radarb1con);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.ONTZIAIKUSITA);
									}else if (i==xJotzekoRandom+1 && j == yJotzekoRandom -1) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radarc1con);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.ONTZIAIKUSITA);
									}else if (i==xJotzekoRandom-1 && j == yJotzekoRandom) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radara2con);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.ONTZIAIKUSITA);
									}else if (i==xJotzekoRandom && j == yJotzekoRandom) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radarb2con);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.ONTZIAIKUSITA);
									}else if (i==xJotzekoRandom+1 && j == yJotzekoRandom) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radarc2con);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.ONTZIAIKUSITA);
									}else if (i==xJotzekoRandom-1 && j == yJotzekoRandom+1) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radara3con);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.ONTZIAIKUSITA);
									}else if (i==xJotzekoRandom && j == yJotzekoRandom+1) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radarb3con);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.ONTZIAIKUSITA);
									}else if (i==xJotzekoRandom+1 && j == yJotzekoRandom+1) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radarc3con);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.ONTZIAIKUSITA);
									}													
								}else if(JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(i, j) == Egoera.URAIKUSITA || JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(xJotzekoRandom, yJotzekoRandom) == Egoera.OKUPATUTA) {//URA 
									if(i==xJotzekoRandom-1 && j==yJotzekoRandom-1) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radara1sin);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.URAIKUSITA);
									}else if (i==xJotzekoRandom && j == yJotzekoRandom -1) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radarb1sin);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.URAIKUSITA);
									}else if (i==xJotzekoRandom+1 && j == yJotzekoRandom -1) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radarc1sin);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.URAIKUSITA);
									}else if (i==xJotzekoRandom-1 && j == yJotzekoRandom) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radara2sin);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.URAIKUSITA);
									}else if (i==xJotzekoRandom && j == yJotzekoRandom) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radarb2sin);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.URAIKUSITA);
									}else if (i==xJotzekoRandom+1 && j == yJotzekoRandom) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radarc2sin);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.URAIKUSITA);
									}else if (i==xJotzekoRandom-1 && j == yJotzekoRandom+1) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radara3sin);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.URAIKUSITA);
									}else if (i==xJotzekoRandom && j == yJotzekoRandom+1) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radarb3sin);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.URAIKUSITA);
									}else if (i==xJotzekoRandom+1 && j == yJotzekoRandom+1) {
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(null);
										((GelaxkaBista) pnlJokalariFlota.getComponent(i*10+j)).setIcon(radarc3sin);
										JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().setEgoera(i,j,Egoera.URAIKUSITA);
									}	 
								}
							}
						}
					}
				}

				arma3=false;
				JokalariArrunta.getNireJokalariArrunta().setTxanda(true);
				JOptionPane.showMessageDialog(null, "Zure txanda da");
				btnOntzi1.setBorder(new MatteBorder(null));
				btnOntzi2.setBorder(new MatteBorder(null));
				btnOntzi3.setBorder(new MatteBorder(null));
				btnOntzi4.setBorder(new MatteBorder(null));
				btnBonba.setBorder(new MatteBorder(null));
				btnMisil.setBorder(new MatteBorder(null));
				btnRadarra.setBorder(new MatteBorder(null));
				btnEzkutua.setBorder(new MatteBorder(null));

			}
		}
		if(armaRandom==2){//eskutua
			Random x = new Random();
			Random y = new Random();
			boolean atera;
			int berriX ;
			int berriY ;
			do {
				berriX = x.nextInt(10);			
				berriY = y.nextInt(10);


			}while(JokalariBot.getNireJokalariBot().getJokalariZelaia().egoeraJakin(berriX, berriY) != Egoera.ONTZIA);
			JOptionPane.showMessageDialog(null, "Ezkutua erabiliko dut, X:"+berriX+" Y:"+berriY+"kasillan");
			JokalariBot.getNireJokalariBot().ezkutuaErabili(berriX, berriY);
			if (berriY<9) { //eskuina
				berriY++;
				atera = false; 
				while(!((berriY>9) || (atera))) {

					if(JokalariBot.getNireJokalariBot().getJokalariZelaia().egoeraJakin(berriX, berriY) == Egoera.ONTZIA){
						JokalariBot.getNireJokalariBot().getJokalariZelaia().setEgoera(berriX,berriY,Egoera.ONTZIBABESTUA);
						berriY++;
					}else {
						atera = true;
					}
				}
			}
			berriX = xJotzekoRandom;
			berriY = yJotzekoRandom;
			if (!((berriX>9))) { //behera
				berriY++;
				atera = false;
				while(!((berriX>9)||(atera))) {
					if(JokalariBot.getNireJokalariBot().getJokalariZelaia().egoeraJakin(berriX, berriY) == Egoera.ONTZIA) {
						JokalariBot.getNireJokalariBot().getJokalariZelaia().setEgoera(berriX,berriY,Egoera.ONTZIBABESTUA);
						berriX++;
					}else {
						atera = true;
					}
				}
			}
			berriX=xJotzekoRandom;
			berriY = yJotzekoRandom;
			if (berriY>0) { //ezkerra
				atera = false; 
				berriY--;
				while(!((berriY<0) || (atera))) {
					if(JokalariBot.getNireJokalariBot().getJokalariZelaia().egoeraJakin(berriX, berriY) == Egoera.ONTZIA) {
						JokalariBot.getNireJokalariBot().getJokalariZelaia().setEgoera(berriX,berriY,Egoera.ONTZIBABESTUA);
						berriY--;
					}else {
						atera = true;
					}
				}
			}
			berriX=xJotzekoRandom;
			berriY = yJotzekoRandom;
			if (berriX>0) { //gora
				atera = false;
				berriX --;
				while(!((berriX<0) || (atera))) {
					if(JokalariBot.getNireJokalariBot().getJokalariZelaia().egoeraJakin(berriX, berriY) == Egoera.ONTZIA) {
						JokalariBot.getNireJokalariBot().getJokalariZelaia().setEgoera(berriX,berriY,Egoera.ONTZIBABESTUA);
						berriX--;
					}else {
						atera = true;
					}
				}
			}

			arma4=false;
			JokalariArrunta.getNireJokalariArrunta().setTxanda(true);
			JOptionPane.showMessageDialog(null, "Zure txanda da");
			btnOntzi1.setBorder(new MatteBorder(null));
			btnOntzi2.setBorder(new MatteBorder(null));
			btnOntzi3.setBorder(new MatteBorder(null));
			btnOntzi4.setBorder(new MatteBorder(null));
			btnBonba.setBorder(new MatteBorder(null));
			btnMisil.setBorder(new MatteBorder(null));
			btnRadarra.setBorder(new MatteBorder(null));
			btnEzkutua.setBorder(new MatteBorder(null));
		}
	}





	protected void albokoakMarraztu(int xKoord, int yKoord, Color c, int nora, Egoera e) {
		System.out.println("Ind:"+(xKoord*10+yKoord)+"Nor:"+nora+"Tam:"+aukeratutakoOntzia);
		switch (nora) {
		case 1:
			if ((yKoord+aukeratutakoOntzia <= 10)&&(!eskuinekoaOkupatuta)) {
				eskuinekoakMarraztu(xKoord, yKoord, c, e);
			}else if ((xKoord+aukeratutakoOntzia <= 10)&&(!behekoaOkupatuta)) {				
				behekoakMarraztu(xKoord, yKoord, c, e);
				noranzkoa++;
			}else if (!ezkerrekoaOkupatuta){
				ezkerrekoakMarraztu(xKoord, yKoord, c, e);
				noranzkoa++;
				noranzkoa++;
			}			
			break;	

		case 2:
			if ((xKoord+aukeratutakoOntzia <= 10)&&(!behekoaOkupatuta)) {
				behekoakMarraztu(xKoord, yKoord, c, e);
			}else if ((yKoord-aukeratutakoOntzia >= -1)&&(!ezkerrekoaOkupatuta)){
				ezkerrekoakMarraztu(xKoord, yKoord, c,e );
				noranzkoa++;
			}else if (!goikoaOkupatuta){
				goikoakMarraztu(xKoord, yKoord, c, e );
				noranzkoa++; 
				noranzkoa++;
			}
			break;	

		case 3:
			if ((yKoord-aukeratutakoOntzia >= -1)&&(!ezkerrekoaOkupatuta)) {
				ezkerrekoakMarraztu(xKoord, yKoord, c, e );
			}else if ((xKoord-aukeratutakoOntzia >= -1)&&(!goikoaOkupatuta)){
				goikoakMarraztu(xKoord, yKoord, c, e);
				noranzkoa++;
			}else if (!eskuinekoaOkupatuta){
				eskuinekoakMarraztu(xKoord, yKoord, c, e);
				noranzkoa++;
				noranzkoa++;  
			}
			break;

		case 4:
			if ((xKoord-aukeratutakoOntzia >= -1)&&(!goikoaOkupatuta)){
				goikoakMarraztu(xKoord, yKoord, c, e);
			}else if ((yKoord+aukeratutakoOntzia <= 10)&&(!eskuinekoaOkupatuta)){
				eskuinekoakMarraztu(xKoord, yKoord, c, e);
				noranzkoa++;
			}else if (!behekoaOkupatuta) {
				behekoakMarraztu(xKoord, yKoord, c, e);
				noranzkoa++;
				noranzkoa++;
			}
			break;	
		default:
			break;
		}		
	}

	////////////////////////////
	//ALBOKOAK EZABATU METODOA//
	////////////////////////////
	protected void albokoakEzabatu(int xKoord, int yKoord, int nora, Egoera e) {		
		switch (nora) {
		case 1:
			if ((yKoord+aukeratutakoOntzia <= 10)&&(!eskuinekoaOkupatuta)) {
				eskuinekoakMarraztu(xKoord, yKoord, jokalariZelaiaColor, e);
			}else if ((xKoord+aukeratutakoOntzia <= 10)&&(!behekoaOkupatuta)) {				
				behekoakMarraztu(xKoord, yKoord, jokalariZelaiaColor, e);
				noranzkoa++;
			}else if(!ezkerrekoaOkupatuta) {
				ezkerrekoakMarraztu(xKoord, yKoord, jokalariZelaiaColor, e);
				noranzkoa++;
				noranzkoa++;
			}
			break;


		case 2:
			if ((xKoord+aukeratutakoOntzia <= 10)&&(!behekoaOkupatuta)) {
				behekoakMarraztu(xKoord, yKoord, jokalariZelaiaColor, e);
			}else if ((yKoord-aukeratutakoOntzia >= -1)&&(!ezkerrekoaOkupatuta)){
				ezkerrekoakMarraztu(xKoord, yKoord, jokalariZelaiaColor, e);
				noranzkoa++;
			}else if (!goikoaOkupatuta) {
				goikoakMarraztu(xKoord, yKoord, jokalariZelaiaColor, e);
				noranzkoa++;
				noranzkoa++;
			}
			break;


		case 3:
			if ((yKoord-aukeratutakoOntzia >= -1)&&(!ezkerrekoaOkupatuta)){
				ezkerrekoakMarraztu(xKoord, yKoord, jokalariZelaiaColor, e);
			}else if ((xKoord-aukeratutakoOntzia >= -1)&&(!goikoaOkupatuta)){
				goikoakMarraztu(xKoord, yKoord, jokalariZelaiaColor, e);
				noranzkoa++;
			}else if (!eskuinekoaOkupatuta) {
				eskuinekoakMarraztu(xKoord, yKoord, jokalariZelaiaColor, e);
				noranzkoa++;
				noranzkoa++;  
			}
			break;


		case 4:
			if ((xKoord-aukeratutakoOntzia >= -1)&&(!goikoaOkupatuta)) {
				goikoakMarraztu(xKoord, yKoord, jokalariZelaiaColor, e);
			}else if ((yKoord+aukeratutakoOntzia <= 10)&&(!eskuinekoaOkupatuta)){
				eskuinekoakMarraztu(xKoord, yKoord, jokalariZelaiaColor, e);
				noranzkoa++;
			}else if (!behekoaOkupatuta) {
				behekoakMarraztu(xKoord, yKoord, jokalariZelaiaColor, e);
				noranzkoa++;
				noranzkoa++;
			}
			break;

		default:
			break;
		}		
	}

	////////////////////////////////
	//ESKUINEKOAK MARRAZTU METODOA//
	////////////////////////////////
	protected void eskuinekoakMarraztu(int xKoord, int yKoord, Color c, Egoera e) {
		if (yKoord+aukeratutakoOntzia <= 10) {
			for (int i=0;i<aukeratutakoOntzia;i++) {			
				koordenatuBatenLaukiariKoloreAldaketa(xKoord, yKoord+i, c, e);

			}
		}
	}

	/////////////////////////////
	//BEHEKOAK MARRAZTU METODOA//
	/////////////////////////////
	protected void behekoakMarraztu(int xKoord, int yKoord, Color c, Egoera e) {
		if (xKoord+aukeratutakoOntzia <= 10) {
			for (int i=0;i<aukeratutakoOntzia;i++) {
				koordenatuBatenLaukiariKoloreAldaketa(xKoord+i, yKoord,c, e);
			}
		}
	}

	////////////////////////////////
	//EZKERREKOAK MARRAZTU METODOA//
	////////////////////////////////
	protected void ezkerrekoakMarraztu(int xKoord, int yKoord, Color c, Egoera e) {
		if (yKoord-aukeratutakoOntzia >= -1) {
			for (int i=0;i<aukeratutakoOntzia;i++) {			
				koordenatuBatenLaukiariKoloreAldaketa(xKoord, yKoord-i, c, e);
			}
		}		
	}

	////////////////////////////
	//GOIKOAK MARRAZTU METODOA//
	////////////////////////////
	protected void goikoakMarraztu(int xKoord, int yKoord, Color c, Egoera e) {
		if (xKoord-aukeratutakoOntzia >= -1) {
			for (int i=0;i<aukeratutakoOntzia;i++) {
				koordenatuBatenLaukiariKoloreAldaketa(xKoord-i, yKoord,c, e);			
			}
		}
	}

	////////////////////////////////////////////////////
	//KORDENATU BATEN LAUKIARI KOLORE ALDAKETA METODOA//
	////////////////////////////////////////////////////
	private void koordenatuBatenLaukiariKoloreAldaketa(int x, int y, Color c, Egoera e) {
		if(!pcZelaiaSortuta ) {
			((GelaxkaBista) pnlOrdenagailuFlota.getComponent(x*10+y)).setEgoera(Egoera.ONTZIA);
		}else {
			((GelaxkaBista) pnlJokalariFlota.getComponent(x*10+y)).setEgoera(e);
			pnlJokalariFlota.getComponent(x*10+y).setBackground(c);
		}		
	}

	private GelaxkaBista getLblKasillaOrdenagailu(int x, int y, Color c) {
		lblKasillaOrdenagailu = new GelaxkaBista(x,y,ordenagailuZelaiaColor,Egoera.URA);
		lblKasillaOrdenagailu.setOpaque(true);
		lblKasillaOrdenagailu.setBackground(c);
		Border bordeBeltza = BorderFactory.createLineBorder(Color.BLACK,3);
		lblKasillaOrdenagailu.setBorder(bordeBeltza);		
		lblKasillaOrdenagailu.addMouseListener(getControlerTiro());		
		return lblKasillaOrdenagailu;
	}




	private JPanel getPnlNagusia() {
		if (pnlNagusia == null) {
			pnlNagusia = new JPanel();
		}
		pnlNagusia.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlNagusia.setLayout(new BorderLayout(0, 0));
		pnlNagusia.add(getPnlBiltegia(), BorderLayout.SOUTH);
		pnlNagusia.add(getPnlJokalariFlota(), BorderLayout.WEST);		
		pnlNagusia.add(getPnlOrdenagailuFlota(), BorderLayout.EAST);
		pnlNagusia.add(getPnlErdikoOntziak(), BorderLayout.CENTER);
		pnlNagusia.add(getPnlGoikoa(), BorderLayout.NORTH);
		matrizeaSortu();
		//pcOntziakSortu();
		aukeratutakoOntzia = 0;
		pcZelaiaSortuta = true;
		return pnlNagusia;		
	}



	private JPanel getPnlBiltegia() {
		if (pnlBiltegia == null) {
			pnlBiltegia = new JPanel();
		}
		return pnlBiltegia;
	}


	private Zelaia getPnlJokalariFlota() {
		if (pnlJokalariFlota == null) {
			pnlJokalariFlota = new Zelaia();
			pnlJokalariFlota.setBorder(new MatteBorder(11, 10, 12, 10, (Color) new Color(0, 0, 0)));
			pnlJokalariFlota.setPreferredSize(new Dimension(500, 500));
			pnlJokalariFlota.setLayout(new GridLayout(10, 10, 0, 0));
		}
		return pnlJokalariFlota; 
	}


	private Zelaia getPnlOrdenagailuFlota() {
		if (pnlOrdenagailuFlota == null) {
			pnlOrdenagailuFlota = new Zelaia();
			pnlOrdenagailuFlota.setBorder(new MatteBorder(11, 10, 12, 10, (Color) new Color(0, 0, 0)));
			pnlOrdenagailuFlota.setPreferredSize(new Dimension(500, 500));
			pnlOrdenagailuFlota.setLayout(new GridLayout(10, 10, 0, 0));			
		}
		return pnlOrdenagailuFlota;
	}


	private JPanel getPnlErdikoOntziak() {
		if (pnlErdikoOntziak == null) {
			pnlErdikoOntziak = new JPanel();
			pnlErdikoOntziak.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			GroupLayout gl_pnlErdikoFlota = new GroupLayout(pnlErdikoOntziak);
			gl_pnlErdikoFlota.setHorizontalGroup(
					gl_pnlErdikoFlota.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlErdikoFlota.createSequentialGroup()
							.addGap(24)
							.addGroup(gl_pnlErdikoFlota.createParallelGroup(Alignment.TRAILING)
									.addComponent(getBtnOntzi1(), GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
									.addComponent(getBtnOntzi2(), Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
									.addComponent(getBtnOntzi3(), GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
									.addComponent(getBtnOntzi4(), Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(24))
					);
			gl_pnlErdikoFlota.setVerticalGroup(
					gl_pnlErdikoFlota.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlErdikoFlota.createSequentialGroup()
							.addComponent(getBtnOntzi1(), GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addGap(23)
							.addComponent(getBtnOntzi2(), GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(getBtnOntzi3(), GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addGap(26)
							.addComponent(getBtnOntzi4(), GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(156, Short.MAX_VALUE))
					);
			pnlErdikoOntziak.setLayout(gl_pnlErdikoFlota);
		}
		return pnlErdikoOntziak;
	}


	private JPanel getPnlErdikoArmamentua() { 
		if (pnlErdikoArmamentua == null) {
			pnlErdikoArmamentua = new JPanel();
			GroupLayout gl_pnlErdikoFlota = new GroupLayout(pnlErdikoArmamentua);
			gl_pnlErdikoFlota.setHorizontalGroup(
					gl_pnlErdikoFlota.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlErdikoFlota.createSequentialGroup()
							.addGap(24)
							.addGroup(gl_pnlErdikoFlota.createParallelGroup(Alignment.TRAILING)
									.addComponent(getBtnBonba(), GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
									.addComponent(getBtnMisil(), Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
									.addComponent(getBtnRadarra(), GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
									.addComponent(getBtnEzkutua(), Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(24))
					);
			gl_pnlErdikoFlota.setVerticalGroup(
					gl_pnlErdikoFlota.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlErdikoFlota.createSequentialGroup()
							.addComponent(getBtnBonba(), GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addGap(23)
							.addComponent(getBtnMisil(), GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(getBtnRadarra(), GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addGap(26)
							.addComponent(getBtnEzkutua(), GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(156, Short.MAX_VALUE))
					);
			pnlErdikoArmamentua.setLayout(gl_pnlErdikoFlota);
		}
		return pnlErdikoArmamentua;
	}


	private JPanel getPnlGoikoa() {
		if (pnlGoikoa == null) {
			pnlGoikoa = new JPanel();
		}
		return pnlGoikoa;
	}



	private JButton getBtnOntzi1() {
		if (btnOntzi1 == null) {
			btnOntzi1 = new JButton(""+ontzi1Kop);
			btnOntzi1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnOntzi1.setHorizontalTextPosition(SwingConstants.CENTER);
			btnOntzi1.setIconTextGap(1);
			btnOntzi1.addActionListener(getControlerGelaxka());			
			ImageIcon ontzi1 = new ImageIcon(Jokoa.class.getResource("/sources/Ontzi1.png"));
			Image image = ontzi1.getImage();
			Image newimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			ontzi1 = new ImageIcon(newimage);
			btnOntzi1.setIcon(ontzi1);
		}
		return btnOntzi1;
	}


	private JButton getBtnOntzi2() {
		if (btnOntzi2 == null) {
			btnOntzi2 = new JButton(ontzi2Kop+"      "+ontzi2Kop);
			btnOntzi2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnOntzi2.setHorizontalTextPosition(SwingConstants.CENTER);
			btnOntzi2.addActionListener(getControlerGelaxka());			
			ImageIcon ontzi2 = new ImageIcon(Jokoa.class.getResource("/sources/ontzi2.png"));
			Image image = ontzi2.getImage();
			Image newimage = image.getScaledInstance(50, 25, java.awt.Image.SCALE_SMOOTH);
			ontzi2 = new ImageIcon(newimage);
			btnOntzi2.setIcon(ontzi2);
		}
		return btnOntzi2; 
	}


	private JButton getBtnOntzi3() {
		if (btnOntzi3 == null) {
			btnOntzi3 = new JButton(ontzi3Kop+"      "+ontzi3Kop+"      "+ontzi3Kop);
			btnOntzi3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnOntzi3.setHorizontalTextPosition(SwingConstants.CENTER);
			btnOntzi3.addActionListener(getControlerGelaxka());
			ImageIcon ontzi3 = new ImageIcon(Jokoa.class.getResource("/sources/Ontzi3.png"));
			Image image = ontzi3.getImage();
			Image newimage = image.getScaledInstance(75, 25, java.awt.Image.SCALE_SMOOTH);
			ontzi3 = new ImageIcon(newimage);
			btnOntzi3.setIcon(ontzi3);
		}
		return btnOntzi3;
	}


	private JButton getBtnOntzi4() {
		if (btnOntzi4 == null) {
			btnOntzi4 = new JButton(ontzi4Kop+"      "+ontzi4Kop+"      "+ontzi4Kop+"      "+ontzi4Kop);
			btnOntzi4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnOntzi4.setHorizontalTextPosition(SwingConstants.CENTER);
			btnOntzi4.addActionListener(getControlerGelaxka());
			ImageIcon ontzi4 = new ImageIcon(Jokoa.class.getResource("/sources/Ontzi4.png"));
			Image image = ontzi4.getImage();
			Image newimage = image.getScaledInstance(100, 25, java.awt.Image.SCALE_SMOOTH);
			ontzi4 = new ImageIcon(newimage);
			btnOntzi4.setIcon(ontzi4);
		}
		return btnOntzi4;
	}


	private JButton getBtnMisil() {
		if (btnMisil == null) {
			btnMisil = new JButton("");
			btnMisil.addActionListener(getControlerTiro());			
			ImageIcon misil = new ImageIcon(Jokoa.class.getResource("/sources/Misila.png"));
			Image image = misil.getImage();
			Image newimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			misil = new ImageIcon(newimage);
			btnMisil.setIcon(misil);
		}
		return btnMisil;
	}


	private JButton getBtnBonba() {
		if (btnBonba == null) {
			btnBonba = new JButton("");
			btnBonba.addActionListener(getControlerTiro());			
			ImageIcon bonba = new ImageIcon(Jokoa.class.getResource("/sources/Bonba.png"));
			Image image = bonba.getImage();
			Image newimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			bonba = new ImageIcon(newimage);
			btnBonba.setIcon(bonba);
		}
		return btnBonba;
	}


	private JButton getBtnRadarra() {
		if (btnRadarra == null) {
			btnRadarra = new JButton("");
			btnRadarra.addActionListener(getControlerTiro());			
			ImageIcon radarra = new ImageIcon(Jokoa.class.getResource("/sources/Radar.png"));
			Image image = radarra.getImage();
			Image newimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			radarra = new ImageIcon(newimage);
			btnRadarra.setIcon(radarra);
		}
		return btnRadarra;
	}


	private JButton getBtnEzkutua() {
		if (btnEzkutua == null) {
			btnEzkutua = new JButton("");
			btnEzkutua.addActionListener(getControlerTiro());			
			ImageIcon ezkutua = new ImageIcon(Jokoa.class.getResource("/sources/Ezkutua.png"));
			Image image = ezkutua.getImage();
			Image newimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			ezkutua = new ImageIcon(newimage);
			btnEzkutua.setIcon(ezkutua);
		}
		return btnEzkutua;
	}



	/////////////////////////////// KONTROLER CLASEA //////////////////////////////
	private class ControlerGelaxka implements ActionListener,MouseListener{

		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(btnOntzi1)) {
				aukeratutakoOntzia = 1;
				noranzkoa = 1;
				btnOntzi1.setBorder(new MatteBorder(4, 4, 4, 4, (Color) Color.RED));
				btnOntzi2.setBorder(new MatteBorder(null));
				btnOntzi3.setBorder(new MatteBorder(null));
				btnOntzi4.setBorder(new MatteBorder(null));
			}else if(e.getSource().equals(btnOntzi2)) {
				aukeratutakoOntzia = 2;
				noranzkoa = 1;
				btnOntzi2.setBorder(new MatteBorder(4, 4, 4, 4, (Color) Color.GREEN));
				btnOntzi1.setBorder(new MatteBorder(null));
				btnOntzi3.setBorder(new MatteBorder(null));
				btnOntzi4.setBorder(new MatteBorder(null));
			}else if(e.getSource().equals(btnOntzi3)) {
				aukeratutakoOntzia = 3;
				noranzkoa = 1;
				btnOntzi3.setBorder(new MatteBorder(4, 4, 4, 4, (Color) Color.ORANGE));
				btnOntzi2.setBorder(new MatteBorder(null));
				btnOntzi1.setBorder(new MatteBorder(null));
				btnOntzi4.setBorder(new MatteBorder(null));
			}else if(e.getSource().equals(btnOntzi4)) {
				aukeratutakoOntzia = 4;	
				noranzkoa = 1;
				btnOntzi4.setBorder(new MatteBorder(4, 4, 4, 4, (Color) Color.YELLOW));
				btnOntzi2.setBorder(new MatteBorder(null));
				btnOntzi3.setBorder(new MatteBorder(null));
				btnOntzi1.setBorder(new MatteBorder(null));				
			}
		}


		public void mouseClicked(MouseEvent e) {
			System.out.println(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().egoeraJakin(xKlikatuta, yJotzekoRandom));





		}


		@Override
		public void mousePressed(MouseEvent e) {
			pressed  = true;
			sartuta = true;
			if((!JokalariArrunta.getNireJokalariArrunta().gelaxkaLibreDago(xKlikatuta,yJotzekoRandom) && JokalariArrunta.getNireJokalariArrunta().getTxanda() && aukeratutakoArma == 0 && aukeratutakoOntzia != 0)||(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().eskuinekoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)&&JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().ezkerrekoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)&&JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().goikoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)&&JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().behekoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) ) {
				pressed = false;
				JOptionPane.showMessageDialog(null, "EZIN DA ONTZIA HOR JARRI");
			}
			System.out.println(( JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().egoeraJakin(xKlikatuta, yJotzekoRandom)));
			int betikoX = xKlikatuta;
			int betikoY = yJotzekoRandom;
			boolean atera = false; 

			if (JokalariArrunta.getNireJokalariArrunta().getTxanda()) {
				if (JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().egoeraJakin(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIA) {
					if(aukeratutakoArma==4){

						JokalariArrunta.getNireJokalariArrunta().txandaBukatu();

						aukeratutakoArma = 0;

						ImageIcon ezkutua = new ImageIcon(Jokoa.class.getResource("/sources/Ezkutu.png"));
						Image image = ezkutua.getImage();
						Image newimage = image.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						ezkutua = new ImageIcon(newimage);

						((GelaxkaBista) e.getSource()).setIcon(ezkutua);

						JokalariArrunta.getNireJokalariArrunta().radarraErabili(xKlikatuta, yJotzekoRandom);
						if (yJotzekoRandom<9) { //eskuina
							yJotzekoRandom++;
							atera = false; 
							while(!((yJotzekoRandom>9) || (atera))) {

								if(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().egoeraJakin(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIA){
									((GelaxkaBista) pnlJokalariFlota.getComponent(xKlikatuta*10+yJotzekoRandom)).setIcon(ezkutua);
									yJotzekoRandom++;
								}else {
									atera = true;
								}
							}
						}
						xKlikatuta = betikoX;
						yJotzekoRandom = betikoY;
						if (!((xKlikatuta>9))) { //behera
							xKlikatuta++;
							atera = false;
							while(!((xKlikatuta>9)||(atera))) {
								if(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().egoeraJakin(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIA) {
									((GelaxkaBista) pnlJokalariFlota.getComponent(xKlikatuta*10+yJotzekoRandom)).setIcon(ezkutua);
									xKlikatuta++;
								}else {
									atera = true;
								}
							}
						}
						xKlikatuta = betikoX;
						yJotzekoRandom = betikoY;
						if (yJotzekoRandom>0) { //ezkerra
							atera = false; 
							yJotzekoRandom--;
							while(!((yJotzekoRandom<0) || (atera))) {
								if(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().egoeraJakin(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIA) {
									((GelaxkaBista) pnlJokalariFlota.getComponent(xKlikatuta*10+yJotzekoRandom)).setIcon(ezkutua);
									yJotzekoRandom--;
								}else {
									atera = true;
								}
							}
						}
						xKlikatuta = betikoX;
						yJotzekoRandom = betikoY;
						if (xKlikatuta>0) { //gora
							atera = false;
							xKlikatuta --;
							while(!((xKlikatuta<0) || (atera))) {
								if(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().egoeraJakin(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIA) {
									((GelaxkaBista) pnlJokalariFlota.getComponent(xKlikatuta*10+yJotzekoRandom)).setIcon(ezkutua);
									xKlikatuta--;
								}else {
									atera = true;
								}
							}
						}
						JokalariArrunta.getNireJokalariArrunta().txandaBukatu();
						ordenagailuTxandaJolastu();
					}
				} 
			}

		}


		@Override
		public void mouseReleased(MouseEvent e) {
			pressed = false;
			switch (aukeratutakoOntzia) {
			case 1:
				ontzi1Kop--;
				JokalariArrunta.getNireJokalariArrunta().ontziaKokatu(xKlikatuta,yJotzekoRandom,noranzkoa,1);
				JokalariBot.getNireJokalariBot().getTiroZelaia().ontziaKokatu(xKlikatuta,yJotzekoRandom,noranzkoa,1);
				//				albokoakMarraztu(xKlikatuta, yJotzekoRandom, Color.RED, noranzkoa, Egoera.ONTZIA);
				//				if (ontzi1Kop<=0) {btnOntzi1.setEnabled(false);}
				//				btnOntzi1.setText(""+ontzi1Kop);
				break;
			case 2:
				ontzi2Kop--;
				JokalariArrunta.getNireJokalariArrunta().ontziaKokatu(xKlikatuta,yJotzekoRandom,noranzkoa,2);
				JokalariBot.getNireJokalariBot().getTiroZelaia().ontziaKokatu(xKlikatuta,yJotzekoRandom,noranzkoa,2);
				//				albokoakMarraztu(xKlikatuta, yJotzekoRandom, Color.GREEN, noranzkoa, Egoera.ONTZIA);
				//				if (ontzi2Kop<=0) { btnOntzi2.setEnabled(false);}
				//				btnOntzi2.setText(ontzi2Kop+"      "+ontzi2Kop);
				break;

			case 3:
				ontzi3Kop--;
				JokalariArrunta.getNireJokalariArrunta().ontziaKokatu(xKlikatuta,yJotzekoRandom,noranzkoa,3);
				JokalariBot.getNireJokalariBot().getTiroZelaia().ontziaKokatu(xKlikatuta,yJotzekoRandom,noranzkoa,3);
				//				albokoakMarraztu(xKlikatuta, yJotzekoRandom, Color.ORANGE, noranzkoa, Egoera.ONTZIA);
				//				if (ontzi3Kop<=0) {btnOntzi3.setEnabled(false);}
				//				btnOntzi3.setText(ontzi3Kop+"      "+ontzi3Kop+"      "+ontzi3Kop);
				break;
			case 4:
				ontzi4Kop--;
				JokalariArrunta.getNireJokalariArrunta().ontziaKokatu(xKlikatuta,yJotzekoRandom,noranzkoa,4);
				JokalariBot.getNireJokalariBot().getTiroZelaia().ontziaKokatu(xKlikatuta,yJotzekoRandom,noranzkoa,4);
				//				albokoakMarraztu(xKlikatuta, yJotzekoRandom, Color.YELLOW, noranzkoa, Egoera.ONTZIA);
				//				if (ontzi4Kop<=0) {btnOntzi4.setEnabled(false);}
				//				btnOntzi4.setText(ontzi4Kop+"      "+ontzi4Kop+"      "+ontzi4Kop+"      "+ontzi4Kop);
				break;

			default:
				break;
			}
			aukeratutakoOntzia = 0;
			if(ontzi1Kop<=0 && ontzi2Kop<=0 && ontzi3Kop<=0 && ontzi4Kop<=0) {
				ontziakJarrita = true;
				pnlErdikoOntziak.setVisible(false);
				pnlNagusia.add(getPnlErdikoArmamentua(), BorderLayout.CENTER);
			} 
			btnOntzi1.setBorder(new MatteBorder(null));
			btnOntzi2.setBorder(new MatteBorder(null));
			btnOntzi3.setBorder(new MatteBorder(null));
			btnOntzi4.setBorder(new MatteBorder(null));



		}


		@Override
		public void mouseEntered(MouseEvent e) {
			xKlikatuta = ((GelaxkaBista) e.getSource()).getKoordx();
			yJotzekoRandom = ((GelaxkaBista) e.getSource()).getKoordy();	

			if (JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().eskuinekoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) {
				if(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().behekoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) {
					if(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().ezkerrekoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) {
						noranzkoa = 4;
					}else {
						noranzkoa = 3;
					}
				}else {
					noranzkoa = 2;
				}
			}else {
				noranzkoa = 1;
			}
			System.out.println(xKlikatuta+" "+yJotzekoRandom+"nor:"+noranzkoa);
		}


		@Override
		public void mouseExited(MouseEvent e) {
			behekoaOkupatuta = false;
			ezkerrekoaOkupatuta = false;
			goikoaOkupatuta = false;
			eskuinekoaOkupatuta = false;
			pressed = false;
		}
	}	

	private ControlerGelaxka getControlerGelaxka() {
		if (controlerGelaxka == null) {
			controlerGelaxka = new ControlerGelaxka();
		}
		return controlerGelaxka; 
	}




	private class ControlerTiro implements ActionListener,MouseListener{



		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(btnBonba)) {
				aukeratutakoArma = 1;
				btnBonba.setBorder(new MatteBorder(4, 4, 4, 4, (Color) Color.BLACK));
				btnMisil.setBorder(new MatteBorder(null));
				btnRadarra.setBorder(new MatteBorder(null));
				btnEzkutua.setBorder(new MatteBorder(null));
			}else if(e.getSource().equals(btnMisil)) {
				aukeratutakoArma = 2;
				btnMisil.setBorder(new MatteBorder(4, 4, 4, 4, (Color) Color.BLACK));
				btnRadarra.setBorder(new MatteBorder(null));
				btnBonba.setBorder(new MatteBorder(null));
				btnEzkutua.setBorder(new MatteBorder(null));
			}else if(e.getSource().equals(btnRadarra)) {
				aukeratutakoArma = 3;
				btnRadarra.setBorder(new MatteBorder(4, 4, 4, 4, (Color) Color.BLACK));
				btnMisil.setBorder(new MatteBorder(null));
				btnBonba.setBorder(new MatteBorder(null));
				btnEzkutua.setBorder(new MatteBorder(null));
			}else if(e.getSource().equals(btnEzkutua)) {
				aukeratutakoArma = 4;
				btnEzkutua.setBorder(new MatteBorder(4, 4, 4, 4, (Color) Color.BLACK));
				btnMisil.setBorder(new MatteBorder(null));
				btnRadarra.setBorder(new MatteBorder(null));
				btnBonba.setBorder(new MatteBorder(null));
			}

		}
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println(JokalariArrunta.getNireJokalariArrunta().getTiroZelaia().egoeraJakin(xKlikatuta, yJotzekoRandom));

		}

		@Override
		public void mousePressed(MouseEvent e) {

			xKlikatuta = ((GelaxkaBista) e.getSource()).getKoordx();
			yJotzekoRandom = ((GelaxkaBista) e.getSource()).getKoordy();
			int betikoX = xKlikatuta;
			int betikoY = yJotzekoRandom;
			boolean atera = false;
			if(aukeratutakoArma != 0 && ontziakJarrita) {
				if (JokalariArrunta.getNireJokalariArrunta().getTxanda()) {
					if (((JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIA || JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIAIKUSITA)) && (aukeratutakoArma!=3)) {
						if(aukeratutakoArma==1){							
							((GelaxkaBista) e.getSource()).setBackground(Color.RED);
							((GelaxkaBista) e.getSource()).setIcon(null);
							JOptionPane.showMessageDialog(null, "ONTZIA DA!");
							JokalariArrunta.getNireJokalariArrunta().tiroEgin(xKlikatuta, yJotzekoRandom);
							JokalariArrunta.getNireJokalariArrunta().getTiroZelaia().setEgoera(xKlikatuta, yJotzekoRandom, Egoera.ONTZIJOTA);
						}else if(aukeratutakoArma==2){
							((GelaxkaBista) e.getSource()).setBackground(Color.RED);
							((GelaxkaBista) e.getSource()).setIcon(null);
							JokalariArrunta.getNireJokalariArrunta().misilaBota(xKlikatuta, yJotzekoRandom);;
							if (yJotzekoRandom<9) { //eskuina
								yJotzekoRandom++;
								atera = false; 
								while(!((yJotzekoRandom>9) || (atera))) {
									//System.out.println(((Kasilla) pnlOrdenagailuFlota.getComponent(xKlikatuta*10+yJotzekoRandom)).getEgoera());
									if(JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIA || JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIAIKUSITA || JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIJOTA){
										pnlOrdenagailuFlota.getComponent(xKlikatuta*10+yJotzekoRandom).setBackground(Color.RED);
										((GelaxkaBista) pnlOrdenagailuFlota.getComponent(xKlikatuta*10+yJotzekoRandom)).setIcon(null);
										JokalariArrunta.getNireJokalariArrunta().getTiroZelaia().setEgoera(xKlikatuta, yJotzekoRandom, Egoera.ONTZIJOTA);
										yJotzekoRandom++;
									}else {
										atera = true;
									}
								}
							}
							xKlikatuta = betikoX;
							yJotzekoRandom = betikoY;
							if (!((xKlikatuta>9))) { //behera
								xKlikatuta++;
								atera = false;
								while(!((xKlikatuta>9)||(atera))) {
									if(JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIA || JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIAIKUSITA || JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIJOTA) {
										pnlOrdenagailuFlota.getComponent(xKlikatuta*10+yJotzekoRandom).setBackground(Color.RED);
										((GelaxkaBista) pnlOrdenagailuFlota.getComponent(xKlikatuta*10+yJotzekoRandom)).setIcon(null);
										JokalariArrunta.getNireJokalariArrunta().getTiroZelaia().setEgoera(xKlikatuta, yJotzekoRandom, Egoera.ONTZIJOTA);
										xKlikatuta++;
									}else {
										atera = true;
									}
								}
							}
							xKlikatuta = betikoX;
							yJotzekoRandom = betikoY;
							if (yJotzekoRandom>0) { //ezkerra
								atera = false; 
								yJotzekoRandom--;
								while(!((yJotzekoRandom<0) || (atera))) {
									if(JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIAIKUSITA || JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIA || JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIJOTA) {
										pnlOrdenagailuFlota.getComponent(xKlikatuta*10+yJotzekoRandom).setBackground(Color.RED);
										((GelaxkaBista) pnlOrdenagailuFlota.getComponent(xKlikatuta*10+yJotzekoRandom)).setIcon(null);
										JokalariArrunta.getNireJokalariArrunta().getTiroZelaia().setEgoera(xKlikatuta, yJotzekoRandom, Egoera.ONTZIJOTA);
										yJotzekoRandom--;
									}else {
										atera = true;
									}
								}
							}
							xKlikatuta = betikoX;
							yJotzekoRandom = betikoY;
							if (xKlikatuta>0) { //gora
								atera = false;
								xKlikatuta --;
								while(!((xKlikatuta<0) || (atera))) {
									if(JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIA || JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIAIKUSITA || JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(xKlikatuta, yJotzekoRandom) == Egoera.ONTZIJOTA) {
										pnlOrdenagailuFlota.getComponent(xKlikatuta*10+yJotzekoRandom).setBackground(Color.RED);
										((GelaxkaBista) pnlOrdenagailuFlota.getComponent(xKlikatuta*10+yJotzekoRandom)).setIcon(null);
										JokalariArrunta.getNireJokalariArrunta().getTiroZelaia().setEgoera(xKlikatuta, yJotzekoRandom, Egoera.ONTZIJOTA);
										xKlikatuta--;
									}else {
										atera = true;
									}
								}
							}
						}		
					}else if(aukeratutakoArma==3) {
						ImageIcon radara1con = new ImageIcon(Jokoa.class.getResource("/sources/a1con.png"));
						Image image = radara1con.getImage();
						Image newimage = image.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radara1con = new ImageIcon(newimage);

						ImageIcon radara2con = new ImageIcon(Jokoa.class.getResource("/sources/a2con.png"));
						Image image2 = radara2con.getImage();
						Image newimage2 = image2.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radara2con = new ImageIcon(newimage2);

						ImageIcon radara3con = new ImageIcon(Jokoa.class.getResource("/sources/a3con.png"));
						Image image3 = radara3con.getImage();
						Image newimage3 = image3.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radara3con = new ImageIcon(newimage3);

						ImageIcon radarb1con = new ImageIcon(Jokoa.class.getResource("/sources/b1con.png"));
						Image image4 = radarb1con.getImage();
						Image newimage4 = image4.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radarb1con = new ImageIcon(newimage4);

						ImageIcon radarb2con = new ImageIcon(Jokoa.class.getResource("/sources/b2con.png"));
						Image image5 = radarb2con.getImage();
						Image newimage5 = image5.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radarb2con = new ImageIcon(newimage5);

						ImageIcon radarb3con = new ImageIcon(Jokoa.class.getResource("/sources/b3con.png"));
						Image image6 = radarb3con.getImage();
						Image newimage6 = image6.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radarb3con = new ImageIcon(newimage6);

						ImageIcon radarc1con = new ImageIcon(Jokoa.class.getResource("/sources/c1con.png"));
						Image image7 = radarc1con.getImage();
						Image newimage7 = image7.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radarc1con = new ImageIcon(newimage7);

						ImageIcon radarc2con = new ImageIcon(Jokoa.class.getResource("/sources/c2con.png"));
						Image image8 = radarc2con.getImage();
						Image newimage8 = image8.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radarc2con = new ImageIcon(newimage8);

						ImageIcon radarc3con = new ImageIcon(Jokoa.class.getResource("/sources/c3con.png"));
						Image image9 = radarc3con.getImage();
						Image newimage9 = image9.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radarc3con = new ImageIcon(newimage9);

						ImageIcon radara1sin = new ImageIcon(Jokoa.class.getResource("/sources/a1sin.png"));
						Image image10 = radara1sin.getImage();
						Image newimage10 = image10.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radara1sin = new ImageIcon(newimage10);

						ImageIcon radara2sin = new ImageIcon(Jokoa.class.getResource("/sources/a2sin.png"));
						Image image11 = radara2sin.getImage();
						Image newimage11 = image11.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radara2sin = new ImageIcon(newimage11);

						ImageIcon radara3sin = new ImageIcon(Jokoa.class.getResource("/sources/a3sin.png"));
						Image image12 = radara3sin.getImage();
						Image newimage12 = image12.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radara3sin = new ImageIcon(newimage12);

						ImageIcon radarb1sin = new ImageIcon(Jokoa.class.getResource("/sources/b1sin.png"));
						Image image13 = radarb1sin.getImage();
						Image newimage13 = image13.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radarb1sin = new ImageIcon(newimage13);

						ImageIcon radarb2sin = new ImageIcon(Jokoa.class.getResource("/sources/b2sin.png"));
						Image image14 = radarb2sin.getImage();
						Image newimage14 = image14.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radarb2sin = new ImageIcon(newimage14);

						ImageIcon radarb3sin = new ImageIcon(Jokoa.class.getResource("/sources/b3sin.png"));
						Image image15 = radarb3sin.getImage();
						Image newimage15 = image15.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radarb3sin = new ImageIcon(newimage15);

						ImageIcon radarc1sin = new ImageIcon(Jokoa.class.getResource("/sources/c1sin.png"));
						Image image16 = radarc1sin.getImage();
						Image newimage16 = image16.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radarc1sin = new ImageIcon(newimage16);

						ImageIcon radarc2sin = new ImageIcon(Jokoa.class.getResource("/sources/c2sin.png"));
						Image image17 = radarc2sin.getImage();
						Image newimage17 = image17.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radarc2sin = new ImageIcon(newimage17);

						ImageIcon radarc3sin = new ImageIcon(Jokoa.class.getResource("/sources/c3sin.png"));
						Image image18 = radarc3sin.getImage();
						Image newimage18 = image18.getScaledInstance(42, 40, java.awt.Image.SCALE_SMOOTH);
						radarc3sin = new ImageIcon(newimage18);
						JokalariArrunta.getNireJokalariArrunta().getTiroZelaia().radarraErabili(xKlikatuta, yJotzekoRandom);
						JokalariArrunta.getNireJokalariArrunta().txandaBukatu();
						for(int i=xKlikatuta-1; i<=xKlikatuta+1; i++) {
							for(int j=yJotzekoRandom-1; j<=yJotzekoRandom+1; j++) {
								if(i>=0 && i<10 && j>=0 && j<10) {
									if (JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(i, j) != Egoera.ONTZIJOTA || JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(i, j) != Egoera.URAJOTA)	{
										if(JokalariArrunta.getNireJokalariArrunta().noraEmanDuTiroa(i, j) == Egoera.ONTZIAIKUSITA) {
											if(i==xKlikatuta-1 && j==yJotzekoRandom-1) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radara1con);
											}else if (i==xKlikatuta && j == yJotzekoRandom -1) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radarb1con);
											}else if (i==xKlikatuta+1 && j == yJotzekoRandom -1) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radarc1con);
											}else if (i==xKlikatuta-1 && j == yJotzekoRandom) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radara2con);
											}else if (i==xKlikatuta && j == yJotzekoRandom) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radarb2con);
											}else if (i==xKlikatuta+1 && j == yJotzekoRandom) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radarc2con);
											}else if (i==xKlikatuta-1 && j == yJotzekoRandom+1) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radara3con);
											}else if (i==xKlikatuta && j == yJotzekoRandom+1) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radarb3con);
											}else if (i==xKlikatuta+1 && j == yJotzekoRandom+1) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radarc3con);
											}													
										}else if(JokalariArrunta.getNireJokalariArrunta().getTiroZelaia().egoeraJakin(i, j) == Egoera.URAIKUSITA || JokalariArrunta.getNireJokalariArrunta().getTiroZelaia().egoeraJakin(xKlikatuta, yJotzekoRandom) == Egoera.OKUPATUTA) {//URA 
											if(i==xKlikatuta-1 && j==yJotzekoRandom-1) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radara1sin);
											}else if (i==xKlikatuta && j == yJotzekoRandom -1) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radarb1sin);
											}else if (i==xKlikatuta+1 && j == yJotzekoRandom -1) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radarc1sin);
											}else if (i==xKlikatuta-1 && j == yJotzekoRandom) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radara2sin);
											}else if (i==xKlikatuta && j == yJotzekoRandom) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radarb2sin);
											}else if (i==xKlikatuta+1 && j == yJotzekoRandom) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radarc2sin);
											}else if (i==xKlikatuta-1 && j == yJotzekoRandom+1) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radara3sin);
											}else if (i==xKlikatuta && j == yJotzekoRandom+1) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radarb3sin);
											}else if (i==xKlikatuta+1 && j == yJotzekoRandom+1) {
												((GelaxkaBista) pnlOrdenagailuFlota.getComponent(i*10+j)).setIcon(radarc3sin);
											}	 

										}
									}
								}
							}
						}
						JokalariArrunta.getNireJokalariArrunta().txandaBukatu();
						ordenagailuTxandaJolastu();		
					}else {
						((GelaxkaBista) e.getSource()).setBackground(new Color(0,249,255));
						JOptionPane.showMessageDialog(null, "URA!");
						JokalariArrunta.getNireJokalariArrunta().tiroEgin(xKlikatuta, yJotzekoRandom);
						JokalariArrunta.getNireJokalariArrunta().txandaBukatu();
						ordenagailuTxandaJolastu();
						aukeratutakoArma = 0;
					}

				}

			}	
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			btnBonba.setBorder(new MatteBorder(null));
			btnMisil.setBorder(new MatteBorder(null));
			btnEzkutua.setBorder(new MatteBorder(null));
			btnRadarra.setBorder(new MatteBorder(null));
			aukeratutakoArma = 0;

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}


	}
	private ControlerTiro getControlerTiro() {
		if (controlerTiro == null) {
			controlerTiro = new ControlerTiro();
		}
		return controlerTiro;
	}







	@Override
	public void update(Observable o, Object arg) {
		if (arg.equals(0)) {
			for(int l = 0;l<10;l++) {
				for(int z=0;z<10;z++) {
					Gelaxka gelaxka = JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().getGelaxka(l, z);
					if (gelaxka.getEgoera() == Egoera.JOTA) {
						if (gelaxka.ontziaDa()) {
							
						}
					}
					
					
					
					
					
					if (JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().getGelaxka(l, z).ontziaDa()) {
						koordenatuBatenLaukiariKoloreAldaketa(l, z, Color.RED, Egoera.ONTZIA);
					}else if (JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().egoeraJakin(l, z)==Egoera.ONTZIA) {
						koordenatuBatenLaukiariKoloreAldaketa(l, z, Color.RED, Egoera.ONTZIA);	
					}else if (JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().egoeraJakin(l, z)==Egoera.OKUPATUTA) {
						koordenatuBatenLaukiariKoloreAldaketa(l, z, Color.BLACK, Egoera.OKUPATUTA);	
					}
				}
			}
			JOptionPane.showMessageDialog(null, JokalariArrunta.getNireJokalariArrunta().getNireFlota().getOntziak().size());
		}		


		if (arg.equals(1)) {
			albokoakMarraztu(xKlikatuta, yJotzekoRandom, Color.RED, noranzkoa, Egoera.ONTZIA); 
			if (ontzi1Kop<=0) {btnOntzi1.setEnabled(false);}
			btnOntzi1.setText(""+ontzi1Kop);
		}else if (arg.equals(2)) {
			albokoakMarraztu(xKlikatuta, yJotzekoRandom, Color.GREEN, noranzkoa, Egoera.ONTZIA);
			if (ontzi2Kop<=0) { btnOntzi2.setEnabled(false);}
			btnOntzi2.setText(ontzi2Kop+"      "+ontzi2Kop);
		}else if (arg.equals(3)) {
			albokoakMarraztu(xKlikatuta, yJotzekoRandom, Color.ORANGE, noranzkoa, Egoera.ONTZIA);
			if (ontzi3Kop<=0) {btnOntzi3.setEnabled(false);}
			btnOntzi3.setText(ontzi3Kop+"      "+ontzi3Kop+"      "+ontzi3Kop);
		}else if (arg.equals(4)) {
			albokoakMarraztu(xKlikatuta, yJotzekoRandom, Color.YELLOW, noranzkoa, Egoera.ONTZIA);
			if (ontzi4Kop<=0) {btnOntzi4.setEnabled(false);}
			btnOntzi4.setText(ontzi4Kop+"      "+ontzi4Kop+"      "+ontzi4Kop+"      "+ontzi4Kop);
		}
		//System.out.println(JokalariArrunta.getNireJokalariArrunta().getNireFlota().getOntziak().size());
		//System.out.println(aukeratutakoArma);
		if(taulabeteta) {
			if (JokalariArrunta.getNireJokalariArrunta().ontziakJarrita()) {
				if(JokalariArrunta.getNireJokalariArrunta().partidaAmaitu()) {
					if(JokalariArrunta.getNireJokalariArrunta().irabaziDu()) {
						JOptionPane.showMessageDialog(null, "IRABAZI DUZU!  :)");
						setVisible(false);
						System.exit(0);
					}
					else if(JokalariArrunta.getNireJokalariArrunta().galduDu()) {
						JOptionPane.showMessageDialog(null, "GALDU DUZU! :(");
						setVisible(false);
						System.exit(0);
					}
				}
			}
		}

		if (pressed) {
			if ((sartuta)){{
				switch (noranzkoa) {		
				case 1:
					if (JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().behekoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) {
						if (JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().ezkerrekoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) {
							if (JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().goikoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) {
								noranzkoa = 1;
								aurrekoNoranzkoa = 1;
							}else {
								noranzkoa = 4;
								aurrekoNoranzkoa = 1;
							}
						}else {
							noranzkoa = 3;
							aurrekoNoranzkoa = 1;
						}
					}else {
						noranzkoa = 2;
						aurrekoNoranzkoa = 1;
					}				
					break;

				case 2:
					if (JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().ezkerrekoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) {
						if (JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().goikoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) {
							if (JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().eskuinekoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) {
								noranzkoa = 2;
								aurrekoNoranzkoa = 2;
							}else {
								noranzkoa = 1;
								aurrekoNoranzkoa = 2;
							}
						}else {
							noranzkoa = 4;
							aurrekoNoranzkoa = 2;
						}
					}else {
						noranzkoa = 3;
						aurrekoNoranzkoa = 2;
					}
					break;

				case 3:
					if(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().goikoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) {
						if(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().eskuinekoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) {
							if(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().behekoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) {
								noranzkoa = 3;
								aurrekoNoranzkoa = 3;
							}else {
								noranzkoa = 2;
								aurrekoNoranzkoa = 3;
							}
						}else {
							noranzkoa = 1;
							aurrekoNoranzkoa = 3;
						}
					}else {
						noranzkoa = 4;
						aurrekoNoranzkoa = 3;
					}
					break;

				case 4:
					if (JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().eskuinekoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) {
						if (JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().behekoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) {
							if(JokalariArrunta.getNireJokalariArrunta().getJokalariZelaia().ezkerrekoaOkupatuta(xKlikatuta, yJotzekoRandom, aukeratutakoOntzia)) {
								noranzkoa = 4;
								aurrekoNoranzkoa = 4;
							}else {
								noranzkoa = 3;
								aurrekoNoranzkoa = 4;
							}
						}else {
							noranzkoa = 2;
							aurrekoNoranzkoa = 4;
						}
					}else {
						noranzkoa  = 1;
						aurrekoNoranzkoa = 4;
					}

				default:
					break;
				}	

				switch (aukeratutakoOntzia) {
				case 2:		
					albokoakEzabatu(xKlikatuta, yJotzekoRandom, aurrekoNoranzkoa, Egoera.URA);		
					albokoakMarraztu(xKlikatuta, yJotzekoRandom, Color.GREEN, noranzkoa, Egoera.ONTZIA);				
					break;

				case 3:
					albokoakEzabatu(xKlikatuta, yJotzekoRandom, aurrekoNoranzkoa, Egoera.URA);				
					albokoakMarraztu(xKlikatuta, yJotzekoRandom, Color.ORANGE, noranzkoa, Egoera.ONTZIA);					
					break;

				case 4:
					albokoakEzabatu(xKlikatuta, yJotzekoRandom, aurrekoNoranzkoa, Egoera.URA );				
					albokoakMarraztu(xKlikatuta, yJotzekoRandom, Color.YELLOW, noranzkoa, Egoera.ONTZIA);
					break;					
				}
			}}


		}	
	}
}



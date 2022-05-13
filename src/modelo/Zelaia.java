package modelo;

import java.awt.Color;
import java.util.Observable;



public class Zelaia extends Observable{

	private Gelaxka[][] zelaia;

	public Zelaia() {
		zelaia=new Gelaxka[10][10];
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				zelaia[i][j]=new Gelaxka(Egoera.URA);
			}
		}
	}

	public boolean ontziakGeratzenDira() {
		int kont = 0;
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				if (zelaia[i][j].getEgoera() == Egoera.ONTZIJOTA ) { 
					kont++; 
				}
			}
		}
		if(kont == 20) {
			return(false);
		}else {
			return(true);
		}
		
	}

	public Egoera noraEmanDuTiroa(int x, int y) {
		return(zelaia[x][y].getEgoera());
	}

	public boolean ontziaEzinDutKokatu(int x, int y, int noran, int tamaina) {
		if(noran == 1) {
			return(eskuinekoaOkupatuta(x,y,tamaina));
		}else if(noran == 2) {
			return(behekoaOkupatuta(x,y,tamaina));
		}else if(noran == 3) {
			return(ezkerrekoaOkupatuta(x,y,tamaina));
		}else{
			return(goikoaOkupatuta(x,y,tamaina));
		}
	}

	public boolean eskuinekoaOkupatuta(int x, int y, int tamaina) {
		boolean okupatuta = false;
			for(int i = 0; i<tamaina; i++) {
				if(!okupatuta) {				
					if(zelaia[x][y+i].getEgoera() == Egoera.ONTZIA || zelaia[x][y+i].getEgoera() == Egoera.OKUPATUTA || y+tamaina>10) {
						okupatuta = true;
					}
				}
			}
		
		return okupatuta;
	}

	public boolean behekoaOkupatuta(int x, int y, int tamaina) {
		boolean okupatuta = false;
			for(int i = 0; i<tamaina; i++) {
				if(!okupatuta) {
					if(zelaia[x+i][y].getEgoera() == Egoera.ONTZIA || zelaia[x+i][y].getEgoera() == Egoera.OKUPATUTA || x+tamaina>10) {
						okupatuta = true;
					}
				}
			}
		
		return okupatuta;
	}

	public boolean ezkerrekoaOkupatuta(int x, int y, int tamaina) {
		boolean okupatuta = false;
			for(int i = 0; i<tamaina; i++) {
				if(!okupatuta) {
					if(zelaia[x][y-i].getEgoera() == Egoera.ONTZIA || zelaia[x][y-i].getEgoera() == Egoera.OKUPATUTA || y-tamaina<-1) {
						okupatuta = true;
					}
				}
			}
		
		return okupatuta;
	}

	public boolean goikoaOkupatuta(int x, int y, int tamaina) {
		boolean okupatuta = false;		
			for(int i = 0; i<tamaina; i++) {
				if(!okupatuta) {						
					if(zelaia[x-i][y].getEgoera() == Egoera.ONTZIA || zelaia[x-i][y].getEgoera() == Egoera.OKUPATUTA || x-tamaina<-1) {
						okupatuta = true;
					}
				}
			}
		return okupatuta;
	}

	public void ontziaKokatu(int x, int y, Ontzia pOntzia, int noran) {
		int tamaina = pOntzia.getTamaina();
		int i = 0;
		while(tamaina>0) {
			if(noran == 1) {
				zelaia[x][y+i].setOntzia(pOntzia);
				i++;
				tamaina--;
			}else if(noran == 2) {
				zelaia[x+i][y].setOntzia(pOntzia);
				i++;
				tamaina--;
			}else if(noran == 3) {
				zelaia[x][y-i].setOntzia(pOntzia);
				i++;
				tamaina--;
			}else{
				zelaia[x-i][y].setOntzia(pOntzia);
				i++;
				tamaina--;
			}

		} 		
		ontziIngurua(x,y,noran,tamaina);
		setChanged();
		notifyObservers(0);
		}
	
		

	public void ontziIngurua (int x,int y, int noran,int tamaina) {
		int aukX = x;
		int aukY = y;
		int luzeera = tamaina;

		while(aukX<=9 && aukX>=0 && aukY<=9 && aukY>=0 && luzeera>0) {
			for(int i=aukX-1;i<=aukX+1;i++) {
				for(int j=aukY-1;j<=aukY+1;j++) {
					if((i*10+j <= 99) && (i*10+j >= 00)) {
						if(!(i<0 || i >9 || j<0 || j>9  )) {
							if (zelaia[i][j].getEgoera() != Egoera.ONTZIA) {
								zelaia[i][j].setEgoera(Egoera.OKUPATUTA);
							}
						}
					}
				}							
			}
			luzeera--;
			if(noran == 1) {
				aukY++;						
			}else if(noran == 2){
				aukX++;
			}else if(noran == 3) {
				aukY--;
			}else {//noran == 4
					aukX--;
			}
		}
		
	}
	
	public void okupatutaUrBihurtu() {
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				if (zelaia[i][j].getEgoera() == Egoera.OKUPATUTA) {
					zelaia[i][j].setEgoera(Egoera.URA);
				}
			}
		}
	}
	
	public void tiroEgin(int x, int y) {
		if (zelaia[x][y].getEgoera() == Egoera.URA || zelaia[x][y].getEgoera() == Egoera.OKUPATUTA) {
			zelaia[x][y].setEgoera(Egoera.URAJOTA);
		}else if (zelaia[x][y].getEgoera() == Egoera.ONTZIA || zelaia[x][y].getEgoera() == Egoera.ONTZIAIKUSITA) {
			zelaia[x][y].setEgoera(Egoera.ONTZIJOTA);
		}		
	}

	public void misilaBota(int x, int y) {
		boolean atera = false;
		int xKlikatuta = x;
		int yKlikatuta = y;	
		if(zelaia[x][y].getEgoera() == Egoera.ONTZIA || zelaia[x][y].getEgoera() == Egoera.ONTZIAIKUSITA) {
			zelaia[x][y].setEgoera(Egoera.ONTZIJOTA);
			if (yKlikatuta<9) { //eskuina
				yKlikatuta++;
				atera = false; 
				while(!((yKlikatuta>9) || (atera))) {
					if(zelaia[x][y].getEgoera() == Egoera.ONTZIA|| zelaia[x][y].getEgoera() == Egoera.ONTZIAIKUSITA){
						zelaia[x][y].setEgoera(Egoera.ONTZIJOTA);
						yKlikatuta++;
					}else {
						atera = true;
					}
				}
			}
			xKlikatuta = x;
			yKlikatuta = y;
			if (!((xKlikatuta>9))) { //behera
				xKlikatuta++;
				atera = false;
				while(!((xKlikatuta>9)||(atera))) {
					if(zelaia[x][y].getEgoera() == Egoera.ONTZIA|| zelaia[x][y].getEgoera() == Egoera.ONTZIAIKUSITA) {
						zelaia[x][y].setEgoera(Egoera.ONTZIJOTA);
						xKlikatuta++;
					}else {
						atera = true;
					}
				}
			}
			xKlikatuta = x;
			yKlikatuta = y;
			if (yKlikatuta>0) { //ezkerra
				atera = false; 
				yKlikatuta--;
				while(!((yKlikatuta<0) || (atera))) {
					if(zelaia[x][y].getEgoera() == Egoera.ONTZIA|| zelaia[x][y].getEgoera() == Egoera.ONTZIAIKUSITA) {
						zelaia[x][y].setEgoera(Egoera.ONTZIJOTA);
						yKlikatuta--;
					}else {
						atera = true;
					}
				}
			}
			xKlikatuta = x;
			yKlikatuta = y;
			if (xKlikatuta>0) { //gora
				atera = false;
				xKlikatuta --;
				while(!((xKlikatuta<0) || (atera))) {
					if(zelaia[x][y].getEgoera() == Egoera.ONTZIA|| zelaia[x][y].getEgoera() == Egoera.ONTZIAIKUSITA) {
						zelaia[x][y].setEgoera(Egoera.ONTZIJOTA);
						xKlikatuta--;
					}else {
						atera = true;
					}
				}
			}
		}
		
	}

	public void radarraErabili(int x, int y) {
        int xKoord = x;
        int yKoord = y;
        for(int i=xKoord-1; i<=xKoord+1; i++) {
            for(int j=yKoord-1; j<=yKoord+1; j++) {
                if(i>=0 && i<10 && j>=0 && j<10) {
                    if(zelaia[i][j].getEgoera() == Egoera.ONTZIJOTA || zelaia[i][j].getEgoera() == Egoera.URAJOTA){
                        if(zelaia[i][j].getEgoera() == Egoera.ONTZIJOTA) {
                            zelaia[i][j].setEgoera(Egoera.ONTZIJOTA);

                        }else{
                            zelaia[i][j].setEgoera(Egoera.URAJOTA);
                        }
                    }else{
                        if(zelaia[i][j].getEgoera() == Egoera.ONTZIA) {
                            zelaia[i][j].setEgoera(Egoera.ONTZIAIKUSITA);
                        }else{
                            zelaia[i][j].setEgoera(Egoera.URAIKUSITA);
                        }
                    }
                }
            }
        }
    }

	public void ezkutuaErabili(int x, int y) {
		boolean atera = false;
		int xKlikatuta = x;
		int yKlikatuta = y;	
		if(zelaia[x][y].getEgoera() == Egoera.ONTZIA) {
			zelaia[x][y].setEgoera(Egoera.EZKUTUA);
			if (yKlikatuta<9) { //eskuina
				yKlikatuta++;
				atera = false; 
				while(!((yKlikatuta>9) || (atera))) {
					if(zelaia[x][y].getEgoera() == Egoera.ONTZIA){
						zelaia[x][y].setEgoera(Egoera.EZKUTUA);
						yKlikatuta++;
					}else {
						atera = true;
					}
				}
			}
			xKlikatuta = x;
			yKlikatuta = y;
			if (!((xKlikatuta>9))) { //behera
				xKlikatuta++;
				atera = false;
				while(!((xKlikatuta>9)||(atera))) {
					if(zelaia[x][y].getEgoera() == Egoera.ONTZIA) {
						zelaia[x][y].setEgoera(Egoera.EZKUTUA);
						xKlikatuta++;
					}else {
						atera = true;
					}
				}
			}
			xKlikatuta = x;
			yKlikatuta = y;
			if (yKlikatuta>0) { //ezkerra
				atera = false; 
				yKlikatuta--;
				while(!((yKlikatuta<0) || (atera))) {
					if(zelaia[x][y].getEgoera() == Egoera.ONTZIA) {
						zelaia[x][y].setEgoera(Egoera.EZKUTUA);
						yKlikatuta--;
					}else {
						atera = true;
					}
				}
			}
			xKlikatuta = x;
			yKlikatuta = y;
			if (xKlikatuta>0) { //gora
				atera = false;
				xKlikatuta --;
				while(!((xKlikatuta<0) || (atera))) {
					if(zelaia[x][y].getEgoera() == Egoera.ONTZIA) {
						zelaia[x][y].setEgoera(Egoera.EZKUTUA);
						xKlikatuta--;
					}else {
						atera = true;
					}
				}
			}
		}
		
		
	}

	public void ontziakKokatuBot() {
		ontziaKokatu(0,0,1,1);//Fragata
		ontziaKokatu(2,1,1,1);//Fragata
		ontziaKokatu(1,3,1,1);//Fragata
		ontziaKokatu(0,6,1,1);//Fragata
		ontziaKokatu(2,6,1,2);//Suntsitzaile
		ontziaKokatu(5,7,2,2);//Suntsitzaile
		ontziaKokatu(9,3,4,2);//Suntsitzaile
		ontziaKokatu(3,3,2,3);//Itsaspeko
		ontziaKokatu(8,6,1,3);//Itsaspeko
		ontziaKokatu(8,1,4,4);//Hegazkin-ontzi
	}

	public boolean gelaxkaLibreDago(int x, int y) {
		if (zelaia[x][y].getEgoera() == Egoera.ONTZIA || zelaia[x][y].getEgoera() == Egoera.OKUPATUTA) {
			return(false);
		}else {
			return(true);
		}
		
	}

	public Egoera egoeraJakin(int x,int y) {
		return(zelaia[x][y].getEgoera());
		
	}

	public void setEgoera(int x,int y, Egoera egoera) { 
		zelaia[x][y].setEgoera(egoera);
	}

	public void matrizeaSortu() {
		for(int l = 0;l<10;l++) {
			for(int z=0;z<10;z++) {
				zelaia[l][z].setEgoera(Egoera.URA);
			}
		}
		setChanged();
		notifyObservers("ura");
		
	}
	
	public Gelaxka getGelaxka(int x, int y) {
		return zelaia[x][y];
	}
}



package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import modelo.Egoera;

public class Zelaia extends JPanel{

	public static void matrizeaSortu(Zelaia pPanela, Color pColor) {
		for(int l = 0;l<10;l++) {
			for(int z=0;z<10;z++) {
				pPanela.add(new GelaxkaBista(l, z, pColor, Egoera.URA));
			}
		}
		
	}
	
	
}

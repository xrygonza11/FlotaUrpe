package modelo;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;


public class TimerKudeatu extends Observable {
	private static TimerKudeatu nTimerKudeatu = new TimerKudeatu();
	private Timer timer = null;
	private TimerKudeatu() {
		TimerTask timerTask = new TimerTask() {			
			@Override
			public void run() {
				setChanged();
				notifyObservers(5);	 			
			}
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(timerTask,0,1000);
	}

	public static TimerKudeatu getKudeatzailea() {
		return nTimerKudeatu;
	}
	
}

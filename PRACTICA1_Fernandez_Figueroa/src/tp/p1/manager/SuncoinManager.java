package tp.p1.manager;

import tp.p1.object.Sunflower;

public class SuncoinManager {
	private int numCoins;
	
	public SuncoinManager() {
		numCoins=50;
	}
	
	public void anyadir() {
		
		numCoins +=  Sunflower.SOLES_CICLO;
	}
	
	public boolean tener(int soles) {//comprueba si tenemos monedas suficientes para gastar
		boolean coins = true;
		if(soles>numCoins) {
			coins=false;
		}
		return coins;
	}
	public void gastar(int cantidad) {
		numCoins -= cantidad;
	}
	
	public String toString() {
		return Integer.toString(numCoins);
	}
}

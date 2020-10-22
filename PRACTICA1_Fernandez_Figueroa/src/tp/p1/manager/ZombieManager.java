package tp.p1.manager;
import java.util.Random;

import tp.p1.auxiliar.Level;

public class ZombieManager {
	private int number;
	private Random random;
	private Level level;
	
	public ZombieManager(Level level, Random random) {
		this.number = level.getZom();
		this.random = random;
		this.level = level;
		
	}
	
	public boolean isZombieAdded() {
		boolean yes = false;
		
		if(number>0) {
			
			int[] array = {1, 2, 2, 3, 3, 3, 0, 0, 0, 0};
			int resultado = array[random.nextInt(array.length)];
			if(resultado == level.getFreq() * 10){
				yes = true;
			}
			else {
				yes = false;
			}
		}
		return yes;		
	}
	
	public int zombiesRestantes() {
		return number;
	}
	
	public void anyadirZombie() {
		number--;
	}

	
	public String toString () {
		return Integer.toString(number);
	}
}

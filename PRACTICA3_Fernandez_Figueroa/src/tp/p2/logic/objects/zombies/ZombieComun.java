package tp.p2.logic.objects.zombies;

import tp.p2.logic.objects.Zombie;

public class ZombieComun extends Zombie {
	public static final int VELOCIDAD = 1;
	public static final int CICLOS = 2;
	public static final int RESISTENCIA = 5;
	public static final int DANYO = 1;
	public static final String NAME = "Zombie";
	public static final String SHORT_NAME = "Z";
	
	public ZombieComun() {
		super(RESISTENCIA, DANYO, CICLOS, NAME, SHORT_NAME);
	}
	
	public Zombie clone() {
		ZombieComun comun = new ZombieComun();
		
		comun.setResistance(getResistance());
		comun.setRemaining(getRemaining());
		comun.setGame(game);
		comun.setX(getX());
		comun.setY(getY());
		
		return comun;
	}
	
	public Zombie parse(String word) {
		Zombie zombie = null;
		if(word.equalsIgnoreCase(getName())||word.equalsIgnoreCase(getShort()))
				zombie = this;
		return zombie;
	}
}

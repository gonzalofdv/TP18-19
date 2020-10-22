package tp.p2.logic.objects.zombies;

import tp.p2.logic.objects.Zombie;

public class Deportista extends Zombie{
	public static final int VELOCIDAD = 1;
	public static final int CICLOS = 1;
	public static final int RESISTENCIA = 2 ;
	public static final int DANYO = 1;
	public static final String NAME = "Deportista";
	public static final String SHORT_NAME = "X";
	
	public Deportista() {
		super(RESISTENCIA, DANYO, CICLOS, NAME, SHORT_NAME);
	}
	
	public Zombie clone() {
		Deportista depor = new Deportista();
		
		depor.setResistance(getResistance());
		depor.setRemaining(getRemaining());
		depor.setGame(game);
		depor.setX(getX());
		depor.setY(getY());
		
		return depor;
	}
	
	public Zombie parse(String word) {
		Zombie zombie = null;
		if(word.equalsIgnoreCase(getName())||word.equalsIgnoreCase(getShort()))
				zombie = this;
		return zombie;
	}
}

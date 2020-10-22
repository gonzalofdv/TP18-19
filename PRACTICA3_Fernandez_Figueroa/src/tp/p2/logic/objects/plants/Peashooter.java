package tp.p2.logic.objects.plants;


import tp.p2.logic.objects.Plant;
import tp.p2.logic.objects.Zombie;


public class Peashooter extends Plant{
	
	public static final int DANYO = 1;
	public static final int COSTE = 50;
	public static final int CICLOS = 1;
	public static final int FRECUENCIA = 1;
	public static final int RESISTENCIA = 3;
	public static final String NAME = "Peashooter";
	public static final String SHORT_NAME = "P";
	
	public Peashooter() {
		super(COSTE, FRECUENCIA, CICLOS, DANYO, RESISTENCIA, NAME, SHORT_NAME);
	}
	
	public void update() {

		if(this.getRemaining() == 1) {
		
			attack();
		}
		else {
			this.removeCycles();
		}
		
	}
	
	public void attack() {
		int yZombie = game.encontrarZombie(getX(), getY());
		Zombie zombie = null;
		
		if(yZombie != -1) {
			zombie = game.getZombie(getX(), yZombie);
			zombie.damage(this);
		}
		
	}
	
	public Plant parse(String word) {
		Plant plant = null;
		if(word.equalsIgnoreCase(getName())||word.equalsIgnoreCase(getShort()))
			plant = this;
		return plant;
	}
	
	public Plant clone() {
		Peashooter peashooter = new Peashooter();
		peashooter.setResistance(getResistance());
		peashooter.setRemaining(getRemaining());
		peashooter.setGame(game);
		peashooter.setX(getX());
		peashooter.setY(getY());
		
		return peashooter;
	}
}
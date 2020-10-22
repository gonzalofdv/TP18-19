package tp.p2.logic.objects.plants;

import tp.p2.manager.SuncoinManager;
import tp.p2.logic.objects.Plant;


public class Sunflower extends Plant {
	public final static int COSTE = 20;
	public final static int FRECUENCIA = 10;
	public final static int CICLOS = 2;
	public final static int DANYO = 0;
	public static final int RESISTENCIA = 1;
	public static final String NAME = "Sunflower";
	public static final String SHORT_NAME = "S";
	

	public Sunflower () {
		super(COSTE, FRECUENCIA, CICLOS, DANYO, RESISTENCIA, NAME, SHORT_NAME);
	}
	
	public Plant parse(String word) {
		Plant plant = null;
		if(word.equalsIgnoreCase(getName())||word.equalsIgnoreCase(getShort()))
			plant = this;
		return plant;
	}
	
	public Plant clone() {
		Sunflower sunflower = new Sunflower();
		sunflower.setResistance(getResistance());
		sunflower.setRemaining(getRemaining());
		sunflower.setGame(game);
		sunflower.setX(this.getX());
		sunflower.setY(this.getY());
		return sunflower;
	}
	
	public void update() {

		if(this.getRemaining()== 1) {
			SuncoinManager sunc = game.getSunc();
			sunc.anyadir();
			setRemaining(this.getCycles());
		}
		else 
			removeCycles();	
	}
		
}

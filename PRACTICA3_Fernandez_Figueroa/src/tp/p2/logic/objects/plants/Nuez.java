package tp.p2.logic.objects.plants;

import tp.p2.logic.objects.Plant;

public class Nuez extends Plant {
	
	public static final int DANYO = 0;
	public static final int COSTE = 50;
	public static final int CICLOS = 1;
	public static final int FRECUENCIA = 1;
	public static final int RESISTENCIA = 10;
	public static final String NAME = "Nuez";
	public static final String SHORT_NAME = "N";
	
	public Nuez() {
		super(COSTE, FRECUENCIA, CICLOS, DANYO, RESISTENCIA, NAME, SHORT_NAME);
	}
	
	public void update() {
		//el removeCycles y el setRemaining lo hacemos como algo "simbolico" ya que la nuez solo sirve de muro y no hace nada
		this.removeCycles();
		this.setRemaining(this.getCycles());
	}
	
	public Plant clone() {
		Nuez nuez = new Nuez();
		nuez.setResistance(getResistance());
		nuez.setRemaining(getRemaining());
		nuez.setGame(game);
		nuez.setX(this.getX());
		nuez.setY(this.getY());
		return nuez;
	}
	
	public Plant parse(String word) {
		Plant plant = null;
		if(word.equalsIgnoreCase(getName())||word.equalsIgnoreCase(getShort()))
			plant = this;
		return plant;
	}
}

package tp.p2.logic.objects.zombies;

import tp.p2.logic.objects.Zombie;

public class Caracubo extends Zombie{
	public static final int VELOCIDAD = 1;
	public static final int CICLOS = 4;
	public static final int RESISTENCIA = 8 ;
	public static final int DANYO = 1;
	public static final String NAME = "Caracubo";
	public static final String SHORT_NAME = "W";
	
	public Caracubo() {
		super(RESISTENCIA, DANYO, CICLOS, NAME, SHORT_NAME);
	}
	
	public Zombie clone() {
		Caracubo caraCubo = new Caracubo();
		
		caraCubo.setResistance(getResistance());
		caraCubo.setRemaining(getRemaining());
		return caraCubo;
	}
}

package tp.p2.logic.objects.plants;

import tp.p2.logic.objects.Plant;
import tp.p2.logic.objects.Zombie;

public class Petacereza extends Plant {
	public static final int DANYO = 10;
	public static final int COSTE = 50;
	public static final int CICLOS = 2;
	public static final int FRECUENCIA = 1;
	public static final int RESISTENCIA = 2;
	public static final String NAME = "Petacereza";
	public static final String SHORT_NAME = "C";
	public Petacereza() {
		super(COSTE, FRECUENCIA, CICLOS, DANYO, RESISTENCIA, NAME, SHORT_NAME);
	}
	
	public Plant parse(String word) {
		Plant plant = null;
		if(word.equalsIgnoreCase(getName())||word.equalsIgnoreCase(getShort()))
			plant = this;
		return plant;
	}
	public void update() {
		if(this.getRemaining()==0) {
			this.attack();
			this.setRemaining(this.getCycles());
			game.eliminarPlanta(this.getX(), this.getY());
		}
		else {
			this.removeCycles();
		}
		
	}
	
	public Plant clone() {
		Petacereza petacereza = new Petacereza();
		petacereza.setResistance(getResistance());
		petacereza.setRemaining(getRemaining());
		petacereza.setGame(game);
		petacereza.setX(getX());
		petacereza.setY(getY());
		return petacereza;
	}
	
	public void attack() {
		Zombie zombie = null;
		if(game.casilla(this.getX(), this.getY()+1).getC()!=-1) {
			zombie = game.getZombie(this.getX(), this.getY()+1);
			zombie.damage(this);
		}
		if(game.casilla(this.getX(), this.getY()-1).getC()!=-1) {
			zombie = game.getZombie(this.getX(), this.getY()-1);
			zombie.damage(this);
		}
		if(game.casilla(this.getX()-1, this.getY()).getC()!=-1) {
			zombie = game.getZombie(this.getX()-1, this.getY());
			zombie.damage(this);
		}
		if(game.casilla(this.getX()+1, this.getY()).getC()!=-1) {
			zombie = game.getZombie(this.getX()+1, this.getY());
			zombie.damage(this);
		}
		if(game.casilla(this.getX()+1, this.getY()+1).getC()!=-1) {
			zombie = game.getZombie(this.getX()+1, this.getY()+1);
			zombie.damage(this);
		}
		if(game.casilla(this.getX()-1, this.getY()+1).getC()!=-1) {
			zombie = game.getZombie(this.getX()-1, this.getY()+1);
			zombie.damage(this);
		}
		if(game.casilla(this.getX()+1, this.getY()-1).getC()!=-1){
			zombie = game.getZombie(this.getX()+1, this.getY()-1);
			zombie.damage(this);
		}
		if(game.casilla(this.getX()-1, this.getY()-1).getC()!=-1) {
			zombie = game.getZombie(this.getX()-1, this.getY()-1);
			zombie.damage(this);
		}
		
	}
}

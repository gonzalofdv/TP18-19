package tp.p2.logic.objects;

import tp.p2.auxiliar.Posicion;

public abstract class Zombie extends GameObject{
	
	
	public Zombie (int res, int dam, int cy, String nam, String shor) {

		super(res,dam,cy,nam,shor);
	}
	
	public abstract Zombie parse(String command);
	
	public abstract Zombie clone();
	
	//causa daño al Zombie y si se le acaban los puntos de vida, lo elimina del tablero
	public void damage (GameObject object) {
		int nuevaResistencia = getResistance() - object.getDamage();
		setResistance(nuevaResistencia);
		
		if(nuevaResistencia <= 0) {
			game.eliminarZombie(this.getX(), this.getY());
		}
	}
	
	public void update() {
		boolean act= false;
		if(!game.zombieWin()) {
			if(getY()==0) {
				act = true;
				game.setZombieWin(act);
			}
			else 
				attack();
		if(this.getRemaining()== 1) {
			if(game.movimiento(this)) {
				setY(getY() - 1);
				if (getY() == 0) {
					act = true;
					game.setZombieWin(act);
				}
				else {
					attack();
				}
			}
			this.setRemaining(this.getCycles());
		}
		else
			this.removeCycles();				
		}
	}

	public void attack() {

		Posicion posicionPlant = game.ataqueZombiePlant(this);
		Plant plant = null;
		
		if(posicionPlant.getC() != -1) {
			plant = game.getPlant(posicionPlant.getF(), posicionPlant.getC());
			plant.damage(this);
		}
	}
}

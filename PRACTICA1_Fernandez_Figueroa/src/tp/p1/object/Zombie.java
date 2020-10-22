package tp.p1.object;

import tp.p1.logic.Game;

public class Zombie {
	
	
	public static final int DANYO = 1;
	public static final int VELOCIDAD = 1;
	public static final int CICLOS = 2;
	
	private int posx;
	private int posy;
	private int vida;
	private int contadorCiclos;
	private Game game;
	
	public Zombie (int x, int y, Game game) {
		this.posx=x;
		this.posy=y;
		this.game=game;
		this.vida=5;
		this.contadorCiclos=0;
		
	}
	
	public int getX() {
		return this.posx;
	}
	
	public int getY() {
		return this.posy;
	}
	
	public Game getGame() {
		return this.game;
	}
	
	//causa daño al Zombie y si se le acaban los puntos de vida, lo elimina del tablero
	public void death () {
		vida = vida - Peashooter.DANYO;
		 if (vida <= 0) {
			 game.eliminarDeTablero("Zombie", posx, posy);
		 }
	}
	
	public boolean update(){
		boolean act= false;
		if(!game.zombieWin()) {
			if(posy==0) {
				act = true;
				game.setZombieWin(act);
			}
			else 
				game.zombieDamage(this);
				if(contadorCiclos % CICLOS == 0 && contadorCiclos != 0) {
					if(game.movimiento(this)) {
						posy--;
						if (posy == 0) {
							act = true;
							game.setZombieWin(act);
						}
						else 
							game.zombieDamage(this);
					}
				}
		}
		masCiclos();
		return act;
	}

	public void masCiclos() {
		this.contadorCiclos++;
	}
	

	public int getVidaActual() {
		return vida;
	}	
	
	public String toString() {
		return "Z [" + Integer.toString(vida) + "]";
	}
}

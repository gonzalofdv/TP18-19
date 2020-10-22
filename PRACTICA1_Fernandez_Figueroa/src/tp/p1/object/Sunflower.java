package tp.p1.object;

import tp.p1.logic.Game;
import tp.p1.manager.SuncoinManager;

public class Sunflower {
	public final static int COSTE = 20;
	public final static int SOLES_CICLO = 10;
	public final static int CICLOS = 2;
	public final static int DANYO = 0;
	
	private int posx;
	private int posy;
	private int vida ;
	private int contadorCiclos ;
	private Game game;
	
	public Sunflower (int x, int y, Game game) {
		this.posx=x;
		this.posy=y;
		this.game = game;
		this.vida = 1;
		this.contadorCiclos = 0;
		
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
	
	public int getCont() {
		return this.contadorCiclos;
	}
	
	//causa daño al Sunflower y si se le acaban los puntos de vida, lo elimina del tablero
	public void death () {
		vida = vida - Zombie.DANYO;
		 if (vida <= 0) {
			 game.eliminarDeTablero("Sunflower", posx, posy);
		 }
	}
	
	public boolean update(){
		boolean act= false;
		if(contadorCiclos % CICLOS == 0 && contadorCiclos != 0) {
			act = true;
			SuncoinManager sunc = game.getSunc();
			sunc.anyadir();
		}
		contadorCiclos++;
		return act;
		
		}
	
	public int getVidaActual() {
		return vida;
	}
	
	public String toString() {
		return "S [" + Integer.toString(vida) + "]";
	}
}

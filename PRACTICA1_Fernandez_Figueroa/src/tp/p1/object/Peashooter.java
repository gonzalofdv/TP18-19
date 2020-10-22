package tp.p1.object;

import tp.p1.logic.Game;

public class Peashooter {
	
	public static final int DANYO = 1;
	public static final int COSTE = 50;
	public static final int CICLOS = 1;
	
	private int posx;
	private int posy;
	private int vida;
	private int contadorCiclos;
	private Game game;
	
	public Peashooter (int x, int y, Game game ) {
		this.posx=x;
		this.posy=y;
		this.game=game;
		this.vida=3;
		this.contadorCiclos=0;
	}
	
	public int getX() {
		return posx;
	}
	
	public int getY() {
		return posy;
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public boolean update(){
		//actua siempre porque ciclos = 1, aunque se puede hacer de otra forma,
		//lo dejamos creado por si en el futuro
		//queremos cambiar la frecuencia con la que actua
		boolean act= false;
		if(contadorCiclos % CICLOS == 0) {
			act = true;
			game.ataquePeashooter(posx, posy);
			
		}
		contadorCiclos++;
		
		return act;
	}
	
	//causa daño al Peashooter y si se le acaban los puntos de vida, lo elimina del tablero
	public void death () {
		vida = vida - Zombie.DANYO;
		 if (vida <= 0) {
			 game.eliminarDeTablero("Peashooter", posx, posy);
		 }
	}
	
	public int getVidaActual() {
		return vida;
	}
	
	public String toString() {
		return "P [" + Integer.toString(vida) + "]";
	}
}




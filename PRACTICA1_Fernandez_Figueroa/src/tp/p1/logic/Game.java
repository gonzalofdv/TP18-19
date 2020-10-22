package tp.p1.logic;

import java.util.Random;

import tp.p1.auxiliar.Level;
import tp.p1.auxiliar.Posicion;
import tp.p1.list.PeashooterList;
import tp.p1.list.SunflowerList;
import tp.p1.list.ZombieList;
import tp.p1.manager.SuncoinManager;
import tp.p1.manager.ZombieManager;
import tp.p1.object.Peashooter;
import tp.p1.object.Sunflower;
import tp.p1.object.Zombie;


public class Game {
	public final static int FILAS = 4;
	public final static int COLUMNAS = 8;
	
	private ZombieList zombieList;
	private PeashooterList peashooterList;
	private SunflowerList sunflowerList;
	private Random rand;
	private int ciclos;
	private SuncoinManager numcoins;
	private ZombieManager zombieManager;
	private boolean fin;
	private boolean ganaJugador;
	private boolean ganaZombie;
	Level level;
	private int semilla;
	private boolean dentro;
	private boolean posVacia;
	private boolean haySuncoins;

	public Game (Level level, int semilla) {
		this.level= level;
		rand = new Random();
		rand.setSeed(semilla);
		this.semilla = semilla;
		ciclos = 0;
		sunflowerList = new SunflowerList();
		peashooterList = new PeashooterList();
		zombieList = new ZombieList();
		zombieManager = new ZombieManager(level, rand);
		numcoins = new SuncoinManager();
	
		this.fin = false;
		this.ganaJugador = false;
		this.ganaZombie = false;
		
	}
	
	public void update() {			
		
		//Actualizar sunflowers
		sunflowerList.update();
				 	
		//Actualizar peashooters
		peashooterList.update();
	
		//Actualizar zombies
		zombieList.update();
		
		ganaJugador = (zombieList.getLength() == 0 && zombieManager.zombiesRestantes() == 0);
		
		fin = ganaZombie || ganaJugador;
	}
	
	public void ataquePeashooter(int x , int y) { //nos devuelve la posicion donde se encuentra el zombie al que atacaremos
		int yZombie = encontrarZombie(x, y);
		boolean encontrado = false;
		
		
		if(yZombie != -1) {
			encontrado = true;
			y = yZombie;
		}
		if (encontrado)
			zombieList.death(x, y);
	}
	
	public int encontrarZombie(int x, int y) { //dadas unas coordenadas donde se encuentra un peashooter, busca un zombie en esa fila
		boolean encontrado = false;
		int coord = -1;
		while (y < COLUMNAS-1 && !encontrado) {
			if(zombieList.buscar(x, y+1)) {
				encontrado = true;
				coord = y+1;
			}
			else
				++y;
		}
		return coord;
	}
	
	public Posicion ataqueZombiePeashooter(Zombie zombie) {
		int x = -1;
				
		int y = -1;
		int fil = zombie.getX();
		int col = zombie.getY()-1;	
		
		if(peashooterList.buscar(fil, col)) {
			x = fil;
			y = col;
		}
		
		Posicion devolver;
		devolver = new Posicion (x,y);
		
		return devolver;
	}
	
	public Posicion ataqueZombieSunflower(Zombie zombie) {
		int x = -1;
		int y = -1;
		int fil = zombie.getX();
		int col = zombie.getY()-1;
		
		if(sunflowerList.buscar(fil, col)) {
			x = fil;
			y = col;
		}
		
		Posicion devolver ;
		devolver = new Posicion (x,y);
		return devolver;
	}
	
	public boolean anyadirSunflower(int x, int y) {
		boolean anyadido = false;
		haySuncoins = numcoins.tener(Sunflower.COSTE);
		posVacia = esPosicionVacia(x, y);
		dentro = dentroTablero(x, y);
		if (dentro) {
		
			if (posVacia && haySuncoins) {
							
				sunflowerList.insert(new Sunflower(x, y, this)); //con este this, se refiere a game (el constructor de sunflower tiene parametros x, y, game)
				numcoins.gastar(Sunflower.COSTE);
				
				anyadido = true;
			}
		}
		
		return anyadido;
	}
	
	public boolean anyadirPeashooter(int x, int y) {
		boolean anyadido = false;
		haySuncoins = numcoins.tener(Peashooter.COSTE);
		posVacia = esPosicionVacia(x, y);
		dentro = dentroTablero(x, y);
		if (dentro) {
		
			if (posVacia && haySuncoins) {
				
				peashooterList.insert(new Peashooter(x, y, this)); //con este this, se refiere a game (el constructor de sunflower tiene parametros x, y, game)
				
				numcoins.gastar(Peashooter.COSTE);
				
				anyadido = true;
			}
		}
		
		return anyadido;
	}
	
	public boolean esPosicionVacia(int x, int y) {
		//nos da igual recibir por ejemplo S[3], porque lo que nos interesa
		//es saber si nos envia " "
		String elemento = sunflowerList.toString(x, y);
		if(elemento == " ") {
			elemento = peashooterList.toString(x, y);
			if (elemento == " ") {
				elemento = zombieList.toString(x, y);
			}
		}
		
		return elemento == " ";
	}
	
	public boolean dentroTablero(int x, int y) {
		return (x >= 0 && x < FILAS && y >= 0 && y < COLUMNAS);
	}
	
	public boolean anyadirZombie(int x, int y) { //devuelve true si ha podido añadir un zombie en la posicion que corresponde a los valores que llegan en el argumento
		boolean anyadido = false;
		boolean posVacia = esPosicionVacia(x, y);
		
		if(posVacia) {
			zombieList.insert(new Zombie(x, y, this));
			
			zombieManager.anyadirZombie();
			anyadido = true;
		}
		return anyadido;
	}
	
	public void eliminarDeTablero(String elemento, int x, int y) {
		//elemento contendrá "Peashooter, Sunflower o Zombie"
		
		if (elemento == "Peashooter") {
			peashooterList.eliminate(x, y);
		}
		else if (elemento == "Sunflower") {
			sunflowerList.eliminate(x, y);
		}
		else if (elemento == "Zombie") {
			zombieList.eliminate(x, y);
		}
	}
	
	public boolean movimiento(Zombie zombie) {
		boolean movido = false;
		int x = zombie.getX();
		int y = zombie.getY()-1;
		boolean posVacia = esPosicionVacia(x, y);
		
		if (posVacia) {
			movido = true;
		}
		
		return movido;
	}
	
	public void computerAction() {
		int x;
		
		if (this.zombieManager.isZombieAdded()) {
			x = rand.nextInt(FILAS); //genera un numero aleatorio desde 0 hasta FILAS (no incluido) para ver donde aparecerá el nuevo Zombie
			
			boolean posVacia = esPosicionVacia(x, COLUMNAS-1);
			
			if(posVacia) {
				anyadirZombie(x,COLUMNAS-1);
				//zombieList.ciclosZombie(zombieList.getZombie(x,COLUMNAS-1));
				zombieList.ciclosZombie(x, COLUMNAS-1);
			}
		}
	}
	
	public void sumaCiclo() {
		ciclos++;
	}
	public void reset() {
		reiniciarListas();
		reiniciarManagers();
		ciclos = 0;
		fin = false;
		
	}
	
	//Las siguientes funciones estan pensadas para aprovecharlas en futuras practicas donde tengamos
	//que añadir otras listas y managers de otros elementos del juego adicionales.
	
	public void reiniciarListas() {
		peashooterList = new PeashooterList();
		sunflowerList = new SunflowerList();
		zombieList = new ZombieList();
	}
	
	public void reiniciarManagers() {
		numcoins = new SuncoinManager();
		zombieManager = new ZombieManager(level, rand);
	}

	public boolean esFinalJuego() {
		return fin;
	}
	
	public boolean playerWin() {
		return ganaJugador;
	}
	
	public boolean zombieWin() {
		return ganaZombie;
	}
	public void setZombieWin(boolean win) {
		ganaZombie = win;
	}
	
	public String recibirElementoYVida(int x, int y) { //dada una coordenada, nos devuelve un string con la inicial del objeto y su vida en el momento
		String letra = " ";
		
		letra = sunflowerList.toString(x, y);
		if (letra == " ") {
			letra = peashooterList.toString(x, y);
			if (letra == " ") {
				letra = zombieList.toString(x, y);
			}
		}
		
		return letra;
	}
	
	public void zombieDamage(Zombie zombie) { //dado un zombie, comprueba si puede atacar y en su caso, ataca al elemento correspondiente
		
		Posicion posicionSun = ataqueZombieSunflower(zombie);
		Posicion posicionPea = ataqueZombiePeashooter(zombie);
		
		if (posicionPea.getC()!= -1) {
			
			peashooterList.death(posicionPea.getF(),posicionPea.getC());
		}
		else if (posicionSun.getC() != -1) {
			sunflowerList.death(posicionSun.getF(),posicionSun.getC());
		}			
	}
	public SuncoinManager getSunc() {
		return numcoins;
	}
	
	public int getSemilla() {
		return semilla;
	}
	
	public int getCiclos() {
		return ciclos;
	}
	
	public String getSuncoins() {
		return numcoins.toString();
	}
	
	public String getZombiesRestantes() {
		return zombieManager.toString();
	}
	
	public boolean getDentroTablero() {
		return dentro;
	}
	
	public boolean getPosVacia() {
		return posVacia;
	}
	
	public boolean getHaySuncoins() {
		return haySuncoins;
	}
	
}

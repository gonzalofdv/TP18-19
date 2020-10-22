package tp.p2.logic;

import java.util.Random;

import tp.p2.auxiliar.Posicion;
import tp.p2.lists.ZombieList;
import tp.p2.manager.SuncoinManager;
import tp.p2.manager.ZombieManager;
import tp.p2.lists.PlantList;
import tp.p2.logic.objects.Plant;
import tp.p2.logic.objects.Zombie;
import tp.p2.logic.objects.zombies.*;


public class Game {
	public final static int FILAS = 4;
	public final static int COLUMNAS = 8;
	
	private ZombieList zombieList;
	private PlantList plantList;
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
		plantList = new PlantList();
		zombieList = new ZombieList();
		zombieManager = new ZombieManager(level, rand);
		numcoins = new SuncoinManager();
	
		this.fin = false;
		this.ganaJugador = false;
		this.ganaZombie = false;
		
		this.dentro = true;
		this.posVacia = true;
		this.haySuncoins = true;
		
	}
	
	public void update() {			

		//Actualizar plantas
		plantList.update();
	
		//Actualizar zombies
		zombieList.update();
		
		ganaJugador = (zombieList.getLength() == 0 && zombieManager.zombiesRestantes() == 0);
		
		fin = ganaZombie || ganaJugador;
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
	public Posicion casilla (int x, int y) {
		Posicion posicion;
		int j = -1;
		int k = -1;
		
		if (dentroTablero(x,y)) {
			if(zombieList.buscar(x, y)) {
				j = x;
				k = y;
			}
		}
		
		posicion = new Posicion(j, k);
		return posicion;
			
	}
	
	public Zombie getZombie(int x, int y) {
		Zombie zombie = zombieList.getZombie(x, y);
		
		return zombie;
	}
	
	public Posicion ataqueZombiePlant(Zombie zombie) {
		int x = -1;
				
		int y = -1;
		int fil = zombie.getX();
		int col = zombie.getY()-1;	
		
		if(plantList.buscar(fil, col)) {
			x = fil;
			y = col;
		}
		
		Posicion devolver;
		devolver = new Posicion (x,y);
		
		return devolver;
	}
	
	public Plant getPlant(int x, int y) {
		Plant plant = plantList.getPlant(x, y);
		
		return plant;
	}
	
	
	public boolean anyadirPlant(Plant plant,int x, int y) {
		boolean anyadido = false;
		haySuncoins = numcoins.tener(plant.getCost());
		posVacia = esPosicionVacia(x,y);
		dentro = dentroTablero(x,y);
		if (dentro) {
			if (posVacia && haySuncoins) {
				plantList.insert(plant);
				numcoins.gastar(plant.getCost());
				anyadido = true;
			}
		}
		return anyadido;
		
	}
	
	public boolean anyadirZombie(Zombie zombie,int x, int y) { //devuelve true si ha podido añadir un zombie en la posicion que corresponde a los valores que llegan en el argumento
		boolean anyadido = false;
		posVacia = esPosicionVacia(x, y);
		
		if(posVacia) {
			zombie.setGame(this);
			zombie.setX(x);
			zombie.setY(y);
			zombieList.insert(zombie);
			
			zombieManager.anyadirZombie();
			anyadido = true;
		}
		return anyadido;
	}
	
	
	public boolean esPosicionVacia(int x, int y) {
		//nos da igual recibir por ejemplo S[3], porque lo que nos interesa
		//es saber si nos envia " "
		String elemento = plantList.toString(x, y);
		if(elemento == " ") {
			elemento = zombieList.toString(x, y);
		
		}
		
		return elemento == " ";
	}
	
	public boolean dentroTablero(int x, int y) {
		return (x >= 0 && x < FILAS && y >= 0 && y < COLUMNAS-1);
	}
	
	public void eliminarPlanta(int x, int y) {

		plantList.eliminate(x,y);
	}
	public void eliminarZombie(int x, int y) {
		
		zombieList.eliminate(x, y);	
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
		
		Zombie zombie = null;
		if(this.zombieManager.isZombieAdded())
		{
			zombie = tipoZombie();
		
			x = rand.nextInt(FILAS);
			anyadirZombie(zombie,x,COLUMNAS-1);
		}
	}
	
	public Zombie tipoZombie() {
		Zombie zombie = null;
		Random random = new Random();
			int resultado = random.nextInt(3);
			
			if(resultado == 0) {
				zombie = new ZombieComun();
			}
			else if (resultado == 1) {
				zombie = new Deportista();
			}
			else if (resultado == 2) {
				zombie = new Caracubo();
			}
		return zombie;
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
		plantList = new PlantList();
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
		
		letra = plantList.toString(x, y);
		if (letra == " ") {
			letra = zombieList.toString(x, y);
		}
		return letra;
	}
	
	public String recibirDebugPlanta(int x) {
		String elemento = plantList.toStringDebug(x);
		return elemento;
	}
	
	public String recibirDebugZombie(int x) {
		String elemento = zombieList.toStringDebug(x);
		return elemento;
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
	
	public void setDentroTablero(boolean yesno) {
		dentro = yesno;
	}
	
	public boolean getPosVacia() {
		return posVacia;
	}
	
	public void setPosVacia(boolean yesno) {
		posVacia = yesno;
	}
	
	public boolean getHaySuncoins() {
		return haySuncoins;
	}
	
	public void setHaySuncoins(boolean yesno) {
		haySuncoins = yesno;
	}
	
	public int getLengthPlants() {
		return plantList.getLength();
	}
	
	public int getLengthZombies() {
		return zombieList.getLength();
	}
	
}

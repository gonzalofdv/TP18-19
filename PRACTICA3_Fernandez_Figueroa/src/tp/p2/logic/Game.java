package tp.p2.logic;

import java.io.*;
import java.util.Random;

import tp.p2.auxiliar.Posicion;
import tp.p2.exceptions.CommandExecuteException;
import tp.p2.exceptions.FileContentsException;
import tp.p2.factory.PlantFactory;
import tp.p2.factory.ZombieFactory;
import tp.p2.lists.ZombieList;
import tp.p2.manager.SuncoinManager;
import tp.p2.manager.ZombieManager;
import tp.p2.lists.PlantList;
import tp.p2.logic.objects.Plant;
import tp.p2.logic.objects.Zombie;


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
	private String modePintado;
	private boolean changed;

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
		
		this.modePintado = "release";
		this.changed = false;
		
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
	
	
	public boolean anyadirPlant(Plant plant,int x, int y) throws CommandExecuteException {
		boolean anyadido = false;
		haySuncoins = numcoins.tener(plant.getCost());
		if(haySuncoins) {
			plantList.insert(plant);
			numcoins.gastar(plant.getCost());
			anyadido = true;
		}
		else {
			throw new CommandExecuteException("Failed to add " + plant.getName() + ": not enough suncoins to buy it");
		}
		return anyadido;
	}
	
	public boolean anyadirZombie(Zombie zombie,int x, int y) { //devuelve true si ha podido aÃ±adir un zombie en la posicion que corresponde a los valores que llegan en el argumento
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
		String[] zombieAvailables = {"Caracubo", "Deportista", "Zombie"};
		Random random = new Random();
		String resultado = zombieAvailables[random.nextInt(zombieAvailables.length)];
		zombie = ZombieFactory.getZombie(resultado);
		
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
	//que aÃ±adir otras listas y managers de otros elementos del juego adicionales.
	
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
	
	public void setEsFinalJuego(boolean end) {
		fin = end;
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
	
	public String store() {
		StringBuilder guardado = new StringBuilder();
		
		guardado.append("cycle: " + ciclos + System.lineSeparator());
		guardado.append("sunCoins: " + getSuncoins() + System.lineSeparator());
		guardado.append("level: " + level.name() + System.lineSeparator());
		guardado.append("remZombies: " + zombieManager.toString() + System.lineSeparator());
		guardado.append("plantList: " + plantList.externalise() + System.lineSeparator());
		guardado.append("zombieList: " + zombieList.externalise() + System.lineSeparator());
		
		return guardado.toString();
	}
	
	public void load(BufferedReader br) throws FileContentsException, IOException {
		Game gameCopy = clone();//por si falla a la hora de cagrar el fichero, hacemos una copia y si hace falta restaurar a partir 
		//de esta (restoreGame)
		
		try {
			this.ciclos = Integer.parseInt(loadLine(br, "cycle", false)[0]);
			this.numcoins.setSuncoins(Integer.parseInt(loadLine(br, "sunCoins", false)[0]));
			this.level = Level.define(loadLine(br, "level", false)[0]);
			this.zombieManager.setZombiesRestantes(Integer.parseInt(loadLine(br, "remZombies", false)[0]));
			
			String[] listaPlantasFichero = loadLine(br, "plantList", true); //array que recoge la info de todas las plantas de la lista
			PlantList newPlantList = new PlantList(); //nueva lista para guardar los datos del fichero
			
			for(int i = 0; i < listaPlantasFichero.length; ++i) {
				String[] datosObject = listaPlantasFichero[i].split(":");//guarda en string[] un string cada vez que hay :
				Plant plantAux = PlantFactory.getPlant(datosObject[0]);
				plantAux.setResistance(Integer.parseInt(datosObject[1]));
				int auxX = Integer.parseInt(datosObject[2]);
				int auxY = Integer.parseInt(datosObject[3]);
				if(dentroTablero(auxX, auxY)) { //comprobamos si las coordenadas que aparecen en el fichero son validas
					plantAux.setX(auxX);
					plantAux.setY(auxY);
				}
				else { // si no lo son, lanzamos una excepcion
					throw new FileContentsException();
				}
				plantAux.setGame(this);
				plantAux.setRemaining(Integer.parseInt(datosObject[4]));
				newPlantList.insert(plantAux);
			}
			
			plantList = newPlantList;
			
			String[] listaZombiesFichero = loadLine(br, "zombieList", true);
			ZombieList newZombieList = new ZombieList();
			
			for(int i = 0; i < listaZombiesFichero.length; ++i) {
				String[] datosObject = listaZombiesFichero[i].split(":");
				Zombie zombieAux = ZombieFactory.getZombie(datosObject[0]);
				zombieAux.setResistance(Integer.parseInt(datosObject[1]));
				int auxX = Integer.parseInt(datosObject[2]);
				int auxY = Integer.parseInt(datosObject[3]);
				if(dentroTablero(auxX, auxY)) {
					zombieAux.setX(auxX);
					zombieAux.setY(auxY);
				}
				else {
					throw new FileContentsException();
				}
				zombieAux.setGame(this);
				zombieAux.setRemaining(Integer.parseInt(datosObject[4]));
				newZombieList.insert(zombieAux);
			}
			
			zombieList = newZombieList;
			
			//si se produce una excepcion, la capturamos, restauramos la copia de seguridad y la relanzamos
		}catch(FileContentsException ex){
			restoreGame(gameCopy);
			throw new FileContentsException();
		}catch(IOException ex) {
			restoreGame(gameCopy);
			throw new IOException();
		}		
		
	}
	
	public static final String wrongPrefixMsg = "unknown game attribute: ";
	public static final String lineTooLongMsg = "too many words on line commencing: ";
	public static final String lineTooShortMsg = "missing data on line commencing: ";
	
	public String[] loadLine(BufferedReader inStream, String prefix, boolean isList) throws IOException, FileContentsException{

		String line = inStream.readLine().trim();
		
		// absence of the prefix is invalid
		if ( ! line . startsWith(prefix + ":") )
			throw new FileContentsException(wrongPrefixMsg + prefix);
		
		// cut the prefix and the following colon off the line
		// then trim it to get the attribute contents
		String contentString = line. substring(prefix . length()+1).trim();
		String[] words;
		
		// the attribute contents are not empty
		if (!contentString. equals("")) {
			if (!isList ) {
				// split non−list attribute contents into words
				// using 1−or−more−white−spaces as separator
				words = contentString.split ("\\s+");
				
			// a non−list attribute with contents of more than one word is invalid
				if (words.length != 1)
					throw new FileContentsException(lineTooLongMsg + prefix);
			} else
				// split list attribute contents into words
				// using comma+0−or−more−white−spaces as separator
				words = contentString.split (",\\s*");
			
			// the attribute contents are empty
		} else {
			
		// a non−list attribute with empty contents is invalid
			if (! isList )
				throw new FileContentsException(lineTooShortMsg + prefix);
			
			// a list attribute with empty contents is valid;
			// use a zero−length array to store its words
			words = new String[0];
		}
		return words;
	}

	public Game clone() { //hacemos un clone de los elementos necesarios para guardar una copia de seguridad del juego actual
		Game newGame = new Game(this.level, this.semilla);
		
		newGame.ciclos = ciclos;
		newGame.plantList = plantList.clone();
		newGame.zombieList = zombieList.clone();
		newGame.numcoins = numcoins.clone();
		newGame.zombieManager = zombieManager.clone(zombieManager.zombiesRestantes());
		
		return newGame;
	}
	
	public void restoreGame(Game auxiliarGame) {
		this.ciclos = auxiliarGame.ciclos;
		this.plantList = auxiliarGame.plantList;
		this.zombieList = auxiliarGame.zombieList;
		this.numcoins = auxiliarGame.numcoins;
		this.zombieManager = auxiliarGame.zombieManager;
	}
	
	public void setModoPintado(String mode) {
		modePintado = mode;
	}
	
	public String getModoPintado() {
		return modePintado;
	}
	
	public void setPrintButNoUpdate(boolean cambio) {
		changed = cambio;
	}
	
	public boolean getPrintButNoUpdate() {
		return changed;
	}
}

package tp.p2.logic;

import java.util.Scanner;

import tp.p2.factory.PlantFactory;
import tp.p2.commands.Command;
import tp.p2.commands.CommandParser;
import tp.p2.logic.printers.DebugPrinter;
import tp.p2.logic.printers.GamePrinter;
import tp.p2.logic.printers.ReleasePrinter;

public class Controller {
	private Game game;
	private Scanner lectura;
	boolean exit;
	boolean noPrint;
	boolean wrongCommand;
	private String modePrint;
	boolean changed;
	
	public Controller(Game game, String mode) {
		this.game = game;
		this.lectura = new Scanner(System.in);
		this.exit = false;
		this.noPrint = false;
		this.wrongCommand = false;
		this.modePrint = mode;
		this.changed = false;
	}
	
	public void board() {
		if (!noPrint) {
			if (modePrint.equals("release")) {
				GamePrinter release;
				getCycleInfo();
				release = new ReleasePrinter(game, Game.FILAS, Game.COLUMNAS);
				System.out.println(release.printGame(game));
				
			}
			else {
				GamePrinter debug;
				getCycleInfo();
				debug = new DebugPrinter(game, 1, game.getLengthPlants() + game.getLengthZombies());
				System.out.println(debug.printGame(game));
				
			}
		}		
	}
	
	
	public void run() {

		while(!exit && game.esFinalJuego()==false) {
			//UPDATE
			if(!noPrint && !changed) {
				game.update();	
			}
			//restablecemos el valor inicial de changed para que la proxima ejecucion si toca actualizar, lo haga
			changed = false;
			//DIBUJAR TABLERO
			board();
			
			if ( game.esFinalJuego() == false ) {
				//USER COMMAND
				System.out.println("command >");
				String[] words = lectura.nextLine().toLowerCase().trim().split("\\s+");
				Command command = CommandParser.parseCommand(words, this);
				
				if(command != null) {
					command.execute(game, this);
				}
				else {
					System.out.println("Wrong command");
					setNoPrintGameState();
				}
				if(!game.getDentroTablero() || !game.getPosVacia()) {
					game.setPosVacia(true);
					game.setDentroTablero(true);
					System.out.println("Invalid position");
				}
				if(!game.getHaySuncoins()) {
					game.setHaySuncoins(true);
					System.out.println("Not enough suncoins");
				}
				
				//COMPUTER ACTION
				if (!exit && !noPrint && !changed) {
					game.computerAction();
				}
			}
			else {
				System.out.println("Game over");
				if (game.zombieWin() == true) {
					System.out.println("Computer wins");
					
				}
				else if (game.playerWin() == true) {
					System.out.println("Player wins");
				}
			}
		}
	}
	
	public void setNoPrintGameState() {
		this.noPrint = true;
	}
	
	public void setPrintGameState() {
		this.noPrint = false;
	}
	
	public void setExit(boolean entrada) {
		exit = entrada;
	}
	
	public void setChanged(boolean yesno) { //Asignamos un valor a changed, variable que nos informa de si se ha cambiado el modo de pintado
		changed = yesno;
	}
	
	public void getCycleInfo() {
		System.out.println("Random seed used: " + game.getSemilla());
		System.out.println("Number of cycles: " + game.getCiclos());
		System.out.println("Sun coins: " + game.getSuncoins());
		System.out.println("Remaining zombies: " + game.getZombiesRestantes());
	}
	
	public void list() {
		System.out.println(PlantFactory.listOfAvailablePlants());
	}
	
	public void help(String ayuda) {
		System.out.println(ayuda);
	}
	
	public void setWrongCommand(boolean yes_no) {
		wrongCommand = yes_no;
	}
	
	public void setModoPintado(String mode) {
		modePrint = mode;
	}	
}


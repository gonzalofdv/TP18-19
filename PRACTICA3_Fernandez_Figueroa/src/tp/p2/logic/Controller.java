package tp.p2.logic;

import java.util.Scanner;

import tp.p2.commands.Command;
import tp.p2.commands.CommandParser;
import tp.p2.exceptions.CommandExecuteException;
import tp.p2.exceptions.CommandParseException;
import tp.p2.logic.printers.DebugPrinter;
import tp.p2.logic.printers.GamePrinter;
import tp.p2.logic.printers.ReleasePrinter;

public class Controller {
	private Game game;
	private Scanner lectura;
	boolean exit;
	boolean noPrint;
	boolean wrongCommand;
	boolean excepcion;
	
	public Controller(Game game, String mode) {
		this.game = game;
		this.lectura = new Scanner(System.in);
		this.exit = false;
		this.noPrint = false;
		this.wrongCommand = false;
		this.excepcion = false;
		game.setModoPintado(mode);
	}
	
	public void board() {
		if (!noPrint) {
			if (game.getModoPintado().equals("release")) {
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

		while(game.esFinalJuego()==false) {
			//UPDATE
			if(!noPrint && !game.getPrintButNoUpdate() && !excepcion) {
				game.update();	
			}
			
			//restablecemos el valor de excepcion para que tras introducir el nuevo comando, se pueda ejecutar computerAction y update
			excepcion = false;
			//restablecemos el valor del atributo changed de game para que la proxima ejecucion se pueda realizar computerAction y update (si toca)
			game.setPrintButNoUpdate(false);
			//DIBUJAR TABLERO
			board();
			
			if ( game.esFinalJuego() == false ) {
				//USER COMMAND
				System.out.println("command >");
				String[] words = lectura.nextLine().toLowerCase().trim().split("\\s+");
				Command command = null;
				try {
					command = CommandParser.parseCommand(words);
					
					if(command != null) {
						if(command.execute(game)) {
							setPrintGameState();
						}
						else {
							setNoPrintGameState();
						}
					}
					else {
						System.out.println("Wrong command");
						setNoPrintGameState();
					}
					
				}catch(CommandParseException | CommandExecuteException ex){
					command = null;
					System.out.println(ex.getMessage());
					excepcion = true;
				}
				
				//COMPUTER ACTION
				if(game.esFinalJuego()==false && !noPrint && !game.getPrintButNoUpdate() && !excepcion) {
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
	
	public void getCycleInfo() {
		System.out.println("Random seed used: " + game.getSemilla());
		System.out.println("Number of cycles: " + game.getCiclos());
		System.out.println("Sun coins: " + game.getSuncoins());
		System.out.println("Remaining zombies: " + game.getZombiesRestantes());
	}
	
	public void help(String ayuda) {
		System.out.println(ayuda);
	}
	
	public void setWrongCommand(boolean yes_no) {
		wrongCommand = yes_no;
	}

}


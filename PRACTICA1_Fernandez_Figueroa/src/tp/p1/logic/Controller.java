package tp.p1.logic;

import java.util.Scanner;

import tp.p1.object.Peashooter;
import tp.p1.object.Sunflower;

public class Controller {
	private Game game;
	private Scanner lectura;
	
	public Controller(Game game) {
		this.game = game;
		this.lectura = new Scanner(System.in);
	}
	
	public void board() {
		GamePrinter gamePrinter;
		getCycleInfo();
		gamePrinter = new GamePrinter (game, Game.FILAS, Game.COLUMNAS);
		System.out.println(gamePrinter.toString());
		
	}
	
	
	public void run() {
		String action = "";

		while(action != "exit" && game.esFinalJuego()==false) {
			game.update();
			board();
			
			if ( game.esFinalJuego() == false ) {
				action = userCommand();
				if(action != "exit") {
					if(action == "yes") {
						game.computerAction();
						game.sumaCiclo();
					}
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
	
	private String userCommand() {
		String command = "";
		String[] array;
		boolean ok = false;
		String action = "";
		
		while (ok==false) {
			System.out.println("Command > ");
			command = lectura.nextLine();
			command = command.trim(); //elimina espacios en blanco iniciales
			command = command.toLowerCase(); //pasa el string a minusculas
			array = command.split(" "); //separa el comando en elementos para meterlo en el array
			
			if(array[0].equals("add") || array[0].equals("a")) {
				if(array.length == 4) {
					ok = addCommand(array[1],array[2],array[3]); //comprueba si puede añadir un elemento en una posicion
					action = "yes";
				}
				else {
					System.out.println("Invalid parameter number");
					
				}				
			}
			else if (array[0].equals("reset") || array[0].equals("r")) {
				game.reset();
				ok = true;
				action = "yes";
			}
			else if (array[0].equals("list") || array[0].equals("l")) {
				list();
				ok = true;
				action = "no";
			}
			else if (array[0].equals("none") || array[0].equals("n")) {
				ok = true;
				action = "yes";
			}
			else if (array[0].equals("exit") || array[0].equals("e")) {
				ok = true;
				action = "exit";
			}
			else if (array[0].equals("help") || array[0].equals("h")) {
				help();
				ok = true;
				action = "no";
			}
			else if (array[0].equals("")) {
				ok = true;
				action = "yes";
			}
			else {
				System.out.println("Wrong command");
				ok = false;
			}
		}
		return action;
		
	}
	
	public void getCycleInfo() {
		System.out.println("Random seed used: " + game.getSemilla());
		System.out.println("Number of cycles: " + game.getCiclos());
		System.out.println("Sun coins: " + game.getSuncoins());
		System.out.println("Remaining zombies: " + game.getZombiesRestantes());
	}
	
	public void list() {
		System.out.println("");
		System.out.println("[S]unflower: Cost: " + Sunflower.COSTE + " suncoins  Harm: " + Sunflower.DANYO);
		System.out.println("[P]eashooter: Cost: " + Peashooter.COSTE + " suncoins  Harm: " + Peashooter.DANYO);
		System.out.println("");
	}
	
	public void help() {
		System.out.println("");
		System.out.println("Add <plant> <x> <y>: Adds a plant in position x, y.");
		System.out.println("List: Prints the list of available plants.");
		System.out.println("Reset: Starts a new game");
		System.out.println("List: Help: Prints this help message.");
		System.out.println("Exit: Terminates the program.");
		System.out.println("[none]: Skips cycle");
		System.out.println("");
	}
	
	private boolean addCommand (String x, String y, String z) {
		boolean ok = false;
		
		if(x.equals("sunflower")||x.equals("s")) {
			if(game.anyadirSunflower(Integer.parseInt(y), Integer.parseInt(z))==true) { // convierte cadena en entero
				ok = true;
			}
			else {
				if(!game.getDentroTablero())
					System.out.println("Invalid position");
				else if(!game.getPosVacia())
					System.out.println("Invalid position");
				else if(!game.getHaySuncoins())
					System.out.println("Not enough suncoins");
			}
		}
		else if (x.equals("peashooter")||x.equals("p")) {
			if(game.anyadirPeashooter(Integer.parseInt(y),Integer.parseInt(z))==true) {
				ok=true;
			}
			else {
				if(!game.getDentroTablero())
					System.out.println("Invalid position");
				else if(!game.getPosVacia())
					System.out.println("Invalid position");
				else if(!game.getHaySuncoins())
					System.out.println("Not enough suncoins");
			}
		}
		else {
			System.out.println("Wrong command");
		}
		return ok;
	}
	
}


package tp.p2.commands;

import tp.p2.factory.PlantFactory;
import tp.p2.logic.Controller;
import tp.p2.logic.Game;
import tp.p2.logic.objects.Plant;

public class AddCommand extends Command{
	private Plant plant;
	private int x, y;
	public static final String commandInfo = "[A]dd <plant> <x> <y>";
	public static final String helpInfo = "adds a plant in position x, y.";
	
	public AddCommand() {
		super("", commandInfo, helpInfo);
	}
	
	public Command parse(String[] commandWords, Controller controller) {
		Command command = null;
		
		if(commandWords.length == 4) {
			if (commandWords[0].equalsIgnoreCase("add") || commandWords[0].equalsIgnoreCase("a")) {
				plant = PlantFactory.getPlant(commandWords[1]);
				
				if(plant != null) {
					x = Integer.parseInt(commandWords[2]);
					y = Integer.parseInt(commandWords[3]);
					command = this;
				}
			}
		}
		return command;
	}
	
	public void execute(Game game, Controller controller) {

		if(game.esPosicionVacia(x, y)) {
			plant.setX(x);
			plant.setY(y);
		}
		
		plant.setGame(game);
		
		if(game.anyadirPlant(plant, x, y)) {
			game.sumaCiclo(); //sumamos un ciclo al juego
			controller.setPrintGameState(); //se pintara el tablero
		}
		else {
			controller.setNoPrintGameState(); //no se pintara el tablero
			
		}
	}
}

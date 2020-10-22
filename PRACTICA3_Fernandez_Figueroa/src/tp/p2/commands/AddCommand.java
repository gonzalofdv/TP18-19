package tp.p2.commands;

import tp.p2.exceptions.CommandExecuteException;
import tp.p2.exceptions.CommandParseException;
import tp.p2.factory.PlantFactory;
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
	
	public Command parse(String[] commandWords) throws CommandParseException {
		Command command = null;
		
		if (commandWords[0].equalsIgnoreCase("add") || commandWords[0].equalsIgnoreCase("a")) {
			if(commandWords.length == 4) {
				plant = PlantFactory.getPlant(commandWords[1]);
				
				if(plant != null) {
					try {
						x = Integer.parseInt(commandWords[2]);
						y = Integer.parseInt(commandWords[3]);
						command = this;
					}catch (NumberFormatException Ex){
						throw new CommandParseException("Invalid argument for add command, number expected: " + commandInfo);
					}
				}
				else {
					throw new CommandParseException("Unknown plant name: " + commandWords[1]);
				}
			}
			else {
				throw new CommandParseException("Incorrect number of arguments for add command: " + commandInfo);
			}
		}
		return command;
	}
	
	public boolean execute(Game game) throws CommandExecuteException {
		boolean printer = true;
			if(game.esPosicionVacia(x, y)) {
				if(game.dentroTablero(x, y)) {
					plant.setX(x);
					plant.setY(y);
				}
				else {
					throw new CommandExecuteException("Failed to add " + plant.getName() + ": (" + x + ", " + y +") is an invalid position");
				}
			}
			else {
				throw new CommandExecuteException("Failed to add " + plant.getName() + ": position (0, 0) is already occupied");
			}
		
		plant.setGame(game);
		
		if(game.anyadirPlant(plant, x, y)) {
			game.sumaCiclo(); //sumamos un ciclo al juego
			printer = true;
		}
		else {
			printer = false;
			
		}
		return printer;
	}
}

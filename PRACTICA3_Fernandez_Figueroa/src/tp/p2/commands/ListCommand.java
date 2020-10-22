package tp.p2.commands;

import tp.p2.factory.PlantFactory;
import tp.p2.logic.Game;

public class ListCommand extends NoParamsCommand {
	
	public static final String commandText = "list";
	public static final String commandInfo = "[L]ist";
	public static final String helpInfo = "Prints the list of available plants.";

	public ListCommand() {
		super(commandText, commandInfo, helpInfo);
	}
	
	public boolean execute(Game game) {
		boolean printer = false;
			System.out.println(PlantFactory.listOfAvailablePlants());
		return printer;
	}
}

package tp.p2.commands;

import tp.p2.logic.Controller;
import tp.p2.logic.Game;

public class ListCommand extends NoParamsCommand {
	
	public static final String commandText = "list";
	public static final String commandInfo = "[L]ist";
	public static final String helpInfo = "Prints the list of available plants.";

	public ListCommand() {
		super(commandText, commandInfo, helpInfo);
	}
	
	public void execute(Game game, Controller controller) {
		controller.list();
		controller.setNoPrintGameState();
	}
}

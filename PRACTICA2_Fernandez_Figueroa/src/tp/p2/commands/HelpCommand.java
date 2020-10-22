package tp.p2.commands;

import tp.p2.logic.Controller;
import tp.p2.logic.Game;

public class HelpCommand extends NoParamsCommand {
	public static final String commandText = "help";
	public static final String commandInfo = "[H]elp";
	public static final String helpInfo = "Print this help message.";
	
	public HelpCommand() {
		super(commandText, commandInfo, helpInfo);
	}
	
	public void execute(Game game, Controller controller) {
		String ayuda = CommandParser.commandHelp();
		controller.help(ayuda);
		controller.setNoPrintGameState();
	}
}

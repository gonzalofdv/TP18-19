package tp.p2.commands;

import tp.p2.logic.Controller;
import tp.p2.logic.Game;

public class ExitCommand extends NoParamsCommand {
	
	public static final String commandText = "exit";
	public static final String commandInfo = "[E]xit";
	public static final String helpInfo = "Terminates the program.";

	public ExitCommand() {
		super(commandText, commandInfo, helpInfo);
	}
	
	public void execute(Game game, Controller controller) {
		controller.setExit(true);
		controller.setNoPrintGameState();
	}
}

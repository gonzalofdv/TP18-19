package tp.p2.commands;

import tp.p2.logic.Controller;
import tp.p2.logic.Game;

public class ResetCommand extends NoParamsCommand {

	public static final String commandText = "reset";
	public static final String commandInfo = "[R]eset";
	public static final String helpInfo = "Starts a new game.";

	public ResetCommand() {
		super(commandText, commandInfo, helpInfo);
	}
	
	public void execute(Game game, Controller controller) {
		game.reset();
		controller.setPrintGameState();
	}
}

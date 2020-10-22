package tp.p2.commands;

import tp.p2.logic.Controller;
import tp.p2.logic.Game;

public class NoneCommand extends NoParamsCommand {
	public static final String commandText = "none";
	public static final String commandInfo = "[none]";
	public static final String helpInfo = "Skips cycle.";
	
	public NoneCommand() {
		super(commandText, commandInfo, helpInfo);
	}
	
	public void execute(Game game, Controller controller) {
		controller.setPrintGameState();
		game.sumaCiclo();
	}
}

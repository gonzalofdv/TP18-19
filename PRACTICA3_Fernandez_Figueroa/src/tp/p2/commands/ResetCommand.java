package tp.p2.commands;

import tp.p2.exceptions.CommandExecuteException;
import tp.p2.logic.Game;

public class ResetCommand extends NoParamsCommand {

	public static final String commandText = "reset";
	public static final String commandInfo = "[R]eset";
	public static final String helpInfo = "Starts a new game.";

	public ResetCommand() {
		super(commandText, commandInfo, helpInfo);
	}
	
	public boolean execute(Game game) throws CommandExecuteException {
		boolean printer = true;
		game.reset();
		return printer;
	}
}

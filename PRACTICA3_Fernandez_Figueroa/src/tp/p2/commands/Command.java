package tp.p2.commands;

import tp.p2.exceptions.CommandExecuteException;
import tp.p2.exceptions.CommandParseException;
import tp.p2.logic.Game;

public abstract class Command {

	private String helpText;
	private String helpInfo;
	protected final String commandName;
	
	public Command(String commandText, String commandTextMsg, String helpTextMsg) {
		helpText = commandTextMsg;
		helpInfo = helpTextMsg;
		commandName = commandText;
	}
	
	//Some commands may generate an error in execute or parse methods.
	//In the absence of exceptions, they must the tell the controller not to print the boards
	public abstract boolean execute(Game game) throws CommandExecuteException;
	public abstract Command parse(String[] commandWords) throws CommandParseException;
	
	public String helpText() {return " " + helpText + ": " + helpInfo;}
}

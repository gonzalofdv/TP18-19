package tp.p2.commands;

import tp.p2.logic.Controller;
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
	public abstract void execute(Game game, Controller controller);
	public abstract Command parse(String[] commandWords, Controller controller);
	
	public String helpText() {return " " + helpText + ": " + helpInfo;}
}

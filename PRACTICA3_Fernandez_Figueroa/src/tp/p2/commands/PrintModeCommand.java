package tp.p2.commands;

import tp.p2.exceptions.CommandExecuteException;
import tp.p2.exceptions.CommandParseException;
import tp.p2.logic.Game;

public class PrintModeCommand extends Command {
	private String mode;
	public static final String commandText = "printmode";
	public static final String commandInfo = "[P]rintMode release|debug";
	public static final String helpInfo = "change print mode [Release|Debug].";
	
	public PrintModeCommand() {
		super(commandText, commandInfo, helpInfo);
	}

	public Command parse(String[] commandWords) throws CommandParseException {
		Command command = null;
		
		if (commandWords[0].equalsIgnoreCase("printmode") || commandWords[0].equalsIgnoreCase("p")) {
			if(commandWords.length == 2) {
				mode = commandWords[1];
				if(mode.equalsIgnoreCase("release") || mode.equalsIgnoreCase("debug")) {
					command = this;
				}
				else {
					throw new CommandParseException("Unknown print mode: " + commandWords[1]);
				}
			}
			else {
				throw new CommandParseException("Incorrect number of arguments for printmode command: " + commandInfo);
			}
		}
		return command;
	}
	
	public boolean execute(Game game) throws CommandExecuteException {
		boolean printer = true;
		game.setModoPintado(mode);
		game.setPrintButNoUpdate(true);
		return printer;
	}
}

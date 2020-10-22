package tp.p2.commands;

import tp.p2.exceptions.CommandExecuteException;
import tp.p2.exceptions.CommandParseException;
import tp.p2.logic.Game;

public abstract class NoParamsCommand extends Command {
	
	private static Command[] availableCommands = {
			new ExitCommand(),
			new ListCommand(),
			new HelpCommand(),
			new ResetCommand(),
			new NoneCommand()
	};
	
	public NoParamsCommand(String commandText, String commandInfo, String helpInfo) {
		super(commandText, commandInfo, helpInfo);
	}
	
	public abstract boolean execute(Game game) throws CommandExecuteException;
	
	public Command parse(String[] commandWords) throws CommandParseException {
		
			for (Command c : availableCommands) {
				if (commandWords[0].length() == 0) {
					return new NoneCommand();
				}
				else if(commandWords[0].length() == 1) {
					char comando = Character.toLowerCase(commandWords[0].charAt(0));
					if (comando == Character.toLowerCase(c.commandName.charAt(0))) {
						if(commandWords.length > 1) {
							throw new CommandParseException(c.commandName + " command has no arguments");
						}
							return c;
					}
				}
				else if (c.commandName.equalsIgnoreCase(commandWords[0])) {
					if(commandWords.length > 1) {
						throw new CommandParseException(c.commandName + " command has no arguments");
					}
						return c;
				}
			}
		return null;
	}
}

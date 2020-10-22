package tp.p2.commands;

import tp.p2.logic.Controller;
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
	
	public abstract void execute(Game game, Controller controller);
	
	public Command parse(String[] commandWords, Controller controller) {
		
		if (commandWords.length == 1) {
			for (Command c : availableCommands) {
				if (commandWords[0].length() == 0) {
					return new NoneCommand();
				}
				else if(commandWords[0].length() == 1) {
					char comando = Character.toLowerCase(commandWords[0].charAt(0));
					if (comando == Character.toLowerCase(c.commandName.charAt(0)))
						return c;
				}
				else if (c.commandName.equalsIgnoreCase(commandWords[0]))
					return c;
			}
		}
		return null;
	}
}

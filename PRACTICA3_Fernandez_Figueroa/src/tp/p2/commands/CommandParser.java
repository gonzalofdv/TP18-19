package tp.p2.commands;

import tp.p2.exceptions.CommandParseException;

public class CommandParser {

	private static Command[] availableCommands = {
			new AddCommand(),
			new HelpCommand(),
			new ResetCommand(),
			new ExitCommand(),
			new ListCommand(),
			new NoneCommand(),
			new PrintModeCommand(),
			new LoadCommand(),
			new SaveCommand()
	};
	
	public static Command parseCommand(String[] commandWords) throws CommandParseException {
		Command command = null;
		
		if ((commandWords.length == 0) || (commandWords.length > 4)) {
			return null;
		}
		else {
			for (Command c : availableCommands) {
				command = c.parse(commandWords);
				//si a esta altura de la ejecucion, nos llega que command = null
				//va a ser si o si porque el comando no pertenezca
				//a uno de la lista de availableCommands
				if (command != null) {
					return command;
				}
			}
		
			throw new CommandParseException("Unknown command. Use 'help' to see the available commands");
		}
	}
	
	public static String commandHelp() {
		StringBuilder str = new StringBuilder();
		for (Command c : availableCommands) {
			str.append(c.helpText() + System.lineSeparator());
		}
		return str.toString();
	}
}

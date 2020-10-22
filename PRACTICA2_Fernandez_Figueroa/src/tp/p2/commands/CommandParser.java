package tp.p2.commands;

import tp.p2.logic.Controller;

public class CommandParser {

	private static Command[] availableCommands = {
			new AddCommand(),
			new HelpCommand(),
			new ResetCommand(),
			new ExitCommand(),
			new ListCommand(),
			new NoneCommand(),
			new PrintModeCommand()
	};
	
	public static Command parseCommand(String[] commandWords, Controller controller) {
		Command command;
		
		if ((commandWords.length == 0) || (commandWords.length > 4)) {
			return null;
		}
		else {
			for (Command c : availableCommands) {
				command = c.parse(commandWords, controller);
				
				if (command != null) {
					return command;
				}
			}
			return null;
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

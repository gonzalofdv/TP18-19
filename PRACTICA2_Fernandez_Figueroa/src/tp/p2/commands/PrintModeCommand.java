package tp.p2.commands;

import tp.p2.logic.Controller;
import tp.p2.logic.Game;

public class PrintModeCommand extends Command {
	private String mode;
	public static final String commandText = "printmode";
	public static final String commandInfo = "[P]rintMode <mode>";
	public static final String helpInfo = "change print mode [Release|Debug].";
	
	public PrintModeCommand() {
		super(commandText, commandInfo, helpInfo);
	}

	public Command parse(String[] commandWords, Controller controller) {
		Command command = null;
		
		if(commandWords.length == 2) {
			if (commandWords[0].equalsIgnoreCase("printmode") || commandWords[0].equalsIgnoreCase("p")) {
				mode = commandWords[1];
				if(mode.equalsIgnoreCase("release") || mode.equalsIgnoreCase("debug")) {
					command = this;
				}
			}
		}
		return command;
	}
	
	public void execute(Game game, Controller controller) {
		controller.setPrintGameState();
		controller.setModoPintado(mode);
		controller.setChanged(true); //ha cambiado el modo de pintado
	}
}

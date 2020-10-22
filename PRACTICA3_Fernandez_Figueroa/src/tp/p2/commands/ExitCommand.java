package tp.p2.commands;

import tp.p2.logic.Game;

public class ExitCommand extends NoParamsCommand {
	
	public static final String commandText = "exit";
	public static final String commandInfo = "[E]xit";
	public static final String helpInfo = "Terminates the program.";

	public ExitCommand() {
		super(commandText, commandInfo, helpInfo);
	}
	
	public boolean execute(Game game) {
		boolean printer = false;
		game.setEsFinalJuego(true);
		System.out.println("****** Game over!: User exit ******");
		return printer;
	}
}

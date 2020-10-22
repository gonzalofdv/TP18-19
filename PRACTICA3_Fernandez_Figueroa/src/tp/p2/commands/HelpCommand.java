package tp.p2.commands;


import tp.p2.logic.Game;

public class HelpCommand extends NoParamsCommand {
	public static final String commandText = "help";
	public static final String commandInfo = "[H]elp";
	public static final String helpInfo = "Print this help message.";
	
	public HelpCommand() {
		super(commandText, commandInfo, helpInfo);
	}
	
	public boolean execute(Game game) {
		boolean printer = false;
		String ayuda = CommandParser.commandHelp();
		System.out.println(ayuda);
		return printer;
	} //aunque help siempre devolverá false, no ponemos return false por si en algun momento queremos
	//que tambien se pinte el tablero
}

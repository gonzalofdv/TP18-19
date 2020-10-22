
package tp.p2.commands;

import java.io.*;
import tp.p2.exceptions.CommandExecuteException;
import tp.p2.exceptions.CommandParseException;
import tp.p2.logic.Game;
import tp.p2.auxiliar.MyStringUtils;

public class SaveCommand extends Command {
	private String fichero;
	public static final String commandText = "save";
	public static final String commandInfo = "[S]ave <filename>";
	public static final String helpInfo = "Save the state of the game to a file.";

	public SaveCommand() {
		super(commandText, commandInfo, helpInfo);
	}

	
	public boolean execute(Game game) throws CommandExecuteException {
		boolean printer = false;

		if(!MyStringUtils.isValidFilename(fichero+".dat")) { //añadimos + ".dat" para que el programa guarde el fichero con esa extension
			throw new CommandExecuteException("Invalid filename: the filename contains invalid characters");
		}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichero+".dat"))){

				bw.write("Plants Vs Zombies v3.0");
				bw.newLine();
				bw.newLine();
				bw.write(game.store());
				bw.flush();
				System.out.println("Game successfully saved in file " + fichero //<nombre_proporcionado_por_el_usuario>.dat. "
						+ ".dat. Use the load command to reload it");		
			
		}catch(IOException e) {
			
			 throw new CommandExecuteException("Error en la escritura del fichero:" + e);
		}

		return printer;
	}


	public Command parse(String[] commandWords) throws CommandParseException {
		Command command = null;
		
		if(commandWords[0].equalsIgnoreCase("save") || commandWords[0].equalsIgnoreCase("s")) {
			if(commandWords.length == 2) {
				fichero = commandWords[1];
				command = this;
			}
			else {
				throw new CommandParseException("Incorrect number of arguments for save command: " + commandInfo);
			}
		}
		return command;
	}

}

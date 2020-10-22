package tp.p2.commands;

import java.io.*;
import tp.p2.auxiliar.MyStringUtils;
import tp.p2.exceptions.CommandExecuteException;
import tp.p2.exceptions.CommandParseException;
import tp.p2.exceptions.FileContentsException;
import tp.p2.logic.Game;

public class LoadCommand extends Command {
	private String fichero;
	public static final String commandText = "load";
	public static final String commandInfo = "[Lo]ad <filename>";
	public static final String helpInfo = "Load the state of the game from a file.";

	public LoadCommand() {
		super(commandText, commandInfo, helpInfo);
	}


	public boolean execute(Game game) throws CommandExecuteException {
		boolean printer = true;
		
		if(MyStringUtils.isValidFilename(fichero) && MyStringUtils.isReadable(fichero)) {
			try (BufferedReader br = new BufferedReader(new FileReader(fichero))){
				String cabecera = br.readLine();
				
				if(cabecera.startsWith("Plants Vs Zombies")) {
					br.readLine(); //linea en blanco
					game.load(br);
					System.out.println("Game successfully loaded from file " + fichero);
				}
				
			}catch(FileContentsException | NumberFormatException ex) {
				throw new CommandExecuteException("Incorrect content in file.");
			}catch(IOException ex) {
				throw new CommandExecuteException("Ha habido un problema al carga el juego del fichero");
			}
		}
		else {
			throw new CommandExecuteException("El fichero " + fichero + " no es valido.");
		}
		
		game.setPrintButNoUpdate(true);
		return printer;
	}

	
	public Command parse(String[] commandWords) throws CommandParseException {
		Command command = null;
		
		if(commandWords[0].equalsIgnoreCase("load") || commandWords[0].equalsIgnoreCase("lo")) {
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

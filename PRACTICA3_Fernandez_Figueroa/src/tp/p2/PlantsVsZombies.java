package tp.p2;

import java.util.Random;

import tp.p2.logic.Controller;
import tp.p2.logic.Game;
import tp.p2.logic.Level;

public class PlantsVsZombies {
	public static void main (String[] args) {
		String mode = "release";
		Random random = new Random();

		int semilla;
		Level level = null;

		if (args.length > 0 && args.length < 3) {
			level = Level.define(args[0]);
			try {
				if (args.length == 2) {
						semilla = Integer.parseInt(args[1]);
				}
				else { //si no tenemos una semilla en los argumenos, la generamos aleatoriamente
					semilla = random.nextInt(10); //rango entre 0 y 9 por poner unos valores, pero se pueden ampliar
				}
				
				if (level != null) {
					Game game = new Game(level, semilla);
					Controller controller = new Controller(game, mode);
					
					controller.run();
				}
				else {
					System.out.println("Usage: plantsVsZombies <" + Level.all("|") + "> [seed]: level must be one of: " + Level.all(", "));
				}
			}catch(NumberFormatException Ex) {
				System.out.println("Usage: plantsVsZombies <" + Level.all("|") + "> [seed]: the seed must be a number");
			}
			
		}
		else {
			System.out.println("Usage: plantsVsZombies <" + Level.all("|") + "> [seed]");
		}
	}
}
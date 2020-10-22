package tp.p2;

import java.util.Random;

import tp.p2.logic.Controller;
import tp.p2.logic.Game;
import tp.p2.logic.Level;

public class PlantsVsZombies {
	public static void main (String[] args) {
		String mode = "release";
		Random random = new Random();
		int semilla = random.nextInt(10); //entre 0 y 9 por darle un rango, pero se puede ampliar
		Level level = null;

		if (args.length > 0) {
			level = Level.define(args[0]);
			
			if (args.length == 2) {
				semilla = Integer.parseInt(args[1]);
			}
		}
		
		if (level != null) {
			Game game = new Game(level, semilla);
			Controller controller = new Controller(game, mode);
			
			controller.run();
		}
		else {
			System.out.println("Wrong level");
		}
	}
}
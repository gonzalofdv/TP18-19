package tp.p1;

import java.util.Random;

import tp.p1.auxiliar.Level;
import tp.p1.logic.Controller;
import tp.p1.logic.Game;

public class PlantsVsZombies {
	public static void main (String[] args) {
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
			Controller controller = new Controller(game);
			
			controller.run();
		}
		else {
			System.out.println("Wrong level");
		}
	}
}
package tp.p2.logic.printers;

import tp.p2.logic.Game;

public class DebugPrinter extends BoardPrinter {
	private int cellSize = 20;
	
	public DebugPrinter(Game game, int filas, int columnas) {
		super(filas,columnas);
	}

	public String printGame(Game game) {
		encodeGame(game);
		
		String tablero = boardToString(game, cellSize);
		
		return tablero;
	}
	
	public void encodeGame(Game game) {

		int longPlantas = game.getLengthPlants();
		int contPlantas = 0;
		int contZombies = 0;
		board = new String[1][columnas];
		for(int j = 0; j < columnas; j++) {
			if(contPlantas < longPlantas) {
				board[0][j] = game.recibirDebugPlanta(contPlantas);
				contPlantas++;
			}
			else {
				board[0][j] = game.recibirDebugZombie(contZombies);
				contZombies++;
			}
		}
	}

}

package tp.p2.logic.printers;

import tp.p2.logic.Game;

public class ReleasePrinter extends BoardPrinter {
	
	private int cellSize = 7;
	
	public ReleasePrinter(Game game, int filas, int columnas) {
		super(filas,columnas);
	}
	
	public void encodeGame(Game game) {
		board = new String[filas][columnas];
		for (int i = 0; i < filas; ++i) {
			for (int j = 0; j < columnas; ++j) {
				board[i][j] = game.recibirElementoYVida(i, j);
			}
		}
	}

	public String printGame(Game game) {
		
		encodeGame(game);
		
		String tablero = boardToString(game, cellSize);
		
		return tablero;
		
	}
}

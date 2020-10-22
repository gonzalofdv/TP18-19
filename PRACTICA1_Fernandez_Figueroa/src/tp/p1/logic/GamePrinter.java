package tp.p1.logic;

import tp.p1.auxiliar.MyStringUtils;

public class GamePrinter {
	private String tableroPrinter[][];
	private final int filas;
	private final int columnas;
	private static final String space = " ";
	
	public GamePrinter (Game game, int filas, int columnas) {
		this.filas = filas;
		this.columnas = columnas;
		
		encodeGame(game);
		
	}
	
	private void encodeGame(Game game) {
		tableroPrinter = new String[filas][columnas];
		for (int i = 0; i < filas; ++i) {
			for (int j = 0; j < columnas; ++j) {
				tableroPrinter[i][j] = game.recibirElementoYVida(i, j);
			}
		}
	}
	
	public String toString() {

		int cellSize = 7;
		int marginSize = 2;
		String vDelimiter = "|";
		String hDelimiter = "-";

		String rowDelimiter = MyStringUtils.repeat(hDelimiter, (columnas * (cellSize + 1)) - 1);
		String margin = MyStringUtils.repeat(space, marginSize);
		String lineDelimiter = String.format("%n%s%s%n", margin + space, rowDelimiter);

		StringBuilder str = new StringBuilder();

		str.append(lineDelimiter);

		for (int i = 0; i < filas; i++) {
			str.append(margin).append(vDelimiter);
			for (int j = 0; j < columnas; j++) {
				str.append(MyStringUtils.centre(tableroPrinter[i][j], cellSize)).append(vDelimiter);
			}
			str.append(lineDelimiter);
		}
		return str.toString();
	}
}
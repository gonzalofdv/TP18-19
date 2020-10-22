package tp.p2.logic.printers;

import tp.p2.auxiliar.MyStringUtils;
import tp.p2.logic.Game;

public abstract class BoardPrinter implements GamePrinter {
	protected String[][] board;
	protected  int columnas;
	protected  int filas;
	final String space = " ";
	
	public BoardPrinter(int x, int y) {
		this.filas = x;
		this.columnas = y;
	}
	
	public abstract void encodeGame(Game game);
	
	public String boardToString(Game game, int cellSize) {
		
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
				str.append(MyStringUtils.centre(board[i][j], cellSize)).append(vDelimiter);
			}
			str.append(lineDelimiter);
		}
		return str.toString();
	}
	
}

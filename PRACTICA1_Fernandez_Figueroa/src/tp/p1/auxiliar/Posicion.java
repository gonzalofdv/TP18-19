package tp.p1.auxiliar;

public class Posicion {
	private int posFila;
	private int posColumna;
	
	public Posicion (int fila, int columna) {
		this.posColumna=columna;
		this.posFila=fila;
	}
	
	public int getF() {
		return this.posFila;
	}
	public int getC() {
		return this.posColumna;
	}
}

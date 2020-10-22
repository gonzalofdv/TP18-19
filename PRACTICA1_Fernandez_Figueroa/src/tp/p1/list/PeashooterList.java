package tp.p1.list;

import tp.p1.object.Peashooter;

public class PeashooterList {
	
	private static final int FILAS = 4;
	private static final int COLUMNAS = 8;

	public static final int MAX = FILAS * COLUMNAS;
	
	private Peashooter[] listaPeashooter;
	private int cont;
	
	public PeashooterList(){
		listaPeashooter = new Peashooter[MAX];
		cont=0;
	}
	
	public void insert (Peashooter peashooter) {
		listaPeashooter[cont]=peashooter;
		cont++;
	}
	
	public void update() {
		int i = 0;
		while (i< cont) {
			listaPeashooter[i].update();
			i++;
			
		}		
	}
	
	//elimina un Peashooter de la lista, y "recoloca" el array
	public void eliminate(int x, int y) {
		boolean encontrado = false;
		int k = 0;
		Peashooter peashooter = null;
		
		while (encontrado == false && k < cont) {
			peashooter = listaPeashooter[k];
			if (peashooter.getX() == x && peashooter.getY() == y) {
				encontrado = true;
				for(int j=k;j<cont;j++) {
					listaPeashooter[j]=listaPeashooter[j+1];
				}
			cont--;
			}
			else
				k++;
		}
	}
	
	//a partir de unas coordenadas, nos devuelve el objeto Peashooter correspondiente
	public Peashooter getPeashooter(int fila,int columna) {
		boolean encontrado = false;
		Peashooter peashooter= null;
		int i =0;
		while ( encontrado == false && i < cont) {
			peashooter = listaPeashooter[i];
			if (peashooter.getX()== fila && peashooter.getY()==columna) {
				encontrado = true;
			}
			else
				i++;
		}
		if ( encontrado == false) {
			peashooter = null;
		}
		return peashooter;
	}
	
	public int getLength() {
		return cont;
	}
	
	public boolean buscar(int x, int y) {
		boolean encontrado = false;
		int k = 0;
		Peashooter peashooter = null;
		
		while (encontrado == false && k < cont) {
			peashooter = listaPeashooter[k];
			if (peashooter.getX() == x && peashooter.getY() == y) {
				encontrado = true;
			}
			else
				k++;
		}
		
		return encontrado;
	}
	
	public String toString(int i, int j) {
		String elemento = " ";
		Peashooter peashooter = getPeashooter(i, j);
		if(peashooter != null) {
			elemento = peashooter.toString();
		}
		
		return elemento;
	}
	public void death(int x, int y) {
		getPeashooter(x, y).death();
	}
	
	
}

package tp.p1.list;

import tp.p1.object.Sunflower;

public class SunflowerList {
	
	private static final int FILAS = 4;
	private static final int COLUMNAS = 8;

	public static final int MAX = FILAS * COLUMNAS;
	
	private Sunflower[] listaSunflower;
	private int cont;
	
	
	public SunflowerList() {
		listaSunflower = new Sunflower[MAX];
		cont = 0;
	}
	
	public void insert (Sunflower sunflower) {
		listaSunflower[cont]=sunflower;
		cont++;
	}
	
	public void update() {
		int i =0;
		while (i<cont){
			
			listaSunflower[i].update();
			i++;
		}

	}
	
	//elimina un Sunflower de la lista, y "recoloca" el array
	public void eliminate(int x, int y) {
		boolean encontrado = false;
		int k = 0;
		Sunflower sunflower = null;
		
		while (encontrado == false && k < cont) {
			sunflower = listaSunflower[k];
			if (sunflower.getX() == x && sunflower.getY() == y) {
				encontrado = true;
				for(int j=k;j<cont;j++) {
					listaSunflower[j]=listaSunflower[j+1];
				}
			cont--;
			}
			else
				k++;
		}
	}
	
	//a partir de unas coordenadas, nos devuelve el Sunflower correspondiente
	public Sunflower getSunflower(int fila,int columna) {
		boolean encontrado = false;
		Sunflower sunflower= null;
		int i =0;
		while ( encontrado == false && i < cont) {
			sunflower = listaSunflower[i];
			if (sunflower.getX()== fila && sunflower.getY()==columna) {
				encontrado = true;
			}
			else
				i++;
		}
		if ( encontrado == false) {
			sunflower = null;
		}
		return sunflower;
	}
	
	public int getLength() {
		return cont;
	}
	
	public boolean buscar(int x, int y) {
		boolean encontrado = false;
		int k = 0;
		Sunflower sunflower = null;
		
		while (encontrado == false && k < cont) {
			sunflower = listaSunflower[k];
			if (sunflower.getX() == x && sunflower.getY() == y) {
				encontrado = true;
			}
			else
				k++;
		}
		
		return encontrado;
	}
	
	public String toString(int i, int j) {
		String elemento = " ";
		Sunflower sunflower = getSunflower(i, j);
		if(sunflower != null) {
			elemento = sunflower.toString();
		}
		
		return elemento;
	}
	public void death(int x, int y) {
		getSunflower(x, y).death();
	}
}

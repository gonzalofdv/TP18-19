package tp.p1.list;

import tp.p1.object.Zombie;

public class ZombieList {
	
	private static final int FILAS = 4;
	private static final int COLUMNAS = 8;

	public static final int MAX = FILAS * COLUMNAS;
	
	private Zombie[] listaZombie ;
	private int cont;
	
	public ZombieList() {
		listaZombie = new Zombie[MAX];
		cont=0;
	}

	public void insert(Zombie zombie) {
		listaZombie[cont]=zombie;
		cont++;
	}
	
	public void update() {
		int i = 0;
	
		while(i < cont && !listaZombie[i].update()) {
			
			i++;
		}
	}
	
	//elimina un Zombie de la lista, y "recoloca" el array	
	public void eliminate(int x, int y) {
		boolean encontrado = false;
		int k = 0;
		Zombie zombie = null;
		
		while (encontrado == false && k < cont) {
			zombie = listaZombie[k];
			if (zombie.getX() == x && zombie.getY() == y) {
				encontrado = true;
				for(int j=k;j<cont;j++) {
					listaZombie[j]=listaZombie[j+1];
				}
			cont--;
			}
			else
				k++;
		}
	}
	
	//a partir de unas coordenadas, nos devuelve el zombie correspondiente
	public Zombie getZombie(int fila,int columna) {
		boolean encontrado = false;
		Zombie zombie= null;
		int i =0;
		while ( encontrado == false && i < cont) {
			zombie = listaZombie[i];
			if (zombie.getX()== fila && zombie.getY()==columna) {
				encontrado = true;
			}
			else
				i++;
		}
		if ( encontrado == false) {
			zombie = null;
		}
		return zombie;
	}
	
	public void ciclosZombie(int x, int y) {
		Zombie zombie = getZombie(x, y);
		if(zombie != null)
			zombie.masCiclos();
	}
	
	public int getLength() {
		return cont;
	}
	
	public boolean buscar(int x, int y) {
		boolean encontrado = false;
		int k = 0;
		Zombie zombie = null;
		
		while (encontrado == false && k < cont) {
			zombie = listaZombie[k];
			if (zombie.getX() == x && zombie.getY() == y) {
				encontrado = true;
			}
			else
				k++;
		}
		
		return encontrado;
	}
	
	public void death(int x, int y) {
		getZombie(x, y).death();
	}
	
	public String toString(int i, int j) {
		String elemento = " ";
		Zombie zombie = getZombie(i, j);
		if(zombie != null) {
			elemento = zombie.toString();
		}
		
		return elemento;
	}
}

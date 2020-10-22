package tp.p2.lists;

import java.util.Arrays;


import tp.p2.logic.objects.Zombie;

public class ZombieList {

		public static final int MAX = 3;
		private int cont;
		private Zombie[] zombieList;
		
		
		public ZombieList() {
			this.cont = 0;
			zombieList = new Zombie[MAX];
		}

		public void insert (Zombie zombie) {
			if(cont < zombieList.length){
			zombieList[cont]=zombie;
			cont++;
			}
			
			else {
				zombieList = Arrays.copyOf(zombieList, zombieList.length+MAX);
				zombieList[cont]=zombie;
				cont++;
					
			}
		}
		
		public void update() {
			int i = 0;
			while (i< cont) {
				zombieList[i].update();
				i++;
			}		
		}
		
		public void eliminate(int x, int y) {
			boolean encontrado = false;
			int k = 0;
			Zombie zombie = null;
			
			while (encontrado == false && k < cont) {
				zombie = zombieList[k];
				if (zombie.getX() == x && zombie.getY() == y) {
					encontrado = true;
					if(k < cont - 1) {
						for(int j=k;j<cont - 1;j++) {
							zombieList[j]=zombieList[j+1];
						}
					}
				cont--;
				}
				else
					k++;
			}
		}
		
		public Zombie getZombie(int i) {
			return zombieList[i];
		}
		
		public Zombie getZombie(int x,int y) {
			boolean encontrado = false;
			Zombie  zombie= null;
			int i =0;
			while ( encontrado == false && i < cont) {
				zombie = zombieList[i];
				if (zombie.getX()== x && zombie.getY()==y) {
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
		
		public int getLength() {
			return cont;
		}
		
		public boolean buscar(int x, int y) {
			boolean encontrado = false;
			int k = 0;
			Zombie zombie = null;
			
			while (encontrado == false && k < cont) {
				zombie = zombieList[k];
				if (zombie.getX() == x && zombie.getY() == y) {
					encontrado = true;
				}
				else
					k++;
			}
			
			return encontrado;
		}
		public String toString (int x, int y) {
			
			Zombie zombie = this.getZombie(x, y);
			
			if (zombie != null)
				return zombie.toString();
			else
				return " ";
		}
		public String toStringDebug(int x) {
			
			Zombie zombie = this.getZombie(x);
			
			if (zombie != null)
				return zombie.toStringDebug();
			else
				return " ";
		}
		
		public String externalise() {
			StringBuilder listado = new StringBuilder();
			
			for(int i = 0; i < cont; i++) {
				listado.append(zombieList[i].externalise());
				if(i != cont-1) {
					listado.append(", ");
				}
			}
			
			
			return listado.toString();
		}
		
		public ZombieList clone() {
			ZombieList list = new ZombieList();
			
			for (int i = 0; i < cont; ++i) {
				list.insert(zombieList[i].clone());
			}
			
			list.cont = cont;
			
			return list;
		}
	}




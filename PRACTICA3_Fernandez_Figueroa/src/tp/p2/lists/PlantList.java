package tp.p2.lists;

import tp.p2.logic.objects.Plant;
import java.util.Arrays;

public class PlantList {
	public static final int MAX = 3;
	private int cont;
	private Plant[] plantList;
	
	
	public PlantList() {
		this.cont = 0;
		plantList = new Plant[MAX];
	}
	public void insert (Plant plant) {
		if(cont < plantList.length){
			plantList[cont]=plant;
			cont++;
		}
		
		else {
			plantList = Arrays.copyOf(plantList, plantList.length+MAX);
			plantList[cont]=plant;
			cont++;
				
		}
	}
	
	public void update() {
		int i = 0;
		while (i< cont) {
			plantList[i].update();
			i++;
		}		
	}
	
	public void eliminate(int x, int y) {
		boolean encontrado = false;
		int k = 0;
		Plant plant = null;
		
		while (encontrado == false && k < cont) {
			plant = plantList[k];
			if (plant.getX() == x && plant.getY() == y) {
				encontrado = true;
				if(k < cont - 1) {
					for(int j=k;j<cont-1;j++) {
						plantList[j]=plantList[j+1];
					}
				}	
			cont--;
			}
			else
				k++;
		}
	}
	
	public Plant getPlant(int i) {
		return plantList[i];
	}
	
	public Plant getPlant(int x,int y) {
		boolean encontrado = false;
		Plant plant= null;
		int i =0;
		while ( encontrado == false && i < cont) {
			plant = plantList[i];
			if (plant.getX()== x && plant.getY()==y) {
				encontrado = true;
			}
			else
				i++;
		}
		if ( encontrado == false) {
			plant = null;
		}
		return plant;
	}
	
	public int getLength() {
		return cont;
	}
	
	public boolean buscar(int x, int y) {
		boolean encontrado = false;
		int k = 0;
		Plant plant = null;
		
		while (encontrado == false && k < cont) {
			plant = plantList[k];
			if (plant.getX() == x && plant.getY() == y) {
				encontrado = true;
			}
			else
				k++;
		}
		
		return encontrado;
	}
	public String toString (int x, int y) {
		Plant plant = this.getPlant(x, y);
		if (plant != null)
			return plant.toString();
		else
			return " ";
	}
	public String toStringDebug(int x) {
		Plant plant = this.getPlant(x);
		if (plant != null)
			return plant.toStringDebug();
		else
			return " ";
	}
	
	public String externalise() {
		StringBuilder listado = new StringBuilder();
		
		for(int i = 0; i < cont; i++) {
			listado.append(plantList[i].externalise());
			if(i != cont-1) {
				listado.append(", ");
			}
		}
		
		return listado.toString();
	}
	
	public PlantList clone() {
		PlantList list = new PlantList();
		
		for (int i = 0; i < cont; ++i) {
			list.insert(plantList[i].clone());
		}
		
		list.cont = cont;
		
		return list;
	}
}

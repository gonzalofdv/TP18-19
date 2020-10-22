package tp.p2.factory;

import tp.p2.logic.objects.Plant;
import tp.p2.logic.objects.plants.Nuez;
import tp.p2.logic.objects.plants.Peashooter;
import tp.p2.logic.objects.plants.Petacereza;
import tp.p2.logic.objects.plants.Sunflower;

public class PlantFactory {
	private static Plant[] availablePlants = {
			new Sunflower(),
			new Peashooter(),
			new Nuez(),
			new Petacereza()
	};
	
	public static Plant getPlant (String plantName) {
		for(Plant plant : availablePlants) {
			if(plant.parse(plantName) != null) {
				return plant.clone();
			}
		}
		return null;
	}
	
	public static String listOfAvailablePlants() {
		StringBuilder str = new StringBuilder();
		for(Plant plant : availablePlants) {
			str.append(plant.getName()+ "[" + plant.getShort() + "]" + ": Cost "+ plant.getCost()+" suncoins "+"Harm: "+plant.getDamage()+System.lineSeparator());
			
		}
		return str.toString();
	}

}

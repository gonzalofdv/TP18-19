package tp.p2.factory;


import tp.p2.logic.objects.zombies.*;
import tp.p2.logic.objects.Zombie;

public class ZombieFactory {
	private static Zombie[] availableZombies = {
				new Caracubo(),
				new Deportista(),
				new ZombieComun()
	};
	
	public static Zombie getZombie(String zombieName) {
		for(Zombie zombie : availableZombies) {
			if(zombie.parse(zombieName) != null) {
				return zombie.clone();
			}
		}
		return null;
	}
}

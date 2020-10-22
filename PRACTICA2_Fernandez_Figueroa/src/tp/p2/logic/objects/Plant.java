package tp.p2.logic.objects;

public abstract class Plant extends GameObject {
	int cost;
	int frequency;
	
	public Plant(int cost, int freq, int cy, int dam,int res, String nam, String shor) {
		super(res,dam,cy,nam,shor);
		this.cost=cost; 
		this.frequency=freq;
	}
	public abstract Plant parse (String command);
	
	public abstract Plant clone();
	
	public void damage(GameObject object) {
		int nuevaResistencia = getResistance() - object.getDamage();
		setResistance(nuevaResistencia);
		
		if(nuevaResistencia<=0) {
			game.eliminarPlanta(this.getX(),this.getY());
		}
	}

	public int getCost() {
		return this.cost;
	}
	public int getFrequency() {
		return this.frequency;
	}
	
}

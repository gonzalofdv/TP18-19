package tp.p2.logic.objects;


import tp.p2.logic.Game;


public abstract class GameObject {
	private int x;
	private int y;
	private int resistance;
	private int damage;
	private int cycles;
	private int remainingCycles;
	private String name;
	private String shortName;
	protected Game game;
	public GameObject(int res, int dam ,int cy, String nam, String shor) {
		this.resistance=res;
		this.damage= dam;
		this.cycles = cy;
		this.name = nam;
		this.shortName = shor;
		this.remainingCycles = cy;
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getResistance() {
		return this.resistance;
	}
	public void setResistance(int res) {
		this.resistance=res;
	}
	public int getDamage() {
		return this.damage;
	}
	public void setDamage(int dam) {
		this.damage=dam;
	}
	public int getRemaining() {
		return this.remainingCycles;
	}
	public void setRemaining(int rem) {
		this.remainingCycles = rem;
	}
	public int getCycles() {
		return this.cycles;
	}
	public void removeCycles() {
		if(this.remainingCycles>0)
			this.remainingCycles--;
	}
	public String getName() {
		return this.name;
	}
	public String getShort() {
		return this.shortName;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public abstract void update();
	
	public abstract void damage(GameObject object);
	
	public String debugInfo() {
		return shortName + "[l:" + resistance + ",x:" + x + ",y:" + y + ",t:" + remainingCycles + "]"; 
		
	}
	
	public String toString() {
		return shortName + " [" + resistance + "]";
	}
	
	public String toStringDebug() {
		return shortName + " [" + "l:" + this.getResistance() +",x:"+this.getX() +",y:" + this.getY()+",t:" + this.getRemaining()+ "] ";
	}
	
}

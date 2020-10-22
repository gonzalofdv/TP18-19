package tp.p2.logic;

public enum Level {
	EASY(3, 0.1),
	HARD(5, 0.2),
	INSANE(10, 0.3);
	
	private int numZombis;
	private double frecuencia;
	
	Level(int numZombis, double frecuencia){
		this.frecuencia=frecuencia;
		this.numZombis=numZombis;
	}
	
	public int getZom() {
		return this.numZombis;
	}
	
	public double getFreq() {
		return this.frecuencia;
	}
	
	public static Level define(String nivel) {
		nivel = nivel.toLowerCase();
		Level level;
		
		if(nivel.equals("easy")) {
			level = EASY;
		}
		else if (nivel.equals("hard")) {
			level = HARD;
		}
		else if (nivel.equals("insane")) {
			level = INSANE;
		}
		else {
			level = null;
		}
		
		return level;
	}
}

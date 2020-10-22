package simulator.model;

import simulator.misc.Vector;

public class MassLossingBody extends Body {
	private double lossFactor;
	private double lossFrequency;
	private double c;
	
	public MassLossingBody(String id, Vector v, Vector p, double m, double fact, double freq) {
		super(id, v, p, m);
		this.lossFactor = fact;
		this.lossFrequency = freq;
		this.c = 0;
	}

	void move(double t) {
		super.move(t);
		c += t;
		if (c >= lossFrequency) {
			setMass(getMass() * (1 - lossFactor));
			c = 0;
		}
	}
}

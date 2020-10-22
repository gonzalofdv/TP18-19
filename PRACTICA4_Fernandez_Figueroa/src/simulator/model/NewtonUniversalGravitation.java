package simulator.model;

import java.util.List;

import simulator.misc.Vector;

public class NewtonUniversalGravitation implements GravityLaws {

	private final double G = 6.67E-11;
	
	public NewtonUniversalGravitation() {}
	
	public void apply(List<Body> bodies) {
		
		double[] zero = {0.0, 0.0};
		
		for(int i = 0; i < bodies.size(); ++i) {
			
			Body cuerpo = bodies.get(i);
			
			if(cuerpo.getMass() == 0.0) {
				cuerpo.setAcceleration(new Vector(zero));
				cuerpo.setVelocity(new Vector(zero));
			}
			else {
				
				Vector fuerza = new Vector(cuerpo.getPosition().dim());
				for(int j = 0; j < bodies.size(); ++j) {
					if(i != j) {
						//calcular fuerza
						fuerza = fuerza.plus(force(cuerpo, bodies.get(j)));
					}
				}
				//set acceleration
				
				cuerpo.setAcceleration(fuerza.scale(1/cuerpo.getMass()));
			}	
		}
	}
	
	private Vector force(Body a, Body b) {
		Vector fuerza = new Vector(a.getPosition().dim());
		
		double distance = b.getPosition().distanceTo(a.getPosition());
		double distanceSqr = distance * distance;
		
		double fuerzaMinus = (a.getMass()*b.getMass()*G)/distanceSqr;
		
		fuerza = (b.getPosition().minus(a.getPosition())).direction().scale(fuerzaMinus);
		
		return fuerza;
	}
}

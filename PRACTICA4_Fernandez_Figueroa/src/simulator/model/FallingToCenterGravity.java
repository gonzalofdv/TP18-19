package simulator.model;

import java.util.List;

public class FallingToCenterGravity implements GravityLaws {

	private final double accG = 9.81;
	
	public FallingToCenterGravity() {}
	
	public void apply(List<Body> bodies) {
		
		for(Body b : bodies) {
			b.setAcceleration(b.getPosition().direction().scale(-accG));
		}
		
	}

}

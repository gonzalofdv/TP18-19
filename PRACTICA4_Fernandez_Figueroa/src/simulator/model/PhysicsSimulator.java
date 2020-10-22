
package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {
	private GravityLaws laws;
	private double time;
	private double actualTime;
	private List<Body> cuerpos;
	
	public PhysicsSimulator(GravityLaws leyes, double time) {
		
		if(time < 0.0) {
			throw new IllegalArgumentException("The time is not correct");
		}
		
		if(leyes == null) {
			throw new IllegalArgumentException("The law is not correct");
		}
		
		this.laws = leyes;
		this.time = time;
		this.actualTime = 0.0;
		this.cuerpos = new ArrayList<Body>();
	}
	
	public void addBody(Body bod) {
		for(Body b : cuerpos) {
			if(b.getId().equals(bod.getId()))
				throw new IllegalArgumentException("Este cuerpo ya se ha añadido");
		}
		cuerpos.add(bod);
	}
	
	public void advance() {
		laws.apply(cuerpos);
		
		for(Body b : cuerpos) {
			b.move(time);
		}
				
		actualTime += time;
	}
	
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("{ \"time\": " + actualTime + ", \"bodies\": [ ");
		
		for(int i = 0; i < cuerpos.size(); ++i) {
			sb.append(cuerpos.get(i).toString());
			if(i+1 < cuerpos.size())
				sb.append(", ");
		}
		
		sb.append(" ] }");
		
		return sb.toString();
	}
}

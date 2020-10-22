
package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {
	private GravityLaws laws;
	private double time;
	private double actualTime;
	private List<Body> cuerpos;
	private List<SimulatorObserver> observers;
	
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
		this.observers = new ArrayList<SimulatorObserver>();
	}
	
	public void addBody(Body bod) {
		for(Body b : cuerpos) {
			if(b.getId().equals(bod.getId()))
				throw new IllegalArgumentException("Este cuerpo ya se ha añadido");
		}
		cuerpos.add(bod);
		
		for(SimulatorObserver ob : observers) {
			ob.onBodyAdded(cuerpos, bod);
		}
	}
	
	public void advance() {
		laws.apply(cuerpos);
		
		for(Body b : cuerpos) {
			b.move(time);
		}
				
		actualTime += time;
		
		
		for(SimulatorObserver ob : observers) {
			ob.onAdvance(cuerpos, actualTime);
		}
	}
	
	public void reset() {
		actualTime = 0.0;
		cuerpos.clear();
		
		for(SimulatorObserver ob : observers) {
			ob.onReset(cuerpos, actualTime, time, laws.toString());
		}
	}
	
	public void setDeltaTime(double dt) {
		if(dt < 0.0) {
			throw new IllegalArgumentException("The time is not correct");
		}
		time = dt;
		
		for(SimulatorObserver ob : observers) {
			ob.onDeltaTimeChanged(time);
		}
	}
	
	public void setGravityLaws(GravityLaws gravityLaws) {
		if(gravityLaws == null) {
			throw new IllegalArgumentException("The laws are not correct");
		}
		laws = gravityLaws;
		
		for(SimulatorObserver ob : observers) {
			ob.onGravityLawChanged(laws.toString());
		}
	}
	
	public void addObserver(SimulatorObserver o) {

		if(observers.contains(o))
			throw new IllegalArgumentException("Este observer ya se ha añadido");
			
		observers.add(o);
		
		o.onRegister(cuerpos, actualTime, time, laws.toString());
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

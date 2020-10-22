package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {

	private PhysicsSimulator ps;
	private Factory<Body> bodiesFact;
	private Factory<GravityLaws> lawsFact;
	
	public Controller(PhysicsSimulator ps, Factory<Body> bodiesFact, Factory<GravityLaws> lawsFact){
		this.ps = ps;
		this.bodiesFact = bodiesFact;
		this.lawsFact = lawsFact;
	}
	
	public void run(int nVeces){
		for(int i = 0; i < nVeces; ++i) {
			ps.advance();
		}
	}
	
	public void run(int nVeces, OutputStream out){
		
		PrintStream p = (out == null) ? null : new PrintStream(out);
		
		if(p != null) {
			p.println("{");
			p.println("\"states\": [");
		}
		
		p.println(ps + ", ");
		
		for(int i = 0; i < nVeces-1; ++i) {
			ps.advance();
			p.println(ps + ", ");
		}
		
		ps.advance();
		p.println(ps);
		
		p.println("]");
		p.println("}");
		
		p.close();
	}
	
	public void loadBodies(InputStream in){
		
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray bodies = jsonInput.getJSONArray("bodies");
		
		for(int i = 0; i < bodies.length(); ++i)
			ps.addBody(bodiesFact.createInstance(bodies.getJSONObject(i)));
	}
	
	public void reset() {
		ps.reset();
	}
	
	public void setDeltaTime(double dt) {
		ps.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		ps.addObserver(o);
	}
	
	public Factory<GravityLaws> getGravityLawsFactory(){
		return lawsFact;
	}
	
	public void setGravityLaws(JSONObject info) {
		
		for (JSONObject fe : lawsFact.getInfo()) {
			if (info.getString("type").equals(fe.getString("type"))) {
				GravityLaws law = lawsFact.createInstance(fe);
				ps.setGravityLaws(law);
				break;
			}
		}
	}
}

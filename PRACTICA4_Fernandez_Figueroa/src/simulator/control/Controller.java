package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.PhysicsSimulator;

public class Controller {

	private PhysicsSimulator ps;
	private Factory<Body> bodiesFact;
	
	public Controller(PhysicsSimulator ps, Factory<Body> bodiesFact){
		this.ps = ps;
		this.bodiesFact = bodiesFact;
	}
	
	public void run(int nVeces){
		//APARECIA EN EL UML PERO NO LO VEMOS NECESARIO
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
}

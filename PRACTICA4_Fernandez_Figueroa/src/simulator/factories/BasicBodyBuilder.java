		package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {
	public static final String typeTag = "basic";
	public static final String description = "A regular body";
	
	public BasicBodyBuilder() {
		this._typeTag = typeTag;
		this._desc = description;
	}

	
	public Body createTheInstance(JSONObject data) {
		
		String id = data.getString("id");
		Vector pos = new Vector(jsonArrayTodoubleArray(data.getJSONArray("pos")));
		Vector vel = new Vector(jsonArrayTodoubleArray(data.getJSONArray("vel")));
		double mass = data.getDouble("mass");
		
		Body body = new Body(id,vel,pos, mass);
		
		
		return body;	
	}
	
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		
		data.put("id", "identifier");
		data.put("pos", "position");
		data.put("vel", "velocity");
		data.put("mass", "mass");
		
		return data;
		
	}

}

package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body> {
	public static final String typeTag = "mlb";
	public static final String description = "Mass losing body";
	
	public MassLosingBodyBuilder() {
		this._typeTag = typeTag;
		this._desc = description;
	}

	@Override
	protected Body createTheInstance(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		String id = jsonObject.getString("id");
		Vector pos = new Vector(jsonArrayTodoubleArray(jsonObject.getJSONArray("pos")));
		Vector vel = new Vector(jsonArrayTodoubleArray(jsonObject.getJSONArray("vel")));
		double mass = jsonObject.getDouble("mass");
		double freq = jsonObject.getDouble("freq");
		double factor = jsonObject.getDouble("factor");
		
		MassLossingBody body = new MassLossingBody(id,vel,pos,mass,factor,freq);
		return body;
	}
	
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("id", "identifier");
		data.put("pos", "position");
		data.put("vel", "velocity");
		data.put("mass", "mass");
		data.put("freq","frequency");
		data.put("factor","factor");
		return data;
	}

}

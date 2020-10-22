package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NoGravity;

public class NoGravityBuilder extends Builder<GravityLaws> {
	public static final String typeTag = "ng";
	public static final String description = "No gravity law";
	
	public NoGravityBuilder() {
		this._typeTag = typeTag;
		this._desc = description;
	}
	
	protected GravityLaws createTheInstance(JSONObject jsonObject) {
		return new NoGravity();
	}

}

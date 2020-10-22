package simulator.factories;

import org.json.JSONObject;

import simulator.model.FallingToCenterGravity;
import simulator.model.GravityLaws;

public class FallingToCenterGravityBuilder extends Builder<GravityLaws>{

	public static final String typeTag = "ftcg";
	public static final String description = "Falling To Center Gravity";
	
	public FallingToCenterGravityBuilder() {
		this._typeTag = typeTag;
		this._desc = description;
	}
	
	protected GravityLaws createTheInstance(JSONObject jsonObject) {
		return new FallingToCenterGravity();
	}

}

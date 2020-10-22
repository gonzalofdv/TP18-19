package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<GravityLaws> {
	
	public static final String typeTag = "nlug";
	public static final String description = "Newton Law of Universal Gravitation";
	
	public NewtonUniversalGravitationBuilder() {
		this._typeTag = typeTag;
		this._desc = description;
	}

	protected GravityLaws createTheInstance(JSONObject jsonObject) {
		return new NewtonUniversalGravitation();
	}

}

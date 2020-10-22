package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Builder<T> {
	
	String _typeTag;
	String _desc;
	public Builder() {	}
	
	protected double[] jsonArrayTodoubleArray(JSONArray json) {
		double[] arrayDouble = new double[json.length()];
		
		for(int i = 0; i < json.length(); ++i) {
			arrayDouble[i] = json.getDouble(i);
		}
		
		return arrayDouble;
	}
	
	public T createInstance(JSONObject info){
		T b = null;
		if ( _typeTag != null && _typeTag.equals(info.getString("type")))
			b = createTheInstance(info.getJSONObject("data"));
		return b;
	}
	
	protected abstract T createTheInstance(JSONObject jsonObject);
	
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		info.put("type", _typeTag);
		info.put("data", createData());
		info.put("desc", _desc);
		
		return info;
	}

	protected JSONObject createData() {
		return new JSONObject();
	}
	
}

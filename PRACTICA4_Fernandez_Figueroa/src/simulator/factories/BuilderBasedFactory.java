package simulator.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {

	private List<Builder<T>> builders;
	private List<JSONObject> factoryElements;
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this.builders = new ArrayList<>(builders);
		
		List<JSONObject> info = new ArrayList<>();
		
		for(Builder<T> b : builders) {
			info.add(b.getBuilderInfo());
		}
		
		factoryElements = Collections.unmodifiableList(info);
	}
	
	public T createInstance(JSONObject info) {
		if(info == null) {
			throw new IllegalArgumentException("null");
		}		
		
		for(Builder<T> builder : builders) {
			T objeto = builder.createInstance(info);
			if(objeto != null)
				return objeto;
		}
		
		throw new IllegalArgumentException(info.toString());
	}

	public List<JSONObject> getInfo() {
		/*
		List<JSONObject> elemInfo = new ArrayList<JSONObject>();
		
 		for( Builder<T> b: builders) {
			JSONObject json = b.getBuilderInfo();
			elemInfo.add(json);
		}
 		
 		factoryElements = elemInfo;
 		*/
 		return factoryElements;

	}

}

package svr_ex4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import exercice4.Reference;

public class EnvironmentSimple implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public HashMap<String, ReferenceSimple> references;
	
	public EnvironmentSimple() {
		this.references = new HashMap<String, ReferenceSimple>();
	}
	
	@JsonIgnore
	public ReferenceSimple getReferenceByName(String name) {
        if (references.containsKey(name)) {
            return references.get(name);
        } else {
            return null;
        }
    }

	@Override
	public String toString() {
	    String res = "";
	    List<String> keys = new ArrayList<>(references.keySet());
	    Collections.sort(keys);
	    for (String key : keys) {
	        if (key.equals("space")) {
	            res += key + "\n";
	        } else if (key.startsWith("space.")) {
	            String[] parts = key.split("\\.");
	            int depth = parts.length - 1;
	            StringBuilder prefixBuilder = new StringBuilder();
	            for (int i = 0; i < depth; i++) {
	                prefixBuilder.append("    ");
	            }
	            String prefix = prefixBuilder.toString();
	            res += prefix + key.substring(key.lastIndexOf(".") + 1) + "\n";
	        }
	    }
	    return res;
	}



	
	
}

package clnt_ex4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonIgnore;




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
	            res += key + " - "+ references.get(key).receiver + "\n";
	        } else if (key.startsWith("space.")) {
	            String[] parts = key.split("\\.");
	            int depth = parts.length - 1;
	            StringBuilder prefixBuilder = new StringBuilder();
	            for (int i = 0; i < depth; i++) {
	                prefixBuilder.append("    ");
	            }
	            String prefix = prefixBuilder.toString();
	            res += prefix + key+" - "+ references.get(key).receiver + "\n";
	        }
	    }
	    return res;
	}


	
	
}

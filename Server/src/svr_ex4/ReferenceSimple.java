package svr_ex4;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

import graphicLayer.GElement;
import stree.parser.SNode;

public class ReferenceSimple {
	public void setReceiver(String strg) {
		this.receiver = strg;
	}

	public String receiver ="";
	
	
	public ReferenceSimple(String p) {
		this.receiver = p;
	}
	 

	@SuppressWarnings("unchecked")
	public String getReceiver() {
		
		return this.receiver;
	}


}

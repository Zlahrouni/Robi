package clnt_ex4;

import java.io.Serializable;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

import graphicLayer.GElement;
import stree.parser.SNode;

public class ReferenceSimple implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
 
	public String receiver ="";
	
	public ReferenceSimple() {
		
	}
	
	public ReferenceSimple(String p) {
		this.receiver = p;
	}
	

	public String getReceiver() {
		
		return this.receiver;
	}


}

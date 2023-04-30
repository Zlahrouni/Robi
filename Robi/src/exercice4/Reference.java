package exercice4;

import java.io.Serializable;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

import graphicLayer.GElement;
import stree.parser.SNode;

public class Reference implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	public void setReceiver(Object receiver) {
		this.receiver = receiver;
	}
	
	HashMap<String, SNode> script = new HashMap<String, SNode>();

	public Object receiver;
	
	@JsonIgnore
	HashMap<String, Command> commandes = new HashMap<String, Command>();

	public Reference(Object p) {
		this.receiver = p;
	}
	
	
	@JsonIgnore
	public Command getCommandByName(String selector) {
	    if (commandes.containsKey(selector)) {
	        return commandes.get(selector);
	    } else {
	        return null;
	    }
	}
	@JsonIgnore
	public void addCommand(String selector, Command primitive) {
	    commandes.put(selector, primitive);
	}
	@JsonIgnore
	Reference run(SNode expr) {
		String cmd = expr.get(1).contents();
		
		Command commande = this.getCommandByName(cmd);
		boolean script = false;
		
		if(commande != null) {
			//System.out.println("existe "+commande);			
			return commande.run(this, expr);
		}
		else {
			script = this.script.containsKey(cmd);
			if(script) {
				return (new RunScript()).run(this, expr);
			}
			System.out.println("La commande est nulle : "+cmd);
			return this;
		}
	}

	@SuppressWarnings("unchecked")
	@JsonIgnore
	public Class<GElement> getReceiver() {
		
		return (Class<GElement>) this.receiver.getClass();
	}
	@JsonIgnore
	public SNode getScriptByName(String s) {
		return script.get(s);
	}

}

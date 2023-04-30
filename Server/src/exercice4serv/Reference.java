package exercice4serv;

import java.io.Serializable;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import graphicLayer.GElement;
import stree.parser.SNode;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Reference implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2454408125778228962L;
	public void setReceiver(Object receiver) {
		this.receiver = receiver;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public HashMap<String, SNode> script = new HashMap<String, SNode>();

	public Object receiver;
	public HashMap<String, SNode> getScript() {
		return script;
	}

 

	public void setScript(HashMap<String, SNode> script) {
		this.script = script;
	}


	@JsonIgnore
	public HashMap<String, Command> getCommandes() {
		return commandes;
	}



	public void setCommandes(HashMap<String, Command> commandes) {
		this.commandes = commandes;
	}

	
	HashMap<String, Command> commandes = new HashMap<String, Command>();
	
	public Reference(Object p) {
		this.receiver = p;
	}
	
	
	
	public Command getCommandByName(String selector) {
	    if (commandes.containsKey(selector)) {
	        return commandes.get(selector);
	    } else {
	        return null;
	    }
	}

	public void addCommand(String selector, Command primitive) {
	    commandes.put(selector, primitive);
	}
	
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
	public Class<GElement> getReceiver() {	
		return (Class<GElement>) this.receiver.getClass();
	}
	public SNode getScriptByName(String s) {
		return script.get(s);
	}

}

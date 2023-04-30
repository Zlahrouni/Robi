package exercice4serv;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


import graphicLayer.GBounded;
import graphicLayer.GElement;
import graphicLayer.GSpace;
import stree.parser.SNode;

public class DelElement implements Command {

	@Override
	public Reference run(Reference receiver, SNode expr) {
		
		// Définition de l'élément à supprimer.
		GElement g = (GElement)Execute.environment.getReferenceByName(expr.get(2).contents()).receiver;
		Execute.environment.references.remove(expr.get(2).contents());
		Iterator<Entry<String, Reference>> it = Execute.environment.references.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String,Reference> pair = (Entry<String, Reference>)it.next();
			System.out.println(expr.get(0).contents()+".");
			if(pair.getKey().contains(expr.get(2).contents()+".")) {
				it.remove(); 
			}
		}
		// Vérifie le type de receiver puis remove l'élément ciblé.
		//((GBounded)receiver.receiver).clear();
		if(receiver.receiver instanceof GSpace) {
			((GSpace)receiver.receiver).removeElement(g);
		} else {
			((GBounded)receiver.receiver).removeElement(g);
		}
		return receiver;
	}

}

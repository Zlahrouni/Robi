package exercice4;

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
		GElement g = (GElement)Exercice4_2_0.environment.getReferenceByName(expr.get(2).contents()).receiver;
		Exercice4_2_0.environment.references.remove(expr.get(2).contents());

		// Vérifie le type de receiver puis remove l'élément ciblé
		//((GBounded)receiver.receiver).clear();;
		Iterator<Entry<String, Reference>> it = Exercice4_2_0.environment.references.entrySet().iterator();
		 while (it.hasNext()) {
			 Map.Entry<String,Reference> pair = (Entry<String, Reference>)it.next();
			 System.out.println(expr.get(0).contents()+".");
			 if(pair.getKey().contains(expr.get(0).contents()+".")) {
				 it.remove(); 
			 }
		 }
		if(receiver.receiver instanceof GSpace) {
			((GSpace)receiver.receiver).removeElement(g);
		} else {
			((GBounded)receiver.receiver).removeElement(g);
		}
		
		
		return receiver;
	}

}

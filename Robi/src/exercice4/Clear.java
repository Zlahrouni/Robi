package exercice4;


import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import graphicLayer.GBounded;
import graphicLayer.GSpace;
import stree.parser.SNode;

public class Clear implements Command {
	
	GBounded conteneur;
	GSpace conteneurS;
	@Override
	public Reference run(Reference receiver, SNode expr) {
		
		
		//Récupération de la class de l'élément à créé
		//Reference cls = Exercice4_2_0.environment.getReferenceByName(expr.get(3).get(0).contents());

		// Instance de la calss DelElement pour supprimer tous les éléments et leur référence.
		// Command command = cls.getCommandByName("delElement");

		
		//test de la classe du conteneur 
		if(receiver.receiver instanceof GSpace) {
			this.conteneurS = (GSpace) receiver.receiver;

			this.conteneurS.clear();
		}  else {
			this.conteneur = (GBounded) receiver.receiver;			
			this.conteneur.clear();
		} 

		 
		 Iterator<Entry<String, Reference>> it = Exercice4_2_0.environment.references.entrySet().iterator();
		 while (it.hasNext()) {
			 Map.Entry<String,Reference> pair = (Entry<String, Reference>)it.next();
			 if(pair.getKey().contains(expr.get(0).contents()+".")) {
				 it.remove(); 
			 }
		 }
		return receiver;
	}
	

}

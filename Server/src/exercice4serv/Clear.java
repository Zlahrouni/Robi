package exercice4serv;


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
				
		//Test de la classe du conteneur
		if(receiver.receiver instanceof GSpace) {
			 
			// Définition de conteneurS par space (receiver) et suppression des éléments présents dans space.
			this.conteneurS = (GSpace) receiver.receiver;
			this.conteneurS.clear();
		
		}  else {
		
			// Définition de conteneur par le conteneur (receiver) et suppression des éléments présents dans ce conteneur.
			this.conteneur = (GBounded) receiver.receiver;			
			this.conteneur.clear();
		
		} 

		 // Itération sur les références de l'environnement.
		 // Pour chaque référence, si elle contient le nom du conteneur suivi d'un point, elle est supprimée.
		 Iterator<Entry<String, Reference>> it = Execute.environment.references.entrySet().iterator();
		 while (it.hasNext()) {
			 Map.Entry<String,Reference> pair = (Entry<String, Reference>)it.next();
			 if(pair.getKey().contains(expr.get(0).contents()+".")) {
				 it.remove(); 
			 }
		 }
		return receiver;
	}
	

}

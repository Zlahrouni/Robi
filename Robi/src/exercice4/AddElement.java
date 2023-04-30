package exercice4;


import graphicLayer.GBounded;
import graphicLayer.GElement;
import graphicLayer.GSpace;
import stree.parser.SNode;

public class AddElement implements Command {
	
	GBounded conteneur;
	GSpace conteneurS;
	@Override
	public Reference run(Reference receiver, SNode expr) {
		if(Exercice4_2_0.environment.references.containsKey(expr.get(0).contents()+"."+expr.get(2).contents())) {
			System.out.println("L'objet existe déja");
			return null;
		}
		//Récupération de la class de l'élément à créé
		Reference cls = Exercice4_2_0.environment.getReferenceByName(expr.get(3).get(0).contents());		
		
		//Exécution de la commande de récupéaration 
		Reference r=cls.getCommandByName(expr.get(3).get(1).contents()).run(cls, expr);		
		
		//test de la classe du conteneur 
		if(receiver.receiver instanceof GSpace) {
			this.conteneurS = (GSpace) receiver.receiver;
			this.conteneurS.addElement((GElement)r.receiver);
		} else {
			this.conteneur = (GBounded) receiver.receiver;
			this.conteneur.addElement((GElement)r.receiver);
		}
		Exercice4_2_0.environment.addReference(expr.get(0).contents()+"."+expr.get(2).contents(), r);
		return r;
	}
	

}

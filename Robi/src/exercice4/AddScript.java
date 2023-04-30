package exercice4;

import stree.parser.SNode;

public class AddScript implements Command{

	
	@Override
	public Reference run(Reference receiver, SNode expr) {
		//rajoute le script ( SNode) dans le tableau des script de l'objet 
		SNode n = expr.get(3);
		receiver.script.put(expr.get(2).contents(), n);
		return receiver;
	}
}

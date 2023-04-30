package exercice4serv;

import graphicLayer.GElement;
import graphicLayer.GSpace;
import stree.parser.SNode;
import tools.Tools;

/**
 * La classe SetColor implémente l'interface Command pour exécuter une commande 
 * qui permet de changer la couleur un objet de type GElement avec la nouvelle color passé
 * en paramètre
 * 
 * @author Hanane Erraji
 * @author Ziad Lahrouni
 *
 */
public class SetColor implements Command {
	
	Object objet ;
	
	@Override
	public Reference run(Reference receiver, SNode expr) {
		
		objet = receiver.receiver;
		if(objet instanceof GSpace) {
			((GSpace)objet).setColor(Tools.getColorByName(expr.get(2).contents()));
		} else {
			((GElement)objet).setColor(Tools.getColorByName(expr.get(2).contents()));
		}
		receiver.receiver = objet;
		System.out.println(expr.get(2).contents());
		return receiver;
	}

}

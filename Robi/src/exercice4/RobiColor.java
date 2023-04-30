package exercice4;

import graphicLayer.GRect;
import stree.parser.SNode;
import tools.Tools;

/**
 * La classe RobiColor implémente l'interface Command pour exécuter une commande 
 * qui permet de changer la couleur d'un objet de type GRect.
 * 
 * @author Hanane Erraji
 *
 */
public class RobiColor implements Command {
	GRect objet;

	@Override
	public Reference run(Reference receiver, SNode expr) {
		objet = (GRect) receiver.receiver;
		objet.setColor(Tools.getColorByName(expr.get(2).contents()));
		receiver.receiver = objet;
		return receiver;
	}

}

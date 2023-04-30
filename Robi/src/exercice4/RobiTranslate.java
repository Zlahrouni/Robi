package exercice4;

import java.awt.Point;

import graphicLayer.GRect;
import stree.parser.SNode;

/**
 * La classe RobiTranslate implémente l'interface Command pour exécuter une commande 
 * qui permet de déplacer un objet de type GRect vers une nouvelle position en utilisant
 * un code source spécifique.
 * 
 * @author Hanane Erraji
 * @author Ziad Lahrouni
 */
public class RobiTranslate implements Command {
	GRect objet;
	int x,y;

	@Override
	public Reference run(Reference receiver, SNode expr) {
		objet = (GRect) receiver.receiver;
		x = Integer.parseInt(expr.get(2).contents());
		y = Integer.parseInt(expr.get(3).contents());
		
		objet.setPosition(new Point(x,y));
		
		 
         
         receiver.receiver = objet;
		
		
		return receiver;
	}

}

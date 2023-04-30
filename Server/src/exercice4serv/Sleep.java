package exercice4serv;

import stree.parser.SNode;

/**
 * La classe Sleep implémente l'interface Command pour exécuter une commande 
 * qui permet de mettre en pause le thread courrant pour un certain temps.
 * 
 * @author Hanane Erraji
 * @author Ziad Lahrouni
 *
 */
public class Sleep implements Command {

	@Override
	public Reference run(Reference receiver, SNode expr) {
		try {
			Thread.sleep(Integer.parseInt(expr.get(2).contents()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return receiver;
	}

}

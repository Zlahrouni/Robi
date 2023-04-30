package exercice4;

import stree.parser.SNode;

/**
 * L'interface Command représente une commande qui peut être exécutée sur un objet récepteur 
 * à l'aide d'une méthode.
*/
public interface Command {

	/**
	 * Cette méthode exécute la commande en utilisant l'objet récepteur et la S-expression 
	 * donnés en entrée.
	 * 
	 * @param receiver L'objet récepteur qui va exécuter la méthode.
	 * @param expr La S-expression qui représente le code source à exécuter.
	 * @return Une référence mise à jour qui peut être utilisée pour accéder aux informations
	 * sur l'exécution de la commande ou pour interagir avec d'autres objets.
	 */
	abstract public Reference run(Reference receiver, SNode method);
}




package exercice4serv;

import graphicLayer.GString;
import stree.parser.SNode;

public class SetString implements Command {
	
	GString objet ;

	@Override
	public Reference run(Reference receiver, SNode expr) {

		// Définition de l'objet pour lequel modifier le contenu.
		objet = (GString)receiver.receiver;

		// Défintion de la nouvelle chaîne par le premier mot puis les suivant avec for.
		String texte = new String(expr.get(2).contents());
		
		for (int i = 3 ; i < expr.size() ; i++)
		{
			texte += " " + expr.get(i).contents();
		}
		
		// Application de la chaîne à l'objet.
		((GString)receiver.receiver).setString(texte);
		
		
		receiver.receiver = objet;
		System.out.println(expr.get(2).contents());
		
		// Mise à jour du conteneur de l'objet pour afficher la nouvelle chaîne.
		objet.getContainer().repaint();
		
		return receiver;
	}

}

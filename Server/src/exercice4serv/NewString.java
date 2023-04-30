package exercice4serv;

import graphicLayer.GString;
import stree.parser.SNode;

public class NewString implements Command {

	@Override
	public Reference run(Reference receiver, SNode expr) {
		
		// Définition de la chaîne à créer par le premier mot (/argument) puis ajoute chaque mot (/argument) à la chaîne.
		String texte = new String(expr.get(3).get(2).contents());
		
		for (int i = 3 ; i < expr.get(3).size() ; i++)
		{
			texte += " " + expr.get(3).get(i).contents();
		}
		
		
		Reference ref = null;
		try {
			// Définition de l'objet GString par la chaîne précédente.
			GString str = new GString(texte);
			
			// Définition des commandes utilisables sur l'élément.
			ref = new Reference(str);
			ref.addCommand("setString", new SetString());
			ref.addCommand("setColor", new SetColor());
			ref.addCommand("translate", new Translate());
			// sertString
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ref;
		
	}

}

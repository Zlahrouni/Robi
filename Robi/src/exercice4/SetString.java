package exercice4;

import graphicLayer.GString;
import stree.parser.SNode;

public class SetString implements Command {
	
	GString objet ;

	@Override
	public Reference run(Reference receiver, SNode expr) {

		objet = (GString)receiver.receiver;

		String texte = new String(expr.get(2).contents());
		
		for (int i = 3 ; i < expr.size() ; i++)
		{
			texte += " " + expr.get(i).contents();
		}
		
		// objet.setString(texte);
		((GString)receiver.receiver).setString(texte);
		
		
		receiver.receiver = objet;
		System.out.println(expr.get(2).contents());
		
		objet.getContainer().repaint();
		
		return receiver;
	}

}

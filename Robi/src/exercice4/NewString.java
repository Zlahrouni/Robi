package exercice4;

import graphicLayer.GString;
import stree.parser.SNode;

public class NewString implements Command {

	@Override
	public Reference run(Reference receiver, SNode expr) {
		
		String texte = new String(expr.get(3).get(2).contents());
		
		for (int i = 3 ; i < expr.get(3).size() ; i++)
		{
			texte += " " + expr.get(3).get(i).contents();
		}
		
		
		Reference ref = null;
		try {
			GString str = new GString(texte);
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

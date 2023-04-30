package exercice4;

import graphicLayer.GSpace;
import stree.parser.SNode;
import tools.Tools;

public class SpaceColor implements Command {
	GSpace objet;
	@Override
	public Reference run(Reference receiver, SNode expr) {
		 objet = (GSpace) receiver.receiver;
		 objet.setColor(Tools.getColorByName(expr.get(2).contents()));
		 receiver.receiver = objet;
		return receiver;
	}

}

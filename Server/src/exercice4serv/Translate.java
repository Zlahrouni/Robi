package exercice4serv;

import java.awt.Point;

import graphicLayer.GBounded;
import stree.parser.SNode;

/**
 * La classe Translate implémente l'interface Command pour exécuter une commande 
 * qui permet déplacé un objet 
 * 
 * @author Hanane Erraji
 * @author Ziad Lahrouni
 *
 */
public class Translate implements Command {

	GBounded objet ;
	double x,y;
	
	@Override
	public Reference run(Reference receiver, SNode expr) {
		objet = (GBounded)receiver.receiver;
		x = Double.parseDouble(expr.get(2).contents());
		y = Double.parseDouble(expr.get(3).contents());
		//objet.translate(new Point(x,y));
		
		
		 int longueurTrajet = (int) Math.sqrt(   Math.pow(x, 2) + Math.pow(y, 2)   );
         
         for (Double x1=(double) objet.getX(), y1=(double) objet.getY(), i=0.0 ; i<longueurTrajet ; x1 += x/longueurTrajet, y1 += y/longueurTrajet, i++)
         {
             Point p = new Point((int)(Math.round(x1)), (int)(Math.round(y1)));
             objet.setPosition(p);
             try {
                 Thread.sleep(10);
             } catch (InterruptedException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
         }
		
		return null;
	}

}

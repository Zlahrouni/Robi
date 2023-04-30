package exercice2;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;
import stree.parser.SParser;
import tools.Tools;


public class Exercice2_1_0 {
	GSpace space = new GSpace("Exercice 2_1", new Dimension(200, 100));
	GRect robi = new GRect();
	String script = "(space setColor black) (robi setColor yellow)";
	String script2 = "(space setColor white) (robi color red) (robi translate 10 0) (robi translate 0 10) (space sleep 100) (robi translate -10 0) (space sleep 100) (robi translate 0 -10)";
	String script3 = "(robi translate 50 50)";
	
	
	public Exercice2_1_0() {
		space.addElement(robi);
		space.open();
		this.runScript();
	}

	private void runScript() {
		SParser<SNode> parser = new SParser<>();
		List<SNode> rootNodes = null;
		try {
			rootNodes = parser.parse(script3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<SNode> itor = rootNodes.iterator();
		while (itor.hasNext()) {
			this.run(itor.next());
		}
	}
	
	private void run(SNode expr) {
		
		// Définition des variables esp (space ou robi), cmd (commande) et arg (arguments de la commande)
		String esp = expr.get(0).contents();
		String cmd = expr.get(1).contents();
		String arg = expr.get(2).contents();
		
		System.out.println("\nCommande :");
		System.out.println(esp);
		System.out.println(cmd);
		System.out.println(arg);
		
		
		// Liste tous les arguments dans une nouvelle liste (args)
		List<String> args = new ArrayList<>();
		
		for (int i = 2; i < expr.size(); i++) {
		    String argument = expr.get(i).contents();
		    args.add(argument);
		}

		
		// Commandes pour "space" :
		if (esp.equals("space"))
		{

			switch (cmd) {
				case "setColor":
				case "color" :
				{
					space.setColor(Tools.getColorByName(args.get(0)));
					break;
				}
				case "sleep":
				{
					try {
						Thread.sleep(Integer.parseInt(arg));
					} catch (NumberFormatException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + cmd);
			}
			
		}
		// Commande pour "robi" :
		else if (esp.equals("robi"))
		{
			switch (cmd) {
				case "setColor":
				case "color" :
				{
					robi.setColor(Tools.getColorByName(args.get(0)));
					break;
				}
				case "translate":
				{
					
					// Calcul de la longueur du trajet (Pythagore) / Détermine le nombre d'étapes à utiliser avec le for.
					int longueurTrajet = (int) Math.sqrt(   Math.pow(Double.parseDouble(args.get(0)), 2) + Math.pow(Double.parseDouble(args.get(1)), 2)   );
					
					// Pour x abscisse de robi et y ordonnée de robi et i = 0
					// jusqu'à ce que i atteigne le nombre de pas,
					// i est incrémenté de 1 et x et y sont incrémenté de leur longueur respective divisée par le nombre d'étapes.
					for (double x=robi.getX(), y=robi.getY(), i=0 ;
						 i<longueurTrajet ;
						 x += Double.parseDouble(args.get(0))/longueurTrajet, y += Double.parseDouble(args.get(1))/longueurTrajet, i++)
					{
						// Définition de la nouvelle position de robi.
						robi.setPosition(new Point((int)(Math.round(x)), (int)(Math.round(y))));
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					break;
					
				}
			
				default:
					throw new IllegalArgumentException("Unexpected value: " + cmd);
			}
			
		}
		
		
		
	}

	public static void main(String[] args) {
		new Exercice2_1_0();
	}

}
package exercice3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;
import stree.parser.SParser;
import tools.Tools;

public class Exercice3_0 {
	GSpace space = new GSpace("Exercice 3", new Dimension(200, 100));
	GRect robi = new GRect();
	String script = "(robi translate 40 40)"; //
//	"   (space setColor black) " +
//	"   (robi setColor yellow)" +
//	"   (space sleep 1000)" +
//	"   (space setColor white)\n" + 
//	"   (space sleep 1000)" +
//	"	(robi setColor red) \n" + 
//	"   (space sleep 1000)" +
//	"	(robi translate 100 0)\n" + 
//	"	(space sleep 1000)\n" + 
//	"	(robi translate 0 50)\n" + 
//	"	(space sleep 1000)\n" + 
//	"	(robi translate -100 0)\n" + 
//	"	(space sleep 1000)\n" + 
//	"	(robi translate 0 -40)";

	public Exercice3_0() {
		space.addElement(robi);
		space.open();
		this.runScript();
	}

	private void runScript() {
		SParser<SNode> parser = new SParser<>();
		List<SNode> rootNodes = null;
		try {
			rootNodes = parser.parse(script);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<SNode> itor = rootNodes.iterator();
		while (itor.hasNext()) {
			this.run(itor.next());
		}
	}

	private void run(SNode expr) {
		Command cmd = getCommandFromExpr(expr);
		if (cmd == null)
			throw new Error("unable to get command for: " + expr);
		cmd.run();
	}

	Command getCommandFromExpr(SNode expr) {
		Command commande = null;
		String widget = expr.get(0).contents();
		String cmd = expr.get(1).contents();
		if(widget.equals("space")) {
			switch(cmd) {
			case "setColor":
				String color = expr.get(2).contents();
				commande = new SpaceChangeColor(Tools.getColorByName(color));
				break;
			case "sleep" :
				String time = expr.get(2).contents();
				commande =  new SpaceSleep(Integer.parseInt(time));
				break;
				default :
					System.out.println("Unkown command");
			}
		} else if (widget.equals("robi")) {
			switch(cmd) {
			case "setColor":
				String color = expr.get(2).contents();
				commande = new RobiChangeColor(Tools.getColorByName(color));
				break;
			case "translate":
				int x = Integer.parseInt(expr.get(2).contents());
				int y = Integer.parseInt(expr.get(3).contents());
				commande = new RobiTranslate(x,y);
				break;
				default : 
					System.out.println("Unkown command");
			}
		}
		return commande;
	}

	public static void main(String[] args) {
		new Exercice3_0();
	}

	public interface Command {
		abstract public void run();
	}

	public class SpaceChangeColor implements Command {
		Color newColor;

		public SpaceChangeColor(Color newColor) {
			this.newColor = newColor;
		}

		@Override
		public void run() {
			space.setColor(newColor);
		}

	}
	
	public class RobiChangeColor implements Command {
		Color newColor;

		public RobiChangeColor(Color newColor) {
			this.newColor = newColor;
		}

		@Override
		public void run() {
			robi.setColor(newColor);
		}

	}
	
	public class SpaceSleep implements Command {
		int temps;

		public SpaceSleep(int temps ) {
			this.temps = temps;
		}

		@Override
		public void run() {
			try {
				Thread.currentThread();
				Thread.sleep(temps);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public class RobiTranslate implements Command {
		double x,y;

		public RobiTranslate(double x, double y ) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void run() {

            
            int longueurTrajet = (int) Math.sqrt(   Math.pow(x, 2) + Math.pow(y, 2)   );
            
            for (Double x1=(double) robi.getX(), y1=(double) robi.getY(), i=0.0 ; i<longueurTrajet ; x1 += x/longueurTrajet, y1 += y/longueurTrajet, i++)
            {
                Point p = new Point((int)(Math.round(x1)), (int)(Math.round(y1)));
                robi.setPosition(p);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
         
		}

	}
	
	
	
	
}
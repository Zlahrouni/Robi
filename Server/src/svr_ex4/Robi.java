package svr_ex4;

import java.awt.Color;
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

public class Robi {
	GSpace space = new GSpace("Server", new Dimension(300, 200));
	GRect robi = new GRect();
	List<String> instructions = new ArrayList<String>();
	//private String scriptTab[];
	Iterator<SNode> itor;
	
	
	public Robi() {
		space.addElement(robi);
		space.open();
		this.itor = null;
	}
	
	public void addScript(String script) {
		String[] scriptTab =  script.split("\n");
		for (String s : scriptTab) {
			instructions.add(s);
		}
		System.out.println("Size of inst :"+instructions.size());
		//System.out.println("Script stocked "+this.lineScript);
	}
	
	public void executeScript(String mode) throws IOException {
		//this.runScript();
		SParser<SNode> parser = new SParser<>();
		List<SNode> rootNodes = new ArrayList<>();
		
		if (mode.equals("block")) {
			if (!instructions.isEmpty()) {
				System.out.println("----------Block-EXEC----------");
				System.out.println("Size before exec : "+instructions.size());
				for (int i = 0; i < instructions.size(); i++) {
					System.out.println("Executing : "+instructions.get(i));
					rootNodes.addAll(parser.parse(instructions.get(i)));
				}
				
				instructions.clear();
				System.out.println("Size now : "+instructions.size());
				System.out.println("----------End-Block-EXEC----------");
			}
			
		} else if (mode.equals("step")) {
			if (!instructions.isEmpty()) {
				System.out.println("----------Step-EXEC----------");
				System.out.println("Size before exec : "+instructions.size());
				rootNodes = parser.parse(instructions.get(0));
				System.out.println("Executing : "+instructions.get(0));
				instructions.remove(0);
				System.out.println("Size now : "+instructions.size());
				System.out.println("----------End-Step-EXEC----------");
			}
		}
		
		if (rootNodes != null) {
			itor = rootNodes.iterator();
			
			while (itor.hasNext()) {
				this.run(itor.next());
			}
			rootNodes.clear();
		}
	}
	
	public String getImage() {
		return space.getImageBase64();
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

	/*public static void main(String[] args) {
		new Exercice3_0();
	}*/

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

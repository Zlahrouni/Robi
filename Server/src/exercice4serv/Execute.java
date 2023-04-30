package exercice4serv;

 /*
	(space setColor black)  
	(robi setColor yellow) 
	(space sleep 2000) 
	(space setColor white)  
	(space sleep 1000) 	
	(space add robi (GRect new))
	(robi setColor green)
	(robi translate 100 50)
	(space del robi)
	(robi setColor red)		  
	(space sleep 1000)
	(robi translate 100 0)
	(space sleep 1000)
	(robi translate 0 50)
	(space sleep 1000)
	(robi translate -100 0)
	(space sleep 1000)
	(robi translate 0 -40) ) 
	
	
(space add robi (rect.class new))
(robi translate 130 50)
(robi setColor yellow)
(space add momo (oval.class new))
(momo setColor red)
(momo translate 80 80)
(space add pif (image.class new alien.gif))
(pif translate 100 0)
(space add hello (label.class new "Hello world"))
(hello translate 10 10)
(hello setColor black)

*/


import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



import graphicLayer.GBounded;
import graphicLayer.GContainer;
import graphicLayer.GElement;
import graphicLayer.GImage;
import graphicLayer.GOval;
import graphicLayer.GRect;
import graphicLayer.GSpace;
import graphicLayer.GString;
import stree.parser.SNode;
import stree.parser.SParser;
import svr_ex4.EnvironmentSimple;
import svr_ex4.Graph;
import svr_ex4.ReferenceSimple;
import tools.Tools;




class NewElement implements Command {
	public Reference run(Reference reference, SNode method) {
		try {
			@SuppressWarnings("unchecked")
			GElement e = ((Class<GElement>) reference.receiver).getDeclaredConstructor().newInstance();
			Reference ref = new Reference(e);
			ref.addCommand("setColor", new SetColor());
			ref.addCommand("translate", new Translate());
			ref.addCommand("setDim", new SetDim());
			ref.addCommand("addScript", new AddScript());
			ref.addCommand("add", new AddElement());
			ref.addCommand("del", new DelElement());
			ref.addCommand("clear", new Clear());
			return ref;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}



public class Execute {
	// Une seule variable d'instance
	GRect robi = new GRect();
	GSpace space;
	public static List<SNode> instructionSN = new ArrayList<SNode>();
	


	public static Environment environment = new Environment();
	public static Iterator<SNode> itor;
	public static boolean  exec = true;
	public EnvironmentSimple env = null;
	public List<Graph> graphes = new ArrayList<Graph>();
	

	public Execute() {
		space = new GSpace("Server Debug", new Dimension(400, 300));
		space.open();

		Reference spaceRef = new Reference(space);
		Reference rectClassRef = new Reference(GRect.class);
		Reference ovalClassRef = new Reference(GOval.class);
		Reference imageClassRef = new Reference(GImage.class);
		Reference stringClassRef = new Reference(GString.class);

		spaceRef.addCommand("setColor", new SetColor());
		spaceRef.addCommand("sleep", new Sleep());

		spaceRef.addCommand("add", new AddElement());
		spaceRef.addCommand("del", new DelElement());
		spaceRef.addCommand("clear", new Clear());
		spaceRef.addCommand("addScript", new AddScript());
		
		rectClassRef.addCommand("new", new NewElement());
		ovalClassRef.addCommand("new", new NewElement());
		imageClassRef.addCommand("new", new NewImage());
		stringClassRef.addCommand("new", new NewString());
 
		environment.addReference("space", spaceRef);
		environment.addReference("rect.class", rectClassRef);
		environment.addReference("oval.class", ovalClassRef);
		environment.addReference("image.class", imageClassRef);
		environment.addReference("label.class", stringClassRef);
		
		
	}
	public static List<SNode> getInstructionSN() {
		return instructionSN;
	}

	public static void setInstructionSN(List<SNode> instructionSN) {
		Execute.instructionSN = instructionSN;
	}
	
	/**
	 * 
	 * @return env EnvironmentSimple
	 */
	public EnvironmentSimple getEnv() {
		env = new EnvironmentSimple();
		Iterator<Entry<String, exercice4serv.Reference>> it = environment.references.entrySet().iterator();
		 while (it.hasNext()) {
			 Entry<String, exercice4serv.Reference> pair = (Entry<String, exercice4serv.Reference>)it.next();
			 String desc = "";;
			 if(pair.getValue().receiver instanceof graphicLayer.GRect) {
				 desc = "GRect";
			 }else if(pair.getValue().receiver instanceof graphicLayer.GSpace) {
				 desc = "GSpace";
			 }else if(pair.getValue().receiver instanceof graphicLayer.GImage) {
				 desc = "GImage";
			 }else if(pair.getValue().receiver instanceof graphicLayer.GString) {
				 desc = "GString";
			 }else if(pair.getValue().receiver instanceof graphicLayer.GOval) {
				 desc = "GOval";
			 }
			 if(desc ==""){
				 continue;
			 }
			 System.out.println("String : "+desc);
			 
			 ReferenceSimple r = new ReferenceSimple(desc);
			 env.references.put(pair.getKey(),r );
		 }
		 
		return env;
	}

	public void setEnv(EnvironmentSimple env) {
		this.env = env;
	}
	private void mainLoop() {
		while (true) {
			// prompt
			System.out.print("> ");
			// lecture d'une serie de s-expressions au clavier (return = fin de la serie)
			String input = Tools.readKeyboard();
			// creation du parser
			SParser<SNode> parser = new SParser<>();
			// compilation
			List<SNode> compiled = null;
			try {
				compiled = parser.parse(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// execution des s-expressions compilees
			Iterator<SNode> itor = compiled.iterator();
			while (itor.hasNext()) {
				new Interpreter().compute(environment, itor.next());
				((GSpace) environment.getReferenceByName("space").receiver).repaint();
			}
		}
	}
	public void scriptParser(String mode) {

		if (mode.equals("block")) {
			System.out.println("----------Block-EXEC----------");
			exec = true;
			if (instructionSN != null) {

				System.out.println("Size before exec : "+instructionSN.size());
				for(int i =0;i<instructionSN.size();i++) {
					new Interpreter().compute(environment, instructionSN.get(i));
				}
				instructionSN.clear();
				System.out.println("Size now : "+instructionSN.size());
				
			}
			System.out.println("----------End-Block-EXEC----------");
			
		} else if (mode.equals("step")) {
			exec = false;
			System.out.println("----------Step-EXEC----------");
			if (instructionSN != null) {
				itor = instructionSN.iterator();
				
				System.out.println("Size before exec : "+instructionSN.size());
				if (itor.hasNext()) {
					SNode node = itor.next();
					System.out.println("Executing on :"+ node.get(0).contents());
					new Interpreter().compute(environment, node);
					instructionSN.remove(0);
				
				}
				System.out.println("Size now : "+instructionSN.size());
				
				System.out.println("----------End-Step-EXEC----------");
			}
		}
		this.space.repaint();
		this.env = new EnvironmentSimple();

	}
	
	public void addScript(String script) {
		String[] scriptTab =  script.split("\n");
		SParser<SNode> parser = new SParser<>();
		for (String s : scriptTab) {
			try {
				instructionSN.addAll(parser.parse(s));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Size of inst :"+instructionSN.size());
	}
	
	
	public void getGraphes() {
		List<GElement> gelms = space.getElements();
		this.graphes.clear();
		
		Graph gr = new Graph(space);
		this.graphes.add(gr);
		addGElementtoGraph(gelms,null);
	}
	 
	public void addGElementtoGraph(List<GElement> gelms, GBounded ge) {
		for(GElement g : gelms) {		
			Graph grg = new Graph((GBounded) g,ge);
			this.graphes.add(grg);
			if(((GBounded) g).getSubElements().size()>0) {
				addGElementtoGraph(((GBounded) g).getSubElements(),(GBounded)g);
			};
		}
	}
	
	

	public String getImage() {
		return ((GSpace) environment.getReferenceByName("space").receiver).getImageBase64();
	}
	

}
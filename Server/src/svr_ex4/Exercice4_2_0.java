package svr_ex4;

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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import exercice4.AddElement;
import exercice4.AddScript;
import exercice4.Clear;
import exercice4.Command;
import exercice4.DelElement;
import exercice4.Environment;
import exercice4.Interpreter;
import exercice4.NewImage;
import exercice4.NewString;
import exercice4.Reference;
import exercice4.SetColor;
import exercice4.SetDim;
import exercice4.Sleep;
import exercice4.Translate;
import graphicLayer.GElement;
import graphicLayer.GImage;
import graphicLayer.GOval;
import graphicLayer.GRect;
import graphicLayer.GSpace;
import graphicLayer.GString;
import stree.parser.SNode;
import stree.parser.SParser;
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
			return ref;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}



public class Exercice4_2_0 {
	EnvironmentSimple env = null;
	// Une seule variable d'instance
	public List<SNode> rootNodes;
	public Environment environment = new Environment();
	public Exercice4_2_0 e4;
	public Exercice4_2_0() {
		GSpace space = new GSpace("Exercice 4", new Dimension(200, 100));
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
				// TODO Auto-generated catch block
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
	public void scriptParser(String script) {
		SParser<SNode> parser = new SParser<>();
		rootNodes = null;
		try {
			rootNodes = parser.parse(script);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<SNode> itor = rootNodes.iterator();
		while (itor.hasNext()) {
			new Interpreter().compute(environment,itor.next());
			((GSpace) environment.getReferenceByName("space").receiver).repaint();
		
		}
		env = new EnvironmentSimple();
		for (Map.Entry<String, Reference> set : this.environment.references.entrySet()) {
			//env.references.put(set.getKey(), new ReferenceSimple(set.getValue().receiver));

       }  
		//env.references = new ReferenceSimple() this.environment.references;
	}
	
	public Environment getEnvironment() {
		return this.environment;
	}
	
	public List<SNode> getListSNode() {
		return this.rootNodes;
	}
	/*
	public static void main(String[] args) {
		Exercice4_2_0 e4 = new Exercice4_2_0();
		
		//String script = "( space add robi (rect.class new ) )( space.robi addScript addRect (( self name w c )( self add name ( rect.class new ) )( self.name setColor c )( self.name setDim w w ) ) )( space.robi addRect mySquare 30 yellow )";
		String scrt ="(space addScript rectRed((self rec)(self add rec(rect.class new))(self.rec setColor red)))";		
		e4.scriptParser(scrt);
		e4.mainLoop();
		
	}*/

}
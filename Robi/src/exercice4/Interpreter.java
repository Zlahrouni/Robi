package exercice4;

import stree.parser.SNode;

public class Interpreter {

	public void compute(Environment environment, SNode next) {	
		if(!next.hasChildren()) {
			System.out.println("Problèmme de syntaxe !");
			return;
		}
		if(environment.references.containsKey(next.get(0).contents())) {
			environment.references.get(next.get(0).contents()).run(next);
		} else {
			System.out.println("L'objet graphique : "+next.get(0).contents()+" n'existe pas ! ");
		}
			 
	}

}

package exercice5.examples;

import exercice4.Exercice4_2_0;

//import exercice5.Exercice5;

public class Example1 {
	
	/* 
	 * Ajoute un rectangle robi avec ses propriétés par défaut
	 * On doit voir un rectangle bleu en (0,0)
	 * 
	 */
	String script = "(space add robi (Rect new))";
	// on ajoute un peu de tout avec de l'imbrication etc  et on utilise les commandes
	String script2 ="(space add robi(rect.class new))(space setColor orange)(space.robi setColor pink)(space.robi setDim 100 50)(space.robi translate 20 20)(space.robi add oval(oval.class new))(space setColor magenta)(space add robo(rect.class new))(space.robo setColor green)(space.robo translate 50 0)(space.robo add rec(rect.class new))(space.robo.rec translate 10 10)(space.robi add label(label.class new Une phrase))(space.robi.label setColor red)(space.robi.label translate 0 10)(space sleep 1000)(space del space.robo)(space sleep 2000)(space.robi clear)(space sleep 1000)(space clear)";
	// exo 6 script qui créé un cercle bleu dans un carré orange puis déplace le carré	sur un fond vert
	String scriptScript = "(space addScript demo((self rec ova)(self add rec(rect.class new))(self.rec translate 20 20)(self.rec setColor orange)(self.rec setDim 40 40)(self.rec add ova(oval.class new))(self setColor green)))";
	// commande d'execution du script
	String exeScript = "space demo robi oval";
	
	public  void launch() {
		Exercice4_2_0 ex4 = new Exercice4_2_0();
		//ex4.scriptParser(script);
		//ex4.scriptParser(script2);
		//ex4.scriptParser(scriptScript);
		//ex4.scriptParser(exeScript);
	}
	
	public static void main(String[] args) {
		new Example1().launch();
	}
}
  
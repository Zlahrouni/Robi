package exercice4serv;



import java.util.ArrayList;
import java.util.List;

import stree.parser.SNode;
import stree.parser.SPrinter;
import svr_ex4.Graph;

public class RunScript implements Command{

	 
	@Override
	public Reference run(Reference receiver, SNode expr) {
		String s = expr.get(1).contents();
		SNode scr = receiver.getScriptByName(s);
		SNode params = scr.get(0);
		List<SNode> torun = new ArrayList<SNode>();
		for(int i =1;i<scr.children().size();i++) {

			SNode sn = scr.get(i);
			sn = replaceSnodeContent(sn,params.get(0), expr.get(0));
			for(int j = 2;j<params.children().size()+1;j++) {
				sn = replaceSnodeContent(sn,params.get(j-1), expr.get(j));
			}

			torun.add(sn);
			
			//new Interpreter().compute(Execute.environment,sn);
		}
		//Execute.itor = Execute.instructionSN.iterator();
		Execute.instructionSN.addAll(torun);
		SPrinter printerz = new SPrinter();
		scr.accept(printerz);	
		Execute.environment.references.remove("self");
		return receiver;
	}
	
	/**
	 * Fonction récursive qui remplace le contenu de feuille d'une SNode par d'autre
	 * 
	 * @origine SNode d'origine dont le contenu doit être modifié 
	 * @param Le parametre cible qui va être modifié
	 * @newContent La SNode qui va remplacer le parametre 
	 * @return Le SNode mis à jour 
	 */
	private SNode replaceSnodeContent(SNode origine,SNode param, SNode newContent) {
		if(origine.hasChildren()) {
			for(int i = 0; i<origine.size();i++) {
				origine.get(i).setContents(replaceSnodeContent(origine.get(i),param,newContent).contents());			
			}
			return origine;
		} else {
			if(origine.contents().equals(param.contents())) {
				return newContent;
			} else {
				if(origine.contents().indexOf('.') ==-1) {
					return origine;
				}
				String g = origine.contents();
				String[] exp = g.split("\\.");
				String r = "";
				for(int i = 0;i<exp.length;i++) {
					if(exp[i].equals(param.contents())) {
						exp[i] = newContent.contents();
					}
					r += exp[i]+".";
				}
				r = r.substring(0, r.lastIndexOf('.'));
				origine.setContents(r);
				return origine;
			}
		}
	}
}

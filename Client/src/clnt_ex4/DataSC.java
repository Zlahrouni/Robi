package clnt_ex4;

import java.io.Serializable;
import java.util.List;

import stree.parser.SNode;

/**
 * La classe DataSC est une classe qui contient les informations échangées entre
 * un client et un serveur . C'est les informations envoyées du serveur vers le
 * client. Elle implémente l'interface Serializable pour être transmise sous
 * forme de JSON.
 * 
 * @author Hanane Erraji
 * @author Ziad Lahrouni
 * @author Youenn Robitzer
 * @author Gwendal Le Tareau
 *
 */
public class DataSC implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * La commande envoyer au client.
	 */
	private String cmd;
 
	/**
	 * L'environment du space
	 */
	private EnvironmentSimple env;

	/**
	 * Les instruction executées
	 */
	private List<String> instructionSN;

	/**
	 * Contient un message informatif au cas où il y a une erreur
	 */
	private String message;

	/**
	 * Image de l'environment en 64 bits
	 */
	private List<Graph> graph;

	/**
	 * Constructeur de la classe DataSC. Initialise le champ "cmd" à une chaîne de
	 * caractères vide, le champ "env" à null et le champ "message" à une chaîne de
	 * caractères vide.
	 */
	public DataSC() {
		this.cmd = "";
		this.env = null;

		this.message = "";
	}

	/**
	 * Permet de recuperer l'image de l'environment
	 * 
	 * @return image codé en 64 bits
	 */
	public List<Graph> getGraph() {
		return graph;
	}

	/**
	 * Permet definir l'image a envoyé
	 * 
	 * @param img
	 */
	public void setGraph(List<Graph> img) {
		this.graph = img;
	}

	/**
	 * Retourne la commande actuelle.
	 * 
	 * @return la commande actuelle.
	 */
	public String getCmd() {
		return this.cmd;
	}

	/**
	 * Modifie la commande actuelle.
	 * 
	 * @param cmd La nouvelle commande.
	 */
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	/**
	 * Retourne l'environnement courant.
	 * 
	 * @return l'environnement courant.
	 */
	public EnvironmentSimple getEnv() {
		return this.env;
	}

	/**
	 * Modifie l'environnement courant
	 * 
	 * @param env2 Le nouvel environnement.
	 */
	public void setEnv(EnvironmentSimple env2) {
		this.env = env2;
	}

	/**
	 * Retourne le message d'erreur associé.
	 * 
	 * @return le message d'erreur associé.
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Modifie le message d'erreur associé.
	 * 
	 * @param message Le nouveau message d'erreur.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Retourne la liste des instructions à exécuter.
	 * 
	 * @return a liste des instructions à exécuter.
	 */
	public List<String> getInstructionSN() {
		return instructionSN;
	}

	/**
	 * Modifie la liste des instructions à exécuter.
	 * 
	 * @param instructionSN La nouvelle liste d'instructions à exécuter
	 */
	public void setInstructionSN(List<String> instructionSN) {
		this.instructionSN = instructionSN;
	}

}

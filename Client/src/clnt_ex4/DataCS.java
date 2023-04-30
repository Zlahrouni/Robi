package clnt_ex4;

import java.io.Serializable;

/**
 * Cette classe représente les données à envoyer au serveur.
 */
public class DataCS implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cmd;
    private String param;
    
    /**
     * Constructeur par défaut qui initialise les attributs à une chaîne vide.
     */
    public DataCS() {
        cmd = "";
        param = "";
    }

    /**
     * Récupère le mode d'envoi.
     * @return Le mode d'envoi (prog ou exec).
     */
    public String getCmd() {
        return cmd;
    }
    
    /**
     * Définit le mode d'envoi. Si le mode est "prog", les données sont envoyées sans attente de réponse.
     * Si le mode est "exec", les données sont envoyées pour être exécutées comme des S-expressions.
     * @param cmd Le mode d'envoi (prog ou exec).
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * Récupère les S-expressions à envoyer au serveur.
     * @return Les S-expressions à envoyer.
     */
    public String getParam() {
        return param;
    }

    /**
     * Définit les S-expressions à envoyer au serveur.
     * @param param La chaîne de caractères contenant la ou les S-expressions à envoyer.
     */
    public void setParam(String param) {
        this.param = param;
    }
}

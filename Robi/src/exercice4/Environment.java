package exercice4;

import java.io.Serializable;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Cette classe représente un environnement contenant une collection de références nommées.
 * 
 * @author Hanane Erraji
 */
public class Environment implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
     * HashMap de références, associant chaque référence à un nom de clé donné en chaîne de caractères.
     */
    public HashMap<String, Reference> references = new HashMap<String, Reference>();
	
    /**
     * Ajoute une référence dans la collection de références de l'environnement, en associant la référence à un nom de clé donné en chaîne de caractères.
     * 
     * @param name le nom de la clé associée à la référence.
     * @param ref la référence à ajouter dans la collection.
     */
    @JsonIgnore
    public void addReference(String name, Reference ref) {
        references.put(name, ref);
    }
    
    // Supprime toutes les références sauf celle avec la clé "space".
    @JsonIgnore
    public void deleteAllOutOfSpace()
    {
    	references.forEach((key, value) -> {
    		if (key != "space")
    		{
    			references.remove(key);
    		}
    	});
    	
    }
	 
    /**
     * Récupère une référence à partir de son nom de clé donné en chaîne de caractères.
     * 
     * @param name le nom de la clé associée à la référence à récupérer.
     * @return la référence associée à la clé de nom donné, ou null si la clé n'existe pas dans la collection de références de l'environnement.
     */
    public Reference getReferenceByName(String name) {
        if (references.containsKey(name)) {
            return references.get(name);
        } else {
            return null;
        }
    }
}

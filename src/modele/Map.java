/*
 * 
 * 
 * 
 */
package modele;

import gameplay.map.Type;
import java.io.File;
import vue.popup.Dialogues;

/**
 * Map.java
 *
 */
public class Map {

	public String nom;
	public String description;
	public String auteur;	//Pseudo
	public int nbrEquipes;	//0 = chacun pour soi
	public int joueursParEquipe;	//3 = 3v3, 4 = 4v4 etc
	public int difficulte;
	public Type[][] tuiles;
	public File background;
	public String version;

	public Map(Dialogues.MapData mdata) {
		this(mdata.nom, mdata.description, mdata.auteur, mdata.nbrEquipes, mdata.joueursParEquipe, mdata.difficulte, mdata.width, mdata.height, "0.0.1");
	}
	
	public Map(String nom, String description, String auteur, int nbrEquipes, int joueursParEquipe, int difficulte, int width, int height, String version) {
		this.nom = nom;
		this.description = description;
		this.auteur = auteur;
		this.nbrEquipes = nbrEquipes;
		this.joueursParEquipe = joueursParEquipe;
		this.difficulte = difficulte;
		this.background = null;
		this.version = version;
		this.tuiles = new Type[height][width];
		for(Type[] colonne : tuiles) {
			for(int i = 0; i < colonne.length; i++) {
				colonne[i] = Type.SIMPLE;
			}
		}
	}
	
	public Map(String nom, String description, String auteur, int nbrEquipes, int joueursParEquipe, int difficulte, Type[][] tuiles, String version) {
		this.nom = nom;
		this.description = description;
		this.auteur = auteur;
		this.nbrEquipes = nbrEquipes;
		this.joueursParEquipe = joueursParEquipe;
		this.difficulte = difficulte;
		this.background = null;
		this.version = version;
		this.tuiles = tuiles;
	}

}

/*
 * 
 * 
 * 
 */
package gameplay.map;

import java.io.File;
import java.io.Serializable;

/**
 * MapSerializable.java
 * EDITEUR DE MAP
 */
public class MapSerializable implements Serializable {

	private static final long serialVersionUID = 1350092881346723535L;

	public final String nom;
	public final String description;
	public final String auteur;	//Pseudo
	public final int nbrEquipes;	//0 = chacun pour soi
	public final int joueursParEquipe;	//3 = 3v3, 4 = 4v4 etc
	public final int difficulte;
	public final Type[][] tuiles;
	public final File background;
	//TODO foreground

	public MapSerializable(String nom, String description, String auteur, Type[][] tuiles, File background, 
			int nbrEquipes, int joueursParEquipe, int difficulte) {
		this.nom = nom;
		this.description = description;
		this.auteur = auteur;
		this.tuiles = tuiles;
		this.background = background;
		this.nbrEquipes = nbrEquipes;
		this.joueursParEquipe = joueursParEquipe;
		this.difficulte = difficulte;
	}

}

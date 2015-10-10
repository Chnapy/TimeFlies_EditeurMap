/*
 * 
 * 
 * 
 */
package modele;

import gameplay.map.MapSerializable;
import gameplay.map.Type;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import javafx.geometry.Point2D;
import vue.popup.Dialogues.MapData;
import vue.tuiles.TuileView;

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
	
	//Non serializé
	public TuileView[][] view;
	public ArrayList actions;
	public int actionIndex;
	public String prefKey = null;

	public Map(MapSerializable mser) {
		this(mser.nom, mser.description, mser.auteur, mser.nbrEquipes, mser.joueursParEquipe, mser.difficulte, mser.tuiles, mser.version);
	}

	public Map(MapData mdata) {
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
		for (Type[] colonne : tuiles) {
			for (int i = 0; i < colonne.length; i++) {
				colonne[i] = Type.SIMPLE;
			}
		}
		this.view = new TuileView[tuiles.length][tuiles[0].length];
		for (int y = 0; y < view.length; y++) {
			for (int x = 0; x < view[0].length; x++) {
				view[y][x] = new TuileView(tuiles[y][x], new Point2D(x, y));
			}
		}
		actions = new ArrayList();
		actionIndex = -1;
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
		this.view = new TuileView[tuiles.length][tuiles[0].length];
		for (int y = 0; y < view.length; y++) {
			for (int x = 0; x < view[0].length; x++) {
				view[y][x] = new TuileView(tuiles[y][x], new Point2D(x, y));
			}
		}
		actions = new ArrayList();
		actionIndex = -1;
	}

	public static void save(MapSerializable maps, File file) throws IOException {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file))) {
			output.writeObject(maps);
		}
	}

	public MapSerializable getSerializable() {
		return new MapSerializable(nom, description, auteur, tuiles, background, nbrEquipes, joueursParEquipe, difficulte, version);
	}

	public static Map fileToMap(File file) throws IOException, ClassNotFoundException {
		MapSerializable smap;
		try (ObjectInputStream inputFile = new ObjectInputStream(new FileInputStream(file))) {
			smap = (MapSerializable) inputFile.readObject();
		}
		return new Map(smap);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + Objects.hashCode(this.nom);
		hash = 89 * hash + Objects.hashCode(this.description);
		hash = 89 * hash + Objects.hashCode(this.auteur);
		hash = 89 * hash + this.nbrEquipes;
		hash = 89 * hash + this.joueursParEquipe;
		hash = 89 * hash + this.difficulte;
		hash = 89 * hash + Arrays.deepHashCode(this.tuiles);
		hash = 89 * hash + Objects.hashCode(this.background);
		hash = 89 * hash + Objects.hashCode(this.version);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Map other = (Map) obj;
		if (!Objects.equals(this.nom, other.nom)) {
			return false;
		}
		if (!Objects.equals(this.description, other.description)) {
			return false;
		}
		if (!Objects.equals(this.auteur, other.auteur)) {
			return false;
		}
		if (this.nbrEquipes != other.nbrEquipes) {
			return false;
		}
		if (this.joueursParEquipe != other.joueursParEquipe) {
			return false;
		}
		if (this.difficulte != other.difficulte) {
			return false;
		}
		if (!Arrays.deepEquals(this.tuiles, other.tuiles)) {
			return false;
		}
		if (!Objects.equals(this.background, other.background)) {
			return false;
		}
		return Objects.equals(this.version, other.version);
	}

	public void tuileviewToTypes() {
		tuiles = new Type[view.length][view[0].length];
		for (int y = 0; y < view.length; y++) {
			for (int x = 0; x < view[0].length; x++) {
				tuiles[y][x] = view[y][x].getType();
			}
		}
	}

}

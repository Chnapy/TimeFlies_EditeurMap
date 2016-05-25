/*
 * 
 * 
 * 
 */
package modele;

import Serializable.HorsCombat.HorsCombat.TypeCombat;
import Serializable.HorsCombat.Map.MapSerializable;
import Serializable.HorsCombat.Map.PosPlacement;
import Serializable.HorsCombat.Map.TypeTuile;
import Serializable.Position;
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
	public long idCreateur;
	public TypeCombat typeCombat;
	public int nbrEquipes;
	public int joueursParEquipe;	//3 = 3v3, 4 = 4v4 etc
	public int difficulte;
	public TypeTuile[][] tuiles;
	public final ArrayList<PosPlacement> placement;
	public File background;
	public int versionMajeure;
	public int versionMineure;

	//Non serializé
	public TuileView[][] view;
	public ArrayList actions;
	public int actionIndex;
	public String prefKey = null;

	public Map(MapSerializable mser) {
		this(mser.nom, mser.description, mser.idCreateur, mser.typeCombat, mser.nbrEquipes, mser.joueursParEquipe, mser.difficulte, mser.tuiles, mser.placement, mser.versionMajeure, mser.versionMineure);
	}

	public Map(MapData mdata) {
		this(mdata.nom, mdata.description, mdata.idCreateur, mdata.typeCombat, mdata.nbrEquipes, mdata.joueursParEquipe, mdata.difficulte, mdata.width, mdata.height, 0, 1);
	}

	public Map(String nom, String description, long idCreateur, TypeCombat typeCombat, int nbrEquipes, int joueursParEquipe, int difficulte, int width, int height, int versionMajeure, int versionMineure) {
		this.nom = nom;
		this.description = description;
		this.idCreateur = idCreateur;
		this.typeCombat = typeCombat;
		this.nbrEquipes = nbrEquipes;
		this.joueursParEquipe = joueursParEquipe;
		this.difficulte = difficulte;
		this.background = null;
		this.versionMajeure = versionMajeure;
		this.versionMineure = versionMineure;
		this.tuiles = new TypeTuile[height][width];
		this.placement = new ArrayList();
		for (TypeTuile[] colonne : tuiles) {
			for (int i = 0; i < colonne.length; i++) {
				colonne[i] = TypeTuile.SIMPLE;
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

	public Map(String nom, String description, long idCreateur, TypeCombat typeCombat, int nbrEquipes, int joueursParEquipe, int difficulte, TypeTuile[][] tuiles, ArrayList<PosPlacement> placement, int versionMajeure, int versionMineure) {
		this.nom = nom;
		this.description = description;
		this.idCreateur = idCreateur;
		this.typeCombat = typeCombat;
		this.nbrEquipes = nbrEquipes;
		this.joueursParEquipe = joueursParEquipe;
		this.difficulte = difficulte;
		this.background = null;
		this.versionMajeure = versionMajeure;
		this.versionMineure = versionMineure;
		this.tuiles = tuiles;
		this.placement = placement;
		this.view = new TuileView[tuiles.length][tuiles[0].length];
		for (int y = 0; y < view.length; y++) {
			for (int x = 0; x < view[0].length; x++) {
				view[y][x] = new TuileView(tuiles[y][x], new Point2D(x, y));
			}
		}
		placement.forEach((p) -> {
			view[p.position.y][p.position.x].setNumEquipe(p.numEquipe);
		});
		actions = new ArrayList();
		actionIndex = -1;
	}

	public static void save(MapSerializable maps, File file) throws IOException {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file))) {
			output.writeObject(maps);
		}
	}

	public MapSerializable getSerializable() {
		return new MapSerializable(nom, description, idCreateur, typeCombat, nbrEquipes, joueursParEquipe, difficulte, tuiles, placement, background, versionMajeure, versionMineure);
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
		int hash = 5;
		hash = 83 * hash + Objects.hashCode(this.nom);
		hash = 83 * hash + Objects.hashCode(this.description);
		hash = 83 * hash + (int) (this.idCreateur ^ (this.idCreateur >>> 32));
		hash = 83 * hash + Objects.hashCode(this.typeCombat);
		hash = 83 * hash + this.nbrEquipes;
		hash = 83 * hash + this.joueursParEquipe;
		hash = 83 * hash + this.difficulte;
		hash = 83 * hash + Arrays.deepHashCode(this.tuiles);
		hash = 83 * hash + Objects.hashCode(this.background);
		hash = 83 * hash + this.versionMajeure;
		hash = 83 * hash + this.versionMineure;
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
		if (!Objects.equals(this.idCreateur, other.idCreateur)) {
			return false;
		}
		if (!Objects.equals(this.typeCombat, other.typeCombat)) {
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
		if (!Objects.equals(this.placement, other.placement)) {
			return false;
		}
		if (!Arrays.deepEquals(this.tuiles, other.tuiles)) {
			return false;
		}
		if (!Objects.equals(this.background, other.background)) {
			return false;
		}
		if (!Objects.equals(this.versionMajeure, other.versionMajeure)) {
			return false;
		}
		return Objects.equals(this.versionMineure, other.versionMineure);
	}

	public void tuileviewToTypes() {
		tuiles = new TypeTuile[view.length][view[0].length];
		for (int y = 0; y < view.length; y++) {
			for (int x = 0; x < view[0].length; x++) {
				tuiles[y][x] = view[y][x].getType();
				if (view[y][x].getNumEquipe() != -1) {
					placement.add(new PosPlacement(
							new Position(x, y), view[y][x].getNumEquipe()));
				}
			}
		}
	}

}

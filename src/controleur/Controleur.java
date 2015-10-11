/*
 * 
 * 
 * 
 */
package controleur;

import gameplay.map.MapSerializable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.scene.control.Alert;
import modele.Map;
import vue.ExceptionHandler;
import vue.Vue;
import vue.main.Outil;
import vue.outils.OutilsWindow.Boutons;
import vue.popup.Dialogues;
import vue.popup.Dialogues.MapData;
import vue.tuiles.TuileView;

/**
 * Controleur.java
 *
 */
public class Controleur {

	private final Preferences prefs;
	private final Vue vue;
	private Map map;
	private int prefIndex;

	public Controleur(String[] paths) {
		prefs = Preferences.userNodeForPackage(Controleur.class);
		vue = new Vue(this);
		map = null;

		vue.tuiles.getbTuiles()[0].fire();
		tuiler();

		vue.show();

		try {
			chargerPreferences();
		} catch (BackingStoreException ex) {
			ExceptionHandler.handle(ex);
		}
		List<File> files = new ArrayList();
		for (String p : paths) {
			files.add(new File(p));
		}
		ouvrir(files);
	}

	private void chargerPreferences() throws BackingStoreException {
		List<File> files = new ArrayList();
		String temp;
		int length = prefs.keys().length;
		for (int i = 0; i < length; i++) {
			temp = prefs.get("map" + i, null);
			if (temp == null) {
				break;
			}
			files.add(new File(temp));
			prefs.remove("map" + i);
		}
		prefIndex = -1;
		ouvrir(files);
	}

	public void notifyOutils(Boutons bouton) {
		switch (bouton) {
			case NOUVEAU:
				nouveau();
				break;
			case OUVRIR:
				List<File> files = Dialogues.getFiles();
				ouvrir(files);
				break;
			case SAUVEGARDER:
				if (map != null) {
					sauvegarder(map);
				}
				break;
			case SAUVEGARDERTOUT:
				if (map != null) {
					vue.liste.notSaved().forEach((m) -> sauvegarder(m));
					vue.outils.saveAll();
				}
				break;
			case ANNULER:
				if (map != null) {
					annuler();
				}
				break;
			case REFAIRE:
				if (map != null) {
					refaire();
				}
				break;
			case TUILER:
				tuiler();
				break;
			case REMPLIR:
				remplir();
				break;
		}
	}

	public void nouveau() {
		MapData mdata = vue.nouveau();
		if (mdata == null) {
			return;
		}

		setMap(new Map(mdata));
	}

	public final void ouvrir(List<File> files) {
		if (files == null || files.isEmpty()) {
			return;
		}
		Map m;
		try {
			m = Map.fileToMap(files.get(0));
			if (vue.liste.isset(m)) {
				Dialogues.mapChargee(m.nom);
			} else {
				setMap(m);
				vue.liste.setPath(map, files.get(0).getPath());
				prefIndex++;
				map.prefKey = "map" + prefIndex;
				prefs.put(map.prefKey, files.get(0).getPath());
			}
		} catch (Exception ex) {
			Dialogues.openFail(files.get(0).getAbsolutePath(), ex);
		}

		for (int i = 1; i < files.size(); i++) {
			try {
				m = Map.fileToMap(files.get(i));
				if (vue.liste.isset(m)) {
					Dialogues.mapChargee(m.nom);
				} else {
					vue.liste.addMap(m);
					vue.liste.setPath(m, files.get(i).getPath());
					prefIndex++;
					m.prefKey = "map" + prefIndex;
					prefs.put(m.prefKey, files.get(i).getPath());
				}
			} catch (Exception ex) {
				Dialogues.openFail(files.get(i).getAbsolutePath(), ex);
			}
		}
	}

	public void sauvegarder(Map map) {
		String path = vue.liste.getPath(map);
		File file;

		if (path != null) {
			file = new File(path);
		} else {
			file = Dialogues.saveFile(map.nom);

			if (file == null) {
				return;
			}

			path = file.getPath();
			vue.liste.setPath(map, path);
		}

		map.tuileviewToTypes();	//BUG
		MapSerializable maps = map.getSerializable();

		try {
			Map.save(maps, file);
			vue.outils.save(map);
			vue.liste.save(map);
			if (map.prefKey == null) {
				prefIndex++;
				map.prefKey = "map" + prefIndex;
				prefs.put(map.prefKey, path);
			}
		} catch (IOException ex) {
			Dialogues.alert("Sauvegarde du fichier impossible !", "Avez vous les droits nécessaire ?", Alert.AlertType.WARNING);
		}
	}

	private void tuiler() {
		Outil.setEtat(Outil.Etat.TUILE);
	}

	public void remplir() {
		Outil.setEtat(Outil.Etat.REMPLISSAGE);
	}

	public void annuler() {
		if (map.actionIndex >= 0) {
			restaurer(map.actions.get(map.actionIndex));
			map.actionIndex--;
			vue.outils.canRefaire(true);
			if (map.actionIndex < 0) {
				vue.outils.canAnnuler(false);
			}
		}
	}

	public void refaire() {
		if (map.actions.size() > 0 && map.actionIndex < map.actions.size() - 1) {
			map.actionIndex++;
			restaurer(map.actions.get(map.actionIndex));
			vue.outils.canAnnuler(true);
			if (map.actionIndex >= map.actions.size() - 1) {
				vue.outils.canRefaire(false);
			}
		}
	}

	private void restaurer(Object action) {
		if (action instanceof TuileView) {
			TuileView toAdd = (TuileView) action;
			TuileView toRemove = vue.main.mapTuiles.getTabPoly()[(int) toAdd.getCoor().getY()][(int) toAdd.getCoor().getX()];

			map.actions.set(map.actionIndex, toRemove);
			vue.main.mapTuiles.getChildren().remove(toRemove);
			vue.main.mapTuiles.getTabPoly()[(int) toAdd.getCoor().getY()][(int) toAdd.getCoor().getX()] = toAdd;
			vue.main.mapTuiles.getChildren().add(toAdd);
		} else if (action instanceof TuileView[][]) {
			TuileView[][] toAdd = (TuileView[][]) action;
			TuileView[][] toRemove = vue.main.mapTuiles.getCopyTabPoly();
			map.actions.set(map.actionIndex, toRemove);
			vue.main.mapTuiles.setTabPoly(toAdd);
		}
	}

	public void action(TuileView tuile) {
		vue.outils.modif(map);
		vue.liste.modif(map);
		if (map.actions.size() >= 16) {
			map.actions.remove(0);
		}
		while (map.actionIndex < map.actions.size() - 1) {
			map.actions.remove(map.actions.size() - 1);
		}
		switch (Outil.getEtat()) {
			case TUILE:
				map.actions.add(tuile.copy());
				break;
			case REMPLISSAGE:
				map.actions.add(vue.main.mapTuiles.getCopyTabPoly());
				break;
		}
		map.actionIndex = map.actions.size() - 1;
	}

	public void calques(boolean background, boolean tuiles, boolean foreground, boolean grille) {
		vue.main.background.setVisible(background);
		vue.main.mapTuiles.setVisible(tuiles);
		vue.main.foreground.setVisible(foreground);
		vue.main.grille.setVisible(grille);
	}

	public void setMap(Map map) {
		long st = System.currentTimeMillis();
		this.map = map;
		vue.nouvelleMap(map);
		System.out.println("Temps affichage de " + map.nom + " : " + (System.currentTimeMillis() - st) + "ms");
	}

	public void key(String key) {
		vue.tuiles.keyAction(key);
	}

}

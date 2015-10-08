/*
 * 
 * 
 * 
 */
package controleur;

import java.util.ArrayList;
import modele.Connexion;
import modele.Map;
import vue.Vue;
import vue.main.Outil;
import vue.outils.OutilsWindow.Boutons;
import vue.popup.Dialogues.MapData;
import vue.tuiles.TuileView;

/**
 * Controleur.java
 *
 */
public class Controleur {

	private final Vue vue;
	private Map map;

	private final ArrayList actions;
	private int actionIndex;

	public Controleur() {
		actions = new ArrayList();
		actionIndex = -1;
		vue = new Vue(this);
		map = null;

		vue.show();
		vue.tuiles.getbTuiles()[0].fire();
		tuiler();

		map = new Map(new MapData("toto", "tata", Connexion.getLogin(), 4, 5, 4, 12, 12));
		vue.nouvelleMap(map);
	}

	public void notifyOutils(Boutons bouton) {
		switch (bouton) {
			case NOUVEAU:
				nouveau();
				break;
			case OUVRIR:
				ouvrir();
				break;
			case SAUVEGARDER:
				sauvegarder();
				break;
			case ANNULER:
				annuler();
				break;
			case REFAIRE:
				refaire();
				break;
			case TUILER:
				tuiler();
				break;
//			case SELECTIONNER:
//				selectionner();
//				break;
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

		map = new Map(mdata);
		vue.nouvelleMap(map);
	}

	public void ouvrir() {
	}

	public void sauvegarder() {
	}

	private void tuiler() {
		Outil.setEtat(Outil.Etat.TUILE);
	}

	public void remplir() {
		Outil.setEtat(Outil.Etat.REMPLISSAGE);
	}

	public void annuler() {
		System.out.println(actions.size() + " " + actionIndex);
		if (actionIndex >= 0) {
			restaurer(actions.get(actionIndex));
			actionIndex--;
		}
	}

	public void refaire() {
		System.out.println(actions.size() + " " + actionIndex);
		if (actions.size() > 0 && actionIndex < actions.size() - 1) {
			actionIndex++;
			System.out.println(actions.get(actionIndex));
			restaurer(actions.get(actionIndex));
		}
	}

	private void restaurer(Object action) {
		if (action instanceof TuileView) {
			TuileView toAdd = (TuileView) action;
			TuileView toRemove = vue.main.mapTuiles.getTabPoly()[(int) toAdd.getCoor().getY()][(int) toAdd.getCoor().getX()];
			
			actions.set(actionIndex, toRemove);
			vue.main.mapTuiles.getChildren().remove(toRemove);
			vue.main.mapTuiles.getTabPoly()[(int) toAdd.getCoor().getY()][(int) toAdd.getCoor().getX()] = toAdd;
			vue.main.mapTuiles.getChildren().add(toAdd);
		} else if (action instanceof TuileView[][]) {
			TuileView[][] toAdd = (TuileView[][]) action;
			TuileView[][] toRemove = vue.main.mapTuiles.getCopyTabPoly();
			System.out.println(toAdd[0][0].getType() + " " + toRemove[0][0].getType());
			actions.set(actionIndex, toRemove);
			vue.main.mapTuiles.setTabPoly(toAdd);
		}
	}

	public void action(TuileView tuile) {
		vue.liste.modif(map);
		if (actions.size() >= 16) {
			actions.remove(0);
		}
		while(actionIndex < actions.size() - 1) {
			actions.remove(actions.size() - 1);
		}
		switch (Outil.getEtat()) {
			case TUILE:
				actions.add(tuile.copy());
				break;
			case REMPLISSAGE:
				actions.add(vue.main.mapTuiles.getCopyTabPoly());
				break;
		}
		actionIndex = actions.size() - 1;
	}

	public void calques(boolean background, boolean tuiles, boolean foreground, boolean grille) {
		vue.main.background.setVisible(background);
		vue.main.mapTuiles.setVisible(tuiles);
		vue.main.foreground.setVisible(foreground);
		vue.main.grille.setVisible(grille);
	}

}

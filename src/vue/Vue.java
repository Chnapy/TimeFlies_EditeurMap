/*
 * 
 * 
 * 
 */
package vue;

import controleur.Controleur;
import modele.Map;
import vue.calques.CalquesWindow;
import vue.liste_cartes.ListeWindow;
import vue.main.MainWindow;
import vue.outils.OutilsWindow;
import vue.placement.PlacementWindow;
import vue.popup.Dialogues;
import vue.popup.Dialogues.MapData;
import vue.proprietes.ProprietesWindow;
import vue.tuiles.TuilesWindow;

/**
 * Vue.java
 *
 */
public class Vue {

	public final MainWindow main;

	public final OutilsWindow outils;
	public final CalquesWindow calque;
	public final ListeWindow liste;
	public final TuilesWindow tuiles;
	public final ProprietesWindow proprietes;
	public final PlacementWindow placement;

	public Vue(Controleur controleur) {
		outils = new OutilsWindow(controleur);
		calque = new CalquesWindow(controleur);
		liste = new ListeWindow(controleur);
		tuiles = new TuilesWindow(controleur);
		proprietes = new ProprietesWindow(controleur);
		placement = new PlacementWindow(controleur);

		main = new MainWindow(controleur, outils, calque, liste, tuiles, 
				proprietes, placement);
	}

	public void show() {
		outils.show();
		main.show();
		calque.show();
		liste.show();
		tuiles.show();
		proprietes.show();
	}

	public MapData nouveau() {
		return Dialogues.nouvelleMap();
	}

	public void nouvelleMap(Map map) {
		proprietes.setMap(map);
		main.setMap(map);
		outils.reset(map);
		calque.reset();
		liste.nouvelleMap(map);
		tuiles.reset();
		placement.setMap(map);
	}

}

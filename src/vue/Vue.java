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

	public Vue(Controleur controleur) {
		outils = new OutilsWindow(controleur);
		calque = new CalquesWindow(controleur);
		liste = new ListeWindow(controleur);
		tuiles = new TuilesWindow(controleur);
		proprietes = new ProprietesWindow(controleur);

		main = new MainWindow(controleur, outils, calque, liste, tuiles, proprietes);
	}

	public void show() {
		main.show();
		outils.show();
		calque.show();
		liste.show();
		tuiles.show();
		proprietes.show();
	}

	public MapData nouveau() {
		return Dialogues.nouvelleMap();
	}

	public void nouvelleMap(Map map) {
		main.setMap(map);
		outils.reset();
		calque.reset();
		liste.nouvelleMap(map);
		tuiles.reset();
		proprietes.setMap(map);
	}

}

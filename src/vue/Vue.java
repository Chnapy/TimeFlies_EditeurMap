/*
 * 
 * 
 * 
 */
package vue;

import vue.calques.CalquesWindow;
import vue.liste_cartes.ListeWindow;
import vue.main.MainWindow;
import vue.outils.OutilsWindow;
import vue.tuiles.TuilesWindow;

/**
 * Vue.java
 *
 */
public class Vue {

	private final MainWindow main;

	private final OutilsWindow outils;
	private final CalquesWindow calque;
	private final ListeWindow liste;
	private final TuilesWindow tuiles;

	public Vue() {
		outils = new OutilsWindow();
		calque = new CalquesWindow();
		liste = new ListeWindow();
		tuiles = new TuilesWindow();

		main = new MainWindow(outils, calque, liste, tuiles);
	}

	public void show() {
		main.show();
		outils.show();
		calque.show();
		liste.show();
		tuiles.show();
	}

}

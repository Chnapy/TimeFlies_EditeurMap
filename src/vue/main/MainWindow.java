/*
 * 
 * 
 * 
 */
package vue.main;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Screen;
import javafx.stage.Stage;
import vue.Module;
import vue.calques.CalquesWindow;
import vue.liste_cartes.ListeWindow;
import vue.outils.OutilsWindow;
import vue.tuiles.TuilesWindow;

/**
 * MainWindow.java
 *
 */
public class MainWindow extends Stage {

	private final static double SCREEN_COEFF = (double) 3.5 / 5;

	private final Scene scene;
	private final Group root;

	private final Module[] modules;
	private final OutilsWindow outils;
	private final CalquesWindow calques;
	private final ListeWindow liste;
	private final TuilesWindow tuiles;

	public MainWindow(OutilsWindow _outils, CalquesWindow _calques, ListeWindow _liste, TuilesWindow _tuiles) {
		outils = _outils;
		calques = _calques;
		liste = _liste;
		tuiles = _tuiles;
		modules = new Module[]{
			outils,
			calques,
			liste,
			tuiles
		};

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		root = new Group();
		scene = new Scene(root, screenBounds.getWidth() * SCREEN_COEFF, screenBounds.getHeight() * SCREEN_COEFF);

		setScene(scene);
		setTitle("TimeFlies - Editeur de map");

		setX(screenBounds.getWidth() * ((1 - SCREEN_COEFF) / 2));
		setY(screenBounds.getHeight() * ((1 - SCREEN_COEFF) / 2));
		setWidth(screenBounds.getWidth() * SCREEN_COEFF);
		setHeight(screenBounds.getHeight() * SCREEN_COEFF);

		outils.setX(getX() - liste.getWidth());
		outils.setY(getY());

		liste.setX(outils.getX());
		liste.setY(outils.getY() + outils.getHeight());

		tuiles.setX(getX() + getWidth() / 2 - tuiles.getWidth() / 2);
		tuiles.setY(getY() + getHeight());

		calques.setX(getX() + getWidth());
		calques.setY(getY());

		getScene().getStylesheets().add("vue/style.css");
		for (Module m : modules) {
			m.getScene().getStylesheets().add("vue/style.css");
		}

		setOnCloseRequest((event) -> {
			for (Module m : modules) {
				m.close();
			}
		});
		iconifiedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) -> {
			if (t1) {
				for (Module m : modules) {
					m.setAlwaysOnTop(false);
				}
			} else {
				for (Module m : modules) {
					m.setAlwaysOnTop(true);
				}
			}
		});
	}

}

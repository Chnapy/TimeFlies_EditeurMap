/*
 * 
 * 
 * 
 */
package vue.main;

import controleur.Controleur;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import modele.Map;
import vue.Module;
import vue.calques.CalquesWindow;
import vue.liste_cartes.ListeWindow;
import vue.outils.OutilsWindow;
import vue.popup.Dialogues;
import vue.proprietes.ProprietesWindow;
import vue.tuiles.TuilesWindow;

/**
 * MainWindow.java
 *
 */
public class MainWindow extends Stage {

	private final static double SCREEN_COEFF = (double) 3.5 / 5;

	private final Scene scene;
	private final StackPane root;

	//Modules
	private final Module[] modules;
	private final OutilsWindow outils;
	private final CalquesWindow calques;
	private final ListeWindow liste;
	private final TuilesWindow tuiles;
	private final ProprietesWindow proprietes;

	//Main
	public final ImageView background;
	public final MapTuiles mapTuiles;
	public final Foreground foreground;
	public final Grille grille;

	//Map
	private Map map;

	public MainWindow(Controleur controleur, OutilsWindow _outils, CalquesWindow _calques,
			ListeWindow _liste, TuilesWindow _tuiles, ProprietesWindow _proprietes) {
		outils = _outils;
		calques = _calques;
		liste = _liste;
		tuiles = _tuiles;
		proprietes = _proprietes;
		modules = new Module[]{
			outils,
			calques,
			liste,
			tuiles,
			proprietes
		};
		map = null;

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		root = new StackPane();
		scene = new Scene(root, screenBounds.getWidth() * SCREEN_COEFF, screenBounds.getHeight() * SCREEN_COEFF);
		root.setId("mainWindow");

		setScene(scene);
		setTitle("TFmap - Editeur de map");

		setX(screenBounds.getWidth() * ((1 - SCREEN_COEFF) / 2));
		setY(screenBounds.getHeight() * ((1 - SCREEN_COEFF) / 2));
		setWidth(screenBounds.getWidth() * SCREEN_COEFF);
		setHeight(screenBounds.getHeight() * SCREEN_COEFF);

		setModulesPosition();

		getScene().getStylesheets().add("vue/style.css");
		for (Module m : modules) {
			m.getScene().getStylesheets().add("vue/style.css");
		}

		setOnCloseRequest((e) -> {
			List<Map> notSaved = liste.notSaved();
			if (!notSaved.isEmpty()) {
				switch (Dialogues.wantToClose(notSaved.size())) {
					case CANCEL_CLOSE:
						e.consume();
						return;
					case OK_DONE:
						notSaved.forEach((m) -> controleur.sauvegarder(m));
						break;
					case NO:	//Sauvegarder & quitter
						break;
				}
			}
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
		maximizedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) -> {
			setModulesPosition();
		});

		background = new ImageView();
		background.setPickOnBounds(false);
		mapTuiles = new MapTuiles(controleur);
		foreground = new Foreground();
		grille = new Grille();

		root.getChildren().addAll(background, mapTuiles, foreground, grille);
	}

	private void setModulesPosition() {
		if (maximizedProperty().getValue()) {
			outils.setX(0);
			outils.setY(21);

			liste.setX(0);
			liste.setY(Screen.getPrimary().getVisualBounds().getHeight() - liste.getHeight());

			tuiles.setX(Screen.getPrimary().getVisualBounds().getWidth() / 2 - tuiles.getWidth() / 2);
			tuiles.setY(Screen.getPrimary().getVisualBounds().getHeight() - tuiles.getHeight());

			calques.setX(Screen.getPrimary().getVisualBounds().getWidth() - calques.getWidth());
			calques.setY(21);

			proprietes.setX(Screen.getPrimary().getVisualBounds().getWidth() - proprietes.getWidth());
			proprietes.setY(Screen.getPrimary().getVisualBounds().getHeight() - proprietes.getHeight());
		} else {
			outils.setX(getX() - liste.getWidth());
			outils.setY(getY());

			liste.setX(outils.getX());
			liste.setY(outils.getY() + outils.getHeight());

			tuiles.setX(getX() + getWidth() / 2 - tuiles.getWidth() / 2);
			tuiles.setY(getY() + getHeight());

			calques.setX(getX() + getWidth());
			calques.setY(getY());

			proprietes.setX(getX() + getWidth());
			proprietes.setY(calques.getY() + calques.getHeight());
		}
	}

	public void setMap(Map map) {
		this.map = map;
		mapTuiles.setMap(map);
		grille.reset(map.tuiles[0].length, map.tuiles.length);
		setTitle("TFmap - " + map.nom);
	}

	public MapTuiles getMapTuiles() {
		return mapTuiles;
	}

	public ImageView getBackground() {
		return background;
	}

}

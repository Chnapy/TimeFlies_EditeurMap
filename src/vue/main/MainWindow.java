/*
 * 
 * 
 * 
 */
package vue.main;

import controleur.Controleur;
import controleur.Main;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modele.Map;
import vue.ExceptionHandler;
import vue.Module;
import vue.calques.CalquesWindow;
import vue.liste_cartes.ListeWindow;
import vue.outils.OutilsWindow;
import vue.placement.PlacementWindow;
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
	private final VBox vRoot;
	private final StackPane root;
	private final Controleur controleur;

	//Modules
	private final Module[] modules;
	private final OutilsWindow outils;
	private final CalquesWindow calques;
	private final ListeWindow liste;
	private final TuilesWindow tuiles;
	private final ProprietesWindow proprietes;
	private final PlacementWindow placement;

	//Main
	public final ImageView background;
	public final MapTuiles mapTuiles;
	public final Foreground foreground;
	public final Grille grille;

	public MainWindow(Controleur _controleur, OutilsWindow _outils, CalquesWindow _calques,
			ListeWindow _liste, TuilesWindow _tuiles, ProprietesWindow _proprietes, PlacementWindow _placement) {
		controleur = _controleur;
		outils = _outils;
		calques = _calques;
		liste = _liste;
		tuiles = _tuiles;
		proprietes = _proprietes;
		placement = _placement;
		modules = new Module[]{
			outils,
			calques,
			liste,
			tuiles,
			proprietes,
			placement
		};

		Menu menuFile = new Menu("Fichier", null,
				getMenuItem("Nouvelle carte", "Ctrl+N", OutilsWindow.Boutons.NOUVEAU),
				getMenuItem("Ouvrir une carte", "Ctrl+Shift+O", OutilsWindow.Boutons.OUVRIR),
				new SeparatorMenuItem(),
				getMenuItem("Sauvegarder", "Ctrl+S", OutilsWindow.Boutons.SAUVEGARDER),
				getMenuItem("Sauvegarder tout", "Ctrl+Shift+S", OutilsWindow.Boutons.SAUVEGARDERTOUT),
				new SeparatorMenuItem(),
				getMenuItem("Quitter", null, (e) -> {
					getOnCloseRequest().handle(new WindowEvent(null, EventType.ROOT));
					close();
				})
		);
		Menu menuEdit = new Menu("Édition", null,
				getMenuItem("Annuler", "Ctrl+Z", OutilsWindow.Boutons.ANNULER),
				getMenuItem("Refaire", "Ctrl+Shift+Z", OutilsWindow.Boutons.REFAIRE)
		);
		Menu menuWindows = new Menu("Fenêtres", null,
				getCheckMenu("Outils", "Ctrl+O", true, outils),
				getCheckMenu("Liste des cartes", "Ctrl+L", true, liste),
				getCheckMenu("Tuiles", "Ctrl+T", true, tuiles),
				getCheckMenu("Calques", "Ctrl+C", true, calques),
				getCheckMenu("Propriétés", "Ctrl+P", true, proprietes),
				new SeparatorMenuItem(),
				getCheckMenu("Modules toujours en avant", "Ctrl+A", true, (ov, oldV, newV) -> {
					for (Module m : modules) {
						m.setAlwaysOnTop(newV);
					}
				})
		);
		ToggleGroup tg = new ToggleGroup();
		Menu menuApparence = new Menu("Style", null,
				getRadioMenu("Modena", null, tg, true, (t) -> {
					Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
				}),
				getRadioMenu("Caspian", null, tg, false, (t) -> {
					Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
				})
		);
		Menu menuAide = new Menu("?", null,
				getMenuItem("Site officiel", null, (e) -> {
					try {
						Desktop.getDesktop().browse(new URI("http://timeflies.fr"));
						this.setIconified(true);
					} catch (URISyntaxException | IOException ex) {
						ExceptionHandler.handle(ex);
					}
				}),
				getMenuItem("A propos", null, (e) -> {
					Dialogues.alert("TFmapper v" + Main.VERSION.charAt(0),
							Main.LAST_MODIF + " - Version " + Main.VERSION + "\n"
							+ "Copyright © 2016 Richard Haddad\n"
							+ "http://timeflies.fr",
							Alert.AlertType.INFORMATION);
				})
		);
		MenuBar menuBar = new MenuBar(menuFile, menuEdit, menuWindows, menuApparence, menuAide);

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		root = new StackPane(menuBar);
		vRoot = new VBox(menuBar, root);
		scene = new Scene(vRoot, screenBounds.getWidth() * SCREEN_COEFF, screenBounds.getHeight() * SCREEN_COEFF);
		vRoot.setId("mainWindow");

		setScene(scene);
		setTitle("TFmapper - Editeur de map pour Timeflies");

		setX(screenBounds.getWidth() * ((1 - SCREEN_COEFF) / 2));
		setY(screenBounds.getHeight() * ((1 - SCREEN_COEFF) / 2));
		setWidth(screenBounds.getWidth() * SCREEN_COEFF);
		setHeight(screenBounds.getHeight() * SCREEN_COEFF);

		setModulesPosition();

		getScene().getStylesheets().add("vue/style.css");
		for (Module m : modules) {
			m.getScene().getStylesheets().add("vue/style.css");
		}

		setOnCloseRequest((WindowEvent e) -> {
			List<Map> notSaved = liste.notSaved();
			if (!notSaved.isEmpty()) {
				switch (Dialogues.wantToClose(notSaved.size())) {
					case CANCEL_CLOSE:
						e.consume();
						return;
					case OK_DONE:
						notSaved.forEach((m) -> controleur.sauvegarder(m));
						break;
					case NO:
						break;
				}
			}
			for (Module m : modules) {
				m.close();
			}
		});
		iconifiedProperty().addListener((ov, t, t1) -> {
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
		maximizedProperty().addListener((ov, t, t1) -> {
			setModulesPosition();
		});

		background = new ImageView();
		background.setPickOnBounds(false);
		mapTuiles = new MapTuiles(controleur);
		foreground = new Foreground();
		grille = new Grille();

		root.getChildren().addAll(background, mapTuiles, foreground, grille);

		getScene().getAccelerators().put(KeyCombination.valueOf("&"), () -> {
			controleur.key("&");
		});
		getScene().getAccelerators().put(KeyCombination.valueOf("é"), () -> {
			controleur.key("é");
		});
		getScene().getAccelerators().put(KeyCombination.valueOf("\""), () -> {
			controleur.key("\"");
		});
		getScene().getAccelerators().put(KeyCombination.valueOf("{"), () -> {
			controleur.key("{");
		});
	}

	private void setModulesPosition() {
		if (maximizedProperty().getValue()) {
			outils.setX(0);
			outils.setY(47);
			
			placement.setX(outils.getX() + outils.getWidth());
			placement.setY(outils.getY());

			liste.setX(0);
			liste.setY(Screen.getPrimary().getVisualBounds().getHeight() - liste.getHeight());

			tuiles.setX(Screen.getPrimary().getVisualBounds().getWidth() / 2 - tuiles.getWidth() / 2);
			tuiles.setY(Screen.getPrimary().getVisualBounds().getHeight() - tuiles.getHeight());

			calques.setX(Screen.getPrimary().getVisualBounds().getWidth() - calques.getWidth());
			calques.setY(47);

			proprietes.setX(Screen.getPrimary().getVisualBounds().getWidth() - proprietes.getWidth());
			proprietes.setY(Screen.getPrimary().getVisualBounds().getHeight() - proprietes.getHeight());
		} else {
			outils.setX(getX() - liste.getWidth());
			outils.setY(getY());
			
			placement.setX(outils.getX() + outils.getWidth());
			placement.setY(outils.getY());

			liste.setX(outils.getX());
			liste.setY(outils.getY() + outils.getHeight() + 5);

			tuiles.setX(getX() + getWidth() / 2 - tuiles.getWidth() / 2);
			tuiles.setY(getY() + getHeight());

			calques.setX(getX() + getWidth());
			calques.setY(getY());

			proprietes.setX(getX() + getWidth());
			proprietes.setY(calques.getY() + calques.getHeight() + 5);
		}
	}

	public void setMap(Map map) {
		mapTuiles.setMap(map);
		grille.reset(map.tuiles[0].length, map.tuiles.length);
		setTitle("TFmapper - " + map.nom + " " + map.versionMajeure + "." + map.versionMineure);
		requestFocus();
	}

	private CheckMenuItem getCheckMenu(String nom, String keyCombinaison, boolean selected, Module module) {
		return getCheckMenu(nom, keyCombinaison, selected, (ChangeListener<Boolean>) (ov, oldV, newV) -> {
			if (newV) {
				module.show();
			} else {
				module.hide();
			}
		});
	}

	private CheckMenuItem getCheckMenu(String nom, String keyCombinaison, boolean selected, ChangeListener<Boolean> listener) {
		CheckMenuItem cmi = new CheckMenuItem(nom);
		if (keyCombinaison != null && !keyCombinaison.isEmpty()) {
			cmi.setAccelerator(KeyCombination.valueOf(keyCombinaison));
		}
		cmi.setSelected(selected);
		cmi.selectedProperty().addListener(listener);
		return cmi;
	}

	private MenuItem getMenuItem(String nom, String keyCombinaison, OutilsWindow.Boutons bouton) {
		return getMenuItem(nom, keyCombinaison, (e) -> {
			controleur.notifyOutils(bouton);
		});
	}

	private MenuItem getMenuItem(String nom, String keyCombinaison, EventHandler<ActionEvent> event) {
		MenuItem mi = new MenuItem(nom);
		if (keyCombinaison != null && !keyCombinaison.isEmpty()) {
			mi.setAccelerator(KeyCombination.valueOf(keyCombinaison));
		}
		mi.setOnAction(event);
		return mi;
	}

	private RadioMenuItem getRadioMenu(String nom, String keyCombinaison, ToggleGroup group,
			boolean selected, EventHandler<ActionEvent> event) {
		RadioMenuItem rmi = new RadioMenuItem(nom);
		if (keyCombinaison != null && !keyCombinaison.isEmpty()) {
			rmi.setAccelerator(KeyCombination.valueOf(keyCombinaison));
		}
		rmi.setToggleGroup(group);
		rmi.setSelected(selected);
		rmi.setOnAction(event);
		return rmi;
	}

	public MapTuiles getMapTuiles() {
		return mapTuiles;
	}

	public ImageView getBackground() {
		return background;
	}

}

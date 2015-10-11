/*
 * 
 * 
 * 
 */
package vue.outils;

import controleur.Controleur;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import modele.Map;
import vue.Module;
import static vue.outils.OutilsWindow.Boutons.*;

/**
 * OutilsWindow.java
 *
 */
public class OutilsWindow extends Module {

	public static enum Boutons {

		NOUVEAU("new", "FileChooser.fileIcon", "Créer une nouvelle carte"),
		OUVRIR("open", "FileChooser.newFolderIcon", "Ouvrir une carte"),
		SAUVEGARDER("save", "FileChooser.newFolderIcon", "Sauvegarder la carte actuelle"),
		SAUVEGARDERTOUT("saveall", "FileChooser.newFolderIcon", "Sauvegarder toutes les cartes"),
		ANNULER("undo", "FileChooser.newFolderIcon", "Annuler la dernière action"),
		REFAIRE("redo", "FileChooser.newFolderIcon", "Refaire l'action"),
		TUILER("paint", "FileChooser.newFolderIcon", "Placer des tuiles"),
		REMPLIR("fill", "FileChooser.newFolderIcon", "Remplir de tuiles");

		public final Button bouton;

		private Boutons(String pathImage, String systemImage, String tooltip) {
			bouton = new Button();
			bouton.setGraphic(new ImageView("icons/" + pathImage + ".png"));
			bouton.setMinWidth(36);
			bouton.setMaxWidth(36);
			bouton.setMinHeight(36);
			bouton.setMaxHeight(36);
			bouton.setTooltip(new Tooltip(tooltip));
		}
	}

	public OutilsWindow(Controleur controleur) {
		super(controleur, 200, 167, "Outils");

		for (Boutons b : Boutons.values()) {
			b.bouton.setOnAction((e) -> {
				controleur.notifyOutils(b);
			});
		}
		SAUVEGARDER.bouton.setDisable(true);
		SAUVEGARDERTOUT.bouton.setDisable(true);
		ANNULER.bouton.setDisable(true);
		REFAIRE.bouton.setDisable(true);

		GridPane gp = new GridPane();
		getScene().setRoot(gp);

		Separator sepv = new Separator(Orientation.VERTICAL);
		sepv.setPadding(new Insets(0, 0, 0, 3));

		gp.addRow(0, NOUVEAU.bouton, OUVRIR.bouton, sepv, SAUVEGARDER.bouton, SAUVEGARDERTOUT.bouton);
		gp.addRow(2, ANNULER.bouton, REFAIRE.bouton);
		gp.addRow(4, TUILER.bouton, REMPLIR.bouton);
		gp.setHgap(2);
		gp.setVgap(2);
		gp.setPadding(new Insets(2));

		TUILER.bouton.setDisable(true);
		REMPLIR.bouton.setDisable(true);
	}

	public void reset(Map map) {
		TUILER.bouton.setDisable(false);
		REMPLIR.bouton.setDisable(false);
		canRefaire(map.actionIndex >= 0 && map.actionIndex < map.actions.size());
		canAnnuler(map.actionIndex > 0);
	}

	public void modif(Map map) {
		if (SAUVEGARDER.bouton.isDisabled()) {
			SAUVEGARDER.bouton.setDisable(false);
			SAUVEGARDERTOUT.bouton.setDisable(false);
		}
		canAnnuler(true);
	}

	public void save(Map map) {
		SAUVEGARDER.bouton.setDisable(true);
	}

	public void saveAll() {
		SAUVEGARDERTOUT.bouton.setDisable(true);
	}

	public void canRefaire(boolean b) {
		if (b == REFAIRE.bouton.isDisabled()) {
			REFAIRE.bouton.setDisable(!b);
		}
	}

	public void canAnnuler(boolean b) {
		if (b == ANNULER.bouton.isDisabled()) {
			ANNULER.bouton.setDisable(!b);
		}
	}

	@Override
	protected void key(String key) {
		controleur.key(key);
	}

}

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

		NOUVEAU("new", "Créer une nouvelle carte"),
		OUVRIR("open", "Ouvrir une carte"),
		SAUVEGARDER("save", "Sauvegarder la carte actuelle"),
		SAUVEGARDERTOUT("saveall", "Sauvegarder toutes les cartes"),
		ANNULER("undo", "Annuler la dernière action"),
		REFAIRE("redo", "Refaire l'action"),
		TUILER("paint", "Placer des tuiles"),
		REMPLIR("fill", "Remplir de tuiles"),
		PLACER("place", "Indiquer les placements de début de combat");

		public final Button bouton;

		private Boutons(String pathImage, String tooltip) {
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

		Insets insepv = new Insets(0, 0, 0, 3);
		Separator sepv1 = new Separator(Orientation.VERTICAL), 
				sepv2 = new Separator(Orientation.VERTICAL), 
				sepv3 = new Separator(Orientation.VERTICAL);
		sepv1.setPadding(insepv);
		sepv2.setPadding(insepv);
		sepv3.setPadding(insepv);
		
		

		gp.addRow(0, NOUVEAU.bouton, OUVRIR.bouton, sepv1, SAUVEGARDER.bouton, SAUVEGARDERTOUT.bouton);
		gp.addRow(2, ANNULER.bouton, REFAIRE.bouton, sepv2);
		gp.addRow(4, TUILER.bouton, REMPLIR.bouton, sepv3, PLACER.bouton);
		gp.setHgap(2);
		gp.setVgap(2);
		gp.setPadding(new Insets(2));

		TUILER.bouton.setDisable(true);
		REMPLIR.bouton.setDisable(true);
		PLACER.bouton.setDisable(true);
	}

	public void reset(Map map) {
		TUILER.bouton.setDisable(false);
		REMPLIR.bouton.setDisable(false);
		PLACER.bouton.setDisable(false);
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

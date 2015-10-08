/*
 * 
 * 
 * 
 */
package vue.outils;

import controleur.Controleur;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import vue.Module;
import static vue.outils.OutilsWindow.Boutons.*;

/**
 * OutilsWindow.java
 *
 */
public class OutilsWindow extends Module {

	public enum Boutons {

		NOUVEAU("New Document"),
		OUVRIR("Get Document"),
		SAUVEGARDER("Clear Green Button"),
		ANNULER("Appointment Urgent"),
		REFAIRE("Appointment Cool"),
		TUILER("Grey Ball"),
//		SELECTIONNER("Grey Ball"),
		REMPLIR("Purple Ball");

		public Button bouton;

		private Boutons(String pathImage) {
			bouton = new Button();
			bouton.setGraphic(new ImageView("icons/" + pathImage + ".png"));
			bouton.setMinWidth(32);
			bouton.setMaxWidth(32);
		}
	}

	public OutilsWindow(Controleur controleur) {
		super(controleur, 200, 162, "Outils");

		for (Boutons b : Boutons.values()) {
			b.bouton.setOnAction((event) -> {
				controleur.notifyOutils(b);
			});
		}

		GridPane gp = new GridPane();
		getScene().setRoot(gp);

		gp.addRow(0, NOUVEAU.bouton, OUVRIR.bouton, SAUVEGARDER.bouton);
		gp.addRow(1, ANNULER.bouton, REFAIRE.bouton);
		gp.addRow(2, TUILER.bouton, REMPLIR.bouton);
		gp.setHgap(2);
		gp.setVgap(2);
		gp.setPadding(new Insets(2));
	}

	public void reset() {
	}

}

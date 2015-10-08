/*
 * 
 * 
 * 
 */
package vue.calques;

import controleur.Controleur;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import vue.Module;

/**
 * CalquesWindow.java
 *
 */
public class CalquesWindow extends Module {

	private final CheckBox background;
	private final CheckBox tuiles;
	private final CheckBox foreground;
	private final CheckBox grille;

	public CalquesWindow(Controleur controleur) {
		super(controleur, 200, 130, "Calques");

		VBox vb = new VBox();
		getScene().setRoot(vb);

		background = new CheckBox("Background");
		tuiles = new CheckBox("Tuiles");
		foreground = new CheckBox("Foreground");
		grille = new CheckBox("Grille");
		vb.getChildren().addAll(grille, foreground, tuiles, background);
		vb.setAlignment(Pos.CENTER_LEFT);
		vb.setSpacing(5);
		vb.setPadding(new Insets(0, 0, 0, 5));
		EventHandler event = (EventHandler<ActionEvent>) (e) -> {
			controleur.calques(background.isSelected(), tuiles.isSelected(), foreground.isSelected(), grille.isSelected());
		};
		background.setOnAction(event);
		tuiles.setOnAction(event);
		foreground.setOnAction(event);
		grille.setOnAction(event);

		reset();
	}

	public final void reset() {
		background.setSelected(true);
		tuiles.setSelected(true);
		foreground.setSelected(true);
		grille.setSelected(true);

		Platform.runLater(() -> {
			controleur.calques(background.isSelected(), tuiles.isSelected(), foreground.isSelected(), grille.isSelected());
		});
		
	}

}

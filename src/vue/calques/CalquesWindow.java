/*
 * 
 * 
 * 
 */
package vue.calques;

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

	public CalquesWindow() {
		super(200, 100, "Calques");
		
		VBox vb = new VBox();
		getScene().setRoot(vb);
		
		background = new CheckBox("Background");
		tuiles = new CheckBox("Tuiles");
		foreground = new CheckBox("Foreground");
		vb.getChildren().addAll(background, tuiles, foreground);
		vb.setAlignment(Pos.CENTER_LEFT);
		vb.setSpacing(5);
		vb.setPadding(new Insets(0, 0, 0, 5));
	}

}

/*
 * 
 * 
 * 
 */
package vue;

import controleur.Controleur;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Module.java
 *
 */
public abstract class Module extends Stage {

	private final Scene scene;
	private final Group group;
	protected final Controleur controleur;

	public Module(Controleur controleur, double width, double height, String title) {
		this.controleur = controleur;
		group = new Group();
		scene = new Scene(group, width, height);

		setWidth(width);
		setHeight(height);
		setScene(scene);
		setTitle(title);
		setAlwaysOnTop(true);
		initStyle(StageStyle.UTILITY);
		setOnCloseRequest((e) -> {
			e.consume();
			hide();
		});
		
		getScene().getAccelerators().put(KeyCombination.valueOf("&"), () -> {
			key("&");
		});
		getScene().getAccelerators().put(KeyCombination.valueOf("é"), () -> {
			key("é");
		});
		getScene().getAccelerators().put(KeyCombination.valueOf("\""), () -> {
			key("\"");
		});
		getScene().getAccelerators().put(KeyCombination.valueOf("{"), () -> {
			key("{");
		});
	}
	
	protected abstract void key(String key);

}

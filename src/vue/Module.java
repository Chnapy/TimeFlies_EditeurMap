/*
 * 
 * 
 * 
 */
package vue;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Module.java
 *
 */
public abstract class Module extends Stage {

	private final Scene scene;
	private final Group group;

	public Module(double width, double height, String title) {
		group = new Group();
		scene = new Scene(group, width, height);

		setWidth(width);
		setHeight(height);
		setScene(scene);
		setTitle(title);
		setAlwaysOnTop(true);
		initStyle(StageStyle.UTILITY);
	}

}

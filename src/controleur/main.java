/*
 * 
 * 
 * 
 */
package controleur;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * main.java
 *
 */
public class main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.close();

		Controleur controleur = new Controleur();
	}

}

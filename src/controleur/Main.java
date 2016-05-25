/*
 * 
 * 
 * 
 */
package controleur;

import java.util.Arrays;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main.java
 *
 */
public class Main extends Application {
	
	public static final String LAST_MODIF = "25/05/2016";
	public static final String VERSION = "1.02";
	
	private static String[] args;

	public static void main(String[] args) {
		System.out.println("args: " + Arrays.toString(args));
		Main.args = args;
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.close();

		Controleur controleur = new Controleur(args);
	}

}

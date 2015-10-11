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
 * main.java
 *
 */
public class main extends Application {
	
	public static final String LAST_MODIF = "11/10/2015";
	public static final String VERSION = "1.01";
	
	private static String[] args;

	public static void main(String[] args) {
		System.out.println("args: " + Arrays.toString(args));
		main.args = args;
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.close();

		Controleur controleur = new Controleur(args);
	}

}

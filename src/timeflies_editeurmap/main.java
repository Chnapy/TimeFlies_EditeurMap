/*
 * 
 * 
 * 
 */
package timeflies_editeurmap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import vue.main.MainWindow;

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

//		MapSerializable map = new MapSerializable(new Type[][] {
//			{Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.OBSTACLE},
//			{Type.SIMPLE, Type.TROU, Type.OBSTACLE, Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.OBSTACLE, Type.OBSTACLE},
//			{Type.OBSTACLE, Type.SIMPLE, Type.SIMPLE, Type.TROU, Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.OBSTACLE},
//			{Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.OBSTACLE, Type.SIMPLE, Type.SIMPLE, Type.OBSTACLE},
//			{Type.ECRAN, Type.SIMPLE, Type.OBSTACLE, Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.OBSTACLE},
//			{Type.TROU, Type.ECRAN, Type.TROU, Type.SIMPLE, Type.SIMPLE, Type.OBSTACLE, Type.SIMPLE, Type.OBSTACLE},
//			{Type.ECRAN, Type.ECRAN, Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.OBSTACLE},
//			{Type.ECRAN, Type.ECRAN, Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.SIMPLE, Type.OBSTACLE}
//		});
//		
//		File mapFile = new File("test.tfmap");
//		ObjectOutputStream output =  new ObjectOutputStream(new FileOutputStream(mapFile)) ;
//		System.out.println(mapFile.getAbsolutePath());
//		output.writeObject(map);
	}

}

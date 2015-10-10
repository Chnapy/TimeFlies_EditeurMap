/*
 * 
 * 
 * 
 */
package vue;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * ExceptionHandler.java
 *
 */
public final class ExceptionHandler {
	
	private static Throwable ex = null;

	private static final Runnable run = () -> {System.out.println("run");
		if(ex == null)
			return;
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Un bug sauvage apparait !");
		alert.setHeaderText("L'application a crashé :(");
		alert.setContentText("Si le problème survient lors du redémarrage de l'application, copiez les détails et contactez nous sur le site officiel.");
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();
		
		Label label = new Label("Stacktrace de l'exception:");
		
		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		
		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);
		
		alert.getDialogPane().setExpandableContent(expContent);
		
		alert.showAndWait();
		Platform.exit();
	};

	public static void handle(Throwable e) {
		try {System.out.println("handle");
			ex = e;
			Platform.runLater(run);
		} catch (Throwable t) {
			// don't let the exception get thrown out, will cause infinite looping !
		}
	}
}

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
	private static String title;
	private static String head;
	private static String content;

	public static void handle(Throwable e) {
		handle(e,
				"Un bug sauvage apparait !",
				"L'application a crashé :(",
				"Si le problème survient lors du redémarrage de l'application, copiez les détails et contactez nous sur le site officiel."
		);
	}

	public static void handle(Throwable e, String title, String head, String content) {
		try {
			e.printStackTrace();
			ex = e;
			ExceptionHandler.title = title;
			ExceptionHandler.head = head;
			ExceptionHandler.content = content;
			Platform.runLater(() -> {
				if (ex == null) {
					return;
				}
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle(title);
				alert.setHeaderText(head);
				alert.setContentText(content);

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
			});
		} catch (Throwable t) {
			// don't let the exception get thrown out, will cause infinite looping !
		}
	}
}

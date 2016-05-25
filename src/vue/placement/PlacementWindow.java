/*
 * 
 * 
 * 
 */
package vue.placement;

import controleur.Controleur;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import modele.Map;
import vue.Module;
import vue.main.Outil;
import vue.tuiles.TuileView;

/**
 * CalquesWindow.java
 *
 */
public class PlacementWindow extends Module {

	private final ComboBox ccbox;
	private final ObservableList<Integer> boxList;
	private final Rectangle colRec;
	private final Label lPersos;

	public PlacementWindow(Controleur controleur) {
		super(controleur, 200, 130, "Placement");

		VBox vb = new VBox();
		getScene().setRoot(vb);

		boxList = FXCollections.observableArrayList();

		Label lEquipe = new Label("Equipe n°");
		ccbox = new ComboBox(boxList);
		colRec = new Rectangle(40, 25);

		HBox hb1 = new HBox(lEquipe, ccbox, colRec);
		hb1.setSpacing(5);

		Label lPers = new Label("Places posées : ");
		lPersos = new Label();
		HBox hb2 = new HBox(lPers, lPersos);
//		hb2.setSpacing(5);

		vb.getChildren().addAll(hb1, hb2);
		vb.setAlignment(Pos.CENTER_LEFT);
		vb.setSpacing(5);
		vb.setPadding(new Insets(0, 0, 0, 5));
		EventHandler event = (EventHandler<ActionEvent>) (e) -> {
			controleur.placement((int) ccbox.getValue());
			colRec.setFill(TuileView.TUILE_CONTOUR_COLORS[(int) ccbox.getValue()]);
			updatePersos();
		};
		ccbox.setOnAction(event);

		getScene().getRoot().setDisable(true);
	}

	public final void setMap(Map map) {
		boxList.clear();
		for (int i = 0; i < map.nbrEquipes; i++) {
			boxList.add(i);
		}

		ccbox.setValue(ccbox.getItems().get(0));
		colRec.setFill(TuileView.TUILE_CONTOUR_COLORS[0]);
		updatePersos();

		getScene().getRoot().setDisable(false);

	}

	public void updatePersos() {
		lPersos.setText(Outil.getPersosParEquipe()[(int) ccbox.getValue()] + "/" + Outil.getPersosMaxParEquipe());
		if (Outil.getPersosParEquipe()[(int) ccbox.getValue()] >= Outil.getPersosMaxParEquipe()) {
			lPersos.setTextFill(Color.GREEN);
		} else {
			lPersos.setTextFill(Color.FIREBRICK);
		}
	}

	@Override
	protected void key(String key) {
		controleur.key(key);
	}

}

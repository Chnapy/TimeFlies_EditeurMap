/*
 * 
 * 
 * 
 */
package vue.tuiles;

import controleur.Controleur;
import gameplay.map.Type;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import vue.Module;
import vue.main.Outil;
import vue.main.Outil.Etat;

/**
 * TuilesWindow.java
 *
 */
public class TuilesWindow extends Module {

	private static final int WIDTH = 128;
	private static final int HEIGHT = WIDTH / 2;
	private static final float COEFF_TUILE = 0.08f;

	private final ToggleButton[] bTuiles;

	public TuilesWindow(Controleur controleur) {
		super(controleur, 520, 100, "Tuiles à placer");
		HBox hb = new HBox();
		getScene().setRoot(hb);
		hb.setAlignment(Pos.CENTER);
		hb.setStyle("-fx-background: gray");

		int tuile_margin = (int) (WIDTH * COEFF_TUILE);

		double[] tabPoly = {
			WIDTH / 2, 0 + tuile_margin / 2,
			WIDTH - tuile_margin, HEIGHT / 2,
			WIDTH / 2, HEIGHT - tuile_margin / 2,
			0 + tuile_margin, HEIGHT / 2
		};

		bTuiles = new ToggleButton[4];

		for (int i = 0; i < bTuiles.length; i++) {
			final TuileView tuile = new TuileView(tabPoly, Type.values()[i], null) {

				@Override
				protected void setEvents() {
				}

			};
			bTuiles[i] = new ToggleButton(null, tuile);
			bTuiles[i].getStyleClass().add("bTuile");
			bTuiles[i].setOnAction((ActionEvent e) -> {
				for (ToggleButton b : bTuiles) {
					if (b != e.getSource()) {
						b.setSelected(false);
					}
				}
				Outil.setType(tuile.getType());
			});
		}

		hb.getChildren().addAll(bTuiles);
	}

	public void reset() {
	}

	public ToggleButton[] getbTuiles() {
		return bTuiles;
	}

}

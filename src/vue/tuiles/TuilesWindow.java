/*
 * 
 * 
 * 
 */
package vue.tuiles;

import Serializable.HorsCombat.Map.TypeTuile;
import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import vue.Module;
import vue.main.Outil;

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
			final TuileView tuile = new TuileView(tabPoly, TypeTuile.values()[i], null, false) {

				@Override
				protected void setEvents() {
				}

			};
			bTuiles[i] = new ToggleButton(null, tuile);
			bTuiles[i].getStyleClass().add("bTuile");
			bTuiles[i].setOnAction((ActionEvent e) -> {
				for (ToggleButton b : bTuiles) {
					b.setSelected(b == e.getSource());
				}
				Outil.setType(tuile.getType());
			});
		}
		bTuiles[0].setTooltip(new Tooltip("Tuile simple [Touche 1]"));
		bTuiles[1].setTooltip(new Tooltip("Tuile trou [Touche 2]"));
		bTuiles[2].setTooltip(new Tooltip("Tuile écran [Touche 3]"));
		bTuiles[3].setTooltip(new Tooltip("Tuile obstacle [Touche 4]"));

		hb.getChildren().addAll(bTuiles);

		getScene().getRoot().setDisable(true);
	}

	public void reset() {
		getScene().getRoot().setDisable(false);
	}

	public ToggleButton[] getbTuiles() {
		return bTuiles;
	}

	@Override
	protected void key(String key) {
		keyAction(key);
	}

	public void keyAction(String key) {
		switch (key) {
			case "&":
				bTuiles[0].fire();
				break;
			case "é":
				bTuiles[1].fire();
				break;
			case "\"":
				bTuiles[2].fire();
				break;
			case "{":
				bTuiles[3].fire();
				break;
		}
	}

}

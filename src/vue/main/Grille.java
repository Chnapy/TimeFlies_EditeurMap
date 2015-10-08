/*
 * 
 * 
 * 
 */
package vue.main;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Grille.java
 *
 */
public class Grille extends Pane {

	private Polygon[][] tabPoly;

	public Grille() {
		setPickOnBounds(false);
	}

	public void reset(int width, int height) {
		getChildren().clear();
		tabPoly = new Polygon[height][width];

		double polyWidth = getWidth() / ((double) (width + height) / 2);
		double polyHeight = polyWidth / 2;

		double[] polyBounds = {
			polyWidth / 2, 0,
			polyWidth, polyHeight / 2,
			polyWidth / 2, polyHeight,
			0, polyHeight / 2
		};

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tabPoly[y][x] = new Polygon(polyBounds);
				tabPoly[y][x].setLayoutX(x * polyWidth / 2 + y * polyWidth / 2);
				tabPoly[y][x].setLayoutY(((height - 1) * polyHeight / 2) + x * polyHeight / 2 - y * polyHeight / 2);
				tabPoly[y][x].setStrokeWidth(1);
				tabPoly[y][x].setStroke(Color.DARKGRAY);
				tabPoly[y][x].setFill(Color.TRANSPARENT);
				tabPoly[y][x].setDisable(true);
				getChildren().add(tabPoly[y][x]);
			}
		}

	}

}

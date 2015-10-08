/*
 * 
 * 
 * 
 */
package vue.main;

import controleur.Controleur;
import gameplay.map.Type;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import modele.Map;
import vue.tuiles.TuileView;

/**
 * MapTuiles.java
 *
 */
public class MapTuiles extends Pane implements Observer {

	private final Controleur controleur;
	private TuileView[][] tabPoly;

	public MapTuiles(Controleur controleur) {
		this.controleur = controleur;
	}

	public void setMap(Map map) {
		int width = map.tuiles[0].length;
		int height = map.tuiles.length;
		getChildren().clear();
		tabPoly = new TuileView[height][width];

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
				tabPoly[y][x] = new TuileView(polyBounds, map.tuiles[y][x], new Point2D(x, y));
				tabPoly[y][x].setLayoutX(x * polyWidth / 2 + y * polyWidth / 2);
				tabPoly[y][x].setLayoutY(((height - 1) * polyHeight / 2) + x * polyHeight / 2 - y * polyHeight / 2);
				tabPoly[y][x].getLien().addObserver(this);

				getChildren().add(tabPoly[y][x]);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(arg instanceof TuileView)) {
			return;
		}
		TuileView tuile = (TuileView) arg;
		controleur.action(tuile);
		switch (Outil.getEtat()) {
			case TUILE:
				break;
			case REMPLISSAGE:
				remplissage(tuile, tuile.getType(), Outil.getType(), new ArrayList<>());
				break;
		}
	}

	public void remplissage(TuileView tuile, Type oldType, Type newType, ArrayList<TuileView> listDejaVu) {
		if (tuile == null || !tuile.getType().equals(oldType) || listDejaVu.contains(tuile)) {
			return;
		}
		tuile.setType(newType);
		listDejaVu.add(tuile);
		Point2D pos = tuile.getCoor();
		try {
			remplissage(tabPoly[(int) pos.getY() - 1][(int) pos.getX()], oldType, newType, listDejaVu);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			remplissage(tabPoly[(int) pos.getY() + 1][(int) pos.getX()], oldType, newType, listDejaVu);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			remplissage(tabPoly[(int) pos.getY()][(int) pos.getX() - 1], oldType, newType, listDejaVu);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			remplissage(tabPoly[(int) pos.getY()][(int) pos.getX() + 1], oldType, newType, listDejaVu);
		} catch (ArrayIndexOutOfBoundsException e) {
		}

	}

	public TuileView[][] getTabPoly() {
		return tabPoly;
	}

	public void setTabPoly(TuileView[][] tabPoly) {
		this.tabPoly = tabPoly;
		getChildren().clear();
		for (TuileView[] colonne : tabPoly) {
			getChildren().addAll(colonne);
		}
	}

	public TuileView[][] getCopyTabPoly() {

		TuileView[][] copy = new TuileView[tabPoly.length][tabPoly[0].length];
		for (int y = 0; y < tabPoly.length; y++) {
			for (int x = 0; x < tabPoly[0].length; x++) {
				copy[y][x] = tabPoly[y][x].copy();
				copy[y][x].getLien().addObserver(this);
			}
		}
		return copy;
	}

}

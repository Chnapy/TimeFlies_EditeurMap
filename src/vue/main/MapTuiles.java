/*
 * 
 * 
 * 
 */
package vue.main;

import Serializable.HorsCombat.Map.TypeTuile;
import controleur.Controleur;
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
		getChildren().clear();
		tabPoly = map.view;
		int width = tabPoly[0].length;
		int height = tabPoly.length;

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
				if (tabPoly[y][x].toResize) {
					tabPoly[y][x] = tabPoly[y][x].clone(polyBounds);
					tabPoly[y][x].getLien().addObserver(this);
				}
				tabPoly[y][x].setLayoutX(x * polyWidth / 2 + y * polyWidth / 2);
				tabPoly[y][x].setLayoutY(((height - 1) * polyHeight / 2) + x * polyHeight / 2 - y * polyHeight / 2);

				getChildren().add(tabPoly[y][x]);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(arg instanceof TuileView)) {
			if (arg instanceof Integer && (int) arg == -1) {
				controleur.updatePlacement();
			}
			return;
		}
		TuileView tuile = (TuileView) arg;
		controleur.action(tuile);
		switch (Outil.getEtat()) {
			case REMPLISSAGE:
				remplissage(tuile, tuile.getType(), Outil.getType(), new ArrayList<>());
				break;
		}
	}

	public void remplissage(TuileView tuile, TypeTuile oldType, TypeTuile newType, ArrayList<TuileView> listDejaVu) {
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

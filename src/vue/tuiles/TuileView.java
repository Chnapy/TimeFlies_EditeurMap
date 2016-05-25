/*
 * 
 * 
 * 
 */
package vue.tuiles;

import Serializable.HorsCombat.Map.TypeTuile;
import static Serializable.HorsCombat.Map.TypeTuile.*;
import java.util.Observable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import vue.main.Outil;

/**
 * TuileView.java
 *
 */
public class TuileView extends Polygon {

	public static final Color[] TUILE_CONTOUR_COLORS = {
		Color.valueOf("16BAFB88"),
		Color.valueOf("EE4C2088"),
		Color.valueOf("F5E32188"),
		Color.valueOf("6605CA88"),
		Color.valueOf("62B70188"),
		Color.valueOf("34120B88"),
		Color.valueOf("058A8588"),
		Color.valueOf("13B31788"),
		Color.valueOf("D3559B88"),
		Color.valueOf("6C294288"),};

	public static final Color[] TUILE_COLORS = {
		Color.valueOf("FFFFFF88"),
		Color.valueOf("FF000088"),
		Color.valueOf("00000088"),
		Color.valueOf("00FF0088")
	};

	private TypeTuile type;
	public int numEquipe;
	private final Point2D coordonnees;
	private final Lien lien;
	public final boolean toResize;

	public TuileView copy() {
		double[] bounds = new double[getPoints().size()];
		for (int i = 0; i < getPoints().size(); i++) {
			bounds[i] = getPoints().get(i);
		}
		TuileView copy = new TuileView(bounds, type, coordonnees, numEquipe, false);
		copy.setLayoutX(getLayoutX());
		copy.setLayoutY(getLayoutY());
		return copy;
	}

	class Lien extends Observable {

		public void change() {
			setChanged();
		}
	};

	public TuileView clone(double[] bounds) {
		return new TuileView(bounds, type, coordonnees, numEquipe, false);
	}

	public TuileView(TypeTuile type, Point2D coor) {
		this(null, type, coor, true);
	}

	public TuileView(double[] bounds, TypeTuile type, Point2D coor, boolean toResize) {
		this(bounds, type, coor, -1, toResize);
	}

	public TuileView(double[] bounds, TypeTuile type, Point2D coor, int equipe, boolean toResize) {
		super(bounds);
		coordonnees = coor;
		lien = new Lien();
		this.numEquipe = equipe;
		this.toResize = toResize;
		setStrokeWidth(5);
		setType(type);
		setNumEquipe(numEquipe);
		setEvents();
	}

	private void updateColor() {
		switch (type) {
			case SIMPLE:
				setFill(TUILE_COLORS[0]);
				break;
			case OBSTACLE:
				setFill(TUILE_COLORS[1]);
				break;
			case TROU:
				setFill(TUILE_COLORS[2]);
				break;
			case ECRAN:
				setFill(TUILE_COLORS[3]);
				break;
		}

		if (this.numEquipe >= 0) {
			setStroke(TUILE_CONTOUR_COLORS[numEquipe]);
		} else {
			setStroke(null);
		}
	}

	protected void setEvents() {
		setOnMouseDragged((e) -> {
			if (!(e.getPickResult().getIntersectedNode() instanceof TuileView)) {
				return;
			}
			TuileView cible = (TuileView) e.getPickResult().getIntersectedNode();
			switch (Outil.getEtat()) {
				case TUILE:
					if (!cible.getType().equals(Outil.getType())) {
						alert(cible);
						cible.setType(Outil.getType());
					}
					break;
			}
		});
		setOnMouseEntered((e) -> {
			switch (Outil.getEtat()) {
				case TUILE:
					switch (Outil.getType()) {
						case SIMPLE:
							setFill(TUILE_COLORS[0].darker());
							break;
						case OBSTACLE:
							setFill(TUILE_COLORS[1].darker());
							break;
						case TROU:
							setFill(TUILE_COLORS[2].darker());
							break;
						case ECRAN:
							setFill(TUILE_COLORS[3].darker());
							break;
					}
					break;
				case PLACEMENT:
					if (numEquipe != Outil.getEquipe()) {
						setStroke(TUILE_CONTOUR_COLORS[Outil.getEquipe()].darker());
					} else if (numEquipe != -1) {
						setStroke(TUILE_CONTOUR_COLORS[numEquipe].darker());
					}
			}
		});
		setOnMouseExited((e) -> {
			switch (Outil.getEtat()) {
				case TUILE:
					updateColor();
					break;
				case PLACEMENT:
					if (numEquipe == -1) {
						setStroke(null);
					} else {
						setStroke(TUILE_CONTOUR_COLORS[numEquipe]);
					}
			}
		});
		setOnMouseClicked((e) -> {
			switch (Outil.getEtat()) {
				case TUILE:
					if (!getType().equals(Outil.getType())) {
						alert();
						setType(Outil.getType());
					}
					break;
				case REMPLISSAGE:
					alert();
					break;
				case PLACEMENT:
					if (getType().equals(TypeTuile.OBSTACLE)
							|| getType().equals(TypeTuile.TROU)) {
						break;
					}
					alert();
					switch (e.getButton()) {
						case PRIMARY:
							if (numEquipe != Outil.getEquipe()) {
								if (numEquipe != -1) {
									Outil.getPersosParEquipe()[numEquipe]--;
								}
								setNumEquipe(Outil.getEquipe());
								Outil.getPersosParEquipe()[numEquipe]++;
							}
							break;
						case SECONDARY:
							if (numEquipe != -1) {
								Outil.getPersosParEquipe()[numEquipe]--;
								setNumEquipe(-1);
							}
							break;
					}
					alert(-1);
					break;
			}
		});
	}

	private void alert() {
		alert(this);
	}

	private void alert(Object arg) {
		lien.change();
		lien.notifyObservers(arg);
	}

	public TypeTuile getType() {
		return type;
	}

	public final void setType(TypeTuile type) {
		this.type = type;
		updateColor();
	}

	public int getNumEquipe() {
		return numEquipe;
	}

	public void setNumEquipe(int numEquipe) {
		this.numEquipe = numEquipe;
		updateColor();
	}

	public Point2D getCoor() {
		return coordonnees;
	}

	public Observable getLien() {
		return lien;
	}

}

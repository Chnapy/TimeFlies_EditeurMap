/*
 * 
 * 
 * 
 */
package vue.tuiles;

import gameplay.map.Type;
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

	public static final Color[] TUILE_COLORS = {
		Color.valueOf("FFFFFF88"),
		Color.valueOf("FF000088"),
		Color.valueOf("00000088"),
		Color.valueOf("00FF0088")
	};

	private Type type;
	private final Point2D coordonnees;
	private final Lien lien;
	public final boolean toResize;

	public TuileView copy() {
		double[] bounds = new double[getPoints().size()];
		for (int i = 0; i < getPoints().size(); i++) {
			bounds[i] = getPoints().get(i);
		}
		TuileView copy = new TuileView(bounds, type, coordonnees, false);
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
		return new TuileView(bounds, type, coordonnees, false);
	}

	public TuileView(Type type, Point2D coor) {
		this(null, type, coor, true);
	}

	public TuileView(double[] bounds, Type type, Point2D coor, boolean toResize) {
		super(bounds);
		coordonnees = coor;
		lien = new Lien();
		this.toResize = toResize;
		setType(type);
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
			}
		});
		setOnMouseExited((e) -> {
			switch (Outil.getEtat()) {
				case TUILE:
					updateColor();
					break;
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

	public Type getType() {
		return type;
	}

	public final void setType(Type type) {
		this.type = type;
		updateColor();
	}

	public Point2D getCoor() {
		return coordonnees;
	}

	public Observable getLien() {
		return lien;
	}

}

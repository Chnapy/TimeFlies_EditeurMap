/*
 * 
 * 
 * 
 */
package vue.main;

import gameplay.map.Type;
import static gameplay.map.Type.SIMPLE;
import static vue.main.Outil.Etat.TUILE;

/**
 * Outil
 Enum
 */
public class Outil {
	
	private static Etat etat = TUILE;
	private static Type type = SIMPLE;

	public static enum Etat {
		TUILE,
//		SELECTION,
		REMPLISSAGE;
	}

	public static Etat getEtat() {
		return etat;
	}

	public static boolean isEtat(Etat e) {
		return etat.equals(e);
	}

	public static void setEtat(Etat etat) {
		Outil.etat = etat;
	}

	public static Type getType() {
		return type;
	}

	public static void setType(Type type) {
		Outil.type = type;
	}

	public static void set(Etat etat, Type type) {
		System.out.println(etat + " " + type);
		Outil.etat = etat;
		Outil.type = type;
	}

}

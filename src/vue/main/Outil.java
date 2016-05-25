/*
 * 
 * 
 * 
 */
package vue.main;

import Serializable.HorsCombat.Map.TypeTuile;
import static Serializable.HorsCombat.Map.TypeTuile.SIMPLE;
import static vue.main.Outil.Etat.TUILE;

/**
 * Outil
 Enum
 */
public class Outil {
	
	private static Etat etat = TUILE;
	private static TypeTuile type = SIMPLE;
	private static int equipe;
	private static int[] persosParEquipe;
	private static int persosMaxParEquipe;

	public static enum Etat {
		TUILE,
		REMPLISSAGE,
		PLACEMENT;
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

	public static TypeTuile getType() {
		return type;
	}

	public static void setType(TypeTuile type) {
		Outil.type = type;
	}

	public static void set(Etat etat, TypeTuile type) {
		System.out.println(etat + " " + type);
		Outil.etat = etat;
		Outil.type = type;
	}

	public static int getEquipe() {
		return equipe;
	}

	public static void setEquipe(int equipe) {
		Outil.equipe = equipe;
	}

	public static int[] getPersosParEquipe() {
		return persosParEquipe;
	}

	public static void setPersosParEquipe(int[] persosParEquipe) {
		Outil.persosParEquipe = persosParEquipe;
	}

	public static int getPersosMaxParEquipe() {
		return persosMaxParEquipe;
	}

	public static void setPersosMaxParEquipe(int persosMaxParEquipe) {
		Outil.persosMaxParEquipe = persosMaxParEquipe;
	}

}

/*
 * 
 * 
 * 
 */
package Serializable.HorsCombat.Map;

/**
 * TypeTuile.java
 Représente les différents états que peut posséder une tuile de la map.
 *
 */
public enum TypeTuile {

	/**
	 * Une entité peut accéder à cette case.
	 * Ne bloque pas la ligne de vue.
	 */
	SIMPLE(true, true),
	/**
	 * Une entité ne peut pas accéder à cette case.
	 * Ne bloque pas la ligne de vue.
	 */
	TROU(false, true),
	/**
	 * Une entité peut accéder à cette case.
	 * Bloque la ligne de vue.
	 */
	ECRAN(true, false),
	/**
	 * Une entité ne peut pas accéder à cette case.
	 * Bloque la ligne de vue.
	 */
	OBSTACLE(false, false);
	
	private final boolean acces;
	private final boolean vue;

	private TypeTuile(boolean acces, boolean vue) {
		this.acces = acces;
		this.vue = vue;
	}
	
	public boolean canAcces() {
		return acces;
	}
	
	public boolean canVue() {
		return vue;
	}
	
}

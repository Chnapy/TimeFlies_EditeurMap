/*
 * 
 * 
 * 
 */
package gameplay.map;

/**
 * Type.java
 * Représente les différents états que peut posséder une tuile de la map.
 *
 */
public enum Type {

	/**
	 * Une entité peut accéder à cette case.
	 * Ne bloque pas la ligne de vue.
	 */
	SIMPLE,
	/**
	 * Une entité ne peut pas accéder à cette case.
	 * Ne bloque pas la ligne de vue.
	 */
	TROU,
	/**
	 * Une entité peut accéder à cette case.
	 * Bloque la ligne de vue.
	 */
	ECRAN,
	/**
	 * Une entité ne peut pas accéder à cette case.
	 * Bloque la ligne de vue.
	 */
	OBSTACLE
}

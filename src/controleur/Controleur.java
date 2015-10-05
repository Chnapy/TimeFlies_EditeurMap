/*
 * 
 * 
 * 
 */
package controleur;

import vue.Vue;

/**
 * Controleur.java
 *
 */
public class Controleur {

	private final Vue vue;

	public Controleur() {
		vue = new Vue();

		vue.show();
	}

}

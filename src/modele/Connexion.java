/*
 * 
 * 
 * 
 */
package modele;

/**
 * Connexion.java
 * 
 */
public class Connexion {
	
	private static boolean connecte = false;
	private static long idJoueur = -1;
	
	public static void connecter(String _login, String mdp) {
		if(connecte)
			return;
		
		//TODO Connexion
		
		connecte = true;
	}

	public static boolean isConnecte() {
		return connecte;
	}

	public static long getID() {
		return idJoueur;
	}

}

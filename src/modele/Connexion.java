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
	private static String login = null;
	
	public static void connecter(String _login, String mdp) {
		if(connecte)
			return;
		
		login = _login;
		//TODO Connexion
		
		connecte = true;
	}

	public static boolean isConnecte() {
		return connecte;
	}

	public static String getLogin() {
		return login;
	}

}

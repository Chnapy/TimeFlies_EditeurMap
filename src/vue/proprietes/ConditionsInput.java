/*
 * 
 * 
 * 
 */
package vue.proprietes;

import java.util.Arrays;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextInputControl;
import vue.popup.Dialogues.NumberField;

/**
 * ConditionsInput.java
 *
 */
public class ConditionsInput {

	public static final class MyListener implements ChangeListener<String> {

		private final String nom;
		private TextInputControl input;
		private final int min;
		private final int max;
		private boolean number;
		private final int[] filtres;

		public MyListener(String nom, int min, int max, int... filtres) {
			this.nom = nom;
			this.input = null;
			this.min = min;
			this.max = max;
			this.filtres = filtres;
		}

		@Override
		public void changed(ObservableValue<? extends String> ob, String oldValue, String newValue) {
			if(input == null)
				throw new Error("Input non intégré au listener. Utiliser .with()");
			if (number) {
				int newInt = newValue.isEmpty() ? 0 : Integer.parseInt(newValue);

				if (newInt >= min && newInt <= max && !isFiltred(newInt)) {
					input.setUserData("");
				} else {
					input.setUserData("Le champ " + nom + " doit être rempli avec un nombre compris entre " + min + " et " + max 
							+ ((filtres.length > 0) ? " et différent de " + Arrays.toString(filtres) : "") + ".");
				}
			} else {
				if (newValue.length() >= min && newValue.length() <= max) {
					input.setUserData("");
				} else {
					input.setUserData("Le champ " + nom + " doit être rempli avec un nombre de caractères compris entre " + min + " et " + max + ".");
				}
			}
		}

		private boolean isFiltred(int value) {
			if (filtres.length == 0) {
				return false;
			}
			for (int f : filtres) {
				if (f == value) {
					return true;
				}
			}
			return false;
		}
		
		public MyListener with(TextInputControl input) {
			this.input = input;
			this.number = input instanceof NumberField;
			this.input.setUserData("0");
			return this;
		}

	}

	public static final MyListener NOM = new MyListener("nom", 1, 64);
	public static final MyListener DESCRIPTION = new MyListener("description", 0, 256);
	public static final MyListener VERSION = new MyListener("version", 1, 8);
	public static final MyListener LARGEUR = new MyListener("largeur", 2, 64);
	public static final MyListener LONGUEUR = new MyListener("longueur", 2, 64);
	public static final MyListener NBR_EQUIPES = new MyListener("nbr. équipes", 0, 8, 1);
	public static final MyListener NBR_PERSOS = new MyListener("nbr. personnages", 2, 32);
	public static final MyListener LOGIN = new MyListener("login", 1, 64);
	public static final MyListener MDP = new MyListener("mot de passe", 1, 64);

}

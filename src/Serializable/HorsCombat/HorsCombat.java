/*
 * 
 * 
 * 
 */
package Serializable.HorsCombat;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * HorsCombat.java
 *
 */
public interface HorsCombat extends Serializable {

	public static enum TypeCombat {
		SOLO, EQUIPE, EQUIPE_CPS;
	}

	public static class DonneeJoueur implements HorsCombat {

		private static final long serialVersionUID = 2517617883765090872L;

		public final long id;
		public final int niveau;
		public final String nom;
		public final boolean pret;
		public final ArrayList<DonneePerso> persos;

		public DonneeJoueur(long id, int niveau, String nom, boolean pret, ArrayList<DonneePerso> persos) {
			this.id = id;
			this.niveau = niveau;
			this.nom = nom;
			this.pret = pret;
			this.persos = persos;
		}

	}

	public static class DonneePerso implements HorsCombat {

		private static final long serialVersionUID = -848412085536363880L;

		public final int niveau;
		public final String nomClasse;

		public DonneePerso(int niveau, String nomClasse) {
			this.niveau = niveau;
			this.nomClasse = nomClasse;
		}

	}

}

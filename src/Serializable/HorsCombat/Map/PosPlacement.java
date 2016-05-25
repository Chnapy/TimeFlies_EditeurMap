/*
 * 
 * 
 * 
 */
package Serializable.HorsCombat.Map;

import Serializable.HorsCombat.HorsCombat;
import Serializable.Position;

/**
 * PosPlacement.java
 * 
 */
public class PosPlacement implements HorsCombat {

	private static final long serialVersionUID = -7770846453654563059L;
	
	public final Position position;
	public final int numEquipe;

	public PosPlacement(Position position, int numEquipe) {
		this.position = position;
		this.numEquipe = numEquipe;
	}

}

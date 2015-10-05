/*
 * 
 * 
 * 
 */
package gameplay.map;

import java.io.Serializable;

/**
 * MapSerializable.java
 * EDITEUR DE MAP
 */
public class MapSerializable implements Serializable {

	private static final long serialVersionUID = 1350092881346723535L;

	public final Type[][] plan;

	public MapSerializable(Type[][] plan) {
		this.plan = plan;
	}

}

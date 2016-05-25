/*
 * 
 * 
 * 
 */
package Serializable;

import java.io.Serializable;

/**
 * Position.java
 * 
 */
public class Position implements Serializable {

	private static final long serialVersionUID = -8848246289523111641L;
	
	public final int x;
	public final int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

}

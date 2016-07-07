
package net.imagej.domains;

import visad.Gridded2DDoubleSet;

/**
 * Main class.
 * 
 * @author Curtis Rueden
 */
public final class Main {

	private Main() {
		// prevent instantiation of utility class
	}

	public static void main(final String... args) {
		Gridded2DDoubleSet set = new Linear2DDoubleSet(12.3, 23.4, 5.6, 98.7, 87.6, 4.3);
	}

}

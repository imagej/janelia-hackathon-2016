/**
 * 
 */
package net.imagej.domains;

public class DiscreteSpace {

	private String[] axes;

	public DiscreteSpace(String... axes) {
		this.axes = axes;
	}
	
	public String axis(int d) {
		return axes[d];
	}

}

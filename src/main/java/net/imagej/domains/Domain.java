/**
 * 
 */
package net.imagej.domains;

import java.util.Map;

import net.imagej.axis.Axis;
import net.imglib2.EuclideanSpace;

/**
 * Specifies a domain.
 * 
 * @author Stephan Saalfeld &lt;saalfelds@janelia.hhmi.org&gt;
 * @author Curtis Rueden
 */
public interface Domain extends EuclideanSpace {
	
	/** Gets the axis associated with the given dimension of the domain. */
	Axis axis(int d);
	
	/** Get all axes of this domain */
	Map<String, Axis> getAxes();
	
}

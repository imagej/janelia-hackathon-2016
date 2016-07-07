/**
 * 
 */
package net.imagej.spaces;

import net.imagej.axis.CalibratedAxis;
import net.imagej.space.CalibratedSpace;

/**
 * Specifies a domain.
 */
public interface Space extends EuclideanSpace {
	
	Axis axis(int d);

}

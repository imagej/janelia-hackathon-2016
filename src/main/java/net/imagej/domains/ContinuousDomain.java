/**
 * 
 */
package net.imagej.domains;

import net.imglib2.RealRandomAccessible;

/**
 * @author Stephan Saalfeld &lt;saalfelds@janelia.hhmi.org&gt;
 *
 */
public class ContinuousDomain<A, T> implements Domain, RealRandomAccessible<A> {
	
	/**
	 * Get a flattened {@link RealRandomAccessible} that provides
	 * access to the terminal values.  The number of dimensions
	 * is this.numDimensions + the number of dimensions of the
	 * co-domains.
	 * 
	 * TODO This is not necessarily possible if any of the co-domains
	 * is discrete as long as we haven't solved how to do interpolation.
	 * It requires that each co-domain can be transferred into a
	 * continuous domain.  If interpolation is necessary to do so, then
	 * this only makes sense if T can be interpolated (nearest neighbor
	 * is always possible?).
	 * 
	 * @return
	 */
	RealRandomAccessible<T> flatAccessible();

}

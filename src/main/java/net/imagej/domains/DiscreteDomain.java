/**
 * 
 */
package net.imagej.domains;

import net.imglib2.RandomAccessible;

/**
 * @author Stephan Saalfeld &lt;saalfelds@janelia.hhmi.org&gt;
 *
 */
public interface DiscreteDomain<A, T> extends Domain, RandomAccessible<A> {
	
	/**
	 * Get a flattened {@link RandomAccessible} that provides
	 * access to the terminal values.  The number of dimensions
	 * is this.numDimensions + the number of dimensions of the
	 * co-domains.
	 * 
	 * @return
	 */
	RandomAccessible<T> flatAccessible();

}

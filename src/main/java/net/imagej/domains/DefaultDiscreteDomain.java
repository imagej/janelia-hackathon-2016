/**
 * 
 */
package net.imagej.domains;

import net.imglib2.Interval;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessible;

/**
 * @author Stephan Saalfeld &lt;saalfelds@janelia.hhmi.org&gt;
 *
 */
public class DefaultDiscreteDomain<A, T> implements DiscreteDomain<A, T> {
	
	final protected RandomAccessible<A> sourceAccessible;
	final protected Domain sourceDomain;
	
	public <B extends RandomAccessible<A> & Domain> DefaultDiscreteDomain(
			final B source) {
		this.sourceAccessible = source;
		this.sourceDomain = source;
	}
	
	/**
	 * TODO build one, make it efficient if source is in a single RA
	 */
	@Override
	public RandomAccessible<T> flatAccessible() {
		return null;
	}

	@Override
	public int numDimensions() {
		return sourceAccessible.numDimensions();
	}

	@Override
	public RandomAccess<A> randomAccess() {
		return sourceAccessible.randomAccess();
	}

	@Override
	public RandomAccess<A> randomAccess(Interval interval) {
		return sourceAccessible.randomAccess(interval);
	}
}

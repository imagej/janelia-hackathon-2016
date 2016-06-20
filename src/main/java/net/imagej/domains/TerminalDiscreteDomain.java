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
public class TerminalDiscreteDomain<T> implements DiscreteDomain<T, T> {
	
	final protected RandomAccessible<T> source;
	
	public TerminalDiscreteDomain(final RandomAccessible<T> source) {
		this.source = source;
	}
	
	@Override
	public RandomAccessible<T> flatAccessible() {
		return source;
	}

	@Override
	public int numDimensions() {
		return source.numDimensions();
	}

	@Override
	public RandomAccess<T> randomAccess() {
		return source.randomAccess();
	}

	@Override
	public RandomAccess<T> randomAccess(Interval interval) {
		return source.randomAccess(interval);
	}
}

package net.imglib2.view.meta;

import gnu.trove.iterator.TIntIterator;
import net.imglib2.EuclideanSpace;

// Rename IntArraySet? implement Trove TIntSet?
public class AxisSet implements EuclideanSpace
{
	private final int n;

	private final boolean[] flags;

	private int size;

	/**
	 * Should numDimensions rather be like capacity and able to grow?
	 */
	public AxisSet( final int numDimensions )
	{
		n = numDimensions;
		flags = new boolean[ numDimensions ];
		size = 0;
	}

	@Override
	public int numDimensions()
	{
		return n;
	}

	public boolean contains( final int i )
	{
		return flags[ i ];
	}

	/**
	 * Add a value to the set.
	 *
	 * @param i
	 *            value to add.
	 * @return true if the set was modified by the add operation
	 */
	public boolean add( final int i )
	{
		if ( flags[ i ] )
			return false;

		flags[ i ] = true;
		++size;
		return true;
	}

	/**
	 * Remove a value from the set.
	 *
	 * @param i
	 *            value to remove
	 * @return true if the set was modified by the remove operation.
	 */
	public boolean remove( final int i )
	{
		if ( !flags[ i ] )
			return false;

		flags[ i ] = false;
		--size;
		return true;
	}

	/**
	 * Number of values in the set.
	 *
	 * @return number of values in the set.
	 */
	public int size()
	{
		return size;
	}

	public TIntIterator iterator()
	{
		return new TIntIterator()
		{
			int remaining = size;

			int i = 0;

			@Override
			public boolean hasNext()
			{
				return remaining > 0;
			}

			@Override
			public int next()
			{
				--remaining;
				while ( i < size && flags[ i ] == false )
					++i;
				return i;
			}

			@Override
			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		};
	}

    @Override
	public String toString() {
        final StringBuilder sb = new StringBuilder( "[" );
		boolean first = true;
		for ( int d = 0; d < flags.length; ++d )
		{
			if ( flags[ d ] )
			{
				if ( !first )
					sb.append( ", " );
				else
					first = false;
				sb.append( d );
			}
		}
		sb.append( "]" );
		return sb.toString();
    }
}

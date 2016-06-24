package net.imglib2.view.meta;

import java.util.Arrays;

import gnu.trove.iterator.TIntIterator;
import net.imglib2.Localizable;
import net.imglib2.Positionable;

public class OrderedAxisSet implements Localizable, Positionable
{
	private final int n;

	private final boolean[] flags;

	private int size;

	/**
	 * Should numDimensions rather be like capacity and able to grow?
	 */
	public OrderedAxisSet( final int numDimensions )
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

	public boolean isEmpty()
	{
		return size == 0;
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

	public int[] toArray()
	{
		final int[] array = new int[ size ];
		for ( int d = 0, i = 0; d < n; ++d )
			if ( flags[ d ] )
				array[ i++ ] = d;
		return array;
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

	@Override
	public int hashCode()
	{
		return Arrays.hashCode( flags );
	}

	@Override
	public boolean equals( final Object obj )
	{
        if (obj == this)
            return true;
        else if ( obj instanceof OrderedAxisSet )
        	return ( Arrays.equals( ( ( OrderedAxisSet ) obj ).flags, flags ) );
        else
        	return false;
	}

	/*
	 * === Localizable ==========================
	 */

	@Override
	public void localize( final float[] position )
	{
		for ( int d = 0; d < n; ++d )
			position[ d ] = getFloatPosition( d );
	}

	@Override
	public void localize( final double[] position )
	{
		for ( int d = 0; d < n; ++d )
			position[ d ] = getDoublePosition( d );
	}

	@Override
	public void localize( final int[] position )
	{
		for ( int d = 0; d < n; ++d )
			position[ d ] = getIntPosition( d );
	}

	@Override
	public void localize( final long[] position )
	{
		for ( int d = 0; d < n; ++d )
			position[ d ] = getLongPosition( d );
	}

	@Override
	public float getFloatPosition( final int d )
	{
		return flags[ d ] ? 1 : 0;
	}

	@Override
	public double getDoublePosition( final int d )
	{
		return flags[ d ] ? 1 : 0;
	}

	@Override
	public int getIntPosition( final int d )
	{
		return flags[ d ] ? 1 : 0;
	}

	@Override
	public long getLongPosition( final int d )
	{
		return flags[ d ] ? 1 : 0;
	}

	/*
	 * === Positionable ========================
	 */

	@Override
	public void fwd( final int d )
	{
		flags[ d ] = true;
	}

	@Override
	public void bck( final int d )
	{
		flags[ d ] = false;
	}

	@Override
	public void move( final int distance, final int d )
	{
		if ( distance != 0 )
			flags[ d ] = distance > 0;
	}

	@Override
	public void move( final long distance, final int d )
	{
		if ( distance != 0 )
			flags[ d ] = distance > 0;
	}

	@Override
	public void move( final Localizable distance )
	{
		for ( int d = 0; d < n; ++d )
			move( distance.getIntPosition( d ), d );
	}

	@Override
	public void move( final int[] distance )
	{
		for ( int d = 0; d < n; ++d )
			move( distance[ d ], d );
	}

	@Override
	public void move( final long[] distance )
	{
		for ( int d = 0; d < n; ++d )
			move( distance[ d ], d );
	}

	@Override
	public void setPosition( final Localizable position )
	{
		for ( int d = 0; d < n; ++d )
			setPosition( position.getIntPosition( d ), d );
	}

	@Override
	public void setPosition( final int[] position )
	{
		for ( int d = 0; d < n; ++d )
			setPosition( position[ d ], d );
	}

	@Override
	public void setPosition( final long[] position )
	{
		for ( int d = 0; d < n; ++d )
			setPosition( position[ d ], d );
	}

	@Override
	public void setPosition( final int position, final int d )
	{
		flags[ d ] = position > 0;
	}

	@Override
	public void setPosition( final long position, final int d )
	{
		flags[ d ] = position > 0;
	}
}

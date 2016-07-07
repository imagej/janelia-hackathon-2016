package net.imglib2.view.meta;

import java.util.Arrays;

import gnu.trove.iterator.TIntIterator;
import net.imglib2.Localizable;
import net.imglib2.Positionable;

/**
 * A subset of axes of <em>n</em>-dimensional space ({@link #numDimensions()}).
 * It can be accessed via various interfaces:
 * <p>
 * It can be used as an ordered set of {@code int}s (axis indices, iteration is
 * always from smallest to larges).
 * <p>
 * It can alse be used as a <em>n</em>-dimensional {@link Localizable}/
 * {@link Positionable}, where coordinate {@code 1} along dimension {@code d}
 * means that the axis with index {@code d} is in the set, and coordinate
 * {@code 0} means that the axis is not in the set.
 * <p>
 * It can also be positioned and localized with a {@code boolean[]} array.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */

// TODO: rename to Axes
// TODO: contructor numDims, axes...
public class OrderedAxisSet implements Localizable, Positionable
{
	private final int n;

	private final boolean[] flags;

	private int size;

	private void recomputeSize()
	{
		size = 0;
		for ( int d = 0; d < n; ++d )
			if ( flags[ d ] )
				++size;
	}

	/**
	 * Create empty OrderedAxisSet.
	 *
	 * @param numDimensions
	 *            number of dimensions of the space
	 */
	public OrderedAxisSet( final int numDimensions )
	{
		n = numDimensions;
		flags = new boolean[ numDimensions ];
		size = 0;
	}

	/**
	 * Protected constructor that can re-use the passed flags array.
	 *
	 * @param flags
	 *            array used to store the flags.
	 * @param copy
	 *            flag indicating whether flags array should be duplicated.
	 */
	protected OrderedAxisSet( final boolean[] flags, final boolean copy )
	{
		n = flags.length;
		this.flags = copy ? flags.clone() : flags;
		recomputeSize();
	}

	/**
	 * Create a point at a definite location in a space of the dimensionality of
	 * the position.
	 *
	 * @param position
	 *            the initial position. The length of the array determines the
	 *            dimensionality of the space.
	 */
	public OrderedAxisSet( final boolean... flags )
	{
		this( flags, true );
	}

	/**
	 * Create an OrderedAxisSet that is stored in the provided flags array.
	 *
	 * @param flags
	 *            array to use for storing the flags.
	 */
	public static OrderedAxisSet wrap( final boolean[] flags )
	{
		return new OrderedAxisSet( flags, false );
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

	public void setPosition( final OrderedAxisSet position )
	{
		System.arraycopy( position.flags, 0, flags, 0, n );
		size = position.size;
	}

	public void localize( final OrderedAxisSet position )
	{
		System.arraycopy( flags, 0, position.flags, 0, n );
		position.size = size;
	}

	public void setPosition( final boolean[] position )
	{
		System.arraycopy( position, 0, flags, 0, n );
		recomputeSize();
	}

	public void localize( final boolean[] position )
	{
		System.arraycopy( flags, 0, position, 0, n );
	}

	public void clear()
	{
		Arrays.fill( flags, false );
		size = 0;
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
		add( d );
	}

	@Override
	public void bck( final int d )
	{
		remove( d );
	}

	@Override
	public void move( final int distance, final int d )
	{
		if ( distance > 0 )
			add( d );
		else if ( distance < 0 )
			remove( d );
	}

	@Override
	public void move( final long distance, final int d )
	{
		if ( distance > 0 )
			add( d );
		else if ( distance < 0 )
			remove( d );
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
		if ( position > 0 )
			add( d );
		else
			remove( d );
	}

	@Override
	public void setPosition( final long position, final int d )
	{
		if ( position > 0 )
			add( d );
		else
			remove( d );
	}
}

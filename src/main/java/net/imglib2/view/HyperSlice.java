/**
 * 
 */
package net.imglib2.view;

import java.util.Arrays;

import net.imglib2.FinalInterval;
import net.imglib2.Interval;
import net.imglib2.Localizable;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessible;

/**
 * @author Stephan Saalfeld &lt;saalfelds@janelia.hhmi.org&gt;
 *
 */
public class HyperSlice< T > implements RandomAccessible< T >
{
	final protected RandomAccessible< T > source;
	final protected int numDimensions;
	
	/* in the hyperslice */
	final protected int[] axes;
	
	/* absolute position including internal axes that are ignored */
	final protected long[] position;
	
	/**
	 * Generates a {@link FinalInterval} whose boundaries are the current
	 * position of the hyperslice and {@link Interval interval} on the
	 * hyperslice.
	 * 
	 * @param interval
	 * @return
	 */
	private Interval sourceInterval( final Interval interval )
	{
		assert interval.numDimensions() == axes.length : "Interval dimensions do not match Hyperslice dimensions.";
		
		final long[] min = new long[ numDimensions ];
		final long[] max = new long[ numDimensions ];
		
		for ( int d = 0; d < position.length; ++d )
			min[ d ] = max[ d ] = position[ d ];
		
		for ( int d = 0; d < interval.numDimensions(); ++d )
		{
			min[ axes[ d ] ] = interval.min( d );
			max[ axes[ d ] ] = interval.max( d );
		}
		
		return new FinalInterval( min, max );
	}
	
	public class HyperSliceRandomAccess implements RandomAccess< T >
	{
		final protected RandomAccess< T > sourceAccess;
		
		public HyperSliceRandomAccess()
		{
			sourceAccess = source.randomAccess();
			sourceAccess.setPosition( position );
		}
		
		public HyperSliceRandomAccess( final Interval interval )
		{
			sourceAccess = source.randomAccess( sourceInterval( interval ) );
			sourceAccess.setPosition( position );
		}

		@Override
		public void localize( int[] position )
		{
			for ( int d = 0; d < numDimensions; ++d )
				position[ d ] = sourceAccess.getIntPosition( axes[ d ] );
		}

		@Override
		public void localize( long[] position )
		{
			for ( int d = 0; d < numDimensions; ++d )
				position[ d ] = sourceAccess.getLongPosition( axes[ d ] );
		}

		@Override
		public int getIntPosition( int d )
		{
			return sourceAccess.getIntPosition( axes[ d ] );
		}

		@Override
		public long getLongPosition( int d )
		{
			return sourceAccess.getLongPosition( axes[ d ] );
		}

		@Override
		public void localize( float[] position )
		{
			for ( int d = 0; d < numDimensions; ++d )
				position[ d ] = sourceAccess.getFloatPosition( axes[ d ] );
		}

		@Override
		public void localize( double[] position )
		{
			for ( int d = 0; d < numDimensions; ++d )
				position[ d ] = sourceAccess.getDoublePosition( axes[ d ] );
		}

		@Override
		public float getFloatPosition( int d )
		{
			return sourceAccess.getFloatPosition( axes[ d ] );
		}

		@Override
		public double getDoublePosition( int d )
		{
			return sourceAccess.getDoublePosition( axes[ d ] );
		}

		@Override
		public int numDimensions()
		{
			return numDimensions;
		}

		@Override
		public void fwd( int d )
		{
			sourceAccess.fwd( axes[ d ] );
		}

		@Override
		public void bck( int d )
		{
			sourceAccess.bck( axes[ d ] );
		}

		@Override
		public void move( int distance, int d )
		{
			sourceAccess.move( distance, axes[ d ] );
		}

		@Override
		public void move( long distance, int d )
		{
			sourceAccess.move( distance, axes[ d ] );
		}

		@Override
		public void move( Localizable localizable )
		{
			for ( int d = 0; d < numDimensions; ++d )
				sourceAccess.move( localizable.getLongPosition( d ), axes[ d ] );
		}

		@Override
		public void move( int[] distance )
		{
			for ( int d = 0; d < numDimensions; ++d )
				sourceAccess.move( distance[ d ], axes[ d ] );	
		}

		@Override
		public void move( long[] distance )
		{
			for ( int d = 0; d < numDimensions; ++d )
				sourceAccess.move( distance[ d ], axes[ d ] );	
		}

		@Override
		public void setPosition( Localizable localizable )
		{
			for ( int d = 0; d < numDimensions; ++d )
				sourceAccess.setPosition( localizable.getLongPosition( d ), axes[ d ] );	
		}

		@Override
		public void setPosition( int[] position )
		{
			for ( int d = 0; d < numDimensions; ++d )
				sourceAccess.setPosition( position[ d ], axes[ d ] );
		}

		@Override
		public void setPosition( long[] position )
		{
			for ( int d = 0; d < numDimensions; ++d )
				sourceAccess.setPosition( position[ d ], axes[ d ] );
		}

		@Override
		public void setPosition( int position, int d )
		{
			sourceAccess.setPosition( position, axes[ d ] );
		}

		@Override
		public void setPosition( long position, int d )
		{
			sourceAccess.setPosition( position, axes[ d ] );
		}

		@Override
		public T get()
		{
			return sourceAccess.get();
		}

		@Override
		public HyperSliceRandomAccess copy()
		{
			return new HyperSliceRandomAccess();
		}

		@Override
		public HyperSliceRandomAccess copyRandomAccess()
		{
			return copy();
		}
	}
	
	/**
	 * Create a new HyperSlice at a position.  The position is passed as
	 * position vector in source space, i.e. the positions along fixed axes
	 * are ignored.
	 * 
	 * @param source
	 * @param fixedAxes
	 * @param position
	 */
	public HyperSlice(
			final RandomAccessible< T > source,
			final int[] fixedAxes,
			final long[] position )
	{
		this.source = source;
		final int[] sortedFixedAxes = fixedAxes.clone();
		Arrays.sort( sortedFixedAxes );
		numDimensions = source.numDimensions() - fixedAxes.length;
		axes = new int[ numDimensions ];
		this.position = new long[ position.length ];
		
		for ( int d = 0, da = 0, db = 0; d < source.numDimensions(); ++d )
		{
			if ( da < sortedFixedAxes.length && sortedFixedAxes[ da ] == d )
			{
				++da;
				this.position[ d ] = position[ d ];
			}
			else
				axes[ db++ ] = d;
		}

//		System.out.println( "axes       " + Arrays.toString( axes ) );
//		System.out.println( "fixed axes " + Arrays.toString( sortedFixedAxes ) );
	}

	@Override
	public int numDimensions()
	{
		return axes.length;
	}

	@Override
	public HyperSliceRandomAccess randomAccess()
	{
		return new HyperSliceRandomAccess();
	}

	@Override
	public RandomAccess< T > randomAccess( Interval interval )
	{
		return new HyperSliceRandomAccess();
	}

}

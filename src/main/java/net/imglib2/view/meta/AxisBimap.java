package net.imglib2.view.meta;

import java.util.Arrays;

public class AxisBimap
{
	private final int[] sourceToTarget;

	private final int[] targetToSource;

	public AxisBimap( final int[] sourceToTarget, final int[] targetToSource )
	{
		this.sourceToTarget = sourceToTarget;
		this.targetToSource = targetToSource;
	}

	public AxisBimap copy()
	{
		throw new UnsupportedOperationException();
	}

	public AxisBimap inverse()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the index of the target axis to which the specified
	 * {@code sourceAxis} maps, or -1 if {@code sourceAxis} does not map to
	 * any target axis.
	 */
	public int getTargetAxisFor( final int sourceAxis )
	{
		return sourceToTarget[ sourceAxis ];
	}

	/**
	 * Returns the index of the source axis to which the specified
	 * {@code targetAxis} maps, or -1 if {@code targetAxis} does not map to
	 * any source axis.
	 */
	public int getSourceAxisFor( final int targetAxis )
	{
		return targetToSource[ targetAxis ];
	}

	public static AxisBimap fromSourceToTargetMap( final int[] sourceToTarget, final int numTargetDimensions )
	{
		return new AxisBimap( sourceToTarget, inverseMapping( sourceToTarget, numTargetDimensions ) );
	}

	public static AxisBimap fromTargetToSourceMap( final int[] targetToSource, final int numSourceDimensions )
	{
		return new AxisBimap( inverseMapping( targetToSource, numSourceDimensions ), targetToSource );
	}

	public OrderedAxisSet transformToTarget( final OrderedAxisSet source )
	{
		final OrderedAxisSet axisSet = new OrderedAxisSet( numTargetDimensions() );
		for ( int d = 0; d < targetToSource.length; ++d )
			if ( targetToSource[ d ] >= 0 && source.contains( targetToSource[ d ] ) )
				axisSet.add( d );
		return axisSet;
	}

	public OrderedAxisSet transformToSource( final OrderedAxisSet target )
	{
		final OrderedAxisSet axisSet = new OrderedAxisSet( numSourceDimensions() );
		for ( int d = 0; d < sourceToTarget.length; ++d )
			if ( sourceToTarget[ d ] >= 0 && target.contains( sourceToTarget[ d ] ) )
				axisSet.add( d );
		return axisSet;
	}

	public int numSourceDimensions()
	{
		return sourceToTarget.length;
	}

	public int numTargetDimensions()
	{
		return targetToSource.length;
	}

	private static int[] inverseMapping( final int[] a2b, final int nb )
	{
		final int na = a2b.length;
		final int[] b2a = new int[ nb ];
		Arrays.fill( b2a, -1 );
		for ( int da = 0; da < na; ++da )
		{
			final int db = a2b[ da ];
			if ( db >= 0 )
				b2a[ db ] = da;
		}
		return b2a;
	}
}
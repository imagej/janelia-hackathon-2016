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

	private static int[] inverseMapping( final int[] a2b, final int nb )
	{
		final int na = a2b.length;
		final int[] b2a = new int[ nb ];
		Arrays.fill( b2a, -1 );
		for ( int da = 0; da < na; ++da )
		{
			final int db = a2b[ da ];
			if ( db >= 0 )
				b2a[ da ] = da;
		}
		return b2a;
	}
}
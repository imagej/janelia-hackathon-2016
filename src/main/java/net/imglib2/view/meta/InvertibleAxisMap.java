package net.imglib2.view.meta;

import java.util.Arrays;

/**
 * A partial bijection of axes of a <em>n</em>-dimensional source space to axes
 * of a <em>m</em>-dimensional target space (maps every source axis to one or
 * zero target axes, maps every target axis to one or zero target axes.)
 * <p>
 * This can be used to transform {@link OrderedAxisSet}s.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class InvertibleAxisMap
{
	private final int[] sourceToTarget;

	private final int[] targetToSource;

	public InvertibleAxisMap( final int[] sourceToTarget, final int[] targetToSource )
	{
		this.sourceToTarget = sourceToTarget;
		this.targetToSource = targetToSource;
	}

	public InvertibleAxisMap copy()
	{
		throw new UnsupportedOperationException();
	}

	public InvertibleAxisMap inverse()
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

	public static InvertibleAxisMap fromSourceToTargetMap( final int[] sourceToTarget, final int numTargetDimensions )
	{
		return new InvertibleAxisMap( sourceToTarget, inverseMapping( sourceToTarget, numTargetDimensions ) );
	}

	public static InvertibleAxisMap fromTargetToSourceMap( final int[] targetToSource, final int numSourceDimensions )
	{
		return new InvertibleAxisMap( inverseMapping( targetToSource, numSourceDimensions ), targetToSource );
	}

	public OrderedAxisSet transformToTarget( final OrderedAxisSet source )
	{
		final OrderedAxisSet target = new OrderedAxisSet( numTargetDimensions() );
		apply( source, target );
		return target;
	}

	public OrderedAxisSet transformToSource( final OrderedAxisSet target )
	{
		final OrderedAxisSet source = new OrderedAxisSet( numSourceDimensions() );
		applyInverse( source, target );
		return source;
	}

	/**
	 * Transforms source to target (writes into target).
	 */
	public void apply( final OrderedAxisSet source, final OrderedAxisSet target )
	{
		target.clear();
		for ( int d = 0; d < targetToSource.length; ++d )
			if ( targetToSource[ d ] >= 0 && source.contains( targetToSource[ d ] ) )
				target.add( d );
	}

	/**
	 * Transforms target to source (writes into source).
	 */
	public void applyInverse( final OrderedAxisSet source, final OrderedAxisSet target )
	{
		source.clear();
		for ( int d = 0; d < sourceToTarget.length; ++d )
			if ( sourceToTarget[ d ] >= 0 && target.contains( sourceToTarget[ d ] ) )
				source.add( d );
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
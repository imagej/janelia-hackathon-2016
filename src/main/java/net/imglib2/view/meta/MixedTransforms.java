package net.imglib2.view.meta;

import net.imglib2.transform.integer.Mixed;
import net.imglib2.transform.integer.MixedTransform;

public class MixedTransforms
{
	/**
	 * Create the {@link AxisBimap} from source space to target space of the
	 * given {@link Mixed} transform.
	 *
	 * @param transform
	 * @return axis mapping of {@code transform}.
	 */
	public static AxisBimap getAxisBimap( final Mixed transform )
	{
		final int ns = transform.numSourceDimensions();
		final int nt = transform.numTargetDimensions();
		final int[] targetToSource = new int[ nt ];
		for  ( int d = 0; d < nt; ++d )
			targetToSource[ d ] = transform.getComponentZero( d ) ? -1 : transform.getComponentMapping( d );
		return AxisBimap.fromTargetToSourceMap( targetToSource, ns );
	}



	// TODO: this is copied from Views.rotate. Should be reused.
	/**
	 * Create a {@link Mixed} transform that rotates by 90 degrees. The rotation
	 * is specified by two axis indices, such that the {@code fromAxis} is
	 * rotated to the {@code toAxis}.
	 *
	 * For example: {@code getRotationTransform(0, 1, 3)} creates a transform
	 * that rotates the X axis (of a XYZ space) to the Y axis. Applying the
	 * transform to <em>(1,2,3)</em> yields <em>(2,-1,3)</em>.
	 *
	 * @param fromAxis
	 *            axis index.
	 * @param toAxis
	 *            axis index.
	 * @param n
	 *            number of dimensions of the space.
	 * @return a transform that rotates the {@code fromAxis} to the
	 *         {@code toAxis}.
	 */
	public static Mixed getRotationTransform( final int fromAxis, final int toAxis, final int n )
	{
		final MixedTransform t = new MixedTransform( n, n );
		if ( fromAxis != toAxis )
		{
			final int[] component = new int[ n ];
			final boolean[] inv = new boolean[ n ];
			for ( int e = 0; e < n; ++e )
			{
				if ( e == toAxis )
				{
					component[ e ] = fromAxis;
					inv[ e ] = true;
				}
				else if ( e == fromAxis )
				{
					component[ e ] = toAxis;
				}
				else
				{
					component[ e ] = e;
				}
			}
			t.setComponentMapping( component );
			t.setComponentInversion( inv );
		}
		return t;
	}

	// TODO: this is copied from Views.permute. Should be reused.
	/**
	 * TODO.
	 *
	 * @param fromAxis
	 * @param toAxis
	 * @param n
	 * @return
	 */
	public static Mixed getPermuteTransform( final int fromAxis, final int toAxis, final int n )
	{
		final int[] component = new int[ n ];
		for ( int e = 0; e < n; ++e )
			component[ e ] = e;
		component[ fromAxis ] = toAxis;
		component[ toAxis ] = fromAxis;
		final MixedTransform t = new MixedTransform( n, n );
		t.setComponentMapping( component );
		return t;
	}

	// TODO: this is copied from Views.hyperSlice. Should be reused.
	/**
	 * TODO
	 *
	 * @param d
	 * @param pos
	 * @param m
	 * @return
	 */
	public static MixedTransform getHyperSliceTransform( final int d, final long pos, final int m )
	{
		final int n = m - 1;
		final MixedTransform t = new MixedTransform( n, m );
		final long[] translation = new long[ m ];
		translation[ d ] = pos;
		final boolean[] zero = new boolean[ m ];
		final int[] component = new int[ m ];
		for ( int e = 0; e < m; ++e )
		{
			if ( e < d )
			{
				zero[ e ] = false;
				component[ e ] = e;
			}
			else if ( e > d )
			{
				zero[ e ] = false;
				component[ e ] = e - 1;
			}
			else
			{
				zero[ e ] = true;
				component[ e ] = 0;
			}
		}
		t.setTranslation( translation );
		t.setComponentZero( zero );
		t.setComponentMapping( component );
		return t;
	}
}

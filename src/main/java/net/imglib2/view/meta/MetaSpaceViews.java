package net.imglib2.view.meta;

import net.imglib2.transform.integer.Mixed;
import net.imglib2.transform.integer.MixedTransform;
import net.imglib2.view.Views;

/**
 * Static {@link MetaSpace} transform methods analogous to {@link Views}.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class MetaSpaceViews
{
	public static < S extends MetaSpace< S, T >, T >
			S rotate( final S source, final int fromAxis, final int toAxis )
	{
		if ( fromAxis == toAxis )
			return source;
		return mixedTransform( source, MixedTransforms.getRotationTransform( fromAxis, toAxis, source.numDimensions() ) );
	}

	public static < S extends MetaSpace< S, T >, T >
			S permute( final S source, final int fromAxis, final int toAxis )
	{
		if ( fromAxis == toAxis )
			return source;
		return mixedTransform( source, MixedTransforms.getPermuteTransform( fromAxis, toAxis, source.numDimensions() ) );
	}

	public static < S extends MetaSpace< S, T >, T >
			S hyperSlice( final S source, final int d, final long pos )
	{
		return mixedTransform( source, MixedTransforms.getHyperSliceTransform( d, pos, source.numDimensions() ) );
	}

	public static < S extends MetaSpace< S, T >, T >
			S mixedTransform( final S source, final Mixed transformToSource )
	{
		if ( source instanceof MetaSpaceView )
		{
			final MetaSpaceView< S, T > sourceView = ( ( MetaSpaceView< S, T > ) source );
			final S s = sourceView.getSource();
			final MixedTransform t = new MixedTransform( transformToSource.numSourceDimensions(), transformToSource.numTargetDimensions() );
			t.set( transformToSource );
			t.concatenate( sourceView.getTransformToSource() );
			return source.viewFactory().createView( s, t );
		}
		else
			return source.viewFactory().createView( source, transformToSource );
	}
}

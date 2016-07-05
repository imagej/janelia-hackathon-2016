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
	public static < T > MetaSpace< T > rotate( final MetaSpace< T > source, final int fromAxis, final int toAxis )
	{
		if ( fromAxis == toAxis )
			return source;
		return mixedTransform( source, MixedTransforms.getRotationTransform( fromAxis, toAxis, source.numDimensions() ) );
	}

	public static < T > MetaSpace< T > permute( final MetaSpace< T > source, final int fromAxis, final int toAxis )
	{
		if ( fromAxis == toAxis )
			return source;
		return mixedTransform( source, MixedTransforms.getPermuteTransform( fromAxis, toAxis, source.numDimensions() ) );
	}

	public static < T > MetaSpace< T > hyperSlice( final MetaSpace< T > source, final int d, final long pos )
	{
		return mixedTransform( source, MixedTransforms.getHyperSliceTransform( d, pos, source.numDimensions() ) );
	}

	public static < T > MetaSpace< T > mixedTransform( final MetaSpace< T > source, final Mixed transformToSource )
	{
		if ( source instanceof MetaSpaceView )
		{
			final MetaSpaceView< T > sourceView = ( ( MetaSpaceView< T > ) source );
			final MetaSpace< T > s = sourceView.getSource();
			final MixedTransform t = new MixedTransform( transformToSource.numSourceDimensions(), transformToSource.numTargetDimensions() );
			t.set( transformToSource );
			t.concatenate( sourceView.getTransformToSource() );
			return new MetaSpaceView<>( s, t, source.factory() );
		}
		else
			return new MetaSpaceView<>( source, transformToSource, source.factory() );
	}
}

package net.imglib2.view.meta;

import net.imglib2.RandomAccessible;
import net.imglib2.transform.integer.MixedTransform;
import net.imglib2.view.MixedTransformView;

public class MetaViewsUtils
{
	public static < T > RandomAccessible< T > addNonVaryingDimensions(
			final RandomAccessible< T > data,
			final OrderedAxisSet varyingAxes )
	{
		assert ( data.numDimensions() == varyingAxes.size() );
		final MixedTransform t = new MixedTransform( varyingAxes.numDimensions(), data.numDimensions() );
		t.setComponentMapping( varyingAxes.toArray() );
		return new MixedTransformView<>( data, t );
	}

	public static < T > RandomAccessible< T > wrapAsRandomAccessible(
			final T data,
			final int numDimensions )
	{
		final MixedTransform t = new MixedTransform( numDimensions, 0 );
		return new MixedTransformView<>( new ZeroDimRandomAccessible<>( data ), t );
	}
}

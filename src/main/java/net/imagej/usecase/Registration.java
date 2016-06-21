package net.imagej.usecase;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.imglib2.RandomAccessible;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.RealRandomAccessible;
import net.imglib2.interpolation.InterpolatorFactory;
import net.imglib2.interpolation.randomaccess.NLinearInterpolatorFactory;
import net.imglib2.outofbounds.OutOfBoundsBorderFactory;
import net.imglib2.outofbounds.OutOfBoundsFactory;
import net.imglib2.realtransform.AffineGet;
import net.imglib2.realtransform.AffineTransform;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.Views;

public class Registration
{
	public < T extends RealType< T > >
			Map< Long, AffineGet > registerSlices( final RandomAccessibleInterval< T > data ) // assumes data is XYT or XYZT
	{
		final InterpolatorFactory< T, RandomAccessible< T > > interpolatorFactory = new NLinearInterpolatorFactory<>(); // ?
		final OutOfBoundsFactory< T, RandomAccessibleInterval< T > > oobFactory = new OutOfBoundsBorderFactory<>(); // ?

		final int timeDimension = data.numDimensions() - 1; // ?
		final long timeMin = data.min( timeDimension );
		final long timeMax = data.max( timeDimension );

		final Map< Long, AffineGet > transforms = new HashMap<>();
		final AffineGet identity = new AffineTransform( data.numDimensions() - 1 ); // ?
		transforms.put( timeMin, identity );

		final RandomAccessibleInterval< T > template = Views.hyperSlice( data, timeDimension, timeMin ); // ?
		for ( long t = timeMin + 1; t <= timeMax; ++t )
		{
			final RealRandomAccessible< T > image =
					Views.interpolate(
						Views.extend(
							Views.hyperSlice( data, timeDimension, t ), // ?
							oobFactory ),
						interpolatorFactory );

			final AffineGet transform = align( template, image );
			transforms.put( t, transform );
		}

		return transforms;
	}

	public < T extends RealType< T > > AffineGet align(
			final RandomAccessibleInterval< T > template,
			final RealRandomAccessible< T > image )
	{
		throw new UnsupportedOperationException( "not implemented" );
	}



	// ===========================================================================



	public < T extends RealType< T > >
	Map< Long, AffineGet > registerSlices(
			final RandomAccessibleInterval< T > data, // assumes data is XYT or XYZT
			final double[] spatialCalib )
	{
		final int numSpatialDimensions = data.numDimensions() - 1;
		final AffineTransform calib = new AffineTransform( numSpatialDimensions );
		for ( int d = 0; d < numSpatialDimensions; ++d )
			calib.set( spatialCalib[ d ], d, d );

		final Map< Long, AffineGet > transforms = new HashMap<>();

		for ( final Entry< Long, AffineGet > entry : registerSlices( data ).entrySet() )
		{
			final Long t = entry.getKey();
			final AffineGet transformToT0 = entry.getValue();
			final AffineTransform transform = calib.copy();
			transform.concatenate( transformToT0 );
			transforms.put( t, transform );
		}

		return transforms;
	}



}

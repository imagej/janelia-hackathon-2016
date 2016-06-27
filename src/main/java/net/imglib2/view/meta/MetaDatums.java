package net.imglib2.view.meta;

import net.imglib2.AbstractEuclideanSpace;
import net.imglib2.EuclideanSpace;
import net.imglib2.Localizable;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessible;
import net.imglib2.transform.integer.Mixed;

public class MetaDatums
{
	public interface MetaDatumKey< T >
	{
	}

	public static interface MetaDatum< T > extends EuclideanSpace
	{
		public MetaDatumKey< T > getKey();

		public OrderedAxisSet getVariesWithAxes();

		public OrderedAxisSet getAttachedToAxes();

		public default boolean isAttachedToAxes()
		{
			return !getAttachedToAxes().isEmpty();
		}

		public RandomAccessible< T > getValues();

		/**
		 * Get the value of this {@link MetaDatum} (if the value is invariant
		 * across the space).
		 *
		 * @return the value if this {@link MetaDatum} is invariant across the
		 *         space. {@code null} if this is a varying {@link MetaDatum}.
		 */
		public T getValue();
	}

	public static class MetaDatumView< T > extends AbstractEuclideanSpace implements MetaDatum< T >
	{
		private final MetaDatum< T > source;

		private final AxisBimap axisBimap;

		public MetaDatumView( final MetaDatum< T > source, final Mixed transformToSource )
		{
			super( transformToSource.numSourceDimensions() );
			this.source = source;
			this.axisBimap = MixedTransforms.getAxisBimap( transformToSource );
		}

		@Override
		public OrderedAxisSet getVariesWithAxes()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OrderedAxisSet getAttachedToAxes()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RandomAccessible< T > getValues()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public T getValue()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MetaDatumKey< T > getKey()
		{
			// TODO Auto-generated method stub
			return null;
		}

	}

	public static class MetaDatumContainer< T > extends AbstractEuclideanSpace implements MetaDatum< T >
	{
		private final MetaDatumKey< T > key;

		private final T value;

		private final RandomAccessible< T > values;

		private final OrderedAxisSet variesWithAxes;

		private final OrderedAxisSet attachedToAxes;

		// invariant
		public MetaDatumContainer( final MetaDatumKey< T > key, final T data, final OrderedAxisSet attachedToAxes )
		{
			super( attachedToAxes.numDimensions() );
			this.key = key;
			this.value = data;
			this.values = MetaViewsUtils.wrapAsRandomAccessible( data, n );
			this.variesWithAxes = new OrderedAxisSet( n );
			this.attachedToAxes = attachedToAxes;
		}

		// varying
		public MetaDatumContainer( final MetaDatumKey< T > key, final RandomAccessible< T > data, final OrderedAxisSet variesWithAxes, final OrderedAxisSet attachedToAxes )
		{
			super( attachedToAxes.numDimensions() );
			this.key = key;
			this.value = null;
			this.values = MetaViewsUtils.addNonVaryingDimensions( data, variesWithAxes );
			this.variesWithAxes = variesWithAxes;
			this.attachedToAxes = attachedToAxes;
		}

		@Override
		public MetaDatumKey< T > getKey()
		{
			return key;
		}

		@Override
		public OrderedAxisSet getVariesWithAxes()
		{
			return variesWithAxes;
		}

		@Override
		public OrderedAxisSet getAttachedToAxes()
		{
			return attachedToAxes;
		}

		// TODO: replace this by something better/faster
		public T getAt( final Localizable pos )
		{
			final RandomAccess< T > access = values.randomAccess();
			access.setPosition( pos );
			return access.get();
		}

		@Override
		public String toString()
		{
			final StringBuilder sb = new StringBuilder( "MetaDatum \"" );
			sb.append( getKey() );
			sb.append( "\" attached to " );
			sb.append( getAttachedToAxes() );
			sb.append( " varying with " );
			sb.append( getVariesWithAxes() );
			return sb.toString();
		}

		@Override
		public RandomAccessible< T > getValues()
		{
			return values;
		}

		@Override
		public T getValue()
		{
			return value;
		}
	}
}

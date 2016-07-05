package net.imglib2.view.meta;

import java.util.HashMap;

import net.imglib2.AbstractEuclideanSpace;
import net.imglib2.EuclideanSpace;
import net.imglib2.Localizable;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessible;
import net.imglib2.transform.integer.Mixed;
import net.imglib2.view.MixedTransformView;

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

		// TODO: replace this by something better/faster
		public default T getAt( final Localizable pos )
		{
			final RandomAccess< T > access = getValues().randomAccess();
			access.setPosition( pos );
			return access.get();
		}
	}

	public static interface MetaDataSet extends EuclideanSpace
	{
		public < T > MetaDatum< T > getMetaDatum( MetaDatumKey< T > key );

		public OrderedAxisSet getAttachedToAxes();

//		TODO:
//		contains
//		isEmpty
//		iterator
	}

	public static class MetaDataSetView extends AbstractEuclideanSpace implements MetaDataSet
	{
		private final MetaDataSet source;

		private final Mixed transformToSource;

		private final InvertibleAxisMap axisMap;

		private final OrderedAxisSet attachedToAxes;

		public MetaDataSetView( final MetaDataSet source, final Mixed transformToSource )
		{
			this ( source, transformToSource, MixedTransforms.getAxisMap( transformToSource ) );
		}

		protected MetaDataSetView( final MetaDataSet source, final Mixed transformToSource, final InvertibleAxisMap axisMap )
		{
			this( source, transformToSource, axisMap, axisMap.transformToSource( source.getAttachedToAxes() ) );
		}

		protected MetaDataSetView( final MetaDataSet source, final Mixed transformToSource, final InvertibleAxisMap axisMap, final OrderedAxisSet attachedToAxes )
		{
			super( transformToSource.numSourceDimensions() );
			this.source = source;
			this.transformToSource = transformToSource;
			this.axisMap = axisMap;
			this.attachedToAxes = attachedToAxes;
		}

		@Override
		public < T > MetaDatum< T > getMetaDatum( final MetaDatumKey< T > key )
		{
			final MetaDatum< T > s = source.getMetaDatum( key );
			return ( s == null )
					? null
					: new MetaDatumView< T >( s, transformToSource, axisMap, attachedToAxes );
		}

		@Override
		public OrderedAxisSet getAttachedToAxes()
		{
			return attachedToAxes;
		}
	}

	public static class MetaDataSetContainer extends AbstractEuclideanSpace implements MetaDataSet
	{
		private final HashMap< MetaDatumKey< ? >, MetaDatum< ? > > datums;

		private final OrderedAxisSet attachedToAxes;

		public MetaDataSetContainer( final OrderedAxisSet attachedToAxes )
		{
			super( attachedToAxes.numDimensions() );
			this.datums = new HashMap<>();
			this.attachedToAxes = attachedToAxes;
		}

		@SuppressWarnings( "unchecked" )
		@Override
		public < T > MetaDatum< T > getMetaDatum( final MetaDatumKey< T > key )
		{
			return ( MetaDatum< T > ) datums.get( key );
		}

		@Override
		public OrderedAxisSet getAttachedToAxes()
		{
			return attachedToAxes;
		}
	}

	public static class MetaDatumView< T > extends AbstractEuclideanSpace implements MetaDatum< T >
	{
		private final MetaDatum< T > source;

		private final Mixed transformToSource;

		private final InvertibleAxisMap axisMap;

		private final OrderedAxisSet attachedToAxes;

		private OrderedAxisSet variesWithAxes;

		private RandomAccessible< T > transformedSourceValues;

		private T value;

		public MetaDatumView( final MetaDatum< T > source, final Mixed transformToSource )
		{
			this( source, transformToSource, MixedTransforms.getAxisMap( transformToSource ) );
		}

		protected MetaDatumView( final MetaDatum< T > source, final Mixed transformToSource, final InvertibleAxisMap axisMap )
		{
			this( source, transformToSource, axisMap, axisMap.transformToSource( source.getVariesWithAxes() ) );
		}

		protected MetaDatumView( final MetaDatum< T > source, final Mixed transformToSource, final InvertibleAxisMap axisMap, final OrderedAxisSet attachedToAxes )
		{
			super( transformToSource.numSourceDimensions() );
			this.source = source;
			this.transformToSource = transformToSource;
			this.axisMap = axisMap;
			this.attachedToAxes = attachedToAxes;
		}

		@Override
		public OrderedAxisSet getVariesWithAxes()
		{
			if ( variesWithAxes == null )
				variesWithAxes = axisMap.transformToSource( source.getVariesWithAxes() );
			return variesWithAxes;
		}

		@Override
		public OrderedAxisSet getAttachedToAxes()
		{
			return attachedToAxes;
		}

		@Override
		public RandomAccessible< T > getValues()
		{
			if ( transformedSourceValues == null )
				transformedSourceValues = new MixedTransformView< >( source.getValues(), transformToSource );
			return transformedSourceValues;
		}

		@Override
		public T getValue()
		{
			if ( value == null && variesWithAxes.isEmpty() )
					value = getValues().randomAccess().get();
			return value;
		}

		@Override
		public MetaDatumKey< T > getKey()
		{
			return source.getKey();
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
	}
}

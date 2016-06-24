package net.imglib2.view.meta;

import net.imglib2.EuclideanSpace;
import net.imglib2.Localizable;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessible;

public class MetaViews
{

	public static interface VaryingItemI< T > extends EuclideanSpace
	{
		public OrderedAxisSet getVariesWithAxes();

		public OrderedAxisSet getAttachedToAxes();

		public boolean isAttachedToAxes();

		public RandomAccessible< T > getValues();

		/**
		 * May return {@code null} if this is a varying MetaItem.
		 */
		public T getValue();

		public boolean isValid();
	}







	public static class VaryingItem< T > implements VaryingItemI< T >
	{
		private final int n;

		private final String name;

		private final T originalValue;

		private final RandomAccessible< T > originalVaryingData;

		private final RandomAccessible< T > data;

		private final OrderedAxisSet variesWithAxes;

		private final OrderedAxisSet attachedToAxes;

		// non-varying actually ...
		public VaryingItem( final String name, final T data, final OrderedAxisSet attachedToAxes )
		{
			this.n = attachedToAxes.numDimensions();
			this.name = name;
			this.originalValue = data;
			this.originalVaryingData = null;
			this.data = MetaViewsUtils.wrapAsRandomAccessible( data, n );
			this.variesWithAxes = new OrderedAxisSet( n );
			this.attachedToAxes = attachedToAxes;
		}

		public VaryingItem( final String name, final RandomAccessible< T > data, final OrderedAxisSet variesWithAxes, final OrderedAxisSet attachedToAxes )
		{
			this.n = attachedToAxes.numDimensions();
			this.name = name;
			this.originalValue = null;
			this.originalVaryingData = data;
			this.data = MetaViewsUtils.addNonVaryingDimensions( data, variesWithAxes );
			this.variesWithAxes = variesWithAxes;
			this.attachedToAxes = attachedToAxes;
		}

		@Override
		public int numDimensions()
		{
			return attachedToAxes.numDimensions();
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

		// TODO do we need this?
		@Override
		public boolean isAttachedToAxes()
		{
			return !attachedToAxes.isEmpty();
		}

		// TODO: replace this by something better/faster
		public T getAt( final Localizable pos )
		{
			final RandomAccess< T > access = data.randomAccess();
			access.setPosition( pos );
			return access.get();
		}

		@Override
		public String toString()
		{
			final StringBuilder sb = new StringBuilder( "MetaItem \"" );
			sb.append( name );
			sb.append( "\" attached to " );
			sb.append( attachedToAxes );
			sb.append( " varying with " );
			sb.append( variesWithAxes );
			return sb.toString();
		}

		@Override
		public RandomAccessible< T > getValues()
		{
			return data;
		}

		@Override
		public T getValue()
		{
			return originalValue;
		}

		@Override
		public boolean isValid()
		{
			return true;
		}
	}
}

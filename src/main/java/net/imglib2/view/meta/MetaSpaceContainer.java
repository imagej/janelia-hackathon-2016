package net.imglib2.view.meta;

import java.util.Arrays;
import java.util.HashMap;

import net.imglib2.AbstractInterval;
import net.imglib2.Interval;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;

public class MetaSpaceContainer< T > extends AbstractInterval implements RandomAccessibleInterval< T >
{
	public static interface Factory< T >
	{
		public T create();
	}

	private final HashMap< OrderedAxisSet, T > map;

	private final MetaSpaceContainer.Factory< T > factory;

	private final CreatingAccessible creatingAccessible;

	public MetaSpaceContainer( final int numDimensions, final Factory< T > factory )
	{
		super( numDimensions );
		Arrays.fill( min, 0 );
		Arrays.fill( max, 1 );
		map = new HashMap< >();
		this.factory = factory;
		creatingAccessible = new CreatingAccessible();
	}

	public CreatingAccessible creatingAccessible()
	{
		return creatingAccessible;
	}

	protected T getOrCreate( final OrderedAxisSet position )
	{
		T t = map.get( position );
		if ( t == null )
		{
			t = factory.create();
			map.put( position, t );
		}
		return t;
	}

	protected T get( final OrderedAxisSet position )
	{
		return map.get( position );
	}

	protected void put( final OrderedAxisSet position, final T value )
	{
		map.put( position, value );
	}

	@Override
	public Access randomAccess()
	{
		return new Access();
	}

	@Override
	public Access randomAccess( final Interval interval )
	{
		return new Access();
	}

	public class Access extends OrderedAxisSet implements RandomAccess< T >
	{
		Access()
		{
			super( n );
		}

		@Override
		public T get()
		{
			return MetaSpaceContainer.this.get( this );
		}

		@Override
		public Access copy()
		{
			return new Access();
		}

		@Override
		public Access copyRandomAccess()
		{
			return copy();
		}
	}

	public class CreatingAccess extends OrderedAxisSet implements RandomAccess< T >
	{
		CreatingAccess()
		{
			super( n );
		}

		@Override
		public T get()
		{
			return MetaSpaceContainer.this.getOrCreate( this );
		}

		@Override
		public CreatingAccess copy()
		{
			return new CreatingAccess();
		}

		@Override
		public CreatingAccess copyRandomAccess()
		{
			return new CreatingAccess();
		}
	}

	public class CreatingAccessible extends AbstractInterval implements RandomAccessibleInterval< T >
	{
		CreatingAccessible()
		{
			super( MetaSpaceContainer.this.n );
			Arrays.fill( min, 0 );
			Arrays.fill( max, 1 );
		}

		@Override
		public CreatingAccess randomAccess()
		{
			return new CreatingAccess();
		}

		@Override
		public CreatingAccess randomAccess( final Interval interval )
		{
			return new CreatingAccess();
		}
	}
}

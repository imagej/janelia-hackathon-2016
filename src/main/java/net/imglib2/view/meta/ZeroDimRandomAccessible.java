package net.imglib2.view.meta;

import net.imglib2.Interval;
import net.imglib2.Point;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessible;

/**
 * A 0-dimensional {@link RandomAccessible} wrapping a value of type {@code T}.
 */
public class ZeroDimRandomAccessible< T > implements RandomAccessible< T >
{
	private final T value;

	private final Access access;

	public ZeroDimRandomAccessible( final T value )
	{
		this.value = value;
		this.access = new Access();
	}

	@Override
	public int numDimensions()
	{
		return 0;
	}

	@Override
	public RandomAccess< T > randomAccess()
	{
		return access;
	}

	@Override
	public RandomAccess< T > randomAccess( final Interval interval )
	{
		return access;
	}

	class Access extends Point implements RandomAccess< T >
	{
		Access()
		{
			super( 0 );
		}

		@Override
		public T get()
		{
			return value;
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
}

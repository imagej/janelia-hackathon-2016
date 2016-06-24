package net.imglib2.view.meta;

import net.imglib2.Interval;
import net.imglib2.Point;
import net.imglib2.Positionable;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.RealPositionable;

/**
 * A 0-dimensional {@link RandomAccessibleInterval} wrapping a value of type {@code T}.
 */
public class ZeroDimRandomAccessible< T > extends Point implements RandomAccess< T >, RandomAccessibleInterval< T >
{
	private final T value;

	public ZeroDimRandomAccessible( final T value )
	{
		super( 0 );
		this.value = value;
	}

	@Override
	public RandomAccess< T > randomAccess()
	{
		return this;
	}

	@Override
	public RandomAccess< T > randomAccess( final Interval interval )
	{
		return this;
	}

	@Override
	public T get()
	{
		return value;
	}

	@Override
	public ZeroDimRandomAccessible< T > copy()
	{
		return this;
	}

	@Override
	public ZeroDimRandomAccessible< T > copyRandomAccess()
	{
		return this;
	}

	@Override
	public long min( final int d )
	{
		return 0;
	}

	@Override
	public void min( final long[] min )
	{}

	@Override
	public void min( final Positionable min )
	{}

	@Override
	public long max( final int d )
	{
		return 0;
	}

	@Override
	public void max( final long[] max )
	{}

	@Override
	public void max( final Positionable max )
	{}

	@Override
	public double realMin( final int d )
	{
		return 0;
	}

	@Override
	public void realMin( final double[] min )
	{}

	@Override
	public void realMin( final RealPositionable min )
	{}

	@Override
	public double realMax( final int d )
	{
		return 0;
	}

	@Override
	public void realMax( final double[] max )
	{}

	@Override
	public void realMax( final RealPositionable max )
	{}

	@Override
	public void dimensions( final long[] dimensions )
	{}

	@Override
	public long dimension( final int d )
	{
		return 0;
	}
}

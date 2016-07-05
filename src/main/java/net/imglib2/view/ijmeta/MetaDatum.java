package net.imglib2.view.ijmeta;

import net.imglib2.EuclideanSpace;
import net.imglib2.Localizable;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessible;
import net.imglib2.view.meta.OrderedAxisSet;

public interface MetaDatum< T > extends EuclideanSpace
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
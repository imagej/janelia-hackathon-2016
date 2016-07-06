package net.imglib2.view.meta;

import net.imglib2.EuclideanSpace;
import net.imglib2.transform.integer.Mixed;

public interface MetaSpace< S extends MetaSpace< S, T >, T > extends EuclideanSpace
{
	public static interface ElementFactory< T >
	{
		public T create( final OrderedAxisSet position );

		public T createView( T source, final Mixed transformToSource );
	}

	public static interface ViewFactory< S >
	{
		public S createView( S source, final Mixed transformToSource );
	}

	/**
	 * Returns {@code T} at the specified position if it exists, or {@code null}
	 * if it doesn't.
	 */
	public T getIfExists( final OrderedAxisSet position );

	/**
	 * Creates a {@code T} if none exists at the specified position.
	 */
	public T get( final OrderedAxisSet position );

	// create thread-safe independent access
//	public S access();

	// for creating Ts and views of Ts
	public ElementFactory< T > elementFactory();

	// for creating views of MetaSpaces
	public ViewFactory< S > viewFactory();
}

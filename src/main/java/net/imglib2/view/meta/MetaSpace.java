package net.imglib2.view.meta;

import net.imglib2.EuclideanSpace;
import net.imglib2.transform.integer.Mixed;

public interface MetaSpace< T > extends EuclideanSpace
{
	public static interface Factory< T >
	{
		public T create();

		public T createView( T source, final Mixed transformToSource );
	}

	public T get( final OrderedAxisSet position );

	// TODO: maybe this should be only in the (non-view) writable MetaSpace implementations???
	public T getOrCreate( final OrderedAxisSet position );

	// create thread-safe independent access
	public MetaSpace< T > access();
}
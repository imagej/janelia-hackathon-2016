package net.imglib2.view.ijmeta;

import net.imglib2.EuclideanSpace;
import net.imglib2.RandomAccessible;
import net.imglib2.view.meta.OrderedAxisSet;

public interface MetaDataSet extends EuclideanSpace, Iterable< MetaDatum< ? > >
{
	public < T > MetaDatum< T > getMetaDatum( MetaDatumKey< T > key );

	public < T > void put( MetaDatumKey< T > key, T data );

	public < T > void put( MetaDatumKey< T > key, final RandomAccessible< T > data, final OrderedAxisSet variesWithAxes );

	public boolean containsKey( MetaDatumKey< ? > key );

	public int size();

	public boolean isEmpty();

	public OrderedAxisSet getAttachedToAxes();
}

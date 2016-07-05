package net.imglib2.view.ijmeta;

import net.imglib2.EuclideanSpace;
import net.imglib2.view.meta.OrderedAxisSet;

public interface MetaDataSet extends EuclideanSpace
{
	public < T > MetaDatum< T > getMetaDatum( MetaDatumKey< T > key );

	public OrderedAxisSet getAttachedToAxes();

//	TODO:
//	contains
//	isEmpty
//	iterator
}

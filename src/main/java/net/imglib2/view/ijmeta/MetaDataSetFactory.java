package net.imglib2.view.ijmeta;

import net.imglib2.transform.integer.Mixed;
import net.imglib2.view.meta.MetaSpace;
import net.imglib2.view.meta.OrderedAxisSet;

public class MetaDataSetFactory implements MetaSpace.Factory< MetaDataSet >
{
	@Override
	public MetaDataSet create( final OrderedAxisSet position )
	{
		return new MetaDataSetContainer( position );
	}

	@Override
	public MetaDataSet createView( final MetaDataSet source, final Mixed transformToSource )
	{
		return new MetaDataSetView( source, transformToSource );
	}
}

package net.imglib2.view.ijmeta;

import net.imglib2.RandomAccessible;
import net.imglib2.view.meta.MetaSpace;
import net.imglib2.view.meta.MetaSpaceContainer;
import net.imglib2.view.meta.OrderedAxisSet;

public class IjMetaSpaceContainer extends MetaSpaceContainer< IjMetaSpace, MetaDataSet > implements IjMetaSpace
{
	public IjMetaSpaceContainer( final int numDimensions )
	{
		super( numDimensions );
	}

	@Override
	public MetaDataSetFactory elementFactory()
	{
		return MetaDataSetFactory.instance;
	}

	@Override
	public MetaSpace.ViewFactory< IjMetaSpace > viewFactory()
	{
		return IjMetaSpaceView.Factory.instance;
	}

	public < T > void put( final MetaDatumKey< T > key, final T data, final OrderedAxisSet attachedToAxes )
	{
		get( attachedToAxes ).put( key, data );
	}

	public < T > void put( final MetaDatumKey< T > key, final RandomAccessible< T > data, final OrderedAxisSet variesWithAxes, final OrderedAxisSet attachedToAxes  )
	{
		get( attachedToAxes ).put( key, data, variesWithAxes );
	}
}
package net.imglib2.view.ijmeta;

import net.imglib2.transform.integer.Mixed;
import net.imglib2.view.meta.MetaSpace;
import net.imglib2.view.meta.MetaSpaceView;

public class IjMetaSpaceView extends MetaSpaceView< IjMetaSpace, MetaDataSet > implements IjMetaSpace
{
	public IjMetaSpaceView( final IjMetaSpace source, final Mixed transformToSource )
	{
		super( source, transformToSource );
	}

	@Override
	public MetaDataSetFactory elementFactory()
	{
		return MetaDataSetFactory.instance;
	}

	@Override
	public MetaSpace.ViewFactory< IjMetaSpace > viewFactory()
	{
		return Factory.instance;
	}

	static class Factory implements MetaSpace.ViewFactory< IjMetaSpace >
	{
		private Factory()
		{}

		public static final Factory instance = new Factory();

		@Override
		public IjMetaSpace createView( final IjMetaSpace source, final Mixed transformToSource )
		{
			return new IjMetaSpaceView( source, transformToSource );
		}
	}
}
package net.imglib2.view.ijmeta;

import java.util.Iterator;

import net.imglib2.view.meta.MetaSpace;
import net.imglib2.view.meta.MetaSpaceUtils;

public interface IjMetaSpace extends MetaSpace< IjMetaSpace, MetaDataSet >, Iterable< MetaDatum< ? > >
{
	@Override
	public default Iterator< MetaDatum< ? > > iterator()
	{
		final Iterator< MetaDataSet > metaDataSets = MetaSpaceUtils.iterator( this );
		return new Iterator< MetaDatum< ? > >()
		{
			Iterator< MetaDatum< ? > > currentMetaDataSetIterator;

			MetaDataSet nextMetaDataSet;

			private void prepareNextMetaDataSet()
			{
				while ( metaDataSets.hasNext() )
				{
					nextMetaDataSet = metaDataSets.next();
					if ( !nextMetaDataSet.isEmpty() )
						return;
				}
				nextMetaDataSet = null;
			}

			{
				prepareNextMetaDataSet();
				currentMetaDataSetIterator = nextMetaDataSet != null
						? nextMetaDataSet.iterator()
						: null;
				prepareNextMetaDataSet();
			}

			@Override
			public boolean hasNext()
			{
				return nextMetaDataSet != null || ( currentMetaDataSetIterator != null && currentMetaDataSetIterator.hasNext() );
			}

			@Override
			public MetaDatum< ? > next()
			{
				if ( currentMetaDataSetIterator.hasNext() )
					return currentMetaDataSetIterator.next();
				else if ( nextMetaDataSet != null )
				{
					currentMetaDataSetIterator = nextMetaDataSet.iterator();
					prepareNextMetaDataSet();
					return currentMetaDataSetIterator.next();
				}
				else return null;
			}
		};
	}
}
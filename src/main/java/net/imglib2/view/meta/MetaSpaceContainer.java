package net.imglib2.view.meta;

import java.util.HashMap;

import net.imglib2.AbstractEuclideanSpace;

// TODO: MetaSpace could default implement Interval methods.
public class MetaSpaceContainer< T > extends AbstractEuclideanSpace implements MetaSpace< T >
{
	private final HashMap< OrderedAxisSet, T > map;

	private final MetaSpace.Factory< T > factory;

	public MetaSpaceContainer( final int numDimensions, final MetaSpace.Factory< T > factory )
	{
		super( numDimensions );
		map = new HashMap< >();
		this.factory = factory;
	}

	@Override
	public T get( final OrderedAxisSet position )
	{
		return map.get( position );
	}

	@Override
	public T getOrCreate( final OrderedAxisSet position )
	{
		T t = map.get( position );
		if ( t == null )
		{
			t = factory.create( position );
			map.put( position, t );
		}
		return t;
	}

	@Override
	public MetaSpace< T > access()
	{
		return this;
	}
}

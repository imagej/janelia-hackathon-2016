package net.imglib2.view.meta;

import java.util.HashMap;

import net.imglib2.AbstractEuclideanSpace;

public abstract class MetaSpaceContainer< S extends MetaSpace< S, T >, T >
		extends AbstractEuclideanSpace
		implements MetaSpace< S, T >
{
	private final HashMap< OrderedAxisSet, T > elements;

	public MetaSpaceContainer( final int numDimensions )
	{
		super( numDimensions );
		elements = new HashMap<>();
	}

	@Override
	public T getIfExists( final OrderedAxisSet position )
	{
		return elements.get( position );
	}

	@Override
	public T get( final OrderedAxisSet position )
	{
		T t = elements.get( position );
		if ( t == null )
		{
			t = elementFactory().create( position );
			elements.put( position, t );
		}
		return t;
	}

//	@Override
//	public S access()
//	{
//		return ( S ) this;
//	}
}

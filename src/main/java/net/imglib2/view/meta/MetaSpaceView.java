package net.imglib2.view.meta;

import java.util.HashMap;

import net.imglib2.AbstractEuclideanSpace;
import net.imglib2.transform.integer.Mixed;

public abstract class MetaSpaceView< S extends MetaSpace< S, T >, T >
		extends AbstractEuclideanSpace
		implements MetaSpace< S, T >
{
	private final S source;

	private final Mixed transformToSource;

	final InvertibleAxisMap axisMap;

	private final HashMap< OrderedAxisSet, T > elements;

	public MetaSpaceView( final S source, final Mixed transformToSource )
	{
		super( transformToSource.numSourceDimensions() );
		this.source = source;
		this.transformToSource = transformToSource;
		this.axisMap = MixedTransforms.getAxisMap( transformToSource );
		this.elements = new HashMap<>();
	}

	/**
	 * TODO: should created views really be cached?
	 */

	@Override
	public T getIfExists( final OrderedAxisSet position )
	{
		T t = elements.get( position );
		if ( t == null )
		{
			t = getFromSource( position, new OrderedAxisSet( source.numDimensions() ) );
			if ( t != null )
			{
				t = elementFactory().createView( t, transformToSource );
				elements.put( position, t );
			}
		}
		return t;
	}

	@Override
	public T get( final OrderedAxisSet position )
	{
		T t = elements.get( position );
		if ( t == null )
		{
			t = getOrCreateFromSource( position, new OrderedAxisSet( source.numDimensions() ) );
			t = elementFactory().createView( t, transformToSource );
			elements.put( position, t );
		}
		return t;
	}

	private T getFromSource( final OrderedAxisSet position, final OrderedAxisSet sourcePos  )
	{
		axisMap.apply( position, sourcePos );
		return source.getIfExists( sourcePos );
	}

	private T getOrCreateFromSource( final OrderedAxisSet position, final OrderedAxisSet sourcePos  )
	{
		axisMap.apply( position, sourcePos );
		return source.get( sourcePos );
	}

	public S getSource()
	{
		return source;
	}

	public Mixed getTransformToSource()
	{
		return transformToSource;
	}
}

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

	private final InvertibleAxisMap axisMap;

	private final HashMap< OrderedAxisSet, T > elements;

	public MetaSpaceView( final S source, final Mixed transformToSource )
	{
		super( transformToSource.numSourceDimensions() );
		this.source = source;
		this.transformToSource = transformToSource;
		this.axisMap = MixedTransforms.getAxisMap( transformToSource );
		this.elements = new HashMap<>();
	}

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
		axisMap.applyInverse( sourcePos, position );
		return source.getIfExists( sourcePos );
	}

	private T getOrCreateFromSource( final OrderedAxisSet position, final OrderedAxisSet sourcePos  )
	{
		axisMap.applyInverse( sourcePos, position );
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

//	@Override
//	public S access()
//	{
//		return new Access();
//	}
//
//	class Access implements MetaSpace< S, T >
//	{
//		private final OrderedAxisSet sourcePos = new OrderedAxisSet( source.numDimensions() );
//
//		@Override
//		public T get( final OrderedAxisSet position )
//		{
//			return MetaSpaceView.this.get( position, sourcePos );
//		}
//
//		@Override
//		public T getOrCreate( final OrderedAxisSet position )
//		{
//			return MetaSpaceView.this.getOrCreate( position, sourcePos );
//		}
//
//		@Override
//		public MetaSpace< T > access()
//		{
//			return MetaSpaceView.this.access();
//		}
//
//		@Override
//		public int numDimensions()
//		{
//			return MetaSpaceView.this.numDimensions();
//		}
//
//		@Override
//		public MetaSpace.Factory< T > factory()
//		{
//			return MetaSpaceView.this.factory();
//		}
//	}
}

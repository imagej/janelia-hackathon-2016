package net.imglib2.view.meta;

import net.imglib2.AbstractEuclideanSpace;
import net.imglib2.transform.integer.Mixed;

public class MetaSpaceView< T > extends AbstractEuclideanSpace implements MetaSpace< T >
{
	private final MetaSpace< T > source;

	private final MetaSpace.Factory< T > factory;

	private final Mixed transformToSource;

	private final InvertibleAxisMap axisMap;

	public MetaSpaceView( final MetaSpace< T > source, final Mixed transformToSource, final MetaSpace.Factory< T > factory )
	{
		super( transformToSource.numSourceDimensions() );
		this.source = source;
		this.transformToSource = transformToSource;
		this.factory = factory;
		this.axisMap = MixedTransforms.getAxisMap( transformToSource );
	}

	@Override
	public T get( final OrderedAxisSet position )
	{
		return get( position, new OrderedAxisSet( source.numDimensions() ) );
	}

	@Override
	public T getOrCreate( final OrderedAxisSet position )
	{
		return getOrCreate( position, new OrderedAxisSet( source.numDimensions() ) );
	}

	@Override
	public MetaSpace< T > access()
	{
		return new Access();
	}

	@Override
	public MetaSpace.Factory< T > factory()
	{
		return source.factory();
	}

	public MetaSpace< T > getSource()
	{
		return source;
	}

	public Mixed getTransformToSource()
	{
		return transformToSource;
	}

	private T get( final OrderedAxisSet position, final OrderedAxisSet sourcePos  )
	{
		axisMap.applyInverse( sourcePos, position );
		return factory.createView( source.get( sourcePos ), transformToSource );
	}

	private T getOrCreate( final OrderedAxisSet position, final OrderedAxisSet sourcePos  )
	{
		axisMap.applyInverse( sourcePos, position );
		return factory.createView( source.getOrCreate( sourcePos ), transformToSource );
	}

	class Access implements MetaSpace< T >
	{
		private final OrderedAxisSet sourcePos = new OrderedAxisSet( source.numDimensions() );

		@Override
		public T get( final OrderedAxisSet position )
		{
			return MetaSpaceView.this.get( position, sourcePos );
		}

		@Override
		public T getOrCreate( final OrderedAxisSet position )
		{
			return MetaSpaceView.this.getOrCreate( position, sourcePos );
		}

		@Override
		public MetaSpace< T > access()
		{
			return MetaSpaceView.this.access();
		}

		@Override
		public int numDimensions()
		{
			return MetaSpaceView.this.numDimensions();
		}

		@Override
		public MetaSpace.Factory< T > factory()
		{
			return MetaSpaceView.this.factory();
		}
	}
}

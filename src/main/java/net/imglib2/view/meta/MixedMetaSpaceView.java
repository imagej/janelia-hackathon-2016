package net.imglib2.view.meta;

import net.imglib2.AbstractEuclideanSpace;
import net.imglib2.transform.integer.Mixed;

public class MixedMetaSpaceView< T > extends AbstractEuclideanSpace implements MetaSpace< T >
{
	private final MetaSpace< T > source;

	private final MetaSpace.Factory< T > factory;

	private final AxisBimap axisBimap;

	public MixedMetaSpaceView( final MetaSpace< T > source, final Mixed transformToSource, final MetaSpace.Factory< T > factory )
	{
		super( transformToSource.numSourceDimensions() );
		this.source = source;
		this.factory = factory;
		axisBimap = MixedTransforms.getAxisBimap( transformToSource );
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

	private void computeSourcePos( final OrderedAxisSet position, final OrderedAxisSet sourcePos )
	{
		for ( int d = 0; d < n; ++d )
			if ( position.contains( d ) )
				sourcePos.fwd( axisBimap.getTargetAxisFor( d ) );
	}

	private T get( final OrderedAxisSet position, final OrderedAxisSet sourcePos  )
	{
		computeSourcePos( position, sourcePos );
		return source.get( sourcePos );
	}

	private T getOrCreate( final OrderedAxisSet position, final OrderedAxisSet sourcePos  )
	{
		computeSourcePos( position, sourcePos );
		return source.getOrCreate( sourcePos );
	}

	class Access implements MetaSpace< T >
	{
		private final OrderedAxisSet sourcePos = new OrderedAxisSet( source.numDimensions() );

		@Override
		public T get( final OrderedAxisSet position )
		{
			return MixedMetaSpaceView.this.get( position, sourcePos );
		}

		@Override
		public T getOrCreate( final OrderedAxisSet position )
		{
			return MixedMetaSpaceView.this.getOrCreate( position, sourcePos );
		}

		@Override
		public MetaSpace< T > access()
		{
			return MixedMetaSpaceView.this.access();
		}

		@Override
		public int numDimensions()
		{
			return MixedMetaSpaceView.this.numDimensions();
		}

	}
}

package net.imglib2.view.ijmeta;

import java.util.HashMap;

import net.imglib2.AbstractEuclideanSpace;
import net.imglib2.RandomAccessible;
import net.imglib2.view.meta.OrderedAxisSet;

public class MetaDataSetContainer extends AbstractEuclideanSpace implements MetaDataSet
{
	private final HashMap< MetaDatumKey< ? >, MetaDatum< ? > > datums;

	private final OrderedAxisSet attachedToAxes;

	public MetaDataSetContainer( final OrderedAxisSet attachedToAxes )
	{
		super( attachedToAxes.numDimensions() );
		this.datums = new HashMap<>();
		this.attachedToAxes = attachedToAxes;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T > MetaDatum< T > getMetaDatum( final MetaDatumKey< T > key )
	{
		return ( MetaDatum< T > ) datums.get( key );
	}

	@Override
	public OrderedAxisSet getAttachedToAxes()
	{
		return attachedToAxes;
	}

	@Override
	public < T > void put( final MetaDatumKey< T > key, final T data )
	{
		datums.put( key, new MetaDatumContainer<>( key, data, attachedToAxes ) );
	}

	@Override
	public < T > void put( final MetaDatumKey< T > key, final RandomAccessible< T > data, final OrderedAxisSet variesWithAxes )
	{
		datums.put( key, new MetaDatumContainer<>( key, data, variesWithAxes, attachedToAxes ) );
	}

}
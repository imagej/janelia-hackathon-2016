package net.imglib2.view.ijmeta;

import java.util.HashMap;

import net.imglib2.AbstractEuclideanSpace;
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
}
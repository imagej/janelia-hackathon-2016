package net.imglib2.view.ijmeta;

import net.imglib2.AbstractEuclideanSpace;
import net.imglib2.transform.integer.Mixed;
import net.imglib2.view.meta.InvertibleAxisMap;
import net.imglib2.view.meta.MixedTransforms;
import net.imglib2.view.meta.OrderedAxisSet;

public class MetaDataSetView extends AbstractEuclideanSpace implements MetaDataSet
{
	private final MetaDataSet source;

	private final Mixed transformToSource;

	private final InvertibleAxisMap axisMap;

	private final OrderedAxisSet attachedToAxes;

	public MetaDataSetView( final MetaDataSet source, final Mixed transformToSource )
	{
		this ( source, transformToSource, MixedTransforms.getAxisMap( transformToSource ) );
	}

	protected MetaDataSetView( final MetaDataSet source, final Mixed transformToSource, final InvertibleAxisMap axisMap )
	{
		this( source, transformToSource, axisMap, axisMap.transformToSource( source.getAttachedToAxes() ) );
	}

	protected MetaDataSetView( final MetaDataSet source, final Mixed transformToSource, final InvertibleAxisMap axisMap, final OrderedAxisSet attachedToAxes )
	{
		super( transformToSource.numSourceDimensions() );
		this.source = source;
		this.transformToSource = transformToSource;
		this.axisMap = axisMap;
		this.attachedToAxes = attachedToAxes;
	}

	@Override
	public < T > MetaDatum< T > getMetaDatum( final MetaDatumKey< T > key )
	{
		final MetaDatum< T > s = source.getMetaDatum( key );
		return ( s == null )
				? null
				: new MetaDatumView< T >( s, transformToSource, axisMap, attachedToAxes );
	}

	@Override
	public OrderedAxisSet getAttachedToAxes()
	{
		return attachedToAxes;
	}
}
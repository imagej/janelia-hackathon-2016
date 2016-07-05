package net.imglib2.view.ijmeta;

import net.imglib2.AbstractEuclideanSpace;
import net.imglib2.RandomAccessible;
import net.imglib2.transform.integer.Mixed;
import net.imglib2.view.MixedTransformView;
import net.imglib2.view.meta.InvertibleAxisMap;
import net.imglib2.view.meta.MixedTransforms;
import net.imglib2.view.meta.OrderedAxisSet;

public class MetaDatumView< T > extends AbstractEuclideanSpace implements MetaDatum< T >
{
	private final MetaDatum< T > source;

	private final Mixed transformToSource;

	private final InvertibleAxisMap axisMap;

	private final OrderedAxisSet attachedToAxes;

	private OrderedAxisSet variesWithAxes;

	private RandomAccessible< T > transformedSourceValues;

	private T value;

	public MetaDatumView( final MetaDatum< T > source, final Mixed transformToSource )
	{
		this( source, transformToSource, MixedTransforms.getAxisMap( transformToSource ) );
	}

	protected MetaDatumView( final MetaDatum< T > source, final Mixed transformToSource, final InvertibleAxisMap axisMap )
	{
		this( source, transformToSource, axisMap, axisMap.transformToSource( source.getVariesWithAxes() ) );
	}

	protected MetaDatumView( final MetaDatum< T > source, final Mixed transformToSource, final InvertibleAxisMap axisMap, final OrderedAxisSet attachedToAxes )
	{
		super( transformToSource.numSourceDimensions() );
		this.source = source;
		this.transformToSource = transformToSource;
		this.axisMap = axisMap;
		this.attachedToAxes = attachedToAxes;
	}

	@Override
	public OrderedAxisSet getVariesWithAxes()
	{
		if ( variesWithAxes == null )
			variesWithAxes = axisMap.transformToSource( source.getVariesWithAxes() );
		return variesWithAxes;
	}

	@Override
	public OrderedAxisSet getAttachedToAxes()
	{
		return attachedToAxes;
	}

	@Override
	public RandomAccessible< T > getValues()
	{
		if ( transformedSourceValues == null )
			transformedSourceValues = new MixedTransformView< >( source.getValues(), transformToSource );
		return transformedSourceValues;
	}

	@Override
	public T getValue()
	{
		if ( value == null && variesWithAxes.isEmpty() )
				value = getValues().randomAccess().get();
		return value;
	}

	@Override
	public MetaDatumKey< T > getKey()
	{
		return source.getKey();
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder( "MetaDatum \"" );
		sb.append( getKey() );
		sb.append( "\" attached to " );
		sb.append( getAttachedToAxes() );
		sb.append( " varying with " );
		sb.append( getVariesWithAxes() );
		return sb.toString();
	}
}
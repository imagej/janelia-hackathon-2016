package net.imglib2.view.ijmeta;

import net.imglib2.AbstractEuclideanSpace;
import net.imglib2.RandomAccessible;
import net.imglib2.view.meta.MetaViewsUtils;
import net.imglib2.view.meta.OrderedAxisSet;

public class MetaDatumContainer< T > extends AbstractEuclideanSpace implements MetaDatum< T >
{
	private final MetaDatumKey< T > key;

	private final T value;

	private final RandomAccessible< T > values;

	private final OrderedAxisSet variesWithAxes;

	private final OrderedAxisSet attachedToAxes;

	// invariant
	public MetaDatumContainer( final MetaDatumKey< T > key, final T data, final OrderedAxisSet attachedToAxes )
	{
		super( attachedToAxes.numDimensions() );
		this.key = key;
		this.value = data;
		this.values = MetaViewsUtils.wrapAsRandomAccessible( data, n );
		this.variesWithAxes = new OrderedAxisSet( n );
		this.attachedToAxes = attachedToAxes;
	}

	// varying
	public MetaDatumContainer( final MetaDatumKey< T > key, final RandomAccessible< T > data, final OrderedAxisSet variesWithAxes, final OrderedAxisSet attachedToAxes )
	{
		super( attachedToAxes.numDimensions() );
		this.key = key;
		this.value = null;
		this.values = MetaViewsUtils.addNonVaryingDimensions( data, variesWithAxes );
		this.variesWithAxes = variesWithAxes;
		this.attachedToAxes = attachedToAxes;
	}

	@Override
	public MetaDatumKey< T > getKey()
	{
		return key;
	}

	@Override
	public OrderedAxisSet getVariesWithAxes()
	{
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
		return values;
	}

	@Override
	public T getValue()
	{
		return value;
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
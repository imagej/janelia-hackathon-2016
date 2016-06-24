package net.imglib2.view.meta;

import net.imglib2.Localizable;

public class AxesItem< T > implements MetaItem< T >
{
	private final T data;

	private final boolean[] attachedToAxes;

	public AxesItem( final T data, final boolean[] attachedToAxes )
	{
		this.data = data;
		this.attachedToAxes = attachedToAxes;
	}

	public boolean isAttachedToAxes()
	{
		return attachedToAxes != null;
	}

	@Override
	public T getAt( final Localizable pos )
	{
		return data;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder( "AxesItem " );
		sb.append( MetaViewsUtils.axisFlagsToString( attachedToAxes ) );
		return sb.toString();
	}
}

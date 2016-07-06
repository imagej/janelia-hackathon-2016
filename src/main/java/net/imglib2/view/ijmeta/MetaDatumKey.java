package net.imglib2.view.ijmeta;

public class MetaDatumKey< T >
{
	private final Class< T > klass;

	private final String name;

	private final int hashcode;

	public MetaDatumKey( final Class< T > klass, final String name )
	{
		this.klass = klass;
		this.name = name;
		this.hashcode = 31 * klass.hashCode() + name.hashCode();
	}

	@Override
	public int hashCode()
	{
		return hashcode;
	}

	@Override
	public boolean equals( final Object obj )
	{
		if ( obj instanceof MetaDatumKey )
		{
			final MetaDatumKey< ? > key = ( MetaDatumKey< ? > ) obj;
			return key.klass.equals( klass ) && key.name.equals( name );
		}
		else
			return false;
	}
}

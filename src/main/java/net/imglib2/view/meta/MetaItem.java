package net.imglib2.view.meta;

import net.imglib2.RandomAccessible;

// TODO: rename MetaDatum
public interface MetaItem< T >
{
	public RandomAccessible< T > randomAccessible();
}

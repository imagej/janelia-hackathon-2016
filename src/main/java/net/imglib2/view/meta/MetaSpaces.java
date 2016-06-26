package net.imglib2.view.meta;

import java.util.HashMap;

import net.imglib2.transform.integer.Mixed;


public class MetaSpaces
{

	// one item (maybe identified by Class< T >)
//	interface MetaObject< T >
//	{
//		public RandomAccessible< T > getValues();
//
//		public OrderedAxisSet getVariesWithAxes();
//	}

	public interface MetaObjectKey< O >
	{
	}

	// all items with same {@link #getAttachedToAxes()}
	public interface MetaObjectSet< T >
	{
		public < O extends T > O getMetaObject( final MetaObjectKey< O > key );

		public OrderedAxisSet getAttachedToAxes();
	}

	public static class MetaObjectHashSet< T > implements MetaObjectSet< T >
	{
		private final OrderedAxisSet attachedToAxes;

		private final HashMap< MetaObjectKey< ? extends T >, T > objects;

		public MetaObjectHashSet( final OrderedAxisSet attachedToAxes )
		{
			this.attachedToAxes = attachedToAxes;
			this.objects = new HashMap<>();
		}

		@Override
		public < O extends T > O getMetaObject( final MetaObjectKey< O > key )
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OrderedAxisSet getAttachedToAxes()
		{
			return attachedToAxes;
		}
	}

	public static class MetaObjectSetMixedTransformView< T > implements MetaObjectSet< T >
	{
		private final MetaObjectSet source;

		private final Mixed transformToSource;

		private OrderedAxisSet attachedToAxes;

		MetaObjectSetMixedTransformView( final MetaObjectSet source, final Mixed transformToSource )
		{
			this.source = source;
			this.transformToSource = transformToSource;

		}

		@Override
		public < O extends T > O getMetaObject( final MetaObjectKey< O > key )
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OrderedAxisSet getAttachedToAxes()
		{
			if ( attachedToAxes == null )
			{
				attachedToAxes = source.getAttachedToAxes()
			}
			return attachedToAxes;
		}
	}



	static void stuff()
	{











	}
}

//	private int positionToIndex( final OrderedAxisSet position )
//	{
//		int i = 0;
//		final TIntIterator iterator = position.iterator();
//		while ( iterator.hasNext() )
//			i += 2 << iterator.next();
//		return i;
//	}

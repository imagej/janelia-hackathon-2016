package net.imglib2.view.meta;

import net.imglib2.Interval;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessible;
import net.imglib2.transform.integer.Mixed;


public class MetaSpaces
{

	// one item (maybe identified by Class< T >)
	interface MetaObject< T >
	{
		public RandomAccessible< T > getValues();

		public OrderedAxisSet getVariesWithAxes();
	}

	// all items with same AttachedToAxes
	public interface MetaObjectSet
	{
		public < T > MetaObject< T > getMetaObject( final Class< T > type );
	}

	public static class MetaObjectSetImp implements MetaObjectSet
	{
		@Override
		public < T > MetaObject< T > getMetaObject( final Class< T > type )
		{
			return null;
		}
	}

	public static class MetaObjectSetMixedTransformView implements MetaObjectSet
	{
		private final MetaObjectSet source;

		private final Mixed transformToSource;

		MetaObjectSetMixedTransformView( final MetaObjectSet source, final Mixed transformToSource )
		{
			this.source = source;
			this.transformToSource = transformToSource;

		}

		@Override
		public < T > MetaObject< T > getMetaObject( final Class< T > type )
		{
			return null;
		}
	}

	public interface MetaSpace extends RandomAccessible< MetaObjectSet >
	{
//		public MetaObjectSet getAt( final OrderedAxisSet attachedToAxes );
	}

	public static class MetaSpaceImp extends MetaSpaceContainer< MetaObjectSet > implements MetaSpace
	{
		public MetaSpaceImp( final int numDimensions )
		{
			super( numDimensions, () -> new MetaObjectSetImp() );
		}
	}

	/**
	 *
	 */

	static class MetaSpaceMixedTransformView implements MetaSpace
	{
		private final int n;

		private final MetaSpace source;

		private final Mixed transformToSource;

		MetaSpaceMixedTransformView( final MetaSpace source, final Mixed transformToSource )
		{
			n = transformToSource.numSourceDimensions();
			this.source = source;
			this.transformToSource = transformToSource;
		}

		@Override
		public RandomAccess< MetaObjectSet > randomAccess()
		{
			return null;
		}

		@Override
		public RandomAccess< MetaObjectSet > randomAccess( final Interval interval )
		{
			return null;
		}

		@Override
		public int numDimensions()
		{
			return n;
		}
	}



	static void stuff()
	{











	}
}



//static class MetaSpaceArray implements MetaSpace
//{
//	private final int n;
//
//	private final ArrayList< MetaObjectSet > objectSets;
//
//	public MetaSpaceArray( final int numDimensions )
//	{
//		n = numDimensions;
//
//		final int numElements = 2 << n;
//		objectSets = new ArrayList<>( numElements );
//		for ( int i = 0; i < numElements; ++i )
//			objectSets.add( null );
//	}
//
//	@Override
//	public MetaObjectSet getAt( final OrderedAxisSet attachedToAxes )
//	{
//		return objectSets.get( positionToIndex( attachedToAxes ) );
//	}
//
//	private int positionToIndex( final OrderedAxisSet position )
//	{
//		int i = 0;
//		final TIntIterator iterator = position.iterator();
//		while ( iterator.hasNext() )
//			i += 2 << iterator.next();
//		return i;
//	}
//}

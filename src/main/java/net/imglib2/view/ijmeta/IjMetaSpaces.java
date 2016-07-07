package net.imglib2.view.ijmeta;

import static net.imglib2.view.meta.MetaSpaceViews.hyperSlice;
import static net.imglib2.view.meta.MetaSpaceViews.permute;

import java.util.Arrays;
import java.util.Iterator;

import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessible;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.iterator.IntervalIterator;
import net.imglib2.transform.integer.Mixed;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.util.Util;
import net.imglib2.view.meta.MetaSpace;
import net.imglib2.view.meta.MetaSpaceContainer;
import net.imglib2.view.meta.MetaSpaceUtils;
import net.imglib2.view.meta.MetaSpaceView;
import net.imglib2.view.meta.MetaSpaceViews;
import net.imglib2.view.meta.OrderedAxisSet;

public class IjMetaSpaces
{
	public static interface IjMetaSpace extends MetaSpace< IjMetaSpace, MetaDataSet >, Iterable< MetaDatum< ? > >
	{
		@Override
		public default Iterator< MetaDatum< ? > > iterator()
		{
			final Iterator< MetaDataSet > metaDataSets = MetaSpaceUtils.iterator( this );
			return new Iterator< MetaDatum< ? > >()
			{
				Iterator< MetaDatum< ? > > currentMetaDataSetIterator;

				MetaDataSet nextMetaDataSet;

				private void prepareNextMetaDataSet()
				{
					while ( metaDataSets.hasNext() )
					{
						nextMetaDataSet = metaDataSets.next();
						if ( !nextMetaDataSet.isEmpty() )
							return;
					}
					nextMetaDataSet = null;
				}

				{
					prepareNextMetaDataSet();
					currentMetaDataSetIterator = nextMetaDataSet != null
							? nextMetaDataSet.iterator()
							: null;
					prepareNextMetaDataSet();
				}

				@Override
				public boolean hasNext()
				{
					return nextMetaDataSet != null || ( currentMetaDataSetIterator != null && currentMetaDataSetIterator.hasNext() );
				}

				@Override
				public MetaDatum< ? > next()
				{
					if ( currentMetaDataSetIterator.hasNext() )
						return currentMetaDataSetIterator.next();
					else if ( nextMetaDataSet != null )
					{
						currentMetaDataSetIterator = nextMetaDataSet.iterator();
						prepareNextMetaDataSet();
						return currentMetaDataSetIterator.next();
					}
					else return null;
				}
			};
		}
	}

	public static class MetaDataSetFactory implements MetaSpace.ElementFactory< MetaDataSet >
	{
		private MetaDataSetFactory()
		{}

		public static final MetaDataSetFactory instance = new MetaDataSetFactory();

		@Override
		public MetaDataSet create( final OrderedAxisSet position )
		{
			return new MetaDataSetContainer( position );
		}

		@Override
		public MetaDataSet createView( final MetaDataSet source, final Mixed transformToSource )
		{
			return new MetaDataSetView( source, transformToSource );
		}
	}

	public static class IjMetaSpaceViewFactory implements MetaSpace.ViewFactory< IjMetaSpace >
	{
		private IjMetaSpaceViewFactory()
		{}

		public static final IjMetaSpaceViewFactory instance = new IjMetaSpaceViewFactory();

		@Override
		public IjMetaSpace createView( final IjMetaSpace source, final Mixed transformToSource )
		{
			return new IjMetaSpaceView( source, transformToSource );
		}
	}

	public static class IjMetaSpaceContainer extends MetaSpaceContainer< IjMetaSpace, MetaDataSet > implements IjMetaSpace
	{
		public IjMetaSpaceContainer( final int numDimensions )
		{
			super( numDimensions );
		}

		@Override
		public MetaDataSetFactory elementFactory()
		{
			return MetaDataSetFactory.instance;
		}

		@Override
		public MetaSpace.ViewFactory< IjMetaSpace > viewFactory()
		{
			return IjMetaSpaceViewFactory.instance;
		}

		public < T > void put( final MetaDatumKey< T > key, final T data, final OrderedAxisSet attachedToAxes )
		{
			get( attachedToAxes ).put( key, data );
		}

		public < T > void put( final MetaDatumKey< T > key, final RandomAccessible< T > data, final OrderedAxisSet variesWithAxes, final OrderedAxisSet attachedToAxes  )
		{
			get( attachedToAxes ).put( key, data, variesWithAxes );
		}
	}

	public static class IjMetaSpaceView extends MetaSpaceView< IjMetaSpace, MetaDataSet > implements IjMetaSpace
	{
		public IjMetaSpaceView( final IjMetaSpace source, final Mixed transformToSource )
		{
			super( source, transformToSource );
		}

		@Override
		public MetaDataSetFactory elementFactory()
		{
			return MetaDataSetFactory.instance;
		}

		@Override
		public MetaSpace.ViewFactory< IjMetaSpace > viewFactory()
		{
			return IjMetaSpaceViewFactory.instance;
		}
	}

	public static void main( final String[] args )
	{
		final MetaDatumKey< String > X_CALIB = new MetaDatumKey<>( String.class, "X Calib" );
		final MetaDatumKey< String > Y_CALIB = new MetaDatumKey<>( String.class, "Y Calib" );
		final MetaDatumKey< String > Z_CALIB = new MetaDatumKey<>( String.class, "Z Calib" );
		final MetaDatumKey< IntType > NUMBERS_XY = new MetaDatumKey<>( IntType.class, "Numbers [XY]" );

		final OrderedAxisSet axes_X = new OrderedAxisSet( true, false, false, false, false );
		final OrderedAxisSet axes_Y = new OrderedAxisSet( false, true, false, false, false );
		final OrderedAxisSet axes_Z = new OrderedAxisSet( false, false, true, false, false );
		final OrderedAxisSet axes_XY = new OrderedAxisSet( true, true, false, false, false );
		final OrderedAxisSet axes_none = new OrderedAxisSet( 5 );

		final IjMetaSpaceContainer space = new IjMetaSpaceContainer( 5 );

		space.put( X_CALIB, "2 um", axes_X );
		space.put( Y_CALIB, "5 um", axes_Y );
		space.put( Z_CALIB, "10 um", axes_Z );

		final int[] numbersXY = new int[] {
				0, 1,
				2, 3 };
		space.put( NUMBERS_XY, ArrayImgs.ints( numbersXY, 2, 2 ), axes_XY, axes_none );

		System.out.println( "space contains" );
		space.forEach(
				datum -> System.out.println( "  " + datum + "\n    value = " + datum.getValue() ) );
		System.out.println();

		final IjMetaSpace space2 = MetaSpaceViews.permute( space, 0, 2 );

		System.out.println( "space2 contains" );
		space2.forEach(
				datum -> System.out.println( "  " + datum + "\n    value = " + datum.getValue() ) );
		System.out.println();

		final IjMetaSpace space3 = MetaSpaceViews.hyperSlice( space2, 1, 1 );

		System.out.println( "space3 contains" );
		space3.forEach(
				datum -> System.out.println( "  " + datum + "\n    value = " + datum.getValue() ) );
		System.out.println();

		final IjMetaSpace space4 = MetaSpaceViews.hyperSlice( space3, 1, 0 );

		System.out.println( "space4 contains" );
		space4.forEach(
				datum -> System.out.println( "  " + datum + "\n    value = " + datum.getValue() ) );
		System.out.println();



		System.out.println();
		System.out.println( "--------------------" );
		System.out.println();

		printVarying(
				space
				.get( axes_none ).getMetaDatum( NUMBERS_XY ) );
		System.out.println();
		System.out.println();

		System.out.println( "XCZTY permute" );
		printVarying(
				permute( space, 1, 4 )
				.get( axes_none ).getMetaDatum( NUMBERS_XY ) );
		System.out.println();
		System.out.println();

		System.out.println( "CZTY permute hyperslice" );
		printVarying(
				hyperSlice( permute( space, 1, 4 ), 0, 1 )
				.get( axes_none ).getMetaDatum( NUMBERS_XY ) );
	}

	static void printVarying( final MetaDatum< IntType > datum )
	{
		final int n = datum.numDimensions();
		final int[] dims = new int[ n ];
		Arrays.fill( dims, 2 );
		final RandomAccess< IntType > a = datum.getValues().randomAccess();
		final IntervalIterator i = new IntervalIterator( dims );
		final int[] coords = new int[ i.numDimensions() ];
		while( i.hasNext() )
		{
			i.fwd();
			i.localize( coords );
			a.setPosition( i );
			System.out.println( Util.printCoordinates( coords ) + " -> " + a.get().get() );
		}
	}
}

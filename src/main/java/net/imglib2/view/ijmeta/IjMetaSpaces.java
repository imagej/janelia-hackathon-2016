package net.imglib2.view.ijmeta;

import net.imglib2.RandomAccessible;
import net.imglib2.transform.integer.Mixed;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.view.meta.MetaSpace;
import net.imglib2.view.meta.MetaSpaceContainer;
import net.imglib2.view.meta.MetaSpaceView;
import net.imglib2.view.meta.MetaSpaceViews;
import net.imglib2.view.meta.OrderedAxisSet;

public class IjMetaSpaces
{
	public static interface IjMetaSpace extends MetaSpace< IjMetaSpace, MetaDataSet >
	{}

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

		final OrderedAxisSet att_X = new OrderedAxisSet( true, false, false, false, false );
		final OrderedAxisSet att_Y = new OrderedAxisSet( false, true, false, false, false );
		final OrderedAxisSet att_Z = new OrderedAxisSet( false, false, true, false, false );
		final OrderedAxisSet att_C = new OrderedAxisSet( false, false, false, true, false );
		final OrderedAxisSet att_T = new OrderedAxisSet( false, false, false, false, true );

		final OrderedAxisSet[] atts = new OrderedAxisSet[] { att_X, att_Y, att_Z, att_C, att_T };

		final IjMetaSpaceContainer space = new IjMetaSpaceContainer( 5 );

		for ( final OrderedAxisSet att : atts )
			System.out.println( att + " > " + space.getIfExists( att ) );
		System.out.println();

		space.put( X_CALIB, "2 um", att_X );
		space.put( Y_CALIB, "5 um", att_Y );
		space.put( Z_CALIB, "10 um", att_Z );

		for ( final OrderedAxisSet att : atts )
			System.out.println( att + " > " + space.getIfExists( att ) );
		System.out.println();

		System.out.println( space.getIfExists( att_X ).getAttachedToAxes() );
		System.out.println( space.getIfExists( att_X ).getMetaDatum( X_CALIB ) );
		System.out.println();

		final IjMetaSpace space2 = MetaSpaceViews.permute( space, 0, 2 );

		for ( final OrderedAxisSet att : atts )
			System.out.println( att + " > " + space2.getIfExists( att ) );
		System.out.println();

		System.out.println( space2.getIfExists( att_Z ).getAttachedToAxes() );
		System.out.println( space2.getIfExists( att_Z ).getMetaDatum( X_CALIB ) );
		System.out.println();
	}
}

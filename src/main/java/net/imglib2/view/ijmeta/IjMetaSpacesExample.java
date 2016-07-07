package net.imglib2.view.ijmeta;

import static net.imglib2.view.meta.MetaSpaceViews.hyperSlice;
import static net.imglib2.view.meta.MetaSpaceViews.permute;

import java.util.Arrays;

import net.imglib2.RandomAccess;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.iterator.IntervalIterator;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.util.Util;
import net.imglib2.view.meta.MetaSpaceViews;
import net.imglib2.view.meta.OrderedAxisSet;

public class IjMetaSpacesExample
{
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

package net.imglib2.view;

import java.util.stream.LongStream;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Iterables;

import gnu.trove.list.array.TLongArrayList;
import net.imglib2.FinalInterval;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.integer.LongType;

public class HyperSlicesViewTest {

	final static long[] sizes = new long[]{ 2, 3, 4, 5, 6 };
	
	final static private long[] hyperSlice2Array(
			final RandomAccess< HyperSlice< LongType > > hyperSlicesAccess,
			final int... axes )
	{
		final long[] max = new long[ axes.length ];
		for ( int d = 0; d < axes.length; ++d )
			max[ d ] = sizes[ axes[ d ] ] - 1;
			
		final RandomAccessibleInterval< LongType > hyperSlice =
				Views.interval(
						hyperSlicesAccess.get(),
						new FinalInterval( new long[ axes.length ], max ) );
		
		final TLongArrayList list = new TLongArrayList();
		
		for ( final LongType t : Views.iterable( hyperSlice ) )
			list.add( t.get() );
		
		return list.toArray();
	}
	
	final static private < T > String printHyperSlice(
			final RandomAccess< HyperSlice< T > > hyperSlicesAccess,
			final int... axes )
	{
		final long[] max = new long[ axes.length ];
		for ( int d = 0; d < axes.length; ++d )
			max[ d ] = sizes[ axes[ d ] ] - 1;
			
		final RandomAccessibleInterval< T > hyperSlice =
				Views.interval(
						hyperSlicesAccess.get(),
						new FinalInterval( new long[ axes.length ], max ) );
		
		return Iterables.toString( Views.iterable( hyperSlice ) );
	}
	
	@Test
	public void test()
	{
		final long n = LongStream.of( sizes ).reduce( 1, ( x, y ) -> x * y );
		
		final long[] data = new long[ ( int )n ];
		for ( int d = 0; d < n ; ++d )
			data[ d ] = d;
		
		final RandomAccessibleInterval< LongType > source = ArrayImgs.longs( data, sizes );
		RandomAccessibleInterval< LongType > slice = source;
		while ( slice.numDimensions() > 2 )
			slice = Views.hyperSlice( slice, slice.numDimensions() - 1, 0 );
		
//		System.out.println( Iterables.toString( Views.iterable( slice ) ) );
		
		final HyperSlicesView< LongType > hyperSlices = new HyperSlicesView< LongType >( source, 2, 3 );
		final RandomAccess< HyperSlice< LongType > > hyperSlicesAccess = hyperSlices.randomAccess();
		
		final long[][] expecteds = new long[][]{
			{0, 6, 12, 18, 24, 30, 36, 42, 48, 54, 60, 66, 72, 78, 84, 90, 96, 102, 108, 114},
			{1, 7, 13, 19, 25, 31, 37, 43, 49, 55, 61, 67, 73, 79, 85, 91, 97, 103, 109, 115},
			{2, 8, 14, 20, 26, 32, 38, 44, 50, 56, 62, 68, 74, 80, 86, 92, 98, 104, 110, 116},
			{3, 9, 15, 21, 27, 33, 39, 45, 51, 57, 63, 69, 75, 81, 87, 93, 99, 105, 111, 117}
		};
		
		for ( int d = 0; hyperSlicesAccess.getLongPosition( 0 ) < sizes[ 2 ]; hyperSlicesAccess.fwd( 0 ), ++d )
		{
			Assert.assertArrayEquals( expecteds[ d ], hyperSlice2Array(hyperSlicesAccess, 2, 3 ) );
//			System.out.println( printHyperSlice( hyperSlicesAccess, 2, 3 ) );
		}
	}

}

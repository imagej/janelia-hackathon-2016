/*
 * #%L
 * ImgLib2: a general-purpose, multidimensional image processing library.
 * %%
 * Copyright (C) 2009 - 2015 Tobias Pietzsch, Stephan Preibisch, Barry DeZonia,
 * Stephan Saalfeld, Curtis Rueden, Albert Cardona, Christian Dietz, Jean-Yves
 * Tinevez, Johannes Schindelin, Jonathan Hale, Lee Kamentsky, Larry Lindsey, Mark
 * Hiner, Michael Zinsmaier, Martin Horn, Grant Harris, Aivar Grislis, John
 * Bogovic, Steffen Jaensch, Stefan Helfrich, Jan Funke, Nick Perry, Mark Longair,
 * Melissa Linkert and Dimiter Prodanov.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package net.imglib2.view;

import java.util.Arrays;

import net.imglib2.Interval;
import net.imglib2.Localizable;
import net.imglib2.RandomAccessible;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.Sampler;

/**
 * {@link HyperslicesView} is a {@link RandomAccessible} of all
 * {@link RandomAccessible} hyperslices.
 * 
 * @author Stephan Saalfeld &lt;saalfelds@janelia.hhmi.org&gt;
 */
public class HyperslicesView< T > implements RandomAccessible< T >
{
	final protected RandomAccessibleInterval< T > source;
	final protected int[] axes;
	final protected int[] accessAxes;
	final protected int numDimensions;
	
	public class RandomAccess implements net.imglib2.RandomAccess< T >
	{
		@Override
		public void localize( int[] position )
		{
			
		}

		@Override
		public void localize( long[] position )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getIntPosition( int d )
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getLongPosition( int d )
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void localize( float[] position )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void localize( double[] position )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public float getFloatPosition( int d )
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public double getDoublePosition( int d )
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int numDimensions()
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void fwd( int d )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void bck( int d )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void move( int distance, int d )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void move( long distance, int d )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void move( Localizable localizable )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void move( int[] distance )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void move( long[] distance )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setPosition( Localizable localizable )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setPosition( int[] position )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setPosition( long[] position )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setPosition( int position, int d )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setPosition( long position, int d )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public T get()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Sampler< T > copy()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public net.imglib2.RandomAccess< T > copyRandomAccess()
		{
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public HyperslicesView( final RandomAccessibleInterval< T > source, final int... axes )
	{
		this.source = source;
		this.axes = axes.clone();
		Arrays.sort( this.axes );
		numDimensions = source.numDimensions() - axes.length;
		accessAxes = new int[ numDimensions ];
		for ( int d = 0, da = 0, db = 0; d < source.numDimensions(); ++d )
		{
			if ( this.axes[ da ] == d )
			{
				++da;
				
			}
				
		}
	}

	@Override
	public int numDimensions()
	{
		return numDimensions;
	}

	@Override
	public RandomAccess< T > randomAccess()
	{
		return new RandomAccess();
	}

	@Override
	public RandomAccess< T > randomAccess( Interval interval )
	{
		return randomAccess();
	}
	
	
}

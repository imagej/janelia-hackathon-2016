/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2015 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
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

package net.imagej.domains;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import net.imglib2.EuclideanSpace;
import net.imglib2.RandomAccessible;
import net.imglib2.img.array.ArrayImg;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.basictypeaccess.array.FloatArray;
import net.imglib2.realtransform.AffineTransform;
import net.imglib2.type.numeric.real.FloatType;

import org.junit.Test;
import org.scijava.object.ObjectIndex;

public class TobiasDomainsTest {

	@Test
	public void testDomains() {
		// two 2D images
		// labelings corresponding to each
		// given transformation for each 2D image
		// create 3D image with two time points
		// with 2D images transformed

		int w = 50, h = 40;
		final ArrayImg<FloatType, FloatArray> img1 = ArrayImgs.floats(w, h);
		final ArrayImg<FloatType, FloatArray> img2 = ArrayImgs.floats(w, h);

		// (c -> (x, y))
		TupleSpace xySpace = new TupleSpace("x", "y");
		FnSpace space = new FnSpace(new TupleSpace("t"), xySpace);

		AffineTransform trans = new AffineTransform(2); // identity transform

		Thing rich = new Thing(space);
		rich.set(0, img1, trans);
		rich.set(1, img2, trans);
	}

	public static class Thing {

		public Thing(DiscreteSpace space)
		{
			// TODO Auto-generated constructor stub
		}
		
	}
	
	public static interface Space {}
	public static class TupleSpace implements Space {
		private String[] axes;
		public TupleSpace(String... axes) {
			this.axes = axes;
		}
	}

	public static class FnSpace implements Space{
		private Space domain;
		private Space range;
		public FnSpace(Space domain, Space range) {
			this.domain = domain;
			this.range = range;
		}
	}

}

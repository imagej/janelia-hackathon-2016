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
import net.imglib2.realtransform.AffineTransform;

import org.junit.Test;
import org.scijava.object.ObjectIndex;

public class ChristianDomainsTest {

	@Test
	public void testDomains() {
		Rich rich = read(); // XYZT dataset
		assertEquals(4, rich.numDimensions());
		assertEquals("x", rich.axis(0));
		assertEquals("y", rich.axis(1));
		assertEquals("z", rich.axis(2));
		assertEquals("t", rich.axis(3));

		// crop Z -- fix at Z=5
		Rich cropped = RichViews.hyperslice(rich, "z", 5);
		assertEquals(3, cropped.numDimensions());
		assertEquals("x", cropped.axis(0));
		assertEquals("y", cropped.axis(1));
		assertEquals("t", cropped.axis(2));

		// adjust the XY plane at each time point
		// e.g., if we have some stage coordinates per timepoint?
		Rich registered = register(cropped, "x", "y");
		assertEquals(3, cropped.numDimensions());
		assertEquals("x", cropped.axis(0));
		assertEquals("y", cropped.axis(1));
		assertEquals("t", cropped.axis(2));
	}

	private Rich register(Rich rich, String... axes) {
		transformations = intensityBasedRegistration(rich, axes);
		return RichViews.doTransforms(rich, transformations);
	}

	private RandomAccessible<AffineTransform> intensityBasedRegistration(Rich rich, String... axes) {
		final Set<String> axisSet = axes(rich);
		final Set<String> remaining = subtract(axisSet, axes);


		// TODO Auto-generated method stub
		return null;
	}

	private Set<String> axes(Rich rich) {
		final Set<String> set = new HashSet<>();
		for (int d=0; d<rich.numDimensions(); d++) {
			set.add(rich.axis(d));
		}
		return set;
	}

	private Set<String> subtract(Set<String> axisSet, String[] axes) {
		//FIXME
		return axisSet;
	}

	public static class RichViews implements EuclideanSpace {

		public static Rich hyperslice(Rich rich, String axis, int index) {
			int index = rich.axisIndex(axis);
		}

	}

	public static class Rich implements EuclideanSpace {

		public String axis(int index) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	private Rich read() {
		// TODO
		return null;
	}

	public static class MetadataMap {
		private ObjectIndex<Metadata> index = new ObjectIndex<>(Metadata.class);

		public <M extends Metadata> M get(final Class<M> metaClass) {
			return (M) index.get(metaClass).get(0);
		}
		public void put(final Metadata m) {
			index.add(m);
		}
	}

	public static class Metadata {
		
	}

}

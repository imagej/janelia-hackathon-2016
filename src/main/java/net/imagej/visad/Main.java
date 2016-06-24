
package net.imagej.visad;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imglib2.RealRandomAccessible;
import net.imglib2.img.Img;
import net.imglib2.img.ImgView;
import net.imglib2.interpolation.randomaccess.NearestNeighborInterpolatorFactory;
import net.imglib2.realtransform.InverseRealTransform;
import net.imglib2.realtransform.RealTransformRandomAccessible;
import net.imglib2.realtransform.RealViews;
import net.imglib2.transform.InverseTransform;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.view.IntervalView;
import net.imglib2.view.Views;

import visad.Gridded2DDoubleSet;
import visad.MathType;
import visad.RealTupleType;
import visad.RealType;

public final class Main {

	public static void main(final String... args) throws Exception {
		ImageJ ij = new ImageJ();
		final Dataset data = ij.scifio().datasetIO().open("/Users/curtis/Desktop/mitosis.tif");
		
		final Img<UnsignedByteType> img = (Img) data.getImgPlus().getImg();
		
		System.out.println(img);
		
		final IntervalView<UnsignedByteType> t0 = Views.hyperSlice(img, 4, 0);
		final IntervalView<UnsignedByteType> t0z0 = Views.hyperSlice(t0, 3, 0);
		final IntervalView<UnsignedByteType> t0z0c0 = Views.hyperSlice(t0z0, 2, 0);
		
		NearestNeighborInterpolatorFactory<UnsignedByteType> interpolatorFac = new NearestNeighborInterpolatorFactory<>();
		final RealRandomAccessible<UnsignedByteType> rra = Views.interpolate(t0z0c0, interpolatorFac);

		long w = t0z0c0.dimension(0), h = t0z0c0.dimension(1);

		RealType xType = RealType.getRealType("x");
		RealType yType = RealType.getRealType("y");
		MathType type = new RealTupleType(xType, yType);
		int gw = 4, gh = 3;
		double[][] grid = { // 12 grid points
			{.05*w, .40*w, .60*w, .95*w,    .10*w, .30*w, .70*w, .90*w,   .03*w, .25*w, .65*w, .85*w },
			{.10*h, .20*h, .15*h, .25*h,    .50*h, .45*h, .40*h, .55*h,   .90*h, .85*h, .80*w, .75*h }
		};
		final Gridded2DDoubleSet set = new Gridded2DDoubleSet(type, grid, gw, gh);
		final GriddedSetTransform transform = new GriddedSetTransform(set);

		final RealTransformRandomAccessible<UnsignedByteType, InverseRealTransform> transformed =
			RealViews.transform(rra, transform);

		// reapply same bounds as before (we set up the grid for this to work OK)
		final IntervalView<UnsignedByteType> transformedInterval = Views.interval(transformed, t0z0c0);
		Img imgView = ImgView.wrap(transformedInterval, null);

		System.out.println(imgView);

		ij.ui().show("original", t0z0c0);
		ij.ui().show("transformed", imgView);
		
		
//		ij.getContext().dispose();
	}
}

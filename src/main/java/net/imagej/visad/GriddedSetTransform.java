package net.imagej.visad;

import net.imglib2.RealLocalizable;
import net.imglib2.RealPositionable;
import net.imglib2.realtransform.InvertibleRealTransform;

import visad.GriddedDoubleSet;
import visad.VisADException;

public class GriddedSetTransform implements InvertibleRealTransform {

	private GriddedDoubleSet set;

	@Override
	public int numSourceDimensions() {
		return set.getManifoldDimension();
	}

	@Override
	public int numTargetDimensions() {
		return set.getDimension();
	}

	@Override
	public void apply(double[] source, double[] target) {
		try {
			unwrap(set.gridToDouble(wrap(source)), target);
		}
		catch (VisADException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}

	@Override
	public void apply(float[] source, float[] target) {
		try {
			unwrap(set.gridToValue(wrap(source)), target);
		}
		catch (VisADException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}

	@Override
	public void apply(RealLocalizable source, RealPositionable target) {
		// TODO Auto-generated method stub
	}

	@Override
	public void applyInverse(double[] source, double[] target) {
		try {
			unwrap(set.doubleToGrid(wrap(source)), target);
		}
		catch (VisADException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}

	@Override
	public void applyInverse(float[] source, float[] target) {
		try {
			unwrap(set.valueToGrid(wrap(source)), target);
		}
		catch (VisADException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}

	@Override
	public void applyInverse(RealPositionable source, RealLocalizable target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InvertibleRealTransform inverse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvertibleRealTransform copy() {
		// TODO Auto-generated method stub
		return null;
	}

	// -- Helper methods --

	private void unwrap(float[][] source, float[] target) {
		for (int d=0; d<source.length; d++) {
			target[d] = source[d][0];
		}
	}

	private float[][] wrap(float[] source) {
		final float[][] visad = new float[source.length][1];
		for (int d=0; d<source.length; d++) {
			visad[d][0] = source[d];
		}
		return visad;
	}

	private void unwrap(double[][] source, double[] target) {
		for (int d=0; d<source.length; d++) {
			target[d] = source[d][0];
		}
	}

	private double[][] wrap(double[] source) {
		final double[][] visad = new double[source.length][1];
		for (int d=0; d<source.length; d++) {
			visad[d][0] = source[d];
		}
		return visad;
	}

}

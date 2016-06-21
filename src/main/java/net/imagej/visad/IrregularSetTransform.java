package net.imagej.visad;

import net.imglib2.RealLocalizable;
import net.imglib2.RealPositionable;
import net.imglib2.realtransform.InvertibleRealTransform;

import visad.IrregularSet;
import visad.VisADException;

public class IrregularSetTransform implements InvertibleRealTransform {

	private IrregularSet set;

	@Override
	public int numSourceDimensions() {
		return 1;
	}

	@Override
	public int numTargetDimensions() {
		return set.getDimension();
	}

	@Override
	public void apply(double[] source, double[] target) {
		try {
			unwrap_f2_d1(set.indexToValue(wrap_d1_i1(source)), target);
		}
		catch (VisADException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}

	@Override
	public void apply(float[] source, float[] target) {
		try {
			unwrap_f2_f1(set.indexToValue(wrap_f1_i1(source)), target);
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
			unwrap_i1_d1(set.valueToIndex(wrap_d1_f2(source)), target);
		}
		catch (VisADException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}

	@Override
	public void applyInverse(float[] source, float[] target) {
		try {
			unwrap_i1_f1(set.valueToIndex(wrap_f1_f2(source)), target);
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

	private int[] wrap_f1_i1(float[] source) {
		final int[] visad = new int[source.length];
		for (int d=0; d<source.length; d++) {
			visad[d] = clip(round(source[d])); // nearest neighbor!
		}
		return visad;
	}

	private int[] wrap_d1_i1(double[] source) {
		final int[] visad = new int[source.length];
		for (int d=0; d<source.length; d++) {
			visad[d] = clip(round(source[d])); // nearest neighbor!
		}
		return visad;
	}

	private float[][] wrap_f1_f2(float[] source) {
		float[][] visad = new float[source.length][1];
		for (int d=0; d<source.length; d++) {
			visad[d][0] = source[d];
		}
		return visad;
	}

	private float[][] wrap_d1_f2(double[] source) {
		float[][] visad = new float[source.length][1];
		for (int d=0; d<source.length; d++) {
			visad[d][0] = (float) source[d];
		}
		return visad;
	}

	private void unwrap_f2_f1(float[][] source, float[] target) {
		for (int d=0; d<source.length; d++) {
			target[d] = source[d][0];
		}
	}

	private void unwrap_f2_d1(float[][] source, double[] target) {
		for (int d=0; d<source.length; d++) {
			target[d] = source[d][0];
		}
	}

	private void unwrap_i1_f1(int[] source, float[] target) {
		for (int d=0; d<source.length; d++) {
			target[d] = source[d];
		}
	}

	private void unwrap_i1_d1(int[] source, double[] target) {
		for (int d=0; d<source.length; d++) {
			target[d] = source[d];
		}
	}

	private int clip(long round) {
		return (round > Integer.MAX_VALUE) ? Integer.MAX_VALUE : (int) round;
	}

	private long round(double d) {
		return Math.round(d);
	}

}

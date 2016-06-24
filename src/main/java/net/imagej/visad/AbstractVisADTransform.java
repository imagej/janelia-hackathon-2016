
package net.imagej.visad;

import net.imglib2.RealLocalizable;
import net.imglib2.RealPositionable;
import net.imglib2.realtransform.InvertibleRealTransform;

import visad.VisADException;

public abstract class AbstractVisADTransform implements
	InvertibleRealTransform
{

	public AbstractVisADTransform() {
		super();
	}

	// -- RealTransform methods --

	@Override
	public void apply(final RealLocalizable source,
		final RealPositionable target)
	{
		final double[] sourcePos = new double[source.numDimensions()];
		final double[] targetPos = new double[target.numDimensions()];
		source.localize(sourcePos);
		apply(sourcePos, targetPos);
		target.setPosition(targetPos);
	}

	// -- InvertibleRealTransform methods --

	@Override
	public void applyInverse(final RealPositionable source,
		final RealLocalizable target)
	{
		final double[] sourcePos = new double[source.numDimensions()];
		final double[] targetPos = new double[target.numDimensions()];
		target.localize(targetPos);
		applyInverse(sourcePos, targetPos);
		source.setPosition(sourcePos);
	}

	// -- Internal methods --

	protected float[][] wrap_f1_f2(final float[] f1) {
		final float[][] f2 = new float[f1.length][1];
		for (int d = 0; d < f1.length; d++) {
			f2[d][0] = f1[d];
		}
		return f2;
	}

	protected float[][] wrap_d1_f2(final double[] d1) {
		final float[][] visad = new float[d1.length][1];
		for (int d = 0; d < d1.length; d++) {
			visad[d][0] = (float) d1[d];
		}
		return visad;
	}

	protected double[][] wrap_d1_d2(final double[] d1) {
		final double[][] visad = new double[d1.length][1];
		for (int d = 0; d < d1.length; d++) {
			visad[d][0] = d1[d];
		}
		return visad;
	}

	protected int[] wrap_f1_i1(final float[] f1) {
		final int[] visad = new int[f1.length];
		for (int d = 0; d < f1.length; d++) {
			visad[d] = clip(round(f1[d])); // nearest neighbor!
		}
		return visad;
	}

	protected int[] wrap_d1_i1(final double[] d1) {
		final int[] visad = new int[d1.length];
		for (int d = 0; d < d1.length; d++) {
			visad[d] = clip(round(d1[d])); // nearest neighbor!
		}
		return visad;
	}

	protected void unwrap_f2_f1(final float[][] f2, final float[] f1) {
		for (int d = 0; d < f2.length; d++) {
			f1[d] = f2[d][0];
		}
	}

	protected void unwrap_f2_d1(final float[][] f2, final double[] d1) {
		for (int d = 0; d < f2.length; d++) {
			d1[d] = f2[d][0];
		}
	}

	protected void unwrap_d2_d1(final double[][] d2, final double[] d1) {
		for (int d = 0; d < d2.length; d++) {
			d1[d] = d2[d][0];
		}
	}

	protected void unwrap_i1_f1(final int[] i1, final float[] f1) {
		for (int d = 0; d < i1.length; d++) {
			f1[d] = i1[d];
		}
	}

	protected void unwrap_i1_d1(final int[] i1, final double[] d1) {
		for (int d = 0; d < i1.length; d++) {
			d1[d] = i1[d];
		}
	}

	protected void fail(final VisADException exc) {
		throw new RuntimeException(exc);
	}

	// -- Helper methods --

	private int clip(final long round) {
		return (round > Integer.MAX_VALUE) ? Integer.MAX_VALUE : (int) round;
	}

	private long round(final double d) {
		return Math.round(d);
	}

}


package net.imagej.visad;

import net.imglib2.RealLocalizable;
import net.imglib2.RealPositionable;
import net.imglib2.realtransform.InvertibleRealTransform;

import visad.IrregularSet;
import visad.VisADException;

/**
 * An ImgLib2 {@link InvertibleRealTransform} backed by an {@link IrregularSet}.
 * <p>
 * The source space is the set's discrete 1D indices. The target space is the
 * N-dimensional value coordinates specified by the set.
 * </p>
 *
 * @author Curtis Rueden
 */
public class IrregularSetTransform implements InvertibleRealTransform {

	private final IrregularSet set;

	public IrregularSetTransform(final IrregularSet set) {
		this.set = set;
	}

	@Override
	public int numSourceDimensions() {
		return 1;
	}

	@Override
	public int numTargetDimensions() {
		return set.getDimension();
	}

	@Override
	public void apply(final double[] source, final double[] target) {
		try {
			unwrap_f2_d1(set.indexToValue(wrap_d1_i1(source)), target);
		}
		catch (final VisADException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}

	@Override
	public void apply(final float[] source, final float[] target) {
		try {
			unwrap_f2_f1(set.indexToValue(wrap_f1_i1(source)), target);
		}
		catch (final VisADException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}

	@Override
	public void apply(final RealLocalizable source,
		final RealPositionable target)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void applyInverse(final double[] source, final double[] target) {
		try {
			unwrap_i1_d1(set.valueToIndex(wrap_d1_f2(source)), target);
		}
		catch (final VisADException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}

	@Override
	public void applyInverse(final float[] source, final float[] target) {
		try {
			unwrap_i1_f1(set.valueToIndex(wrap_f1_f2(source)), target);
		}
		catch (final VisADException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}

	@Override
	public void applyInverse(final RealPositionable source,
		final RealLocalizable target)
	{
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

	private int[] wrap_f1_i1(final float[] source) {
		final int[] visad = new int[source.length];
		for (int d = 0; d < source.length; d++) {
			visad[d] = clip(round(source[d])); // nearest neighbor!
		}
		return visad;
	}

	private int[] wrap_d1_i1(final double[] source) {
		final int[] visad = new int[source.length];
		for (int d = 0; d < source.length; d++) {
			visad[d] = clip(round(source[d])); // nearest neighbor!
		}
		return visad;
	}

	private float[][] wrap_f1_f2(final float[] source) {
		final float[][] visad = new float[source.length][1];
		for (int d = 0; d < source.length; d++) {
			visad[d][0] = source[d];
		}
		return visad;
	}

	private float[][] wrap_d1_f2(final double[] source) {
		final float[][] visad = new float[source.length][1];
		for (int d = 0; d < source.length; d++) {
			visad[d][0] = (float) source[d];
		}
		return visad;
	}

	private void unwrap_f2_f1(final float[][] source, final float[] target) {
		for (int d = 0; d < source.length; d++) {
			target[d] = source[d][0];
		}
	}

	private void unwrap_f2_d1(final float[][] source, final double[] target) {
		for (int d = 0; d < source.length; d++) {
			target[d] = source[d][0];
		}
	}

	private void unwrap_i1_f1(final int[] source, final float[] target) {
		for (int d = 0; d < source.length; d++) {
			target[d] = source[d];
		}
	}

	private void unwrap_i1_d1(final int[] source, final double[] target) {
		for (int d = 0; d < source.length; d++) {
			target[d] = source[d];
		}
	}

	private int clip(final long round) {
		return (round > Integer.MAX_VALUE) ? Integer.MAX_VALUE : (int) round;
	}

	private long round(final double d) {
		return Math.round(d);
	}

}

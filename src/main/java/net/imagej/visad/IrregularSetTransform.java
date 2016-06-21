
package net.imagej.visad;

import net.imglib2.realtransform.InverseRealTransform;
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
public class IrregularSetTransform extends AbstractVisADTransform {

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
			fail(exc);
		}
	}

	@Override
	public void apply(final float[] source, final float[] target) {
		try {
			unwrap_f2_f1(set.indexToValue(wrap_f1_i1(source)), target);
		}
		catch (final VisADException exc) {
			fail(exc);
		}
	}

	@Override
	public void applyInverse(final double[] source, final double[] target) {
		try {
			unwrap_i1_d1(set.valueToIndex(wrap_d1_f2(target)), source);
		}
		catch (final VisADException exc) {
			fail(exc);
		}
	}

	@Override
	public void applyInverse(final float[] source, final float[] target) {
		try {
			unwrap_i1_f1(set.valueToIndex(wrap_f1_f2(target)), source);
		}
		catch (final VisADException exc) {
			fail(exc);
		}
	}

	@Override
	public InvertibleRealTransform inverse() {
		return new InverseRealTransform(this);
	}

	@Override
	public IrregularSetTransform copy() {
		return new IrregularSetTransform(set);
	}

}

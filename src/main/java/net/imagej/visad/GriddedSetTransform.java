
package net.imagej.visad;

import net.imglib2.realtransform.InverseRealTransform;
import net.imglib2.realtransform.InvertibleRealTransform;

import visad.GriddedDoubleSet;
import visad.VisADException;

/**
 * An ImgLib2 {@link InvertibleRealTransform} backed by a
 * {@link GriddedDoubleSet}.
 * <p>
 * The source space set's continuous M-dimensional grid. The target space is the
 * N-dimensional value coordinates given by the set.
 * </p>
 *
 * @author Curtis Rueden
 */
public class GriddedSetTransform extends AbstractVisADTransform {

	private final GriddedDoubleSet set;

	public GriddedSetTransform(final GriddedDoubleSet set) {
		this.set = set;
	}

	@Override
	public int numSourceDimensions() {
		return set.getManifoldDimension();
	}

	@Override
	public int numTargetDimensions() {
		return set.getDimension();
	}

	@Override
	public void apply(final double[] source, final double[] target) {
		try {
			unwrap_d2_d1(set.gridToDouble(wrap_d1_d2(source)), target);
		}
		catch (final VisADException exc) {
			fail(exc);
		}
	}

	@Override
	public void apply(final float[] source, final float[] target) {
		try {
			unwrap_f2_f1(set.gridToValue(wrap_f1_f2(source)), target);
		}
		catch (final VisADException exc) {
			fail(exc);
		}
	}

	@Override
	public void applyInverse(final double[] source, final double[] target) {
		try {
			unwrap_d2_d1(set.doubleToGrid(wrap_d1_d2(target)), source);
		}
		catch (final VisADException exc) {
			fail(exc);
		}
	}

	@Override
	public void applyInverse(final float[] source, final float[] target) {
		try {
			unwrap_f2_f1(set.valueToGrid(wrap_f1_f2(target)), source);
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
	public GriddedSetTransform copy() {
		return new GriddedSetTransform(set);
	}

}

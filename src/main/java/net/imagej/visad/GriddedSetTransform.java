
package net.imagej.visad;

import net.imglib2.RealLocalizable;
import net.imglib2.RealPositionable;
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
public class GriddedSetTransform implements InvertibleRealTransform {

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
			unwrap(set.gridToDouble(wrap(source)), target);
		}
		catch (final VisADException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}

	@Override
	public void apply(final float[] source, final float[] target) {
		try {
			unwrap(set.gridToValue(wrap(source)), target);
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
			unwrap(set.doubleToGrid(wrap(source)), target);
		}
		catch (final VisADException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}

	@Override
	public void applyInverse(final float[] source, final float[] target) {
		try {
			unwrap(set.valueToGrid(wrap(source)), target);
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

	private void unwrap(final float[][] source, final float[] target) {
		for (int d = 0; d < source.length; d++) {
			target[d] = source[d][0];
		}
	}

	private float[][] wrap(final float[] source) {
		final float[][] visad = new float[source.length][1];
		for (int d = 0; d < source.length; d++) {
			visad[d][0] = source[d];
		}
		return visad;
	}

	private void unwrap(final double[][] source, final double[] target) {
		for (int d = 0; d < source.length; d++) {
			target[d] = source[d][0];
		}
	}

	private double[][] wrap(final double[] source) {
		final double[][] visad = new double[source.length][1];
		for (int d = 0; d < source.length; d++) {
			visad[d][0] = source[d];
		}
		return visad;
	}

}

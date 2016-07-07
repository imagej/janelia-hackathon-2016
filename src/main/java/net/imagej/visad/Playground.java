package net.imagej.visad;



import java.awt.BorderLayout;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import visad.DataReferenceImpl;
import visad.Display;
import visad.Field;
import visad.FieldImpl;
import visad.FlatField;
import visad.FunctionType;
import visad.Integer2DSet;
import visad.RealTupleType;
import visad.RealType;
import visad.ScalarMap;
import visad.VisADException;
import visad.java2d.DisplayImplJ2D;
import visad.util.SelectRangeWidget;

public class Playground {

	public static void main(final String... args) throws VisADException,
		RemoteException
	{
		final RealType xType = RealType.getRealType("x");
		final RealType yType = RealType.getRealType("y");
		final RealType tType = RealType.getRealType("time");
		final RealType vType = RealType.getRealType("value");
		final RealTupleType xyType = new RealTupleType(xType, yType); // (x, y)
		final RealTupleType tvType = new RealTupleType(tType, vType); // (t, v)
		// ((x, y) -> (t, v))
		final FunctionType fType = new FunctionType(xyType, tvType);

		final int xLen = 10, yLen = 12;
		final Integer2DSet domainSet = new Integer2DSet(xyType, xLen, yLen);

		// (c -> ((x, y) -> (t, v)))
		// domainset for f = Integer1DSet or Linear1DSet or Gridded1DSet or Irregular1DSet
		// Integer1DSet = 1D Interval describing C
		// Gridded1DSet 
		// Irregular1DSet = cSet = {67.6, 23.5, 98.4, ...}; // NO ORDER
		// Irregular2DSet // delaunay trangulation is computed on the fly to assist with linear interpolation
		final FieldImpl f = new FieldImpl(paramFunctionType);
		for (int c=0; c<numChan; c++) {
			final FlatField xy2tv = new FlatField(fType, domainSet);
			f.setSample(c, xy2tv);
		}

		final double[][] samples = new double[2][xLen * yLen];
		for (int y = 0; y < yLen; y++) {
			for (int x = 0; x < xLen; x++) {
				final int index = xLen * y + x;
				// time shear: simulate a non-linear but ever-increasing time value
				final double t = index + Math.random();
				// "value" is just random [0, 1]
				final double v = xLen*yLen*Math.random();
				samples[0][index] = t;
				samples[1][index] = v;
			}
		}
		xy2tv.setSamples(samples);

		System.out.println("Created field: " + xy2tv.getType());

		final Field y2x2tv = xy2tv.domainFactor(yType);

		System.out.println("Domain factored field: " + y2x2tv.getType());
		System.out.println("Domain multiplied field: " + y2x2tv.domainMultiply().getType());
//		System.out.println("Domain multiplied field: " + xy2tv.domainMultiply().getType());

		final ScalarMap xMap = new ScalarMap(xType, Display.XAxis);
		final ScalarMap yMap = new ScalarMap(yType, Display.YAxis);
		final ScalarMap tMap = new ScalarMap(tType, Display.Red);
		final ScalarMap vMap = new ScalarMap(vType, Display.Green);

		final DisplayImplJ2D d = new DisplayImplJ2D("display");
//		final DisplayImplJ3D d = new DisplayImplJ3D("display");
		d.addMap(xMap);
		d.addMap(yMap);
		d.addMap(tMap);
//		d.addMap(vMap);
		final DataReferenceImpl ref = new DataReferenceImpl("ref");
		ref.setData(xy2tv);
		d.addReference(ref);

		final JFrame f = new JFrame("VisAD");
		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(d.getComponent(), BorderLayout.CENTER);
//		f.getContentPane().add(new SelectRangeWidget(tMap), BorderLayout.SOUTH);
		f.setBounds(100, 100, 500, 500);
		f.setVisible(true);
	}

}

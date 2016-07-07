
package net.imagej.spaces;

public final class Main {

	public static void main(final String... args) {
		RAI<FloatType> data = open("my/data");

		xAxis = new Axis("x", "um", true);
		yAxis = new Axis("y", "um", true);
		tAxis = new Axis("time", "sec", false);
//		cAxis = new Axis("channel", null, false);

		Axis[] axes = {xAxis, yAxis, tAxis};
		Space space = new Space(axes); // numDims inferred as 3

		

		/*
		RAI<RAI<FloatType>> xycData = new HyperslicesView(data, 2);
		RAI<RAI<RAI<FloatType>>> xyData = new HyperslicesView(xycData, 2);
		
		RAI<RAI<RRAI<FloatType>>> xyInterpolated = 

		DiscreteSpaceNode timeNode = new DiscreteSpaceNode(space, null, null);
		timeNode.setData(timeSliceData);
		*/
	}
	
	public class SpaceNode {
		private Space space;
		private RealTransform transform;
		private SpaceNode parent;
	}

	public class DiscreteNode extends SpaceNode implements
		RandomAccessible<SpaceNode>
	{
	}

	public class ContinuousNode extends SpaceNode implements
		RealRandomAccessible<SpaceNode>
	{
	}
}

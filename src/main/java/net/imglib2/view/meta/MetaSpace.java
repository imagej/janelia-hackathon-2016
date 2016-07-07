package net.imglib2.view.meta;

import net.imglib2.EuclideanSpace;
import net.imglib2.transform.integer.Mixed;

/**
 * Represents the axis combinations of a <em>n</em>-dimensional space. A value
 * of type {@code T} can be attached to each of axis combination.
 * <p>
 * The value at position {@code p} can be retrieved using
 * {@link #getIfExists(OrderedAxisSet)}, which returns {@code null} if there is
 * no value stored at {@code p}, or {@link #get(OrderedAxisSet)}, which creates
 * a new {@code T} if there is no value stored at {@code p} (yet).
 * <p>
 * There are two implementations: {@link MetaSpaceContainer} and
 * {@link MetaSpaceView}. {@link MetaSpaceContainer} just has a map with
 * attached values. {@link MetaSpaceView} is a view of another {@link MetaSpace}
 * transformed by a {@link Mixed} transform.
 *
 * @param <S>
 *            recursive type of the {@link MetaSpace}. This exists to be able to
 *            get rid of generic parameters in derived concrete metaspaces (with
 *            specific {@code T}s).
 * @param <T>
 *            type of values stored in the {@link MetaSpace}.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public interface MetaSpace< S extends MetaSpace< S, T >, T > extends EuclideanSpace
{
	/**
	 * Factory for values stored in a {@link MetaSpace}. Can create "final"
	 * values, as well as views of values transformed by {@link Mixed}
	 * transforms.
	 *
	 * @param <T>
	 *            type of values stored in the {@link MetaSpace}.
	 */
	public static interface ElementFactory< T >
	{
		public T create( final OrderedAxisSet position );

		public T createView( T source, final Mixed transformToSource );
	}

	/**
	 * Creates a view of a {@link MetaSpace} transformed by a {@link Mixed}
	 * transform. This should be implemented simply to construct
	 * {@link MetaSpaceView#MetaSpaceView(MetaSpace, Mixed)} of the appropriate
	 * derived type {@code S}.
	 *
	 * @param <S>
	 *            recursive type of the {@link MetaSpace}.
	 */
	public static interface ViewFactory< S >
	{
		public S createView( S source, final Mixed transformToSource );
	}

	/**
	 * Returns {@code T} at the specified position if it exists, or {@code null}
	 * if it doesn't.
	 */
	public T getIfExists( final OrderedAxisSet position );

	/**
	 * Creates a {@code T} if none exists at the specified position.
	 */
	public T get( final OrderedAxisSet position );

	// create thread-safe independent access
//	public S access();

	// for creating Ts and views of Ts
	public ElementFactory< T > elementFactory();

	// for creating views of MetaSpaces
	public ViewFactory< S > viewFactory();
}

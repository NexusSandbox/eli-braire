package eli.braire.math.space;

import java.text.MessageFormat;
import java.util.Arrays;

import eli.veritas.Verifier;
import eli.veritas.exception.AssertionException;

/**
 * An object corresponding to the abstract mathematical notion of a Point. Points are entities that define a 0-dimensional location within a Euclidean
 * space. This also supports typical and relevant Point transformations and operations.
 *
 * @author The Architect
 */
public class Point extends Vector
{
    private Point(final int dimensions)
    {
        super(dimensions);
    }

    private Point(final double ... coordinates)
    {
        super(coordinates);
    }

    /**
     * @param coordinates A non-empty <code>double</code> array.
     * @return a new non-empty {@link Point} containing the specified elements.
     * @throws AssertionException If any of the following are true:
     *             <ul>
     *             <li>coordinates is null OR empty</li>
     *             </ul>
     */
    public static Point create(final double ... coordinates)
    {
        Verifier.Collections.assertNotEmpty("Point coordinates must not be null or empty.", coordinates);

        return new Point(coordinates);
    }

    /**
     * @return a new non-empty {@link Point} with identical element values.
     */
    @Override
    public Point clone()
    {
        return new Point(elements);
    }

    /**
     * @param dimension A dimension index. The index must be between 1 (inclusive) and the total number of dimensions (inclusive).
     * @return the <code>double</code> value corresponding to the {@link Point} element at the specified position.
     * @throws AssertionException If any of the following are true:
     *             <ul>
     *             <li>dimension &lt;= 0 OR dimension &gt; {@link #RANK}</li>
     *             </ul>
     */
    @Override
    public double d(final int dimension)
    {
        Verifier.Ranges.assertInsideRange_Inclusive("Point coordinate index must be within the defined range.", dimension, 1, RANK);

        return elements[dimension];
    }

    @Override
    public Point dotProduct(final Matrix matrix)
    {
        Verifier.assertNotNull("Cannot perform operations with a null matrix.", matrix);
        Verifier.Equality.assertEqual("Left-Point dimensions must match the Right-Matrix rows.", matrix.ROWS, RANK);

        final Point newPoint = new Point(matrix.COLUMNS);
        for (int j = 0; j < matrix.COLUMNS; j++)
        {
            for (int i = 0; i < RANK; i++)
            {
                newPoint.elements[j] += elements[i] * matrix.getElement(i, j);
            }
        }

        return newPoint;
    }

    public Point add(final Point b)
    {
        Verifier.assertNotNull("Origin Point must be provided.", b);
        Verifier.Equality.assertEqual("Both origin and terminator Points must have the same dimensionality.", b.RANK, RANK);

        final double[] newElements = elements.clone();
        for (int i = 0; i < RANK; i++)
        {
            newElements[i] += b.elements[i];
        }

        return Point.create(newElements);
    }

    public Point subtract(final Point b)
    {
        Verifier.assertNotNull("Origin Point must be provided.", b);
        Verifier.Equality.assertEqual("Both origin and terminator Points must have the same dimensionality.", b.RANK, RANK);

        final double[] newElements = elements.clone();
        for (int i = 0; i < RANK; i++)
        {
            newElements[i] -= b.elements[i];
        }

        return Point.create(newElements);
    }

    @Override
    public String toString()
    {
        return MessageFormat.format("({0})", Arrays.toString(elements));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + RANK;
        result = prime * result + Arrays.hashCode(elements);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Point))
        {
            return false;
        }
        final Point other = (Point) obj;
        if (RANK != other.RANK)
        {
            return false;
        }
        if (!Arrays.equals(elements, other.elements))
        {
            return false;
        }
        return true;
    }
}

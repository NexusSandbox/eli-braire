//package eli.braire.math.space;
//
//import java.text.MessageFormat;
//import java.util.Arrays;
//
//import eli.veritas.Verifier;
//import eli.veritas.exception.AssertionException;
//
///**
// * An object corresponding to the abstract mathematical notion of a Vector. Vectors are entities within a Euclidean space consisting of a magnitude
// * and direction. This also supports typical and relevant Vector transformations and operations.
// *
// * @author The Architect
// */
//public class Vector extends AbstractLinearConstruct
//{
//    protected final double[] elements;
//
//    protected Vector(final int rank)
//    {
//        super(rank);
//        elements = new double[rank];
//    }
//
//    protected Vector(final double ... elements)
//    {
//        super(elements.length);
//        this.elements = elements.clone();
//    }
//
//    protected Vector(final Point terminator)
//    {
//        super(terminator.RANK);
//        elements = new double[RANK];
//        for (int i = 0; i < RANK; i++)
//        {
//            elements[i] = terminator.d(i + 1);
//        }
//    }
//
//    /**
//     * @param elements A non-empty <code>double</code> array.
//     * @return a new non-empty {@link Vector} containing the specified elements.
//     * @throws AssertionException If any of the following are true:
//     *             <ul>
//     *             <li>Elements is null</li>
//     *             <li>Elements has less than 1 dimension</li>
//     *             </ul>
//     */
//    public static Vector create(final double ... elements)
//    {
//        Verifier.Collections.assertNotEmpty("Vector elements must not be null or empty.", elements);
//        Verifier.Inequality.assertGreaterThan("A Vector must have at least 1 element.", elements.length, 0);
//
//        return new Vector(elements);
//    }
//
//    /**
//     * @param terminator A terminating {@link Point} for the vector. (Non-null)
//     * @return a new non-empty {@link Vector} containing the specified elements.
//     * @throws AssertionException If any of the following are true:
//     *             <ul>
//     *             <li>Terminator is null</li>
//     *             </ul>
//     */
//    public static Vector create(final Point terminator)
//    {
//        Verifier.assertNotNull("Terminating Point must be provided.", terminator);
//
//        return new Vector(terminator);
//    }
//
//    /**
//     * @return a new non-empty {@link Vector} with identical element values.
//     */
//    @Override
//    public Vector clone()
//    {
//        return new Vector(elements);
//    }
//
//    public Point toPoint()
//    {
//        return Point.create(elements);
//    }
//
//    /**
//     * @param dimension A dimension index. The index must be between 1 (inclusive) and the total number of dimensions (inclusive).
//     * @return the <code>double</code> value corresponding to the {@link Vector} element at the specified position.
//     * @throws AssertionException If any of the following are true:
//     *             <ul>
//     *             <li>dimension &lt;= 0 OR dimension &gt; {@link #RANK}</li>
//     *             </ul>
//     */
//    public double d(final int dimension)
//    {
//        Verifier.Ranges.assertInsideRange_Inclusive("Vector element index must be within the defined range.", dimension, 1, RANK);
//
//        return elements[dimension - 1];
//    }
//
//    /**
//     * @param dimensions
//     * @return
//     * @throws AssertionException If any of the following are true for any of the provided dimensions:
//     *             <ul>
//     *             <li>dimension &lt;= 0 OR dimension &gt; {@link #RANK}</li>
//     *             </ul>
//     */
//    public Vector subVector(final int ... dimensions)
//    {
//        Verifier.Collections.assertNotEmpty("", dimensions);
//        Verifier.Inequality.assertLessThanOrEqual("", dimensions.length, RANK);
//
//        final Vector newVector = new Vector(dimensions.length);
//        for (int i = 0; i < dimensions.length; i++)
//        {
//            Verifier.Ranges.assertInsideRange_Inclusive("", dimensions[i], 1, RANK);
//            newVector.elements[i] = elements[dimensions[i] - 1];
//        }
//
//        return newVector;
//    }
//
//    /**
//     * @return
//     */
//    public Vector negate()
//    {
//        final Vector newVector = new Vector(RANK);
//        for (int i = 0; i < RANK; i++)
//        {
//            newVector.elements[i] = -elements[i];
//        }
//
//        return newVector;
//    }
//
//    /**
//     * @param vector
//     * @return
//     * @throws AssertionException
//     */
//    public Vector add(final Vector vector)
//    {
//        Verifier.Equality.assertEqual("Both Vectors must have the same number of dimensions.", vector.RANK, RANK);
//
//        final Vector newVector = new Vector(RANK);
//        for (int i = 0; i < RANK; i++)
//        {
//            newVector.elements[i] = elements[i] + vector.elements[i];
//        }
//
//        return newVector;
//    }
//
//    /**
//     * @param vector
//     * @return
//     * @throws AssertionException
//     */
//    public Vector subtract(final Vector vector)
//    {
//        Verifier.Equality.assertEqual("Both Vectors must have the same number of dimensions.", vector.RANK, RANK);
//
//        final Vector newVector = new Vector(RANK);
//        for (int i = 0; i < RANK; i++)
//        {
//            newVector.elements[i] = elements[i] - vector.elements[i];
//        }
//
//        return newVector;
//    }
//
//    /**
//     * @param scalar
//     * @return
//     */
//    public Vector multiply(final double scalar)
//    {
//        final Vector newVector = new Vector(RANK);
//        for (int i = 0; i < RANK; i++)
//        {
//            newVector.elements[i] = elements[i] * scalar;
//        }
//
//        return newVector;
//    }
//
//    /**
//     * @param scalar
//     * @return
//     * @throws AssertionException
//     */
//    public Vector divide(final double scalar)
//    {
//        Verifier.Equality.assertNotEqual("Cannot divide Vector elements by zero scalar.", scalar, 0, 0.0000001D);
//
//        final Vector newVector = new Vector(RANK);
//        for (int i = 0; i < RANK; i++)
//        {
//            newVector.elements[i] = elements[i] / scalar;
//        }
//
//        return newVector;
//    }
//
//    /**
//     * @param vector
//     * @return
//     * @throws AssertionException
//     */
//    public double dotProduct(final Vector vector)
//    {
//        Verifier.Equality.assertEqual("Left-Vector dimensions must match the Right-Vector dimensions.", vector.RANK, RANK);
//
//        double scalar = 0D;
//        for (int i = 0; i < RANK; i++)
//        {
//            scalar += elements[i] * vector.elements[i];
//        }
//
//        return scalar;
//    }
//
//    /**
//     * @param matrix
//     * @return
//     * @throws AssertionException
//     */
//    public Vector dotProduct(final Matrix matrix)
//    {
//        Verifier.Equality.assertNotEqual("Cannot perform operations with a null Matrix.", matrix, null);
//        Verifier.Equality.assertEqual("Left-Vector dimensions must match Right-Matrix rows.", matrix.ROWS, RANK);
//
//        final Vector newVector = new Vector(RANK);
//        for (int j = 0; j < matrix.COLUMNS; j++)
//        {
//            for (int i = 0; i < RANK; i++)
//            {
//                newVector.elements[i] += elements[i] * matrix.getElement(i, j);
//            }
//        }
//
//        return newVector;
//    }
//
//    /**
//     * @return
//     * @throws AssertionException
//     */
//    public Vector crossProduct()
//    {
//        Verifier.Equality.assertEqual("The cross-product of 1 Vector is only defined for 2 dimensions.", RANK, 2);
//
//        final Vector newVector = new Vector(2);
//        newVector.elements[0] = elements[1];
//        newVector.elements[1] = -elements[0];
//
//        return newVector;
//    }
//
//    /**
//     * @param vector
//     * @return
//     * @throws AssertionException
//     */
//    public Vector crossProduct(final Vector vector)
//    {
//        Verifier.Equality.assertEqual("The cross-product of 2 Vectors is only defined for 3 dimensions.", RANK, 3);
//        Verifier.Equality.assertEqual("The cross-product is only defined for Vectors of the same number of dimensions.", vector.RANK, RANK);
//
//        final Vector newVector = new Vector(RANK);
//        newVector.elements[0] = elements[1] * vector.elements[2] - elements[2] * vector.elements[1];
//        newVector.elements[1] = elements[2] * vector.elements[0] - elements[0] * vector.elements[2];
//        newVector.elements[2] = elements[0] * vector.elements[1] - elements[1] * vector.elements[0];
//
//        return newVector;
//    }
//
//    /**
//     * @param vectors
//     * @return
//     * @throws AssertionException
//     */
//    public Vector crossProduct(final Vector ... vectors)
//    {
//        Verifier.Inequality.assertGreaterThan("", RANK, 3);
//        Verifier.Equality.assertEqual("", vectors.length, RANK - 2);
//        for (final Vector vector : vectors)
//        {
//            Verifier.Equality.assertEqual("", vector.RANK, RANK);
//        }
//
//        final Vector newVector = new Vector(RANK);
//        final double[] elements = newVector.elements;
//        // TODO Generate algorithm to calculate generalized crossProduct
//
//        return newVector;
//    }
//
//    @Override
//    public String toString()
//    {
//        return MessageFormat.format("<{0}>", Arrays.toString(elements));
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public int hashCode()
//    {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + RANK;
//        result = prime * result + Arrays.hashCode(elements);
//        return result;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public boolean equals(final Object obj)
//    {
//        if (this == obj)
//        {
//            return true;
//        }
//        if (obj == null)
//        {
//            return false;
//        }
//        if (!(obj instanceof Vector))
//        {
//            return false;
//        }
//        final Vector other = (Vector) obj;
//        if (RANK != other.RANK)
//        {
//            return false;
//        }
//        if (!Arrays.equals(elements, other.elements))
//        {
//            return false;
//        }
//        return true;
//    }
//}
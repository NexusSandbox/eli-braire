package eli.braire.math.space;

import java.text.MessageFormat;
import java.util.Arrays;

import eli.veritas.Verifier;
import eli.veritas.exception.AssertionException;

/**
 * An object corresponding to the abstract mathematical notion of a Matrix. Matrices are linear systems of coefficients arranged as a row-major
 * 2-dimensional array of double values. This also supports typical and relevant Matrix transformations and operations.
 *
 * @author The Architect
 */
public class Matrix
{

    /**
     * The total number of row dimensions for the defined {@link Matrix}.
     */
    public final int         ROWS;
    /**
     * The total number of column dimensions for the defined {@link Matrix}.
     */
    public final int         COLUMNS;
    private final int        RANK    = -1;
    private final double[][] elements;
    private final double     EPSILON = 0.0000001d;

    private Matrix(final int rows, final int columns)
    {
        ROWS = rows;
        COLUMNS = columns;
        elements = new double[rows][];
        for (int i = 0; i < rows; i++)
        {
            elements[i] = new double[columns];
        }
    }

    private Matrix(final double[][] elements)
    {
        ROWS = elements.length;
        COLUMNS = elements[0].length;
        this.elements = new double[ROWS][];
        for (int i = 0; i < ROWS; i++)
        {
            this.elements[i] = elements[i].clone();
        }
    }

    private Matrix(final Vector[] columns)
    {
        COLUMNS = columns.length;
        ROWS = columns[0].RANK;
        elements = new double[ROWS][];
        for (int iRow = 0; iRow < ROWS; iRow++)
        {
            elements[iRow] = new double[COLUMNS];
            for (int iColumn = 0; iColumn < COLUMNS; iColumn++)
            {
                elements[iRow][iColumn] = columns[iColumn].d(iRow);
            }
        }
    }

    /**
     * @param elements A non-empty array of non-empty <code>double</code> arrays. Each row must have the same number of columns.
     * @return a new non-empty {@link Matrix} containing the specified elements.
     * @throws AssertionException If any of the following are true:
     *             <ul>
     *             <li>Elements is null</li>
     *             <li>Elements has less than 1 row</li>
     *             <li>Elements contains any empty or null rows</li>
     *             <li>The first elements row must have at least 1 column</li>
     *             <li>Any row does not contain the same number of columns as the first</li>
     *             </ul>
     */
    public static Matrix create(final double[] ... elements)
    {
        Verifier.Collections.assertNotEmpty("Matrix element rows must not be null or empty.", elements);
        Verifier.Collections.assertContainsNoValue("Matrix element columns must not be null or empty.", elements, (double[]) null);
        Verifier.Inequality.assertGreaterThan("A Matrix must have at least 1 column.", elements[0].length, 0);

        final int columns = elements[0].length;
        for (int iRow = 1; iRow < elements.length; iRow++)
        {
            Verifier.Equality.assertEqual("All Matrix rows must have the same number of columns.", elements[iRow].length, columns);
        }

        return new Matrix(elements);
    }

    public static Matrix create(final Vector ... columns)
    {
        Verifier.Collections.assertNotEmpty("Matrix element columns must not be null or empty.", columns);
        Verifier.Collections.assertContainsNoValue("Matrix element rows must not be null or empty.", columns, null);
        Verifier.Inequality.assertGreaterThan("A Matrix must have at least 1 row.", columns[0].RANK, 0);

        final int rowCount = columns[0].RANK;
        for (int iColumn = 1; iColumn < columns.length; iColumn++)
        {
            Verifier.Equality.assertEqual("All Matrix columns must have the same number of rows.", columns[iColumn], rowCount);
        }

        return new Matrix(columns);
    }

    /**
     * @param size The total (positive) number of rows/columns for the new {@link Matrix}.
     * @return a new non-empty identity {@link Matrix}. An identity Matrix is a square Matrix defined as follows:<br />
     *         <code>if (i == j) -> 1; else 0</code>.
     * @throws AssertionException If size is less than 1.
     */
    public static Matrix identity(final int size)
    {
        Verifier.Inequality.assertGreaterThan("Identity Matrix must have a positive size.", size, 0);

        final Matrix newMatrix = new Matrix(size, size);
        final double[][] elements = newMatrix.elements;
        for (int index = 0; index < size; index++)
        {
            elements[index][index] = 1D;
        }

        return newMatrix;
    }

    /**
     * @return a new non-empty {@link Matrix} with identical element values.
     */
    @Override
    public Matrix clone()
    {
        return new Matrix(elements);
    }

    /**
     * @param rowDimension A row dimension [1, total rows].
     * @param columnDimension A column dimension [1, total columns].
     * @return the <code>double</code> value corresponding to the {@link Matrix} element at the specified position.
     * @throws AssertionException If any of the following are true:
     *             <ul>
     *             <li>rowDimension &lt;= 0 OR rowDimension &gt; {@link #RANK}</li>
     *             <li>columnDimension &lt;= 0 OR columnDimension &gt; {@link #RANK}</li>
     *             </ul>
     */
    public double getElement(final int rowDimension, final int columnDimension)
    {
        Verifier.Ranges.assertInsideRange_Inclusive("Matrix row index must be within the defined range.", rowDimension, 1, ROWS);
        Verifier.Ranges.assertInsideRange_Inclusive("Matrix column index must be within the defined range.", columnDimension, 1, COLUMNS);

        return elements[rowDimension][columnDimension];
    }

    /**
     * @return the total number of linearly independent rows of the {@link Matrix}.
     */
    @SuppressWarnings("unused")
    public int getRank()
    {
        if (RANK < 0)
        {
            // TODO Implementation needed
            return 0;
        }

        return RANK;
    }

    /**
     * @return a new non-empty transverse {@link Matrix}. A transverse Matrix is defined as follows:<br />
     *         <code>matrix<sup>t</sup><sub>[i][j]</sub> == matrix<sub>[j][i]</sub></code> .
     */
    public Matrix transverse()
    {
        final Matrix newMatrix = new Matrix(COLUMNS, ROWS);
        for (int i = 0; i < COLUMNS; i++)
        {
            for (int j = 0; j < ROWS; j++)
            {
                newMatrix.elements[i][j] = elements[j][i];
            }
        }

        return newMatrix;
    }

    /**
     * @return a new non-empty inverse {@link Matrix}. An inverse Matrix is defined as follows:
     *         <code>matrix &middot; matrix<sup>inverse</sup> == matrix<sup>identity</sup></code> .
     * @throws AssertionException If any of the following are true:
     *             <ul>
     *             <li>The original Matrix is not square</li>
     *             <li>The original Matrix Rank != the total number of rows (this implies that the linear system is linearly dependent)</li>
     *             </ul>
     */
    public Matrix inverse()
    {
        // TODO Implementation needed
        return null;
    }

    /**
     * @return a scalar value corresponding to a square {@link Matrix}. The determinant is defined as follows:<br />
     *         <code>det(A) = Sum<sub>i,j</sub>( -1<sup>i+j</sup> * a<sub>i,j</sub> * det(M<sub>i,j</sub>) )</code> .
     * @throws AssertionException If the original Matrix is not square.
     */
    public double determinant()
    {
        // TODO Implementation needed
        return 0D;
    }

    /**
     * @param excludeRowIndex The original row index to exclude from the sub-matrix [1, total rows].
     * @param excludeColumnIndex The original column index to exclude from the sub-matrix [1, total columns].
     * @return a new non-empty {@link Matrix} consisting of all rows and columns excluding those specified by the parameters.
     * @throws AssertionException If any of the parameter conditions are not met;<br />
     *             The current {@link Matrix} must have:
     *             <ul>
     *             <li>More than 1 row</li>
     *             <li>More than 1 column</li>
     *             </ul>
     */
    public Matrix subMatrix(final int excludeRowIndex, final int excludeColumnIndex)
    {
        Verifier.Ranges.assertInsideRange_Inclusive("", excludeRowIndex, 1, ROWS);
        Verifier.Ranges.assertInsideRange_Inclusive("", excludeColumnIndex, 1, COLUMNS);

        final Matrix newMatrix = new Matrix(ROWS - 1, COLUMNS - 1);
        for (int i = 0, effectiveRow = 0; i < ROWS; i++, effectiveRow++)
        {
            if (i != excludeRowIndex)
            {
                for (int j = 0, effectiveColumn = 0; j < COLUMNS; j++, effectiveColumn++)
                {
                    if (j != excludeColumnIndex)
                    {
                        newMatrix.elements[effectiveRow][effectiveColumn] = elements[i][j];
                    }
                    else
                    {
                        effectiveColumn--;
                    }
                }
            }
            else
            {
                effectiveRow--;
            }
        }

        return newMatrix;
    }

    public Matrix echelonForm()
    {
        // TODO Implementation needed
        return null;
    }

    public Matrix reducedEchelonForm()
    {
        // TODO Implementation needed
        return null;
    }

    /**
     * @return a new non-empty {@link Matrix} where each element is the negative of the original.
     */
    public Matrix negate()
    {
        final Matrix newMatrix = new Matrix(ROWS, COLUMNS);
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLUMNS; j++)
            {
                newMatrix.elements[i][j] = -elements[i][j];
            }
        }

        return newMatrix;
    }

    /**
     * @param matrix
     * @return
     * @throws AssertionException
     */
    public Matrix add(final Matrix matrix)
    {
        Verifier.assertNotNull("Cannot perform operations with a null Matrix.", matrix);
        Verifier.Equality.assertEqual("Both Matrices must have the same number of rows.", matrix.ROWS, ROWS);
        Verifier.Equality.assertEqual("Both Matrices must have the same number of columns.", matrix.COLUMNS, COLUMNS);

        final Matrix newMatrix = new Matrix(ROWS, COLUMNS);
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLUMNS; j++)
            {
                newMatrix.elements[i][j] = elements[i][j] + matrix.elements[i][j];
            }
        }

        return newMatrix;
    }

    /**
     * @param matrix
     * @return
     * @throws AssertionException
     */
    public Matrix subtract(final Matrix matrix)
    {
        Verifier.assertNotNull("Cannot perform operations with a null Matrix.", matrix);
        Verifier.Equality.assertEqual("Both Matrices must have the same number of rows.", matrix.ROWS, ROWS);
        Verifier.Equality.assertEqual("Both Matrices must have the same number of columns.", matrix.COLUMNS, COLUMNS);

        final Matrix newMatrix = new Matrix(ROWS, COLUMNS);
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLUMNS; j++)
            {
                newMatrix.elements[i][j] = elements[i][j] - matrix.elements[i][j];
            }
        }

        return newMatrix;
    }

    /**
     * @param scalar
     * @return
     */
    public Matrix multiply(final double scalar)
    {
        final Matrix newMatrix = new Matrix(ROWS, COLUMNS);
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLUMNS; j++)
            {
                newMatrix.elements[i][j] = elements[i][j] * scalar;
            }
        }

        return newMatrix;
    }

    /**
     * @param scalar
     * @return
     * @throws AssertionException
     */
    public Matrix divide(final double scalar)
    {
        Verifier.Equality.assertNotEqual("Cannot divide Matrix elements by zero scalar.", scalar, 0, EPSILON);

        final Matrix newMatrix = new Matrix(ROWS, COLUMNS);
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLUMNS; j++)
            {
                newMatrix.elements[i][j] = elements[i][j] / scalar;
            }
        }

        return newMatrix;
    }

    /**
     * @param matrix
     * @return
     * @throws AssertionException
     */
    public Matrix dotProduct(final Matrix matrix)
    {
        Verifier.assertNotNull("Cannot perform operations with a null Matrix.", matrix);
        Verifier.Equality.assertEqual("Left-Matrix columns must match the Right-Matrix rows.", matrix.ROWS, COLUMNS);

        final Matrix newMatrix = new Matrix(ROWS, matrix.COLUMNS);
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < matrix.COLUMNS; j++)
            {
                for (int k = 0; k < COLUMNS; k++)
                {
                    newMatrix.elements[i][j] += elements[i][k] * matrix.elements[k][i];
                }
            }
        }

        return newMatrix;
    }

    /**
     * @param vector
     * @return
     * @throws AssertionException
     */
    public Vector dotProduct(final Vector vector)
    {
        Verifier.assertNotNull("Cannot perform operations with a null Vector.", vector);
        Verifier.Equality.assertEqual("Left-Matrix columns must match the Right-Vector dimensions.", vector.RANK, COLUMNS);

        final double[] newElements = new double[ROWS];
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLUMNS; j++)
            {
                newElements[i] += elements[i][j] * vector.d(j);
            }
        }

        return Vector.create(newElements);
    }

    /**
     * @param point
     * @return
     * @throws AssertionException
     */
    public Point dotProduct(final Point point)
    {
        Verifier.assertNotNull("Cannot perform operations with a null Point.", point);
        Verifier.Equality.assertEqual("Left-Matrix columns must match the Right-Point dimensions.", point.RANK, COLUMNS);

        final double[] coordinates = new double[ROWS];
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLUMNS; j++)
            {
                coordinates[i] += elements[i][j] * point.d(j);
            }
        }

        return Point.create(coordinates);
    }

    /**
     * @param shiftingVector
     * @return
     * @throws AssertionException
     */
    public Matrix translate(final Vector shiftingVector)
    {
        Verifier.assertNotNull("", shiftingVector);
        Verifier.Equality.assertEqual("", shiftingVector.RANK, ROWS);

        return null;
    }

    /**
     * @param scalingVector
     * @return
     * @throws AssertionException
     */
    public Matrix scale(final Vector scalingVector)
    {
        Verifier.assertNotNull("", scalingVector);
        Verifier.Equality.assertEqual("", scalingVector.RANK, ROWS);

        // TODO Implementation needed
        return null;
    }

    /**
     * @param rotatingVector
     * @return
     * @throws AssertionException
     */
    public Matrix rotate(final Vector rotatingVector)
    {
        Verifier.assertNotNull("", rotatingVector);
        Verifier.Equality.assertEqual("", rotatingVector.RANK, ROWS);

        // TODO Implementation needed
        return null;
    }

    /**
     * @param shearingVectors
     * @return
     * @throws AssertionException
     */
    public Matrix shear(final Vector[] shearingVectors)
    {
        Verifier.assertNotNull("", shearingVectors);
        Verifier.Inequality.assertGreaterThan("", shearingVectors.length, 0);
        Verifier.Collections.assertContainsNoValue("", shearingVectors, null);

        // TODO Implementation needed
        return null;
    }

    @Override
    public String toString()
    {
        return MessageFormat.format("[{0}]", Arrays.deepToString(elements));
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + COLUMNS;
        result = prime * result + Arrays.deepHashCode(elements);
        result = prime * result + ROWS;
        return result;
    }

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
        if (!(obj instanceof Matrix))
        {
            return false;
        }
        final Matrix other = (Matrix) obj;
        if (COLUMNS != other.COLUMNS)
        {
            return false;
        }
        if (!Arrays.deepEquals(elements, other.elements))
        {
            return false;
        }
        if (RANK != other.RANK)
        {
            return false;
        }
        if (ROWS != other.ROWS)
        {
            return false;
        }
        return true;
    }
}
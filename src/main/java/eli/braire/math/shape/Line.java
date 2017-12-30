package eli.braire.math.shape;

import java.text.MessageFormat;

import eli.braire.math.space.AbstractLinearConstruct;
import eli.braire.math.space.Point;
import eli.veritas.Verifier;
import eli.veritas.exception.AssertionException;

/**
 * An object corresponding to the abstract mathematical notion of a line segment. Line segments consist of an origin {@link Point} and a terminating
 * {@link Point}. This also supports typical and relevant linear transformations and operations.
 *
 * @author The Architect
 */
public class Line extends AbstractLinearConstruct
{
    /**
     * Originating point of the line segment.
     */
    public final Point origin;
    /**
     * Terminating point of the line segment.
     */
    public final Point terminus;

    protected Line(final Point origin, final Point terminus)
    {
        super(origin.RANK);
        this.origin = origin;
        this.terminus = terminus;
    }

    public static Line create(final Point origin, final Point terminus) throws AssertionException
    {
        Verifier.assertNotNull("Point Origin", origin);
        Verifier.assertNotNull("Point Terminus", terminus);
        Verifier.Equality.assertEqual("Both Line endpoints must has same dimensionality.", origin.RANK, terminus.RANK);

        return new Line(origin, terminus);
    }

    public boolean intersects(final Line line) throws AssertionException
    {
        // TODO Implement switching logic for dimensionality.
        return false;
    }

    private boolean intersects3d(final Line line) throws AssertionException
    {
        final double denominator = (line.terminus.d(2) - line.origin.d(2)) * (terminus.d(1) - origin.d(1)) - (line.terminus.d(1) - line.origin.d(1)) * (terminus.d(2) - origin.d(2));
        final double aNumerator = (line.terminus.d(1) - line.origin.d(1)) * (origin.d(2) - line.origin.d(2)) - (line.terminus.d(2) - line.origin.d(2)) * (origin.d(1) - line.origin.d(1));
        final double bNumerator = (terminus.d(1) - origin.d(1)) * (origin.d(2) - line.origin.d(2)) - (terminus.d(2) - origin.d(2)) * (origin.d(1) - line.origin.d(1));

        if (Verifier.Equality.isEqual(denominator, 0, AbstractLinearConstruct.EPSILON))
        {
            return Verifier.Equality.isEqual(aNumerator,
                                             0,
                                             AbstractLinearConstruct.EPSILON) && Verifier.Equality.isEqual(bNumerator,
                                                                                                           0,
                                                                                                           AbstractLinearConstruct.EPSILON);
        }

        final double aParameter = aNumerator / denominator;
        final double bParameter = bNumerator / denominator;

        return aParameter > 0 && aParameter <= 1 && bParameter > 0 && bParameter <= 1;
    }

    public double sign(final Point point) throws AssertionException
    {
        return (origin.d(2) - terminus.d(2)) * (point.d(1) - terminus.d(1)) - (origin.d(1) - terminus.d(1)) * (point.d(2) - terminus.d(2));
    }

    @Override
    public String toString()
    {
        return MessageFormat.format("[{0}:{1}]", origin, terminus);
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
        result = prime * result + (origin == null ? 0 : origin.hashCode());
        result = prime * result + (terminus == null ? 0 : terminus.hashCode());
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
        if (!(obj instanceof Line))
        {
            return false;
        }
        final Line other = (Line) obj;
        if (RANK != other.RANK)
        {
            return false;
        }
        if (origin == null)
        {
            if (other.origin != null)
            {
                return false;
            }
        }
        else if (!origin.equals(other.origin))
        {
            return false;
        }
        if (terminus == null)
        {
            if (other.terminus != null)
            {
                return false;
            }
        }
        else if (!terminus.equals(other.terminus))
        {
            return false;
        }
        return true;
    }
}

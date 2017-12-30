package eli.braire.math.shape;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import eli.braire.math.space.AbstractLinearConstruct;
import eli.braire.math.space.Point;
import eli.braire.math.space.Vector;
import eli.veritas.Verifier;
import eli.veritas.exception.AssertionException;

/**
 * An object corresponding to the abstract mathematical notion of a Ray. Rays consist of an origin {@link Point} and an offset {@link Vector}. This
 * also supports typical and relevant Ray transformations and operations.
 *
 * @author The Architect
 */
public class Ray extends Line
{
    public final Vector offset;

    protected Ray(final Point origin, final Point terminus, final Vector offset)
    {
        super(origin, terminus);
        this.offset = offset;
    }

    public static Ray create(final Point origin, final Vector offset) throws AssertionException
    {
        Verifier.assertNotNull("Ray Point origin.", origin);
        Verifier.assertNotNull("Ray Vector offset.", offset);
        Verifier.Equality.assertEqual("Both Ray origin Point and Vector offset must have the same dimensionality.", offset.RANK, origin.RANK);

        return new Ray(origin, origin.add(offset).toPoint(), offset);
    }

    public static Ray create(final Point origin, final Point terminus) throws AssertionException
    {
        Verifier.assertNotNull("Ray Point origin.", origin);
        Verifier.assertNotNull("Ray Point terminus.", terminus);
        Verifier.Equality.assertEqual("Both Ray origin and terminus Points must have the same dimensionality.", terminus.RANK, origin.RANK);

        return new Ray(origin, terminus, terminus.subtract(origin));
    }

    /**
     * Determines the <code>t</code> scalar value on this {@link Line} closest to the provided {@link Point}
     *
     * @param point An arbitrary {@link Point} of the same rank as this {@link Line}. (Cannot be null)
     * @return A scalar value for the parameterized input for {@link #f(double)} corresponding to the nearest {@link Point} on this {@link Line}.
     *         <ul>
     *         <li>Will be 0 or 1 if not between initial {@link Point} <code>a</code> and terminal {@link Point} <code>b</code>.</li>
     *         <li>Will be 0 if nearest {@link Point} on the line segment is <code>a</code>.</li>
     *         <li>Will be 1 if nearest {@link Point} on the line segment is <code>b</code>.</li>
     *         </ul>
     * @throws AssertionException
     */
    private double closestApproach(final Point point) throws AssertionException
    {
        // Formula: (P - A) * BA / BA^2
        final Vector pa = point.subtract(origin);
        final double t = pa.dotProduct(offset) / offset.dotProduct(offset);
        if (Verifier.Inequality.isGreaterThanOrEqual(t, 1d, AbstractLinearConstruct.EPSILON))
        {
            return 1;
        }
        else if (Verifier.Inequality.isLessThanOrEqual(t, 0d, AbstractLinearConstruct.EPSILON))
        {
            return 0;
        }

        return t;
    }

    /**
     * Calculates a {@link Point} on this {@link Line} relative to the parameterized input <code>t</code>.
     *
     * @param t Any scalar value.
     * @return A non-null {@link Point} on the current {@link Line}.
     * @throws AssertionException
     */
    public Point f(final double t) throws AssertionException
    {
        // Formula: A + t * BA
        final Vector scalar = offset.multiply(t);
        final Vector scaledOffset = origin.add(scalar);

        return scaledOffset.add(scalar).toPoint();
    }

    /**
     * Used when the literal magnitude of the distance is not necessary, and only direct distance comparisons are needed.
     *
     * @param point An arbitrary {@link Point} of the same rank as this {@link Line}. (Cannot be null)
     * @return A non-negative scalar value corresponding to the distance metric squared. Will be 0 if point is on the defined line.
     * @throws AssertionException
     */
    public double euclideanDistance(final Point point) throws AssertionException
    {
        // Formula: [(P - A) + t0 * BA]^2
        final double t = closestApproach(point);
        final Point pa = point.subtract(origin);
        final Vector bat = offset.multiply(t);
        final Vector result = pa.add(bat);

        return result.dotProduct(result);
    }

    public Collection<Point> findNearest(final Collection<Point> points) throws AssertionException
    {
        Verifier.Collections.assertNotEmpty("", points);

        if (points.size() == 1)
        {
            final Point point = points.iterator().next();
            Verifier.Equality.assertEqual(MessageFormat.format("Comparison Points must have the same dimensionality: {0}", point), point.RANK, RANK);
            return new HashSet<>(points);
        }
        final Set<Point> nearestPoints = new HashSet<>(1);
        double minDistance = Double.MAX_VALUE;
        for (final Point point : points)
        {
            Verifier.Equality.assertEqual(MessageFormat.format("Comparison Points must have the same dimensionality: {0}", point), point.RANK, RANK);
            final double distance = calculateEuclideanDistance(point);
            System.out.println(MessageFormat.format("Ray: {0}; Point: {1}; Distance: {2}", this, point, distance));
            if (Verifier.Inequality.isLessThan(distance, minDistance, EPSILON))
            {
                minDistance = distance;
                nearestPoints.clear();
                nearestPoints.add(point);
            }
            else if (Verifier.Equality.isEqual(distance, minDistance, EPSILON))
            {
                nearestPoints.add(point);
            }
        }

        return nearestPoints;
    }

    private double calculateEuclideanDistance(final Point point) throws AssertionException
    {
        final Vector displacement = origin.subtract(point);
        final double offsetNorm = offset.dotProduct(offset);

        Verifier.Inequality.assertGreaterThan("", offsetNorm, 0, EPSILON);
        final double t = displacement.negate().dotProduct(offset) / offsetNorm;

        final Vector distance = displacement.add(offset.multiply(t));
        return distance.dotProduct(distance);
    }

    @Override
    public String toString()
    {
        return MessageFormat.format("{0}->{1}", origin, offset);
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
        result = prime * result + (offset == null ? 0 : offset.hashCode());
        result = prime * result + (origin == null ? 0 : origin.hashCode());
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
        if (!(obj instanceof Ray))
        {
            return false;
        }
        final Ray other = (Ray) obj;
        if (RANK != other.RANK)
        {
            return false;
        }
        if (offset == null)
        {
            if (other.offset != null)
            {
                return false;
            }
        }
        else if (!offset.equals(other.offset))
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
        return true;
    }
}

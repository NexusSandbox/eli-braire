package eli.braire.algorithm.hull;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

import eli.braire.math.space.Point;
import eli.braire.math.space.RotationDirection;
import eli.veritas.Verifier;

/**
 * TODO Functional Description
 *
 * @author The Architect
 */
public class ConvexHull
{
    private final Point       median;
    private final List<Point> points;
    private final List<Point> hullPath;

    private ConvexHull(final List<Point> points)
    {
        this.points = points;
        median = findMedian(points);
        // TODO Check if this actually sorts the points in a counter-clockwise manner around the median.
        this.points.stream().sorted((p1, p2) -> {
            final RotationDirection dir = RotationDirection.evaluate(median, p1, p2);
            switch (dir)
            {
                case CLOCKWISE:
                    return -1;
                case COUNTER_CLOCKWISE:
                    return 1;
                default:
                    return 0;
            }
        });
        hullPath = Lists.newArrayListWithCapacity(points.size());
    }

    public static ConvexHull create(final Collection<Point> points)
    {
        Verifier.Collections.assertNotEmpty("Unable to create Hull that is empty or null.", points);
        Verifier.Collections.assertContainsNoValue("Unable to create Hull that contains null points.", points, null);

        return new ConvexHull(Lists.newLinkedList(points));
    }

    public static Point findMedian(final Collection<Point> points)
    {
        return null;
    }

    /**
     * @return A non-null, non-empty {@link List} of {@link Point points} indicating the outer-most perimeter of the initial {@link Collection} of
     *         {@link Point points}. These points will be ordered counter-clockwise around a median point.
     * @see https://en.wikipedia.org/wiki/Graham_scan
     */
    public List<Point> grahamScan()
    {

        return hullPath;
    }
}

package eli.braire.math.shape;

import java.text.MessageFormat;
import java.util.List;

import com.google.common.collect.ImmutableList;

import eli.braire.math.space.Point;
import eli.veritas.exception.AssertionException;

/**
 * TODO Functional Description
 *
 * @author The Architect
 */
public class Face
{
    public final Point      p1;
    public final Point      p2;
    public final Point      p3;
    public final List<Line> segments;

    // private final BoundingBox boundingBox;

    private Face(final Point p1, final Point p2, final Point p3) throws AssertionException
    {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        segments = ImmutableList.<Line> of(Line.create(p1, p2), //
                                           Line.create(p2, p3), //
                                           Line.create(p3, p1));
        final Point leftBottom = Point.create(Math.min(Math.min(p1.d(1), p2.d(1)), p3.d(1)), //
                                              Math.min(Math.min(p1.d(2), p2.d(2)), p3.d(2)));
        final Point rightTop = Point.create(Math.max(Math.max(p1.d(1), p2.d(1)), p3.d(1)), //
                                            Math.max(Math.max(p1.d(1), p2.d(1)), p3.d(1)));
        // boundingBox = BoundingBox.create(leftBottom, rightTop);
    }

    public static Face create(final Point p1, final Point p2, final Point p3) throws AssertionException
    {
        return new Face(p1, p2, p3);
    }

    public boolean contains(final Point point) throws AssertionException
    {
        for (final Line segment : segments)
        {
            if (segment.sign(point) >= 0)
            {
                return false;
            }
        }

        return true;
    }

    public boolean intersects(final Face triangle) throws AssertionException
    {
        // if (!boundingBox.intersects(triangle.boundingBox))
        // {
        // return false;
        // }

        return areSegmentsCrossing(triangle) || isInside(triangle);
    }

    private boolean areSegmentsCrossing(final Face triangle) throws AssertionException
    {
        for (final Line segmentA : segments)
        {
            for (final Line segmentB : triangle.segments)
            {
                if (segmentA.intersects(segmentB))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isInside(final Face triangle) throws AssertionException
    {
        return contains(triangle.p1) || contains(triangle.p2) || contains(triangle.p3) || triangle.contains(p1) || triangle.contains(p2) || triangle.contains(p3);
    }

    @Override
    public String toString()
    {
        return MessageFormat.format("<{0}, {1}, {2}>", p1, p2, p3);
    }
}

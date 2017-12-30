package eli.braire.math.shape;

import java.text.MessageFormat;

import eli.braire.math.space.Point;

/**
 * TODO Functional Description
 * 
 * @author The Architect
 */
public class BoundingSphere
{
    public Point center;
    public double radius;

    private BoundingSphere(final Point center, final double radius)
    {
        this.center = center;
        this.radius = radius;
    }

    public static BoundingSphere create(final Point center, final double radius)
    {
        return new BoundingSphere(center, radius);
    }

    public boolean intersects(final Ray ray)
    {
        // return rightTop.x >= rectangle.leftBottom.x && //
        // leftBottom.x <= rectangle.rightTop.x && //
        // rightTop.y >= rectangle.leftBottom.y && //
        // leftBottom.y <= rectangle.rightTop.y;

        return false;
    }

    @Override
    public String toString()
    {
        return MessageFormat.format("[{0}->{1}]", center, radius);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((center == null) ? 0 : center.hashCode());
        long temp;
        temp = Double.doubleToLongBits(radius);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof BoundingSphere))
        {
            return false;
        }
        BoundingSphere other = (BoundingSphere) obj;
        if (center == null)
        {
            if (other.center != null)
            {
                return false;
            }
        }
        else if (!center.equals(other.center))
        {
            return false;
        }
        if (Double.doubleToLongBits(radius) != Double.doubleToLongBits(other.radius))
        {
            return false;
        }
        return true;
    }
}

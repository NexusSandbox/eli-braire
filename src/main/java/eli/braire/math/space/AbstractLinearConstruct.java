/**
 *
 */
package eli.braire.math.space;

/**
 * TODO Functional Description
 *
 * @author The Architect
 */
public abstract class AbstractLinearConstruct
{
    /**
     * The margin of error for comparing double values.
     */
    protected static final double EPSILON = 0.0000001d;

    /**
     * The number of dimensions for the defined construct.
     */
    public final int RANK;

    protected AbstractLinearConstruct(final int RANK)
    {
        this.RANK = RANK;
    }
}

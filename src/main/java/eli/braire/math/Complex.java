package eli.braire.math;

/**
 * An object corresponding to the abstract mathematical notion of a complex number. Complex numbers consist of a real value, and an imaginary value. This supports typical and relevant complex
 * transformations and operations.
 *
 * @author The Architect
 */
public final class Complex
{
    /**
     * Enumerates the complex numeric formats.
     */
    public static enum Type
    {
     CARTESIAN,
     POLAR
    }

    private static double REFLECTION = Math.PI / 2D;

    private final double real;
    private final double imaginary;
    private final Type   type;

    private Complex(final double real, final double imaginary, final Type type)
    {
        this.real = real;
        this.imaginary = imaginary;
        this.type = type;
    }

    public static Complex create()
    {
        return new Complex(0d, 0d, Type.CARTESIAN);
    }

    public static Complex create(final double real)
    {
        return new Complex(real, 0d, Type.CARTESIAN);
    }

    public static Complex create(final double real, final double imaginary)
    {
        return new Complex(real, imaginary, Type.CARTESIAN);
    }

    public static Complex create(final double real, final double imaginary, final Type type)
    {
        return new Complex(real, imaginary, type);
    }

    public double getReal()
    {
        return real;
    }

    public double getImaginary()
    {
        return imaginary;
    }

    public Type getType()
    {
        return type;
    }

    public Complex convertType(final Type type)
    {
        if (this.type != type)
        {
            double real = 0d;
            double imaginary = 0d;
            switch (type)
            {
                case CARTESIAN:
                    real = this.real * Math.cos(imaginary);
                    imaginary = this.real * Math.sin(imaginary);
                    break;
                case POLAR:
                    real = Math.sqrt((this.real * this.real) + (this.imaginary * this.imaginary));
                    imaginary = Math.atan(this.imaginary / this.real);
                    if (this.real < 0d)
                    {
                        if (this.imaginary < 0d)
                        {
                            imaginary -= REFLECTION;
                        }
                        else
                        {
                            imaginary += REFLECTION;
                        }
                    }
                    break;
                default:
                    break;
            }

            return new Complex(real, imaginary, type);
        }

        return this;
    }
}

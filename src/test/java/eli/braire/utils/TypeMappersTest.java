package eli.braire.utils;

import eli.braire.util.TypeMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class TypeMappersTest
{
    private TypeMapper mapper = new TypeMapper();

    @Test
    public void testDoubleToLong_WithDefault()
    {
        final Double input = 4D;
        final Long output = mapper.convert(input, 0L);
        Assert.assertEquals("Unexpected conversion value.", 4l, (long) output);
        Assert.assertEquals("Unexpected conversion type.", Long.class, output.getClass());
    }

    @Test
    public void testDoubleToDouble_WithDefault()
    {
        final Double input = 4D;
        final Double output = mapper.convert(input, 0D);
        Assert.assertEquals("Unexpected conversion value.", 4, output, 0.0);
        Assert.assertEquals("Unexpected conversion type.", Double.class, output.getClass());
    }

    @Test
    public void testDoubleToString_WithDefault()
    {
        final Double input = 4D;
        final String output = mapper.convert(input, "");
        Assert.assertEquals("Unexpected conversion value.", "4.0", output);
        Assert.assertEquals("Unexpected conversion type.", String.class, output.getClass());
    }

    @Test
    public void testDoubleToLong_WithType()
    {
        final Double input = 4D;
        final Optional<Long> output = mapper.convert(input, Long.class);
        Assert.assertEquals("Unexpected conversion value.", 4, (long) output.get());
        Assert.assertEquals("Unexpected conversion type.", Long.class, output.get().getClass());
    }

    @Test
    public void testDoubleToDouble_WithType()
    {
        final Double input = 4D;
        final Optional<Double> output = mapper.convert(input, Double.class);
        Assert.assertEquals("Unexpected conversion value.", 4, output.get(), 0.0);
        Assert.assertEquals("Unexpected conversion type.", Double.class, output.get().getClass());
    }

    @Test
    public void testDoubleToString_WithType()
    {
        final Double input = 4D;
        final Optional<String> output = mapper.convert(input, String.class);
        Assert.assertEquals("Unexpected conversion value.", "4.0", output.get());
        Assert.assertEquals("Unexpected conversion type.", String.class, output.get().getClass());
    }

}

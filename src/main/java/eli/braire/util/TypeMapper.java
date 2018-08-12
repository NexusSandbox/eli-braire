package eli.braire.util;

import eli.braire.exception.TypeConversionException;
import eli.veritas.Verifier;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class TypeMapper
{
    private Map<Class<?>, Mapper<?>> converterMatrix = new HashMap<>();

    public TypeMapper()
    {
        converterMatrix.put(Long.class, new LongMapper());
        converterMatrix.put(Double.class, new DoubleMapper());
        converterMatrix.put(String.class, new StringMapper());
    }

    public <IN, OUT> Optional<OUT> convert(final IN input, final Class<OUT> outputType)
    {
        if (input == null)
        {
            return Optional.empty();
        }

        final Class<IN> inputType = (Class<IN>) input.getClass();
        final OUT output = get(inputType).convert(input, outputType);
        return Optional.ofNullable(output);
    }

    private <IN> Mapper<IN> get(final Class<IN> inputType)
    {
        // Necessary to override the wildcard capture type
        return (Mapper<IN>) converterMatrix.get(inputType);
    }

    public <IN, OUT> OUT convert(final IN input, final OUT defaultValue)
    {
        Verifier.forChecking(getClass())
                .ifNotNull("defaultValue", defaultValue)
                .throwing(TypeConversionException::new);

        return convert(input, (Class<OUT>) defaultValue.getClass()).orElse(defaultValue);
    }

    private static abstract class Mapper<INPUT>
    {
        protected Map<Class<?>, Function<INPUT, ?>> converters = new HashMap<>();

        protected Mapper()
        {
            converters.put(Double.class, input -> toDouble(input));
            converters.put(Long.class, input -> toLong(input));
            converters.put(String.class, input -> toText(input));
        }

        public <OUT> OUT convert(final INPUT input, final Class<OUT> outputType)
        {
            if (input == null)
            {
                return null;
            }

            final Function<INPUT, OUT> inputFunction = (Function<INPUT, OUT>) converters.get(
                    outputType);
            if (inputFunction == null)
            {
                return null;
            }
            return inputFunction.apply(input);
        }

        public abstract Double toDouble(final INPUT input);

        public abstract Long toLong(final INPUT input);

        public abstract String toText(final INPUT input);
    }

    private static class LongMapper extends Mapper<Long>
    {
        @Override
        public Double toDouble(final Long aLong)
        {
            return aLong.doubleValue();
        }

        @Override
        public Long toLong(final Long aLong)
        {
            return aLong;
        }

        @Override
        public String toText(final Long aLong)
        {
            return aLong.toString();
        }
    }

    private static class DoubleMapper extends Mapper<Double>
    {
        @Override
        public Double toDouble(final Double aDouble)
        {
            return aDouble;
        }

        @Override
        public Long toLong(final Double aDouble)
        {
            return aDouble.longValue();
        }

        @Override
        public String toText(final Double aDouble)
        {
            return aDouble.toString();
        }
    }

    private static class StringMapper extends Mapper<String>
    {
        @Override
        public Double toDouble(final String s)
        {
            return Double.parseDouble(s);
        }

        @Override
        public Long toLong(final String s)
        {
            return Long.parseLong(s);
        }

        @Override
        public String toText(final String s)
        {
            return s;
        }
    }
}

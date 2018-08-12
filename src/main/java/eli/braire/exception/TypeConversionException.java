package eli.braire.exception;

import eli.veritas.exception.CompositeException;

import java.util.Collection;

public class TypeConversionException extends CompositeException
{
    public TypeConversionException(final Collection<String> messages)
    {
        super(messages);
    }
}

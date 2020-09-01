package calc.exceptions;

public class NegativeSquareRootException extends OperationException {
    private static final String DEFAULT_EXC_PREFIX = "";
    private static final String EXC_STRING         = "Attempt to calculate the square root of a negative number.";

    private final String EXC_PREFIX;

    public NegativeSquareRootException(String prefix) { EXC_PREFIX = prefix; }
    public NegativeSquareRootException()              { EXC_PREFIX = DEFAULT_EXC_PREFIX; }

    public String toString() { return EXC_PREFIX + EXC_STRING; }
}
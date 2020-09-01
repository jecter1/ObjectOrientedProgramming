package calc.exceptions;

public class DivisionByZeroException extends OperationException {
    private static final String DEFAULT_EXC_PREFIX = "";
    private static final String EXC_STRING         = "Attempt to divide by zero.";

    private final String EXC_PREFIX;

    public DivisionByZeroException(String prefix) { EXC_PREFIX = prefix; }
    public DivisionByZeroException()              { EXC_PREFIX = DEFAULT_EXC_PREFIX; }

    public String toString() { return EXC_PREFIX + EXC_STRING; }
}

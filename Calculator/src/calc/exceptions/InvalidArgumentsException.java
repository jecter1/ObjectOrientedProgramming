package calc.exceptions;

public class InvalidArgumentsException extends OperationException {
    private static final String DEFAULT_EXC_PREFIX = "";
    private static final String EXC_STRING         = "Invalid arguments.";

    private final String EXC_PREFIX;

    public InvalidArgumentsException(String prefix) { EXC_PREFIX = prefix; }
    public InvalidArgumentsException()              { EXC_PREFIX = DEFAULT_EXC_PREFIX; }

    public String toString() { return EXC_PREFIX + EXC_STRING; }
}

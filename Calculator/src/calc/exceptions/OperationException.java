package calc.exceptions;

public class OperationException extends Exception {
    private static final String DEFAULT_EXC_PREFIX = "";
    private static final String EXC_STRING         = "Undefined operation exception.";

    private final String EXC_PREFIX;

    public OperationException(String prefix) { EXC_PREFIX = prefix; }
    public OperationException()              { EXC_PREFIX = DEFAULT_EXC_PREFIX; }

    public String toString() { return EXC_PREFIX + EXC_STRING; }
}

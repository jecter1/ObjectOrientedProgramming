package calc.exceptions;

public class NotEnoughElementsException extends OperationException {
    private static final String DEFAULT_EXC_PREFIX        = "";
    private static final String EXC_SUBSTRING_1           = "Not enough elements in the stack, required ";
    private static final String EXC_SUBSTRING_2_EMPTY     = " but stack is empty.";
    private static final String EXC_SUBSTRING_2_NOT_EMPTY = " but only ";
    private static final String EXC_SUBSTRING_3_NOT_EMPTY = " in the stack.";

    private final int NEED_ELEMENTS;
    private final int STACK_ELEMENTS;
    private final String EXC_PREFIX;

    public NotEnoughElementsException(int needElements, int stackElements, String prefix) {
        NEED_ELEMENTS  = needElements;
        STACK_ELEMENTS = stackElements;
        EXC_PREFIX = prefix;
    }

    public NotEnoughElementsException(int needElements, int stackElements) {
        NEED_ELEMENTS  = needElements;
        STACK_ELEMENTS = stackElements;
        EXC_PREFIX = DEFAULT_EXC_PREFIX;
    }

    public String toString() {
        String excString = EXC_PREFIX + EXC_SUBSTRING_1 + NEED_ELEMENTS;
        if (STACK_ELEMENTS == 0) {
            excString += EXC_SUBSTRING_2_EMPTY;
        } else {
            excString += EXC_SUBSTRING_2_NOT_EMPTY + STACK_ELEMENTS + EXC_SUBSTRING_3_NOT_EMPTY;
        }
        return excString;
    }
}

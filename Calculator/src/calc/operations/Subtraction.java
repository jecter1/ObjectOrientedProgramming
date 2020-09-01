package calc.operations;

import calc.exceptions.InvalidArgumentsException;
import calc.exceptions.NotEnoughElementsException;

import java.util.logging.Logger;

public class Subtraction implements Operation {
    @Override
    public void execute(Context context, String ... args)
                throws InvalidArgumentsException, NotEnoughElementsException {

        if (args.length != NEED_ARGS_ELEMENTS) {
            LOGGER.warning(LOG_WARNING_ARGUMENTS_NUMBER);
            throw new InvalidArgumentsException(EXC_PREFIX);
        } // Incorrect number of arguments

        if (context.getStack().size() < NEED_STACK_ELEMENTS) {
            LOGGER.warning(LOG_WARNING_STACK_ELEMENTS);
            throw new NotEnoughElementsException(NEED_STACK_ELEMENTS, context.getStack().size(), EXC_PREFIX);
        }

        double val1 = context.getStack().pop();
        LOGGER.info(LOG_INFO_POP_FIRST);

        double val2 = context.getStack().pop();
        LOGGER.info(LOG_INFO_POP_SECOND);

        context.getStack().push(val1 - val2);
        LOGGER.info(LOG_INFO_PUSH);
    }

    private static final String CLASS_NAME                   = Subtraction.class.getName();
    private static final Logger LOGGER                       = Logger.getLogger(CLASS_NAME);

    private static final String LOG_INFO_POP_FIRST           = CLASS_NAME + " > execute > POP FIRST ELEMENT";
    private static final String LOG_INFO_POP_SECOND          = CLASS_NAME + " > execute > POP SECOND ELEMENT";
    private static final String LOG_INFO_PUSH                = CLASS_NAME + " > execute > PUSH RESULT";

    private static final String LOG_WARNING_ARGUMENTS_NUMBER = CLASS_NAME + " > execute > WRONG ARGUMENTS NUMBER";
    private static final String LOG_WARNING_STACK_ELEMENTS   = CLASS_NAME + " > execute > NOT ENOUGH ELEMENTS" +
                                                                                        " IN THE STACK";

    private static final int    NEED_STACK_ELEMENTS          = 2;
    private static final int    NEED_ARGS_ELEMENTS           = 0;
    private static final String EXC_PREFIX                   = CLASS_NAME + ": ";
}

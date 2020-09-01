package calc.operations;

import calc.exceptions.InvalidArgumentsException;
import calc.exceptions.NotEnoughElementsException;

import java.util.logging.Logger;

public class Pop implements Operation {
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

        context.getStack().pop();
        LOGGER.info(LOG_INFO_POP);
    }

    private static final String CLASS_NAME                   = Pop.class.getName();
    private static final Logger LOGGER                       = Logger.getLogger(CLASS_NAME);

    private static final String LOG_INFO_POP                 = CLASS_NAME + " > execute > POPPED";

    private static final String LOG_WARNING_ARGUMENTS_NUMBER = CLASS_NAME + " > execute > WRONG ARGUMENTS NUMBER";
    private static final String LOG_WARNING_STACK_ELEMENTS   = CLASS_NAME + " > execute > NOT ENOUGH ELEMENTS " +
                                                                                        " IN THE STACK";

    private static final int    NEED_STACK_ELEMENTS          = 1;
    private static final int    NEED_ARGS_ELEMENTS           = 0;
    private static final String EXC_PREFIX                   = CLASS_NAME + ": ";
}

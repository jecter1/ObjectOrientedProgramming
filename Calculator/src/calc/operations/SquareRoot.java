package calc.operations;

import calc.exceptions.InvalidArgumentsException;
import calc.exceptions.NotEnoughElementsException;
import calc.exceptions.NegativeSquareRootException;

import java.util.logging.Logger;

public class SquareRoot implements Operation {
    @Override
    public void execute(Context context, String ... args)
            throws InvalidArgumentsException, NotEnoughElementsException, NegativeSquareRootException {

        if (args.length != NEED_ARGS_ELEMENTS) {
            LOGGER.warning(LOG_WARNING_ARGUMENTS_NUMBER);
            throw new InvalidArgumentsException(EXC_PREFIX);
        } // Incorrect number of arguments

        if (context.getStack().size() < NEED_STACK_ELEMENTS) {
            LOGGER.warning(LOG_WARNING_STACK_ELEMENTS);
            throw new NotEnoughElementsException(NEED_STACK_ELEMENTS, context.getStack().size(), EXC_PREFIX);
        }

        double val = context.getStack().pop();
        LOGGER.info(LOG_INFO_POP);

        if (val < 0) {
            LOGGER.warning(LOG_WARNING_NEGATIVE_NUMBER);
            context.getStack().push(val);
            LOGGER.info(LOG_INFO_PUSH_BACK);
            throw new NegativeSquareRootException(EXC_PREFIX);
        }

        context.getStack().push(Math.sqrt(val));
        LOGGER.info(LOG_INFO_PUSH);
    }

    private static final String CLASS_NAME                   = SquareRoot.class.getName();
    private static final Logger LOGGER                       = Logger.getLogger(CLASS_NAME);

    private static final String LOG_INFO_POP                 = CLASS_NAME + " > execute > POP ELEMENT";
    private static final String LOG_INFO_PUSH                = CLASS_NAME + " > execute > PUSH RESULT";
    private static final String LOG_INFO_PUSH_BACK           = CLASS_NAME + " > execute > PUSH ELEMENT";

    private static final String LOG_WARNING_ARGUMENTS_NUMBER = CLASS_NAME + " > execute > WRONG ARGUMENTS NUMBER";
    private static final String LOG_WARNING_STACK_ELEMENTS   = CLASS_NAME + " > execute > NOT ENOUGH ELEMENTS" +
                                                                                        " IN THE STACK";
    private static final String LOG_WARNING_NEGATIVE_NUMBER  = CLASS_NAME + " > execute > NEGATIVE NUMBER";

    private static final int    NEED_STACK_ELEMENTS          = 1;
    private static final int    NEED_ARGS_ELEMENTS           = 0;
    private static final String EXC_PREFIX                   = CLASS_NAME + ": ";
}

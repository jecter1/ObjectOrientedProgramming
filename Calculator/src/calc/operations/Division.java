package calc.operations;

import calc.exceptions.InvalidArgumentsException;
import calc.exceptions.NotEnoughElementsException;
import calc.exceptions.DivisionByZeroException;

import java.util.logging.Logger;

public class Division implements Operation {
    @Override
    public void execute(Context context, String ... args)
                throws InvalidArgumentsException, NotEnoughElementsException, DivisionByZeroException {

        if (args.length != NEED_ARGS_ELEMENTS) {
            LOGGER.warning(LOG_WARNING_ARGUMENTS_NUMBER);
            throw new InvalidArgumentsException(EXC_PREFIX);
        } // Incorrect number of arguments

        if (context.getStack().size() < NEED_STACK_ELEMENTS) {
            LOGGER.warning(LOG_WARNING_STACK_ELEMENTS);
            throw new NotEnoughElementsException(NEED_STACK_ELEMENTS, context.getStack().size(), EXC_PREFIX);
        }

        double v1 = context.getStack().pop();
        LOGGER.info(LOG_INFO_POP_FIRST);

        double v2 = context.getStack().pop();
        LOGGER.info(LOG_INFO_POP_SECOND);

        if (v2 == 0) {
            LOGGER.warning(LOG_WARNING_DIVIDE_BY_ZERO);
            context.getStack().push(v2);
            LOGGER.info(LOG_INFO_PUSH_SECOND);
            context.getStack().push(v1);
            LOGGER.info(LOG_INFO_PUSH_FIRST);
            throw new DivisionByZeroException(EXC_PREFIX);
        }

        context.getStack().push(v1 / v2);
        LOGGER.info(LOG_INFO_PUSH);
    }

    private static final String CLASS_NAME                  = Division.class.getName();
    private static final Logger LOGGER                      = Logger.getLogger(CLASS_NAME);

    private static final String LOG_INFO_POP_FIRST          = CLASS_NAME + " > execute > POP FIRST ELEMENT";
    private static final String LOG_INFO_POP_SECOND         = CLASS_NAME + " > execute > POP SECOND ELEMENT";
    private static final String LOG_INFO_PUSH               = CLASS_NAME + " > execute > PUSH RESULT";
    private static final String LOG_INFO_PUSH_FIRST         = CLASS_NAME + " > execute > PUSH SECOND ELEMENT";
    private static final String LOG_INFO_PUSH_SECOND        = CLASS_NAME + " > execute > PUSH FIRST ELEMENT";

    private static final String LOG_WARNING_ARGUMENTS_NUMBER = CLASS_NAME + " > execute > WRONG ARGUMENTS NUMBER";
    private static final String LOG_WARNING_DIVIDE_BY_ZERO   = CLASS_NAME + " > execute > WRONG ARGUMENTS NUMBER";
    private static final String LOG_WARNING_STACK_ELEMENTS   = CLASS_NAME + " > execute > NOT ENOUGH ELEMENTS" +
                                                                                        " IN THE STACK";

    private static final int    NEED_STACK_ELEMENTS         = 2;
    private static final int    NEED_ARGS_ELEMENTS          = 0;
    private static final String EXC_PREFIX                  = CLASS_NAME + ": ";
}

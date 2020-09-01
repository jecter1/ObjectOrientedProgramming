package calc.operations;

import calc.exceptions.InvalidArgumentsException;

import java.util.logging.Logger;

public class Push implements Operation {
    @Override
    public void execute(Context context, String ... args)
                throws InvalidArgumentsException {

        if (args.length != NEED_ARGS_ELEMENTS) {
            LOGGER.warning(LOG_WARNING_ARGUMENTS_NUMBER);
            throw new InvalidArgumentsException(EXC_PREFIX);
        } // Incorrect number of arguments

        double val;
        if (context.getMap().containsKey(args[NEED_ARGS_INDEX])) {
            // Argument is name of variable
            LOGGER.info(LOG_INFO_NAME);
            val = context.getMap().get(args[NEED_ARGS_INDEX]);
            LOGGER.info(LOG_INFO_VALUE_FOUND);
        } else {
            try {
                val = Double.parseDouble(args[NEED_ARGS_INDEX]);
                // Argument is value
                LOGGER.info(LOG_INFO_VALUE);
            } catch (NumberFormatException exc) {
                // Argument is incorrect
                LOGGER.warning(LOG_WARNING_SECOND_ARGUMENT);
                throw new InvalidArgumentsException(EXC_PREFIX);
            }
        }
        context.getStack().push(val);
        LOGGER.info(LOG_INFO_PUSH);
    }

    private static final String CLASS_NAME                   = Push.class.getName();
    private static final Logger LOGGER                       = Logger.getLogger(CLASS_NAME);

    private static final String LOG_INFO_PUSH                = CLASS_NAME + " > execute > PUSHED";
    private static final String LOG_INFO_VALUE               = CLASS_NAME + " > execute > 2ND ARGUMENT IS VALUE";
    private static final String LOG_INFO_NAME                = CLASS_NAME + " > execute > 2ND ARGUMENT IS NAME";
    private static final String LOG_INFO_VALUE_FOUND         = CLASS_NAME + " > execute > VARIABLE VALUE WAS FOUND";

    private static final String LOG_WARNING_ARGUMENTS_NUMBER = CLASS_NAME + " > execute > WRONG ARGUMENTS NUMBER";
    private static final String LOG_WARNING_SECOND_ARGUMENT  = CLASS_NAME + " > execute > INCORRECT 2ND ARGUMENT";

    private static final int    NEED_ARGS_ELEMENTS           = 1;
    private static final int    NEED_ARGS_INDEX              = 0;
    private static final String EXC_PREFIX                   = CLASS_NAME + ": ";
}

package calc.operations;

import calc.exceptions.InvalidArgumentsException;

import java.util.logging.Logger;

public class Define implements Operation {
    @Override
    public void execute(Context context, String ... args)
            throws InvalidArgumentsException {

        if (args.length != NEED_ARGS_ELEMENTS) {
            LOGGER.warning(LOG_WARNING_ARGUMENTS_NUMBER);
            throw new InvalidArgumentsException(EXC_PREFIX);
        } // Incorrect number of arguments

        context.getMap().remove(args[NAME_INDEX]);
        LOGGER.info(LOG_INFO_REMOVE);

        char firstSymbol = args[NAME_INDEX].charAt(FIRST_SYMBOL_INDEX);
        if (firstSymbol >= '0' && firstSymbol <= '9') {
            // First argument is incorrect
            LOGGER.warning(LOG_WARNING_INCORRECT_NAME);
            throw new InvalidArgumentsException(EXC_PREFIX);
        }

        // Putting new pair into map
        double val;
        if (context.getMap().containsKey(args[VALUE_INDEX])) {
            // Second argument is name of defined variable
            LOGGER.info(LOG_INFO_NAME);
            val = context.getMap().get(args[VALUE_INDEX]);
            LOGGER.info(LOG_INFO_VALUE_FOUND);
        } else {
            try {
                val = Double.parseDouble(args[VALUE_INDEX]);
                // Second argument is value
                LOGGER.info(LOG_INFO_VALUE);
            } catch (NumberFormatException exc) {
                // Second argument is incorrect
                LOGGER.warning(LOG_WARNING_SECOND_ARGUMENT);
                throw new InvalidArgumentsException(EXC_PREFIX);
            }
        }
        context.getMap().put(args[NAME_INDEX], val);
        LOGGER.info(LOG_INFO_DEFINE);
    }

    private static final String CLASS_NAME                   = Define.class.getName();
    private static final Logger LOGGER                       = Logger.getLogger(CLASS_NAME);

    private static final String LOG_INFO_DEFINE              = CLASS_NAME + " > execute > DEFINED";
    private static final String LOG_INFO_REMOVE              = CLASS_NAME + " > execute > PREVIOUS VALUE WAS REMOVED";
    private static final String LOG_INFO_VALUE               = CLASS_NAME + " > execute > 2ND ARGUMENT IS VALUE";
    private static final String LOG_INFO_NAME                = CLASS_NAME + " > execute > 2ND ARGUMENT IS NAME";
    private static final String LOG_INFO_VALUE_FOUND         = CLASS_NAME + " > execute > VARIABLE VALUE WAS FOUND";

    private static final String LOG_WARNING_ARGUMENTS_NUMBER = CLASS_NAME + " > execute > WRONG ARGUMENTS NUMBER";
    private static final String LOG_WARNING_INCORRECT_NAME   = CLASS_NAME + " > execute > NAME IS INCORRECT";
    private static final String LOG_WARNING_SECOND_ARGUMENT  = CLASS_NAME + " > execute > 2ND ARGUMENT IS INCORRECT";

    private static final int    NEED_ARGS_ELEMENTS           = 2;
    private static final int    NAME_INDEX                   = 0;
    private static final int    FIRST_SYMBOL_INDEX           = 0;
    private static final int    VALUE_INDEX                  = 1;
    private static final String EXC_PREFIX                   = Define.class.getName() + ": ";
}

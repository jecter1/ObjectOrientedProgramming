package calc;

import calc.exceptions.OperationException;
import calc.operations.Context;
import calc.operations.OperationFactory;

import java.io.*;
import java.util.logging.*;
import java.util.Arrays;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        // Setup logger configuration
        try {
            LogManager.getLogManager().readConfiguration(Calculator.class.getResourceAsStream(LOGGER_CONFIG_FILENAME));
        } catch (IOException exc) {
            System.err.println(EXC_PREFIX_CONFIG_LOG + exc);
        }

        LOGGER.info(LOG_INFO_BEGIN);

        if (args.length > ARGS_FILE_LENGTH) {
            System.out.println(USAGE_STRING);
            LOGGER.severe(LOG_SEVERE_USAGE);
            return;
        } // Incorrect usage

        // Opening file/console
        InputStream fin;
        if (args.length == ARGS_FILE_LENGTH) {
            LOGGER.info(LOG_INFO_OPENING_FILE);
            try {
                fin = new FileInputStream(args[ARGS_FILENAME_INDEX]);
            } catch (FileNotFoundException exc) {
                System.err.println(EXC_PREFIX_OPENING_FILE + exc);
                LOGGER.log(Level.SEVERE, LOG_SEVERE_OPENING_FILE, exc);
                return;
            }
            LOGGER.info(LOG_INFO_OPEN_SUCCESS);
        } else {
            fin = System.in;
            LOGGER.info(LOG_INFO_CONSOLE);
        }

        // Initialize context (stack & map)
        Context cont = new Context();
        LOGGER.info(LOG_INFO_CONS_CONTEXT);

        // Creating factory
        OperationFactory factory;
        try {
            factory = new OperationFactory();
        } catch (Exception exc) {
            System.err.println(EXC_PREFIX_CONS_FACTORY + exc);
            LOGGER.log(Level.SEVERE, LOG_SEVERE_CONS_FACTORY, exc);
            return;
        }
        LOGGER.info(LOG_INFO_CONS_FACTORY);

        // Executing operations
        try (Scanner scanner = new Scanner(fin)) {
            while (scanner.hasNextLine()) {
                String[] operationLine = scanner.nextLine().split(OPERATION_SEPARATOR);

                try {
                    LOGGER.info(LOG_INFO_EXECUTING_BEGIN);
                    factory.getOperation(operationLine[OPERATION_NAME_INDEX]).
                            execute(cont, Arrays.copyOfRange(operationLine,
                                                             OPERATION_ARGS_INDEX_FIRST,
                                                             operationLine.length));
                    LOGGER.info(LOG_INFO_EXECUTING_END);
                } catch (OperationException exc) {
                    System.err.println(EXC_PREFIX_OPERATION + exc);
                    LOGGER.log(Level.WARNING, LOG_WARNING_OPERATION, exc);
                } catch (ClassNotFoundException exc) {
                    System.err.println(EXC_PREFIX_UNKNOWN_OP + operationLine[OPERATION_NAME_INDEX]);
                    LOGGER.log(Level.WARNING, LOG_WARNING_UNKNOWN_OP, exc);
                } catch (Exception exc) {
                    System.err.println(EXC_PREFIX_GET_OPERATION + exc);
                    LOGGER.log(Level.SEVERE, LOG_SEVERE_RECEIVING_OP, exc);
                    throw exc;
                }
            }
        } catch (Exception exc) {
            System.err.println(EXC_PREFIX_READING + exc);
            LOGGER.log(Level.SEVERE, LOG_SEVERE_READING_OP, exc);
        }

        LOGGER.info(LOG_INFO_END);
    }

    private static final String CLASS_NAME                 = Calculator.class.getName();
    private static final Logger LOGGER                     = Logger.getLogger(CLASS_NAME);

    private static final String LOG_INFO_BEGIN             = CLASS_NAME + " > main > BEGIN";
    private static final String LOG_INFO_OPENING_FILE      = CLASS_NAME + " > main > OPENING INPUT FILE";
    private static final String LOG_INFO_OPEN_SUCCESS      = CLASS_NAME + " > main > INPUT FILE WAS OPENED";
    private static final String LOG_INFO_CONSOLE           = CLASS_NAME + " > main > STDIN WAS CHOSEN";
    private static final String LOG_INFO_CONS_CONTEXT      = CLASS_NAME + " > main > CONTEXT WAS CONSTRUCTED";
    private static final String LOG_INFO_CONS_FACTORY      = CLASS_NAME + " > main > FACTORY WAS CONSTRUCTED";
    private static final String LOG_INFO_EXECUTING_BEGIN   = CLASS_NAME + " > main > ATTEMPT TO EXECUTE OPERATION";
    private static final String LOG_INFO_EXECUTING_END     = CLASS_NAME + " > main > OPERATION WAS EXECUTED";
    private static final String LOG_INFO_END               = CLASS_NAME + " > main > END";

    private static final String LOG_SEVERE_USAGE           = CLASS_NAME + " > main > INCORRECT USAGE";
    private static final String LOG_SEVERE_OPENING_FILE    = CLASS_NAME + " > main > COULDN'T OPEN INPUT FILE";
    private static final String LOG_SEVERE_CONS_FACTORY    = CLASS_NAME + " > main > COULDN'T CONSTRUCT FACTORY";
    private static final String LOG_SEVERE_RECEIVING_OP    = CLASS_NAME + " > main > COULDN'T RECEIVE OPERATION";
    private static final String LOG_SEVERE_READING_OP      = CLASS_NAME + " > main > COULDN'T READ OPERATION";

    private static final String LOG_WARNING_OPERATION      = CLASS_NAME + " > main > COULDN'T EXECUTE OPERATION";
    private static final String LOG_WARNING_UNKNOWN_OP     = CLASS_NAME + " > main > COULDN'T FIND OPERATION";

    private static final String EXC_PREFIX_CONFIG_LOG      = "Couldn't setup logger configuration: ";
    private static final String EXC_PREFIX_CONS_FACTORY    = "Constructing factory: ";
    private static final String EXC_PREFIX_GET_OPERATION   = "Getting operation: ";
    private static final String EXC_PREFIX_UNKNOWN_OP      = "Unknown operation: ";
    private static final String EXC_PREFIX_OPERATION       = "Executing ";
    private static final String EXC_PREFIX_READING         = "Reading operation: ";
    private static final String EXC_PREFIX_OPENING_FILE    = "Opening input file: ";

    private static final String LOGGER_CONFIG_FILENAME     = "../../logging.properties";

    private static final String OPERATION_SEPARATOR        = " ";

    private static final int    OPERATION_NAME_INDEX       = 0;
    private static final int    OPERATION_ARGS_INDEX_FIRST = 1;

    private static final int    ARGS_FILE_LENGTH           = 1;
    private static final int    ARGS_FILENAME_INDEX        = 0;

    private static final String USAGE_STRING               = "Usage:\n" +
                                                             "\tjava " + CLASS_NAME + " 'filename'\n" +
                                                             "OR\n" +
                                                             "\tjava " + CLASS_NAME;
}
package calc.operations;

import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.logging.*;

public class OperationFactory {
    private final Map<String, String> m_opClassName; // Pairs (usableName, className)

    public OperationFactory() throws Exception {
        LOGGER.info(LOG_INFO_CONS_BEGIN);

        m_opClassName = new TreeMap<>();
        LOGGER.info(LOG_INFO_CONS_MAP);

        try (Scanner configFile = new Scanner(getClass().getResourceAsStream(CONFIG_FILENAME))) {
            // Fill in map using the configuration file
            while (configFile.hasNextLine()) {
                String[] opInfo = configFile.nextLine().split(CONFIG_SEPARATOR);
                m_opClassName.put(opInfo[USABLE_NAME_INDEX], opInfo[CLASS_NAME_INDEX]);
            }
        }
        LOGGER.info(LOG_INFO_MAP_FILLED);
        LOGGER.info(LOG_INFO_CONS_END);
    }

    // Converts a usable name to a new class instance
    public Operation getOperation(String name) throws Exception {
        return (Operation) Class.forName(this.getClass().getPackageName() +
                PACKAGE_SEPARATOR + m_opClassName.get(name)).getConstructor().newInstance();
    }

    private static final String CLASS_NAME               = OperationFactory.class.getName();
    private static final Logger LOGGER                   = Logger.getLogger(CLASS_NAME);

    private static final String LOG_INFO_CONS_BEGIN      = CLASS_NAME + " > constructor > BEGIN";
    private static final String LOG_INFO_CONS_MAP        = CLASS_NAME + " > constructor > MAP WAS CREATED";
    private static final String LOG_INFO_MAP_FILLED      = CLASS_NAME + " > constructor > MAP WAS FILLED";
    private static final String LOG_INFO_CONS_END        = CLASS_NAME + " > constructor > END";

    private static final int    USABLE_NAME_INDEX        = 0;
    private static final int    CLASS_NAME_INDEX         = 1;

    private static final String CONFIG_SEPARATOR         = ";";
    private static final String PACKAGE_SEPARATOR        = ".";
    private static final String CONFIG_FILENAME          = "Config";
}
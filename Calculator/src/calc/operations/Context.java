package calc.operations;

import java.util.Stack;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

public class Context {
    private final Map<String, Double> m_map; // Pairs (variable name, value)
    private final Stack<Double>       m_stack;

    public Context() {
        m_map   = new TreeMap<>();
        LOGGER.info(LOG_INFO_CONS_MAP);
        m_stack = new Stack<>();
        LOGGER.info(LOG_INFO_CONS_STACK);
    }

    public Map<String, Double> getMap() { return m_map; }
    public Stack<Double> getStack() { return m_stack; }

    private static final String CLASS_NAME          = Context.class.getName();
    private static final Logger LOGGER              = Logger.getLogger(CLASS_NAME);

    private static final String LOG_INFO_CONS_MAP   = CLASS_NAME + " > constructor > MAP WAS CREATED";
    private static final String LOG_INFO_CONS_STACK = CLASS_NAME + " > constructor > STACK WAS CREATED";
}
package tests.operations;

import calc.exceptions.*;
import calc.operations.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

public class PushTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(timeout = 200)
    public void testExecuteArgumentsNumberException1() throws OperationException {
        Context context = new Context();
        Operation operation = new Push();

        thrown.expect(InvalidArgumentsException.class);
        operation.execute(context);
    }

    @Test(timeout = 200)
    public void testExecuteArgumentsNumberException2() throws OperationException {
        Context context = new Context();
        Operation operation = new Push();

        thrown.expect(InvalidArgumentsException.class);
        operation.execute(context, DEFAULT_MANY_ARGS);
    }

    @Test(timeout = 200)
    public void testExecuteUnknownNameException() throws OperationException {
        Context context = new Context();
        Operation operation = new Push();

        thrown.expect(InvalidArgumentsException.class);
        operation.execute(context, DEFAULT_VAR_NAME);
    }

    @Test(timeout = 200)
    public void testExecuteWithName() throws OperationException {
        Context context = new Context();
        Operation operation = new Push();

        context.getMap().put(DEFAULT_VAR_NAME, DEFAULT_VALUE);

        operation.execute(context, DEFAULT_VAR_NAME);
        double actual = context.getStack().peek();

        Assert.assertEquals(DEFAULT_VALUE, actual, DEFAULT_DELTA);
    }

    @Test(timeout = 200)
    public void testExecuteWithValue() throws OperationException {
        Context context = new Context();
        Operation operation = new Push();

        operation.execute(context, DEFAULT_VALUE_STR);
        double actual = context.getStack().peek();

        Assert.assertEquals(DEFAULT_VALUE, actual, DEFAULT_DELTA);
    }

    private final static double   DEFAULT_DELTA     = 1e-10;
    private final static double   DEFAULT_VALUE     = 4.7;
    private final static String   DEFAULT_VALUE_STR = "4.7";
    private final static String   DEFAULT_VAR_NAME  = "abracadabra";
    private final static String[] DEFAULT_MANY_ARGS = {"there", "are", "too", "many", "arguments"};
}
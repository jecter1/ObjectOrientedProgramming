package tests.operations;

import calc.exceptions.*;
import calc.operations.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

public class DefineTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(timeout = 200)
    public void testExecuteArgumentsNumberException1() throws OperationException {
        Context context = new Context();
        Operation operation = new Define();

        thrown.expect(InvalidArgumentsException.class);
        operation.execute(context);
    }

    @Test(timeout = 200)
    public void testExecuteArgumentsNumberException2() throws OperationException {
        Context context = new Context();
        Operation operation = new Define();

        thrown.expect(InvalidArgumentsException.class);
        operation.execute(context, DEFAULT_MANY_ARGS);
    }

    @Test(timeout = 200)
    public void testExecuteIncorrectNameException() throws OperationException {
        Context context = new Context();
        Operation operation = new Define();

        thrown.expect(InvalidArgumentsException.class);
        operation.execute(context, INCORRECT_VAR_NAME, DEFAULT_VALUE_STR);
    }

    @Test(timeout = 200)
    public void testExecuteUnknownNameException() throws OperationException {
        Context context = new Context();
        Operation operation = new Define();

        thrown.expect(InvalidArgumentsException.class);
        operation.execute(context, DEFAULT_VAR_NAME1, DEFAULT_VAR_NAME2);
    }

    @Test(timeout = 200)
    public void testExecute() throws OperationException {
        Context context = new Context();
        Operation operation = new Define();

        operation.execute(context, DEFAULT_VAR_NAME1, DEFAULT_VALUE_STR);
        operation.execute(context, DEFAULT_VAR_NAME2, DEFAULT_VAR_NAME1);
        operation.execute(context, DEFAULT_VAR_NAME3, DEFAULT_VAR_NAME2);

        Assert.assertEquals(context.getMap().get(DEFAULT_VAR_NAME3), DEFAULT_VALUE, DEFAULT_DELTA);
        Assert.assertEquals(context.getMap().get(DEFAULT_VAR_NAME2), DEFAULT_VALUE, DEFAULT_DELTA);
        Assert.assertEquals(context.getMap().get(DEFAULT_VAR_NAME1), DEFAULT_VALUE, DEFAULT_DELTA);
    }

    private final static String   INCORRECT_VAR_NAME = "34abc";
    private final static double   DEFAULT_DELTA      = 1e-10;
    private final static double   DEFAULT_VALUE      = 4.7;
    private final static String   DEFAULT_VALUE_STR  = "4.7";
    private final static String   DEFAULT_VAR_NAME1  = "abracadabra";
    private final static String   DEFAULT_VAR_NAME2  = "secondVariable";
    private final static String   DEFAULT_VAR_NAME3  = "th!rd_v@R!@b13";
    private final static String[] DEFAULT_MANY_ARGS  = {"there", "are", "too", "many", "arguments"};
}
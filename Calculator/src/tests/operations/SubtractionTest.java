package tests.operations;

import calc.exceptions.*;
import calc.operations.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

public class SubtractionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(timeout = 200)
    public void testExecuteElementsException() throws OperationException {
        Context context = new Context();
        Operation operation = new Subtraction();

        thrown.expect(NotEnoughElementsException.class);
        operation.execute(context);
    }

    @Test(timeout = 200)
    public void testExecuteArgumentsException() throws OperationException {
        Context context = new Context();
        Operation operation = new Subtraction();

        thrown.expect(InvalidArgumentsException.class);
        operation.execute(context, DEFAULT_ARGUMENT);
    }

    @Test(timeout = 200)
    public void testExecute() throws OperationException {
        Context context = new Context();
        Operation operation = new Subtraction();

        context.getStack().push(DEFAULT_VALUE1);
        context.getStack().push(DEFAULT_VALUE2);
        operation.execute(context);
        double actual = context.getStack().peek();

        Assert.assertEquals(DEFAULT_EXPECTED, actual, DEFAULT_DELTA);
    }

    private final static double DEFAULT_VALUE1   = 3.4;
    private final static double DEFAULT_VALUE2   = 6.6;
    private final static double DEFAULT_EXPECTED = 3.2;
    private final static double DEFAULT_DELTA    = 1e-10;
    private final static String DEFAULT_ARGUMENT = "argument";
}
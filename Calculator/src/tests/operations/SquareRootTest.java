package tests.operations;

import calc.exceptions.*;
import calc.operations.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

public class SquareRootTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(timeout = 200)
    public void testExecuteElementsException() throws OperationException {
        Context context = new Context();
        Operation operation = new SquareRoot();

        thrown.expect(NotEnoughElementsException.class);
        operation.execute(context);
    }

    @Test(timeout = 200)
    public void testExecuteArgumentsException() throws OperationException {
        Context context = new Context();
        Operation operation = new SquareRoot();

        thrown.expect(InvalidArgumentsException.class);
        operation.execute(context, DEFAULT_ARGUMENT);
    }

    @Test(timeout = 200)
    public void testExecuteNegativeException() throws OperationException {
        Context context = new Context();
        Operation operation = new SquareRoot();

        context.getStack().push(NEGATIVE_VALUE);

        thrown.expect(NegativeSquareRootException.class);
        operation.execute(context);
    }

    @Test(timeout = 200)
    public void testExecute() throws OperationException {
        Context context = new Context();
        Operation operation = new SquareRoot();

        context.getStack().push(DEFAULT_VALUE);
        operation.execute(context);
        double actual = context.getStack().peek();

        Assert.assertEquals(DEFAULT_EXPECTED, actual, DEFAULT_DELTA);
    }

    private final static double NEGATIVE_VALUE   = -9.0;
    private final static double DEFAULT_VALUE    = 9.0;
    private final static double DEFAULT_EXPECTED = 3.0;
    private final static double DEFAULT_DELTA    = 1e-10;
    private final static String DEFAULT_ARGUMENT = "argument";
}
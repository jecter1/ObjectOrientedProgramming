package tests.operations;

import calc.exceptions.*;
import calc.operations.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

public class PrintTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(timeout = 200)
    public void testExecuteElementsException() throws OperationException {
        Context context = new Context();
        Operation operation = new Print();

        thrown.expect(NotEnoughElementsException.class);
        operation.execute(context);
    }

    @Test(timeout = 200)
    public void testExecuteArgumentsException() throws OperationException {
        Context context = new Context();
        Operation operation = new Print();

        thrown.expect(InvalidArgumentsException.class);
        operation.execute(context, DEFAULT_ARGUMENT);
    }

    @Test(timeout = 200)
    public void testExecute() throws OperationException {
        Context context = new Context();
        Operation operation = new Print();

        context.getStack().push(DEFAULT_VALUE);
        operation.execute(context);

        Assert.assertEquals(DEFAULT_EXPECTED, context.getStack().size());
        Assert.assertEquals(DEFAULT_VALUE, context.getStack().peek(), DEFAULT_DELTA);
    }

    private final static double DEFAULT_VALUE    = 7125.2;
    private final static int    DEFAULT_EXPECTED = 1;
    private final static double DEFAULT_DELTA    = 1e-10;
    private final static String DEFAULT_ARGUMENT = "argument";
}
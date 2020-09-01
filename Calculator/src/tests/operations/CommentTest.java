package tests.operations;

import calc.exceptions.*;
import calc.operations.*;
import org.junit.*;

public class CommentTest {
    @Test(timeout = 200)
    public void testExecute()  throws OperationException {
        Context context = new Context();
        Operation operation = new Comment();

        operation.execute(context);
        operation.execute(context, DEFAULT_MANY_ARGS);
    }

    private final static String[] DEFAULT_MANY_ARGS = {"there", "are", "too", "many", "arguments"};
}
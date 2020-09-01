package calc.operations;

import calc.exceptions.OperationException;

@FunctionalInterface
public interface Operation {
    void execute(Context context, String ... args) throws OperationException;
}

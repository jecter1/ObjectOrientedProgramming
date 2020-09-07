package bot.exceptions;

public class IncorrectDebtInfoException extends Exception {
    @Override
    public String toString() {
        return TEXT_EXCEPTION;
    }

    private final static String TEXT_EXCEPTION = IncorrectDebtInfoException.class.getSimpleName();
}

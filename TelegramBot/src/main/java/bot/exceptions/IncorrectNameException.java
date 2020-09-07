package bot.exceptions;

public class IncorrectNameException extends IncorrectDebtInfoException {
    @Override
    public String toString() {
        return TEXT_EXCEPTION;
    }

    private final static String TEXT_EXCEPTION = IncorrectNameException.class.getSimpleName();
}

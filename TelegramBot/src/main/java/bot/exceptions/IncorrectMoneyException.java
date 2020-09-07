package bot.exceptions;

public class IncorrectMoneyException extends IncorrectDebtInfoException {
    @Override
    public String toString() {
        return TEXT_EXCEPTION;
    }

    private final static String TEXT_EXCEPTION = IncorrectMoneyException.class.getSimpleName();
}

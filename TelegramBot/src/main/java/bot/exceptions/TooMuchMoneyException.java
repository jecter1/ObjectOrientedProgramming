package bot.exceptions;

public class TooMuchMoneyException extends Exception {
    @Override
    public String toString() {
        return TEXT_EXCEPTION;
    }

    private final static String TEXT_EXCEPTION = TooMuchMoneyException.class.getSimpleName();
}

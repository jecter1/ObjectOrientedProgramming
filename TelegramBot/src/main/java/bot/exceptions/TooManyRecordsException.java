package bot.exceptions;

public class TooManyRecordsException extends Exception {
    @Override
    public String toString() {
        return TEXT_EXCEPTION;
    }

    private final static String TEXT_EXCEPTION = TooManyRecordsException.class.getSimpleName();
}

package bot.exceptions;

public class NameDoesNotExistException extends Exception {
    @Override
    public String toString() {
        return TEXT_EXCEPTION;
    }

    private final static String TEXT_EXCEPTION = NameDoesNotExistException.class.getSimpleName();
}

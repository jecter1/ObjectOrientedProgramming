package bot.exceptions;

public class StorageException extends Exception {
    @Override
    public String toString() {
        return TEXT_EXCEPTION;
    }

    private final static String TEXT_EXCEPTION = StorageException.class.getSimpleName();
}

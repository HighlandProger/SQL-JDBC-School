package ua.com.foxminded.exception;

public class ReadFileException extends RuntimeException {

    private static final long serialVersionUID = -4535447493204481057L;

    public ReadFileException(String message, Exception cause) {
        super(message, cause);
    }

    public ReadFileException(String message) {
        super(message);
    }
}

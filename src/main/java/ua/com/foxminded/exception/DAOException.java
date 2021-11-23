package ua.com.foxminded.exception;

public class DAOException extends RuntimeException {

    private static final long serialVersionUID = -944581676969714800L;

    public DAOException(String message, Exception cause) {
        super(message, cause);
    }

    public DAOException(String message) {
        super(message);
    }

}

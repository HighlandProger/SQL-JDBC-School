package ua.com.foxminded.dao;

@SuppressWarnings("serial")
public class DAOException extends Exception {

    public DAOException(){
        super();
    }

    public DAOException(String message, Exception cause) {
        super(message, cause);
    }

    public DAOException(String message){
        super(message);
    }

}

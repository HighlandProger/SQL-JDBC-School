package ua.com.foxminded.dao;

import ua.com.foxminded.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory {

    private static DAOFactory instance;
    private Connection connection;

    private DAOFactory(){}

    public static DAOFactory getInstance(){

        if (instance == null){
            instance = new DAOFactory();
        }

        return instance;
    }
    public Connection getConnection() throws DAOException{

        if ( connection == null ) {
            try {
                connection = DriverManager.getConnection(Properties.URL,
                    Properties.USER, Properties.PASSWORD);
            } catch (SQLException e) {
                throw new DAOException("No connection", e);
            }
        }
        return connection;
    }
}

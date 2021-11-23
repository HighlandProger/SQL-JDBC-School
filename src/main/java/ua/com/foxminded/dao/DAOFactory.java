package ua.com.foxminded.dao;

import ua.com.foxminded.exception.DAOException;
import ua.com.foxminded.exception.ReadFileException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {

    private static final Properties PROPERTIES = getProperties();
    private static final String PROPERTIES_FILE_NAME = "db.properties";
    private static final String URL = "url";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static DAOFactory instance;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    private static Properties getProperties() {

        Properties properties = new Properties();

        try (InputStream inputStream = DAOFactory.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            if (inputStream == null) {
                throw new IOException("Cannot read database properties " + DAOFactory.class.getName() + " " + PROPERTIES_FILE_NAME);
            }
            properties.load(inputStream);

        } catch (IOException e) {
            throw new ReadFileException("Error during reading property file");
        }

        return properties;
    }

    public Connection getConnection() throws SQLException {

        String url = PROPERTIES.getProperty(URL);
        String user = PROPERTIES.getProperty(USER);
        String password = PROPERTIES.getProperty(PASSWORD);

        if (url == null || user == null || password == null) {
            throw new DAOException("Cannot establish connection. One of the connection parameters url, user or password is null");
        }

        return DriverManager.getConnection(url, user, password);
    }
}

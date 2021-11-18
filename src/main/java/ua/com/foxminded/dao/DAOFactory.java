package ua.com.foxminded.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {

    private static DAOFactory instance;
    private final String url = getValueFromProperties("URL");
    private final String user = getValueFromProperties("user");
    private final String password = getValueFromProperties("password");

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {

        Connection connection = null;

        if (url != null && user != null && password != null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        } else {
            throw new DAOException("Property field is empty");
        }
        return connection;
    }

    private String getValueFromProperties(String key) {

        String value = null;
        Properties properties = new Properties();
        try (InputStream inputStream = DAOFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (inputStream == null) {
                return null;
            }

            properties.load(inputStream);
            value = properties.getProperty(key);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }

}

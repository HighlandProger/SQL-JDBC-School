package ua.com.foxminded.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {

    private final String url = getValueFromProperties("URL");
    private final String user = getValueFromProperties("user");
    private final String password = getValueFromProperties("password");
    private Connection connection;

    private DAOFactory() {
    }

    public static DAOFactory getInstance(){
        return new DAOFactory();
    }

    public Connection getConnection() throws SQLException {

            try {
                if (url == null || user == null || password == null) {
                    throw new DAOException("Property field is empty");
                }
                connection = DriverManager.getConnection(url, user, password);
            } catch (DAOException e) {
                e.printStackTrace();
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

package ua.com.foxminded.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {

    private static final String URL = getValueFromProperties("URL");
    private static final String USER = getValueFromProperties("user");
    private static final String PASSWORD = getValueFromProperties("password");
    private static Connection connection;

    private DAOFactory() {
    }

    public static Connection getConnection() throws DAOException {

        if (URL != null && USER != null && PASSWORD != null) {

            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new DAOException("No connection", e);
            } finally {
                if (connection != null) {
                    closeConnection(connection);
                }
            }
        }

        return connection;
    }

    private static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getValueFromProperties(String key) {

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

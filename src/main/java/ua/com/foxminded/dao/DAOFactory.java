package ua.com.foxminded.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DAOFactory {

    private static final Map<String, String> PROPERTIES_MAP = getPropertiesMap();
    private static final String URL = "URL";
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

    private static Map<String, String> getPropertiesMap() {

        Map<String, String> propertiesMap = new HashMap<>();
        Properties properties = new Properties();
        try (InputStream inputStream = DAOFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (inputStream == null) {
                throw new IOException("Cannot read properties");
            }
            properties.load(inputStream);
            for (String key : properties.stringPropertyNames()) {
                propertiesMap.put(key, properties.getProperty(key));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return propertiesMap;
    }

    public Connection getConnection() throws SQLException {

        Connection connection = null;
        String url = PROPERTIES_MAP.get(URL);
        String user = PROPERTIES_MAP.get(USER);
        String password = PROPERTIES_MAP.get(PASSWORD);

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
}

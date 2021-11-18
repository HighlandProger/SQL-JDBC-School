package ua.com.foxminded.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlRunner {

    private static final String TABLES_SQL_FILE_NAME = "init.db";

    public void createTables() {

        try (Connection connection = DAOFactory.getInstance().getConnection()) {
            prepareStatements(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareStatements(Connection connection) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TABLES_SQL_FILE_NAME);
        if (inputStream == null) {
            throw new IOException("Cannot get input stream from init.db");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while (reader.ready()) {
            try (PreparedStatement statement = connection.prepareStatement((reader.readLine()))) {
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException("Cannot create tables", e);
            }
        }
    }

}

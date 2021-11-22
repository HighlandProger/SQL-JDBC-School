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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getTablesSql() {

        StringBuilder sql = new StringBuilder();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TABLES_SQL_FILE_NAME)) {
            if (inputStream == null) {
                throw new IOException("Cannot get resource file stream");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (reader.ready()) {
                sql.append(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sql.toString();
    }

    private void prepareStatements(Connection connection) {

        String tablesSql = getTablesSql();

        try (PreparedStatement statement = connection.prepareStatement((tablesSql))) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot create tables", e);
        }

    }

}

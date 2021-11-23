package ua.com.foxminded.dao;

import ua.com.foxminded.exception.DAOException;
import ua.com.foxminded.exception.ReadFileException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.MissingResourceException;

public class SqlRunner {

    private static final String TABLES_SQL_FILE_NAME = "init.db";

    public void createTables() {

        String sql = readFile();

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot create tables", e);
        }
    }

    private String readFile() {

        StringBuilder output = new StringBuilder();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TABLES_SQL_FILE_NAME)) {
            if (inputStream == null) {
                throw new MissingResourceException("Cannot read file", SqlRunner.class.getName(), TABLES_SQL_FILE_NAME);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (reader.ready()) {
                output.append(reader.readLine());
            }
        } catch (IOException e) {
            throw new ReadFileException("Error during reading file");
        }
        return output.toString();

    }

}

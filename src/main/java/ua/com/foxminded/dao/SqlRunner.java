package ua.com.foxminded.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class SqlRunner {

    private static final String STUDENTS_INIT_KEY = "students";
    private static final String GROUPS_INIT_KEY = "groups";
    private static final String COURSES_INIT_KEY = "courses";

    public static void main(String[] args) {

        SqlRunner runner = new SqlRunner();
        runner.createTables();
    }

    private void createTables() {

        createTableByKey(STUDENTS_INIT_KEY);
        createTableByKey(GROUPS_INIT_KEY);
        createTableByKey(COURSES_INIT_KEY);
    }

    void createTableByKey(String key) {

        try (Connection connection = DAOFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(getTableSQLString(key))) {
            statement.executeUpdate();
        } catch (DAOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private String getTableSQLString(String tableName) {

        String sql = null;
        Properties properties = new Properties();
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("init.db")) {
            if (inputStream == null) {
                return null;
            }

            properties.load(inputStream);
            sql = properties.getProperty(tableName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sql;
    }

}

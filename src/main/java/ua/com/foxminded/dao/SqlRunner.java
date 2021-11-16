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
    private final DAOFactory daoFactory = DAOFactory.getInstance();

    public void createTables() {

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement groupsStatement = connection.prepareStatement(getTableSQL(GROUPS_INIT_KEY));
             PreparedStatement coursesStatement = connection.prepareStatement(getTableSQL(COURSES_INIT_KEY));
             PreparedStatement studentsStatement = connection.prepareStatement(getTableSQL(STUDENTS_INIT_KEY))
             )
        {
            groupsStatement.executeUpdate();
            coursesStatement.executeUpdate();
            studentsStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getTableSQL(String tableName) {

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

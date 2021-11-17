package ua.com.foxminded.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class SqlRunner {

    private static final String DELETE_GROUPS_INIT_KEY = "deleteGroups";
    private static final String GROUPS_INIT_KEY = "groups";
    private static final String DELETE_COURSES_INIT_KEY = "deleteCourses";
    private static final String COURSES_INIT_KEY = "courses";
    private static final String DELETE_STUDENTS_INIT_KEY = "deleteStudents";
    private static final String STUDENTS_INIT_KEY = "students";
    private final DAOFactory daoFactory = DAOFactory.getInstance();

    public void createTables() {

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement deleteGroupsStatement = connection.prepareStatement(getSQL(DELETE_GROUPS_INIT_KEY));
             PreparedStatement groupsStatement = connection.prepareStatement(getSQL(GROUPS_INIT_KEY));
             PreparedStatement deleteCoursesStatement = connection.prepareStatement(getSQL(DELETE_COURSES_INIT_KEY));
             PreparedStatement coursesStatement = connection.prepareStatement(getSQL(COURSES_INIT_KEY));
             PreparedStatement deleteStudentsStatement = connection.prepareStatement(getSQL(DELETE_STUDENTS_INIT_KEY));
             PreparedStatement studentsStatement = connection.prepareStatement(getSQL(STUDENTS_INIT_KEY))
        ) {
            deleteGroupsStatement.executeUpdate();
            groupsStatement.executeUpdate();
            deleteCoursesStatement.executeUpdate();
            coursesStatement.executeUpdate();
            deleteStudentsStatement.executeUpdate();
            studentsStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getSQL(String sqlKey) {

        String sql = null;
        Properties properties = new Properties();
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("init.db")) {
            if (inputStream == null) {
                return null;
            }

            properties.load(inputStream);
            sql = properties.getProperty(sqlKey);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sql;
    }

}

package ua.com.foxminded.dao.postgresql;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.DAOFactory;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSqlCourseDAO implements CourseDAO {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    @Override
    public Course create(Course course) {

        String sql = "INSERT INTO courses (name, description) VALUES (?, ?) RETURNING courses.*;";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setStatementParameters(statement, course.getName(), course.getDescription());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.next()) {
                throw new DAOException("Course is not found");
            }
            return createCourseFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Cannot create course", e);
        }
    }

    @Override
    public List<Course> getAll() {

        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses;";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                courses.add(createCourseFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            throw new DAOException("Cannot get courses", e);
        }

        return courses;
    }

    @Override
    public Course getById(long id) {

        String sql = "SELECT * FROM courses WHERE id = ?;";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DAOException("Course is not found");
            }
            return createCourseFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Cannot get course by id", e);
        }
    }


    private void setStatementParameters(PreparedStatement statement,
                                        String name, String description) throws SQLException {
        statement.setString(1, name);
        statement.setString(2, description);
    }

    private Course createCourseFromResultSet(ResultSet resultSet) throws SQLException {
        return new Course(
            resultSet.getInt(ID),
            resultSet.getString(NAME),
            resultSet.getString(DESCRIPTION));
    }

}

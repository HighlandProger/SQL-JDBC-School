package ua.com.foxminded.dao.postgres;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.DAOException;
import ua.com.foxminded.dao.DAOFactory;
import ua.com.foxminded.domain.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresSqlCourseDAO implements CourseDAO {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    @Override
    public void create(Course course) {

        String sql = "INSERT INTO courses (name, description) VALUES (?, ?);";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setStatementParameters(statement, course.getName(), course.getDescription());
            statement.execute();
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
            throw new DAOException("Cannot get groups", e);
        }

        return courses;
    }

    @Override
    public Course getById(long id) {

        String sql = "SELECT * FROM courses WHERE id = ?;";

        Course course = null;

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                course = createCourseFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot get course by id", e);
        }

        if (course == null) {
            throw new DAOException("Group is not found");
        }

        return course;
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

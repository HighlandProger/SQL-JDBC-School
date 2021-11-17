package ua.com.foxminded.dao.postgres;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.DAOException;
import ua.com.foxminded.dao.DAOFactory;
import ua.com.foxminded.domain.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresSqlCourseDAO implements CourseDAO {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private final DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public void create(String name, String description) {

        String sql = "INSERT INTO courses (name, description) VALUES (?, ?);";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setStatementParameters(statement, name, description);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Course getById(long id) throws DAOException {

        String sql = "SELECT * FROM courses WHERE id = ?;";

        ResultSet resultSet = null;
        Course course = null;

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                course = createCourseFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

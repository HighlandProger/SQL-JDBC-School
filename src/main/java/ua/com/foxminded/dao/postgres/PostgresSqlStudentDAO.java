package ua.com.foxminded.dao.postgres;

import ua.com.foxminded.dao.DAOException;
import ua.com.foxminded.dao.DAOFactory;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.domain.Student;

import java.sql.*;

public class PostgresSqlStudentDAO implements StudentDAO {

    private static final String ID = "id";
    private static final String GROUP_ID = "groupId";
    private static final String NAME = "name";
    private static final String LAST_NAME = "lastName";
    private final DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public void create(String name, String lastName) {

        String sql = "INSERT INTO STUDENTS (name, lastName) VALUES (?,?);";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setStatementParameters(statement, name, lastName);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student getById(long id) throws DAOException {

        String sql = "SELECT * FROM students WHERE id = ?;";

        ResultSet resultSet = null;
        Student student = null;

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                student = createStudentFromResultSet(resultSet);
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

        if (student == null) {
            throw new DAOException("Student with id = " + id + " is not found");
        }

        return student;
    }

    private void setStatementParameters(PreparedStatement statement,
                                        String name, String lastName) throws SQLException {
        statement.setString(1, name);
        statement.setString(2, lastName);
    }

    private Student createStudentFromResultSet(ResultSet resultSet) throws SQLException {
        return new Student(
            resultSet.getInt(ID),
            resultSet.getInt(GROUP_ID),
            resultSet.getString(NAME),
            resultSet.getString(LAST_NAME));
    }

}

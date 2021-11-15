package ua.com.foxminded.dao.postgres;

import org.apache.log4j.Logger;
import ua.com.foxminded.dao.DAOException;
import ua.com.foxminded.dao.DAOFactory;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.domain.Student;

import java.sql.*;

public class PostgresSqlStudentDao implements StudentDAO {

    private static final Logger log = Logger.getLogger(PostgresSqlStudentDao.class.getName());
    private static final String ID = "id";
    private static final String GROUP_ID = "groupId";
    private static final String NAME = "name";
    private static final String LAST_NAME = "lastName";

    @Override
    public Student createStudent(int id, int groupId, String name, String lastName) throws DAOException {

        log.info("Creating new student with id = " + id);
        String sql = "INSERT INTO STUDENTS (id, groupId, name, lastName) VALUES (?,?,?,?);";

        Student student = null;

        try (Connection connection = DAOFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             ResultSet resultSet = statement.getGeneratedKeys()) {
            setStatementParameters(statement, id, groupId, name, lastName);
            statement.execute();
            resultSet.next();
            student = createStudentFromResultSet(resultSet);
        } catch (SQLException e) {
            userCreateExceptionLog(e);
        }
        log.trace("Returning student");
        return student;
    }

    @Override
    public Student getStudentById(int id) throws DAOException {

        log.trace("Looking for student with id = " + id);
        String sql = "SELECT * FROM STUDENTS WHERE id = ?;";

        Student student = null;

        try (Connection connection = DAOFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.getGeneratedKeys()) {
            statement.setInt(1, id);
            statement.executeQuery();
            if (resultSet.next()) {
                student = createStudentFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            userCreateExceptionLog(e);
        }

        if (student == null) {
            log.debug("Student is not found");
        } else {
            log.trace("Student with id = " + id + " is found");
        }
        log.trace("Returning student");
        return student;

    }

    @Override
    public Student updateStudent(int id, int groupId, String name, String lastName) throws DAOException {

        String sql = "UPDATE STUDENTS SET groupId = ?, name = ?, lastName = ?, WHERE id = ?;";

        Student student = null;

        try (Connection connection = DAOFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.getGeneratedKeys()) {
            statement.setInt(1, groupId);
            statement.setString(2, name);
            statement.setString(3, lastName);
            statement.setInt(4, id);
            statement.execute();
            resultSet.next();
            student = createStudentFromResultSet(resultSet);

        } catch (SQLException e) {
            userCreateExceptionLog(e);
        }
        log.trace("Student with id = " + id + " was updated");
        log.trace("Returning updated student");
        return student;

    }

    @Override
    public void deleteStudentById(int id) {
        log.trace("ToDo");
    }

    private void userCreateExceptionLog(Exception e) throws DAOException {
        log.error("Cannot create user", e);
        throw new DAOException("Cannot create user", e);
    }

    private void setStatementParameters(PreparedStatement statement,
                                        int id, int groupId, String name, String lastName) throws SQLException {
        statement.setInt(1, id);
        statement.setInt(2, groupId);
        statement.setString(3, name);
        statement.setString(4, lastName);
    }

    private Student createStudentFromResultSet(ResultSet resultSet) throws SQLException {
        return new Student(
            resultSet.getInt(ID),
            resultSet.getInt(GROUP_ID),
            resultSet.getString(NAME),
            resultSet.getString(LAST_NAME));
    }

}

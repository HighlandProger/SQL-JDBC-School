package ua.com.foxminded.dao.postgres;

import org.postgresql.util.PSQLException;
import ua.com.foxminded.dao.DAOFactory;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresSqlStudentDAO implements StudentDAO {

    private static final String ID = "id";
    private static final String GROUP_ID = "group_Id";
    private static final String NAME = "name";
    private static final String LAST_NAME = "lastName";

    @Override
    public Student create(Student student) {

        String sql = "INSERT INTO STUDENTS (name, lastName) VALUES (?,?)  RETURNING students.*;;";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setStatementParameters(statement, student.getName(), student.getLastName());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.next()) {
                throw new DAOException("Student is not found");
            }
            return createStudentFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Cannot create student", e);
        }
    }

    @Override
    public void assignToGroup(Student student, Group group) {

        String sql = "UPDATE students SET group_id = ? WHERE id = ?;";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, group.getId());
            statement.setLong(2, student.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Cannot update student", e);
        }
    }

    @Override
    public List<Student> getAll() {

        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students;";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                students.add(createStudentFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            throw new DAOException("Cannot get students", e);
        }

        return students;
    }

    @Override
    public Student getById(long id) {

        String sql = "SELECT * FROM students WHERE id = ?;";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DAOException("Student is not found");
            }
            return createStudentFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Cannot get student by id", e);
        }
    }

    @Override
    public void delete(long id) {

        String sql = "DELETE FROM students WHERE id = ?;";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Cannot delete student", e);
        }
    }

    @Override
    public void assignToCourse(Student student, Course course) {

        String sql = "INSERT INTO course_student (course_id, student_id) VALUES (?,?)";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, course.getId());
            statement.setLong(2, student.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Cannot assign course to student", e);
        }
    }

    @Override
    public List<Student> getByCourseName(String courseName) {

        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.* FROM students s, courses c, course_student cs " +
            "WHERE s.id=cs.student_id " +
            "AND c.id=cs.course_id " +
            "AND c.name=?;";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                students.add(createStudentFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            throw new DAOException("Cannot get students by course name", e);
        }

        return students;
    }

    @Override
    public void unassignFromCourse(long studentId, long courseId) {

        String sql = "DELETE FROM course_student WHERE student_id=? AND course_id=?;";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, studentId);
            statement.setLong(2, courseId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Cannot unassign student from course", e);
        }
    }

    protected Student createStudentFromResultSet(ResultSet resultSet) throws SQLException {

        try {
            return new Student(
                resultSet.getLong(ID),
                resultSet.getLong(GROUP_ID),
                resultSet.getString(NAME),
                resultSet.getString(LAST_NAME));
        } catch (PSQLException e) {
            return new Student(
                resultSet.getLong(ID),
                null,
                resultSet.getString(NAME),
                resultSet.getString(LAST_NAME));
        }

    }

    private void setStatementParameters(PreparedStatement statement,
                                        String name, String lastName) throws SQLException {
        statement.setString(1, name);
        statement.setString(2, lastName);
    }
}

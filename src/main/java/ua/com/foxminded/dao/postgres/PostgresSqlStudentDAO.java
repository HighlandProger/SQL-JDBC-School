package ua.com.foxminded.dao.postgres;

import org.postgresql.util.PSQLException;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.exception.DAOException;
import ua.com.foxminded.dao.DAOFactory;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresSqlStudentDAO implements StudentDAO {

    private static final String ID = "id";
    private static final String GROUP_ID = "groupId";
    private static final String NAME = "name";
    private static final String LAST_NAME = "lastName";

    @Override
    public void create(Student student) {

        String sql = "INSERT INTO STUDENTS (name, lastName) VALUES (?,?);";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setStatementParameters(statement, student.getName(), student.getLastName());
            statement.execute();
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

        Student student = null;

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                student = createStudentFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot get student by id", e);
        }

        if (student == null) {
            throw new DAOException("Student with id = " + id + " is not found");
        }

        return student;
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
    public void assignToCourse(Course course, Student student) {

        String sql = "INSERT INTO course_student (course_id, student_id) VALUES (?,?);";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, course.getId());
            statement.setLong(2, student.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Cannot create course - student relationship", e);
        }
    }

    @Override
    public List<Student> getByCourseName(String courseName) {

        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.* FROM students s, courses c, course_student cs " +
            "WHERE s.id=cs.student_id AND c.id=cs.course_id " +
            "GROUP BY s.id, c.id " +
            "HAVING c.name=?;";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                students.add(new PostgresSqlStudentDAO().createStudentFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            throw new DAOException("Cannot get students by course name", e);
        }

        return students;
    }

    @Override
    public void deleteCourseRelation(long studentId, long courseId) {

        String sql = "DELETE FROM course_student WHERE student_id=? AND course_id=?;";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, studentId);
            statement.setLong(2, courseId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Cannot delete course - student relation", e);
        }
    }

    protected Student createStudentFromResultSet(ResultSet resultSet) throws SQLException {

        try {
            return new Student(
                resultSet.getInt(ID),
                resultSet.getInt(GROUP_ID),
                resultSet.getString(NAME),
                resultSet.getString(LAST_NAME));
        } catch (PSQLException e) {
            return new Student(
                resultSet.getInt(ID),
                0,
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

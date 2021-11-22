package ua.com.foxminded.dao.postgres;

import ua.com.foxminded.dao.DAOException;
import ua.com.foxminded.dao.DAOFactory;
import ua.com.foxminded.dao.StudentCourseDAO;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresSqlStudentCourseDAO implements StudentCourseDAO {

    @Override
    public void assignCourseToStudent(Course course, Student student) {

        String sql = "INSERT INTO course_student (course_id, student_id) VALUES (?,?);";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setStatementParameters(statement, course.getId(), student.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Cannot create course - student relationship", e);
        }
    }

    @Override
    public List<Student> getStudentsByCourseName(String courseName) {

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
    public void delete(long studentId, long courseId) {

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

    private void setStatementParameters(PreparedStatement statement,
                                        long courseId, long studentId) throws SQLException {

        statement.setLong(1, courseId);
        statement.setLong(2, studentId);
    }
}

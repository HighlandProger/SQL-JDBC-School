package ua.com.foxminded.menu;

import ua.com.foxminded.dao.postgres.PostgresSqlCourseDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlGroupDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlStudentDAO;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.util.List;

public class SQLMenuQueries {

    private static final String SPACE = " ";
    private final PostgresSqlGroupDAO groupDAO = new PostgresSqlGroupDAO();
    private final PostgresSqlStudentDAO studentDAO = new PostgresSqlStudentDAO();
    private final PostgresSqlCourseDAO courseDAO = new PostgresSqlCourseDAO();

    public String findAllGroupsWithLessOrEqualsStudentCount(int studentCount) {
        List<Group> groups = groupDAO.getLessOrEqualsByStudentsCount(studentCount);
        StringBuilder builder = new StringBuilder();
        for (Group group : groups) {
            builder.append(group.getName())
                .append("\n");
        }

        return builder.toString();
    }

    public String findAllStudentsRelatedToCourseWithGivenName(String courseName) {
        List<Student> students = studentDAO.getByCourseName(courseName);

        StringBuilder builder = new StringBuilder();
        for (Student student : students) {
            builder.append(student.getName())
                .append(SPACE)
                .append(student.getLastName())
                .append("\n");
        }

        return builder.toString();
    }

    public void addNewStudent(String name, String lastName) {
        studentDAO.create(new Student(name, lastName));
    }

    public void deleteStudentByStudentId(long id) {
        studentDAO.delete(id);
    }

    public void addStudentToTheCourseFromAList(long studentId, long courseId) {
        Student student = studentDAO.getById(studentId);
        Course course = courseDAO.getById(courseId);
        studentDAO.assignToCourse(student, course);
    }

    public void removeStudentFromCourse(long studentId, long courseId) {

        studentDAO.unassignFromCourse(studentId, courseId);
    }

    public List<Course> getCoursesList() {
        return courseDAO.getAll();
    }
}

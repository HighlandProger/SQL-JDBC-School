package ua.com.foxminded.menu;

import ua.com.foxminded.dao.postgres.PostgreSqlCourseDAO;
import ua.com.foxminded.dao.postgres.PostgreSqlGroupDAO;
import ua.com.foxminded.dao.postgres.PostgreSqlStudentDAO;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.util.List;

public class MainMenuService {

    private PostgreSqlGroupDAO groupDAO = new PostgreSqlGroupDAO();
    private PostgreSqlStudentDAO studentDAO = new PostgreSqlStudentDAO();
    private PostgreSqlCourseDAO courseDAO = new PostgreSqlCourseDAO();

    public List<Group> findAllGroupsWithLessOrEqualsStudentCount(int studentCount) {
        return groupDAO.getLessOrEqualsByStudentsCount(studentCount);
    }

    public List<Student> findAllStudentsRelatedToCourseWithGivenName(String courseName) {
        return studentDAO.getByCourseName(courseName);
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

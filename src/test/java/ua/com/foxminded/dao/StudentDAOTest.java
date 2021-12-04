package ua.com.foxminded.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.postgres.PostgresSqlCourseDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlGroupDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlStudentDAO;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.exception.DAOException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentDAOTest {

    private final SqlRunner sqlRunner = new SqlRunner();
    private final PostgresSqlStudentDAO studentDAO = new PostgresSqlStudentDAO();
    private final PostgresSqlGroupDAO groupDAO = new PostgresSqlGroupDAO();
    private final PostgresSqlCourseDAO courseDAO = new PostgresSqlCourseDAO();
    private Student expectedStudent;
    private Student actualStudent;
    private List<Student> expectedStudents;
    private List<Student> actualStudents;

    @BeforeEach
    void initTables() {

        sqlRunner.createTables();
        assertEquals(0, studentDAO.getAll().size());
    }

    @Test
    void create() {

        expectedStudent = TestUtils.getStudent();
        actualStudent = studentDAO.create(expectedStudent);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getById() {

        expectedStudent = TestUtils.getStudent();
        studentDAO.create(expectedStudent);
        actualStudent = studentDAO.getById(expectedStudent.getId());

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getById_shouldThrowDAOException_whenIdIsAbsent() {

        long randomId = 2;
        Exception exception = assertThrows(DAOException.class,
            () -> studentDAO.getById(randomId));

        String expectedExceptionString = "Student is not found";
        String actualExceptionString = exception.getMessage();

        assertEquals(expectedExceptionString, actualExceptionString);
    }

    @Test
    void getAll() {

        expectedStudents = TestUtils.getStudentsWithoutGroupId();
        for (Student student : expectedStudents) {
            studentDAO.create(student);
        }
        actualStudents = studentDAO.getAll();

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void assignToGroup() {

        Student student = studentDAO.create(TestUtils.getStudent());
        Group group = groupDAO.create(TestUtils.getGroup());
        studentDAO.assignToGroup(student, group);
        Long expectedGroupId = group.getId();
        Long actualGroupId = studentDAO.getById(student.getId()).getGroupId();
        assertEquals(expectedGroupId, actualGroupId);
    }

    @Test
    void delete() {

        Student student = studentDAO.create(TestUtils.getStudent());
        int expectedStudentsCountBefore = 1;
        int actualStudentsCountBefore = studentDAO.getAll().size();
        assertEquals(expectedStudentsCountBefore, actualStudentsCountBefore);

        studentDAO.delete(student.getId());
        int expectedStudentsCountAfter = 0;
        int actualStudentsCountAfter = studentDAO.getAll().size();

        assertEquals(expectedStudentsCountAfter, actualStudentsCountAfter);
    }

    @Test
    void assignToCourse() {

        expectedStudent = studentDAO.create(TestUtils.getStudent());
        Course course = courseDAO.create(TestUtils.getCourse());
        studentDAO.assignToCourse(expectedStudent, course);
        List<Student> assignedStudents = studentDAO.getByCourseName(course.getName());
        actualStudent = assignedStudents.get(0);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getByCourseName() {

        Course course = courseDAO.create(TestUtils.getCourse());
        expectedStudents = TestUtils.getStudentsWithoutGroupId();
        for (Student student : expectedStudents) {
            studentDAO.create(student);
            studentDAO.assignToCourse(student, course);
        }
        actualStudents = studentDAO.getByCourseName(course.getName());

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void unassignFromCourse() {

        Student student = studentDAO.create(TestUtils.getStudent());
        Course course = courseDAO.create(TestUtils.getCourse());
        studentDAO.assignToCourse(student, course);
        studentDAO.unassignFromCourse(student.getId(), course.getId());

        assertEquals(0, studentDAO.getByCourseName(course.getName()).size());
    }
}

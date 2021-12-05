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
    }

    @Test
    void create() {

        assertEquals(0, studentDAO.getAll().size());
        expectedStudent = TestUtils.createStudent(1, null, "Jack", "Richer");
        actualStudent = studentDAO.create(expectedStudent);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getById() {

        assertEquals(0, studentDAO.getAll().size());
        expectedStudent = TestUtils.createStudent(1, null, "Jack", "Richer");
        studentDAO.create(expectedStudent);
        actualStudent = studentDAO.getById(expectedStudent.getId());

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getById_shouldThrowDAOException_whenIdIsAbsent() {

        assertEquals(0, studentDAO.getAll().size());
        long randomId = 2;
        try {
            studentDAO.getById(randomId);
        } catch (DAOException e) {
            String expectedExceptionString = "Student is not found";
            String actualExceptionString = e.getMessage();

            assertEquals(expectedExceptionString, actualExceptionString);
        }
    }

    @Test
    void getAll() {

        assertEquals(0, studentDAO.getAll().size());
        expectedStudents = TestUtils.getFiveRandomStudentsWithoutGroupId();
        for (Student student : expectedStudents) {
            studentDAO.create(student);
        }
        actualStudents = studentDAO.getAll();

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void assignToGroup() {

        assertEquals(0, studentDAO.getAll().size());
        Student student = studentDAO.create(TestUtils.createStudent(1, null, "Jack", "Richer"));
        Group group = groupDAO.create(TestUtils.createGroup(1, "AB-10"));
        studentDAO.assignToGroup(student, group);
        Long expectedGroupId = group.getId();
        Long actualGroupId = studentDAO.getById(student.getId()).getGroupId();
        assertEquals(expectedGroupId, actualGroupId);
    }

    @Test
    void delete() {

        assertEquals(0, studentDAO.getAll().size());
        Student student = studentDAO.create(TestUtils.createStudent(1, null, "Jack", "Richer"));
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

        assertEquals(0, studentDAO.getAll().size());
        expectedStudent = studentDAO.create(TestUtils.createStudent(1, null, "Jack", "Richer"));
        Course course = courseDAO.create(TestUtils.createCourse(1, "math", "Algebra, Geometry"));
        studentDAO.assignToCourse(expectedStudent, course);
        List<Student> assignedStudents = studentDAO.getByCourseName(course.getName());
        actualStudent = assignedStudents.get(0);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getByCourseName() {

        assertEquals(0, studentDAO.getAll().size());
        Course course = courseDAO.create(TestUtils.createCourse(1, "math", "Algebra, Geometry"));
        expectedStudents = TestUtils.getFiveRandomStudentsWithoutGroupId();
        for (Student student : expectedStudents) {
            studentDAO.create(student);
            studentDAO.assignToCourse(student, course);
        }
        actualStudents = studentDAO.getByCourseName(course.getName());

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void unassignFromCourse() {

        assertEquals(0, studentDAO.getAll().size());
        Student student = studentDAO.create(TestUtils.createStudent(1, null, "Jack", "Richer"));
        Course course = courseDAO.create(TestUtils.createCourse(1, "math", "Algebra, Geometry"));
        studentDAO.assignToCourse(student, course);
        studentDAO.unassignFromCourse(student.getId(), course.getId());

        assertEquals(0, studentDAO.getByCourseName(course.getName()).size());
    }
}

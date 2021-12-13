package ua.com.foxminded.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.postgres.PostgreSqlCourseDAO;
import ua.com.foxminded.dao.postgres.PostgreSqlGroupDAO;
import ua.com.foxminded.dao.postgres.PostgreSqlStudentDAO;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.exception.DAOException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOTest {

    private final SqlRunner sqlRunner = new SqlRunner();
    private final PostgreSqlStudentDAO studentDAO = new PostgreSqlStudentDAO();
    private final PostgreSqlGroupDAO groupDAO = new PostgreSqlGroupDAO();
    private final PostgreSqlCourseDAO courseDAO = new PostgreSqlCourseDAO();
    private Student expectedStudent;
    private Student actualStudent;
    private List<Student> expectedStudents;
    private List<Student> actualStudents;

    @BeforeEach
    void initTables() {
        sqlRunner.createTables();
    }

    @Test
    void create_shouldCreateStudent() {

        assertEquals(0, studentDAO.getAll().size());
        expectedStudent = TestUtils.createStudent(1, "Jack", "Richer");
        actualStudent = studentDAO.create(expectedStudent);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getById_shouldReturnStudent() {

        assertEquals(0, studentDAO.getAll().size());
        expectedStudent = TestUtils.createStudent(1, "Jack", "Richer");
        studentDAO.create(expectedStudent);
        actualStudent = studentDAO.getById(expectedStudent.getId());

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getById_shouldThrowDAOException_whenStudentNotFoundWithGivenId() {

        assertEquals(0, studentDAO.getAll().size());
        long randomId = 2;
        Exception exception = assertThrows(DAOException.class,
            () -> studentDAO.getById(randomId));

        String expectedExceptionString = "Student is not found";
        String actualExceptionString = exception.getMessage();

        assertEquals(expectedExceptionString, actualExceptionString);

    }

    @Test
    void getAll_shouldReturnAllStudents() {

        assertEquals(0, studentDAO.getAll().size());
        expectedStudents = TestUtils.getFiveRandomStudentsWithoutGroupId();
        for (Student student : expectedStudents) {
            studentDAO.create(student);
        }
        actualStudents = studentDAO.getAll();

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void assignToGroup_shouldAssignStudentToGroup() {

        Student student = studentDAO.create(TestUtils.createStudent(1, "Jack", "Richer"));
        Group group = groupDAO.create(TestUtils.createGroup(1, "AB-10"));
        assertNull(student.getGroupId());
        studentDAO.assignToGroup(student, group);
        Long expectedGroupId = group.getId();
        Long actualGroupId = studentDAO.getById(student.getId()).getGroupId();
        assertEquals(expectedGroupId, actualGroupId);
    }

    @Test
    void delete_shouldDeleteStudent() {

        Student student = studentDAO.create(TestUtils.createStudent(1, "Jack", "Richer"));
        int expectedStudentsCountBefore = 1;
        int actualStudentsCountBefore = studentDAO.getAll().size();
        assertEquals(expectedStudentsCountBefore, actualStudentsCountBefore);

        studentDAO.delete(student.getId());
        int expectedStudentsCountAfter = 0;
        int actualStudentsCountAfter = studentDAO.getAll().size();

        assertEquals(expectedStudentsCountAfter, actualStudentsCountAfter);
    }

    @Test
    void assignToCourse_shouldAssignCourseToSudent() {

        Course course = courseDAO.create(TestUtils.createCourse(1, "math", "Algebra, Geometry"));
        List<Student> assignedStudentsBefore = studentDAO.getByCourseName(course.getName());
        assertEquals(0, assignedStudentsBefore.size());

        expectedStudent = studentDAO.create(TestUtils.createStudent(1, "Jack", "Richer"));
        studentDAO.assignToCourse(expectedStudent, course);
        List<Student> assignedStudents = studentDAO.getByCourseName(course.getName());
        assertEquals(1, assignedStudents.size());
        actualStudent = assignedStudents.get(0);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getByCourseName_shouldReturnAssignedStudents() {

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
    void unassignFromCourse_shouldUnassignCourseFromStudent() {

        assertEquals(0, studentDAO.getAll().size());
        Student student = studentDAO.create(TestUtils.createStudent(1, "Jack", "Richer"));
        Course course = courseDAO.create(TestUtils.createCourse(1, "math", "Algebra, Geometry"));
        studentDAO.assignToCourse(student, course);
        assertEquals(student, studentDAO.getByCourseName(course.getName()).get(0));
        studentDAO.unassignFromCourse(student.getId(), course.getId());

        assertEquals(0, studentDAO.getByCourseName(course.getName()).size());
    }
}

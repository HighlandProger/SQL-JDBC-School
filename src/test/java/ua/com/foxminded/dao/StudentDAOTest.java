package ua.com.foxminded.dao;

import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.postgres.PostgresSqlCourseDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlGroupDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlStudentDAO;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOTest {

    private final SqlRunner sqlRunner = new SqlRunner();
    private final PostgresSqlStudentDAO studentDAO = new PostgresSqlStudentDAO();
    private final PostgresSqlGroupDAO groupDAO = new PostgresSqlGroupDAO();
    private final PostgresSqlCourseDAO courseDAO = new PostgresSqlCourseDAO();

    @Test
    void create() {

        sqlRunner.createTables();
        Student expectedStudent = new Student(1, null, "John", "Evans");
        Student actualStudent = studentDAO.create(expectedStudent);
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getById() {

        sqlRunner.createTables();
        Student expectedStudent = new Student(1, null, "John", "Evans");
        studentDAO.create(expectedStudent);
        Student actualStudent = studentDAO.getById(expectedStudent.getId());
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getAll() {

        sqlRunner.createTables();
        List<Student> expectedStudents = Arrays.asList(
            new Student(1, null, "Elly", "Goldman"),
            new Student(2, null, "John", "Evans"),
            new Student(3, null, "Jack", "Richer"),
            new Student(4, null, "Tom", "Hanks"));

        for (Student student : expectedStudents){
            studentDAO.create(student);
        }

        List<Student> actualStudents = studentDAO.getAll();
        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void assignToGroup() {

        sqlRunner.createTables();
        Student student = studentDAO.create(new Student(1, null, "Jack", "Johnson"));
        Group group = groupDAO.create(new Group(1, "ED-34"));
        studentDAO.assignToGroup(student, group);
        Long expectedGroupId = group.getId();
        Long actualGroupId = studentDAO.getById(student.getId()).getGroupId();
        assertEquals(expectedGroupId, actualGroupId);
    }

    @Test
    void delete() {

        sqlRunner.createTables();
        studentDAO.create(new Student(1, null, "Joe", "Blind"));
        studentDAO.delete(1);
        int expectedStudentsCount = 0;
        int actualStudentsCount = studentDAO.getAll().size();
        assertEquals(expectedStudentsCount, actualStudentsCount);
    }

//    @Test
//    void assignToCourse() {
//        Student student = studentDAO.create(new Student(1, null, "Jack", "Johnson"));
//        Course course = courseDAO.create(new Course(1, "math", "Algebra, Geometry"));
//        studentDAO.assignToCourse(student, course);
//
//    }

    @Test
    void getByCourseName() {
    }

    @Test
    void unassignFromCourse() {
    }
}

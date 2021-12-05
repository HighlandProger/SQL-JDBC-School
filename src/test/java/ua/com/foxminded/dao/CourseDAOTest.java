package ua.com.foxminded.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.postgres.PostgresSqlCourseDAO;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.exception.DAOException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseDAOTest {

    private final SqlRunner sqlRunner = new SqlRunner();
    private final PostgresSqlCourseDAO courseDAO = new PostgresSqlCourseDAO();
    private Course expectedCourse;
    private Course actualCourse;

    @BeforeEach
    void initTables() {

        sqlRunner.createTables();
    }

    @Test
    void create() {

        assertEquals(0, courseDAO.getAll().size());
        expectedCourse = TestUtils.createCourse(1, "math", "Algebra, Geometry");
        actualCourse = courseDAO.create(expectedCourse);

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void getById() {

        assertEquals(0, courseDAO.getAll().size());
        expectedCourse = TestUtils.createCourse(1, "math", "Algebra, Geometry");
        courseDAO.create(expectedCourse);
        actualCourse = courseDAO.getById(expectedCourse.getId());

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void getById_shouldThrowDAOException_whenIdIsAbsent() {

        assertEquals(0, courseDAO.getAll().size());
        long randomId = 2;
        try {
            courseDAO.getById(randomId);
        } catch (DAOException e) {
            String expectedExceptionString = "Course is not found";
            String actualExceptionString = e.getMessage();

            assertEquals(expectedExceptionString, actualExceptionString);
        }
    }

    @Test
    void getAll() {

        assertEquals(0, courseDAO.getAll().size());
        List<Course> expectedCourses = TestUtils.getFiveRandomCourses();
        for (Course course : expectedCourses) {
            courseDAO.create(course);
        }
        List<Course> actualCourses = courseDAO.getAll();

        assertEquals(expectedCourses, actualCourses);
    }
}
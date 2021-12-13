package ua.com.foxminded.dao.postgresql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.SqlRunner;
import ua.com.foxminded.dao.TestUtils;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.exception.DAOException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostgreSqlCourseDAOTest {

    private final SqlRunner sqlRunner = new SqlRunner();
    private final PostgreSqlCourseDAO courseDAO = new PostgreSqlCourseDAO();
    private Course expectedCourse;
    private Course actualCourse;

    @BeforeEach
    void initTables() {
        sqlRunner.createTables();
    }

    @Test
    void create_shouldCreateCourse() {

        assertEquals(0, courseDAO.getAll().size());
        expectedCourse = TestUtils.createCourse(1, "math", "Algebra, Geometry");
        actualCourse = courseDAO.create(expectedCourse);

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void getById_shouldReturnCourse() {

        assertEquals(0, courseDAO.getAll().size());
        expectedCourse = TestUtils.createCourse(1, "math", "Algebra, Geometry");
        courseDAO.create(expectedCourse);
        actualCourse = courseDAO.getById(expectedCourse.getId());

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void getById_shouldThrowDAOException_whenCourseIsNotFoundWithGivenId() {

        assertEquals(0, courseDAO.getAll().size());
        long randomId = 2;
        Exception exception = assertThrows(DAOException.class,
            () -> courseDAO.getById(randomId));

        String expectedExceptionString = "Course is not found";
        String actualExceptionString = exception.getMessage();

        assertEquals(expectedExceptionString, actualExceptionString);

    }

    @Test
    void getAll_shouldReturnAllCourses() {

        assertEquals(0, courseDAO.getAll().size());
        List<Course> expectedCourses = TestUtils.getFiveRandomCourses();
        for (Course course : expectedCourses) {
            courseDAO.create(course);
        }
        List<Course> actualCourses = courseDAO.getAll();

        assertEquals(expectedCourses, actualCourses);
    }
}

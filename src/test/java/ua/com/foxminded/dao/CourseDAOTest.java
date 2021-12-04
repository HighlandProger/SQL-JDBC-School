package ua.com.foxminded.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.postgres.PostgresSqlCourseDAO;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.exception.DAOException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseDAOTest {

    private final SqlRunner sqlRunner = new SqlRunner();
    private final PostgresSqlCourseDAO courseDAO = new PostgresSqlCourseDAO();
    private Course expectedCourse;
    private Course actualCourse;

    @BeforeEach
    void initTables() {

        sqlRunner.createTables();
        assertEquals(0, courseDAO.getAll().size());
    }

    @Test
    void create() {

        expectedCourse = TestUtils.getCourse();
        actualCourse = courseDAO.create(TestUtils.getCourse());

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void getById() {

        expectedCourse = TestUtils.getCourse();
        courseDAO.create(expectedCourse);
        actualCourse = courseDAO.getById(expectedCourse.getId());

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void getById_shouldThrowDAOException_whenIdIsAbsent() {

        long randomId = 2;
        Exception exception = assertThrows(DAOException.class,
            () -> courseDAO.getById(randomId));

        String expectedExceptionString = "Course is not found";
        String actualExceptionString = exception.getMessage();

        assertEquals(expectedExceptionString, actualExceptionString);
    }

    @Test
    void getAll() {

        List<Course> expectedCourses = TestUtils.getCourses();
        for (Course course : expectedCourses) {
            courseDAO.create(course);
        }
        List<Course> actualCourses = courseDAO.getAll();

        assertEquals(expectedCourses, actualCourses);
    }
}

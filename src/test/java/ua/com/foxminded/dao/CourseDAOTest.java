package ua.com.foxminded.dao;

import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.postgres.PostgresSqlCourseDAO;
import ua.com.foxminded.domain.Course;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseDAOTest {

    private final SqlRunner sqlRunner = new SqlRunner();
    private final PostgresSqlCourseDAO courseDAO = new PostgresSqlCourseDAO();

    @Test
    void create() {

        sqlRunner.createTables();
        Course actualCourse = courseDAO.create(new Course("math", "Algebra, Geometry"));
        Course expectedCourse = new Course(1, "math", "Algebra, Geometry");
        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void getById() {

        sqlRunner.createTables();
        Course expectedCourse = new Course(1, "math", "Algebra, Geometry");
        courseDAO.create(expectedCourse);
        Course actualCourse = courseDAO.getById(expectedCourse.getId());
        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void getAll() {

        sqlRunner.createTables();
        List<Course> expectedCourses = Arrays.asList(
            new Course(1, "math", "Algebra, Geometry"),
            new Course(2, "biology", "Anatomy, mammals, dinosaurs..."),
            new Course(3, "chemists", "Don't try it at home"),
            new Course(4, "history", "It was rewritten too many times"),
            new Course(5,"physics", "Very cool course. Be careful with electricity"));

        for (Course course : expectedCourses){
            courseDAO.create(course);
        }

        List<Course> actualCourses = courseDAO.getAll();
        assertEquals(expectedCourses, actualCourses);
    }
}

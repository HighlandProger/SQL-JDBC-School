package ua.com.foxminded.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.*;
import ua.com.foxminded.dao.postgres.PostgresSqlCourseDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlGroupDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlStudentDAO;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuServiceTest {

    MainMenuService menuService = new MainMenuService();
    GroupDAO groupDAO = new PostgresSqlGroupDAO();
    StudentDAO studentDAO = new PostgresSqlStudentDAO();
    CourseDAO courseDAO = new PostgresSqlCourseDAO();
    SqlRunner sqlRunner = new SqlRunner();

    @BeforeEach
    void initTables() {
        sqlRunner.createTables();
    }

    @Test
    void findAllGroupsWithLessOrEqualsStudentCount_shouldReturnListOfGroups() {

        List<Group> groups = new ArrayList<>(TestUtils.getFiveRandomGroups());
        List<Student> students = TestUtils.getFiveRandomStudentsWithoutGroupId();
        for (int i = 0; i < groups.size(); i++){
            groupDAO.create(groups.get(i));
            studentDAO.create(students.get(i));
        }

        studentDAO.assignToGroup(students.get(0), groups.get(0));
        studentDAO.assignToGroup(students.get(1), groups.get(0));
        studentDAO.assignToGroup(students.get(2), groups.get(1));
        studentDAO.assignToGroup(students.get(3), groups.get(2));
        studentDAO.assignToGroup(students.get(4), groups.get(3));

        int maxAssignedStudentsCount = 1;

        //Exclude groups with students count = 2 (index - 0) and without students (index - 4)
        groups.remove(4);
        groups.remove(0);

        List<Group> expectedGroups = new ArrayList<>(groups);
        List<Group> actualGroups = menuService.findAllGroupsWithLessOrEqualsStudentCount(maxAssignedStudentsCount);

        expectedGroups.sort((el1, el2) -> (int) (el1.getId()-el2.getId()));
        actualGroups.sort((el1, el2) -> (int) (el1.getId()-el2.getId()));

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void findAllStudentsRelatedToCourseWithGivenName_shouldReturnListOfStudents() {
        List<Student> expectedStudents = TestUtils.getFiveRandomStudentsWithoutGroupId();
        Course course = courseDAO.create(TestUtils.createCourse(1, "math", "Algebra, Geometry"));

        for (Student student : expectedStudents) {
            studentDAO.create(student);
            studentDAO.assignToCourse(student, course);
        }

        List<Student> actualStudents = menuService.findAllStudentsRelatedToCourseWithGivenName(course.getName());

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void addNewStudent() {
    }

    @Test
    void deleteStudentByStudentId() {
    }

    @Test
    void addStudentToTheCourseFromAList() {
    }

    @Test
    void removeStudentFromCourse() {
    }

    @Test
    void getCoursesList() {
    }
}

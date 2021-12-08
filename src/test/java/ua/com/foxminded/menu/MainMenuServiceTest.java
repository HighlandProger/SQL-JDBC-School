package ua.com.foxminded.menu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.dao.TestUtils;
import ua.com.foxminded.dao.postgres.PostgresSqlCourseDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlGroupDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlStudentDAO;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Student;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MainMenuServiceTest {

    @InjectMocks
    public MainMenuService menuService;
    @Mock
    PostgresSqlGroupDAO groupDAO;
    @Mock
    PostgresSqlStudentDAO studentDAO;
    @Mock
    PostgresSqlCourseDAO courseDAO;

    @Test
    void findAllGroupsWithLessOrEqualsStudentCount_shouldCallGroupDaoMethod() {

        int maxAssignedStudentsCount = 2;
        menuService.findAllGroupsWithLessOrEqualsStudentCount(maxAssignedStudentsCount);
        verify(groupDAO).getLessOrEqualsByStudentsCount(maxAssignedStudentsCount);
    }

    @Test
    void findAllStudentsRelatedToCourseWithGivenName_shouldCallStudentDaoMethod() {

        String randomCourseName = "math";
        menuService.findAllStudentsRelatedToCourseWithGivenName(randomCourseName);
        verify(studentDAO).getByCourseName(randomCourseName);
    }

    @Test
    void addNewStudent_shouldCallStudentDaoMethod() {

        Student student = TestUtils.createStudent(1, "Jack", "Johnson");
        menuService.addNewStudent(student.getName(), student.getLastName());
        verify(studentDAO).create(new Student(student.getName(), student.getLastName()));
    }

    @Test
    void deleteStudentByStudentId_shouldCallStudentDaoMethod() {

        Student student = TestUtils.createStudent(1, "Jack", "Johnson");
        menuService.deleteStudentByStudentId(student.getId());
        verify(studentDAO).delete(student.getId());
    }

    @Test
    void addStudentToTheCourseFromAList_shouldCallStudentDaoAndCourseDaoMethods() {

        Student student = TestUtils.createStudent(1, "Jack", "Johnson");
        Course course = TestUtils.createCourse(4, "math", "Algebra, Geometry");
        menuService.addStudentToTheCourseFromAList(student.getId(), course.getId());
        verify(studentDAO).getById(student.getId());
        verify(courseDAO).getById(course.getId());
        verify(studentDAO).assignToCourse(null, null);
    }

    @Test
    void removeStudentFromCourse_shouldCallStudentDaoMethod() {

        Student student = TestUtils.createStudent(1, "Jack", "Johnson");
        Course course = TestUtils.createCourse(4, "math", "Algebra, Geometry");
        menuService.removeStudentFromCourse(student.getId(), course.getId());
        verify(studentDAO).unassignFromCourse(student.getId(), course.getId());
    }

    @Test
    void getCoursesList_shouldCallCourseDaoMethod() {

        menuService.getCoursesList();
        verify(courseDAO).getAll();
    }
}

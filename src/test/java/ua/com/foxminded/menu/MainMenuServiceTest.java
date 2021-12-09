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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MainMenuServiceTest {

    @InjectMocks
    private MainMenuService menuService;
    @Mock
    private PostgresSqlGroupDAO groupDAO;
    @Mock
    private PostgresSqlStudentDAO studentDAO;
    @Mock
    private PostgresSqlCourseDAO courseDAO;

    @Test
    void findAllGroupsWithLessOrEqualsStudentCount_shouldCallGroupDaoGetLessOrEqualsByStudentsCount() {

        int maxAssignedStudentsCount = 2;
        menuService.findAllGroupsWithLessOrEqualsStudentCount(maxAssignedStudentsCount);
        verify(groupDAO).getLessOrEqualsByStudentsCount(maxAssignedStudentsCount);
    }

    @Test
    void findAllStudentsRelatedToCourseWithGivenName_shouldCallStudentDaoCreate() {

        String randomCourseName = "math";
        menuService.findAllStudentsRelatedToCourseWithGivenName(randomCourseName);
        verify(studentDAO).getByCourseName(randomCourseName);
    }

    @Test
    void addNewStudent_shouldCallStudentDaoMethod() {

        String randomStudentName = "Jack";
        String randomStudentLastName = "Johnson";
        menuService.addNewStudent(randomStudentName, randomStudentLastName);
        verify(studentDAO).create(new Student(randomStudentName, randomStudentLastName));
    }

    @Test
    void deleteStudentByStudentId_shouldCallStudentDaoDelete() {

        long randomStudentId = 4;
        menuService.deleteStudentByStudentId(randomStudentId);
        verify(studentDAO).delete(randomStudentId);
    }

    @Test
    void addStudentToTheCourseFromAList_shouldCallStudentDaoAssignToCourse() {

        Student student = TestUtils.createStudent(1, "Jack", "Johnson");
        Course course = TestUtils.createCourse(4, "math", "Algebra, Geometry");
        when(studentDAO.getById(student.getId())).thenReturn(student);
        when(courseDAO.getById(course.getId())).thenReturn(course);
        menuService.addStudentToTheCourseFromAList(student.getId(), course.getId());
        verify(studentDAO).assignToCourse(student, course);
    }

    @Test
    void removeStudentFromCourse_shouldCallStudentDaoUnassignFromCourse() {

        long randomStudentId = 4;
        long randomCourseId = 7;
        menuService.removeStudentFromCourse(randomStudentId, randomCourseId);
        verify(studentDAO).unassignFromCourse(randomStudentId, randomCourseId);
    }

    @Test
    void getCoursesList_shouldCallCourseDaoGetAll() {

        menuService.getCoursesList();
        verify(courseDAO).getAll();
    }
}

package ua.com.foxminded.menu;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.dao.TestUtils;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MainMenuTest {

    private static final int EXIT_MENU_POSITION = 0;
    private int menuPosition;

    @InjectMocks
    private MainMenu mainMenu;

    @Mock
    private MainMenuService menuService;

    @Mock
    private MainMenuScanner menuScanner;

    void runMenuAfterSystemExit() {
        try {
            SystemLambda.catchSystemExit(() -> mainMenu.runMenu());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void runMenu_shouldCallMainMenuServiceFindAllGroupsWithLessOrEqualsStudentCount_whenMenuPositionIs1() {

        menuPosition = 1;
        int randomStudentCount = 10;
        findAllGroupsWithLessOrEqualsStudentCountScenario(menuPosition, randomStudentCount);
        runMenuAfterSystemExit();

        verify(menuService).findAllGroupsWithLessOrEqualsStudentCount(randomStudentCount);
    }

    private void findAllGroupsWithLessOrEqualsStudentCountScenario(int menuPosition, int randomStudentCount) {

        List<Group> randomGroups = TestUtils.getFiveRandomGroups();
        when(menuScanner.readInt())
            .thenReturn(menuPosition)
            .thenReturn(randomStudentCount)
            .thenReturn(EXIT_MENU_POSITION);
        when(menuService.findAllGroupsWithLessOrEqualsStudentCount(randomStudentCount))
            .thenReturn(randomGroups);
    }

    @Test
    void runMenu_shouldCallMainMenuServiceFindAllStudentsRelatedToCourseWithGivenName_whenMenuPositionIs2() {

        menuPosition = 2;
        String randomCourseName = "math";
        findAllStudentsRelatedToCourseWithGivenNameScenario(menuPosition, randomCourseName);
        runMenuAfterSystemExit();

        verify(menuService).findAllStudentsRelatedToCourseWithGivenName(randomCourseName);
    }

    private void findAllStudentsRelatedToCourseWithGivenNameScenario(int menuPosition, String randomCourseName) {

        List<Student> randomStudents = TestUtils.getFiveRandomStudentsWithoutGroupId();
        when(menuScanner.readInt())
            .thenReturn(menuPosition)
            .thenReturn(0);
        when(menuScanner.readString())
            .thenReturn(randomCourseName);
        when(menuService.findAllStudentsRelatedToCourseWithGivenName(randomCourseName))
            .thenReturn(randomStudents);
    }

    @Test
    void runMenu_shouldCallMainMenuServiceAddNewStudent_whenMenuPositionIs3() {

        menuPosition = 3;
        String randomStudentName = "Jack";
        String randomStudentLastName = "Richer";
        addNewStudentScenario(menuPosition, randomStudentName, randomStudentLastName);
        runMenuAfterSystemExit();

        verify(menuService).addNewStudent(randomStudentName, randomStudentLastName);
    }

    private void addNewStudentScenario(int menuPosition, String randomStudentName, String randomStudentLastName) {

        when(menuScanner.readInt())
            .thenReturn(menuPosition)
            .thenReturn(EXIT_MENU_POSITION);
        when(menuScanner.readString())
            .thenReturn(randomStudentName)
            .thenReturn(randomStudentLastName);
    }

    @Test
    void runMenu_shouldCallMainMenuServiceDeleteStudentById_whenMenuPositionIs4() {

        menuPosition = 4;
        long randomStudentId = 8;
        deleteStudentByIdScenario(menuPosition, randomStudentId);
        runMenuAfterSystemExit();

        verify(menuService).deleteStudentByStudentId(randomStudentId);
    }

    private void deleteStudentByIdScenario(int menuPosition, long randomStudentId) {

        when(menuScanner.readInt())
            .thenReturn(menuPosition)
            .thenReturn(EXIT_MENU_POSITION);
        when(menuScanner.readLong()).thenReturn(randomStudentId);
    }

    @Test
    void runMenu_shouldCallMainMenuServiceAddStudentToTheCourse_whenMenuPositionIs5() {

        menuPosition = 5;
        long randomStudentId = 8;
        long randomCourseId = 14;
        addStudentToTheCourseScenario(menuPosition, randomStudentId, randomCourseId);
        runMenuAfterSystemExit();

        verify(menuService).addStudentToTheCourseFromAList(randomStudentId, randomCourseId);
    }

    private void addStudentToTheCourseScenario(int menuPosition, long randomStudentId, long randomCourseId) {

        when(menuScanner.readInt())
            .thenReturn(menuPosition)
            .thenReturn(EXIT_MENU_POSITION);
        when(menuScanner.readLong())
            .thenReturn(randomCourseId)
            .thenReturn(randomStudentId);
    }

    @Test
    void runMenu_shouldCallMainMenuServiceRemoveStudentFromTheCourse_whenMenuPositionIs6() {

        menuPosition = 6;
        long randomStudentId = 8;
        long randomCourseId = 14;
        removeStudentFromTheCourseScenario(menuPosition, randomStudentId, randomCourseId);
        runMenuAfterSystemExit();

        verify(menuService).removeStudentFromCourse(randomStudentId, randomCourseId);
    }

    private void removeStudentFromTheCourseScenario(int menuPosition, long randomStudentId, long randomCourseId) {

        when(menuScanner.readInt())
            .thenReturn(menuPosition)
            .thenReturn(EXIT_MENU_POSITION);
        when(menuScanner.readLong())
            .thenReturn(randomStudentId)
            .thenReturn(randomCourseId);
    }

}

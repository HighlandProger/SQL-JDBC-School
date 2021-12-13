package ua.com.foxminded.menu;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void runMenu_shouldCallMainMenuServiceFindAllGroupsWithLessOrEqualsStudentCount_whenMenuPositionIs1() {

        menuPosition = 1;
        int randomStudentCount = 10;

        when(menuScanner.readInt())
            .thenReturn(menuPosition)
            .thenReturn(randomStudentCount)
            .thenReturn(EXIT_MENU_POSITION);

        mainMenu.runMenu();

        verify(menuService).findAllGroupsWithLessOrEqualsStudentCount(randomStudentCount);
    }


    @Test
    void runMenu_shouldCallMainMenuServiceFindAllStudentsRelatedToCourseWithGivenName_whenMenuPositionIs2() {

        menuPosition = 2;
        String randomCourseName = "math";

        when(menuScanner.readInt())
            .thenReturn(menuPosition)
            .thenReturn(0);

        when(menuScanner.readString())
            .thenReturn(randomCourseName);

        mainMenu.runMenu();

        verify(menuService).findAllStudentsRelatedToCourseWithGivenName(randomCourseName);
    }

    @Test
    void runMenu_shouldCallMainMenuServiceAddNewStudent_whenMenuPositionIs3() {

        menuPosition = 3;
        String randomStudentName = "Jack";
        String randomStudentLastName = "Richer";

        when(menuScanner.readInt())
            .thenReturn(menuPosition)
            .thenReturn(EXIT_MENU_POSITION);

        when(menuScanner.readString())
            .thenReturn(randomStudentName)
            .thenReturn(randomStudentLastName);

        mainMenu.runMenu();

        verify(menuService).addNewStudent(randomStudentName, randomStudentLastName);
    }


    @Test
    void runMenu_shouldCallMainMenuServiceDeleteStudentById_whenMenuPositionIs4() {

        menuPosition = 4;
        long randomStudentId = 8;

        when(menuScanner.readInt())
            .thenReturn(menuPosition)
            .thenReturn(EXIT_MENU_POSITION);
        when(menuScanner.readLong()).thenReturn(randomStudentId);

        mainMenu.runMenu();

        verify(menuService).deleteStudentByStudentId(randomStudentId);
    }

    @Test
    void runMenu_shouldCallMainMenuServiceAddStudentToTheCourse_whenMenuPositionIs5() {

        menuPosition = 5;
        long randomStudentId = 8;
        long randomCourseId = 14;

        when(menuScanner.readInt())
            .thenReturn(menuPosition)
            .thenReturn(EXIT_MENU_POSITION);
        when(menuScanner.readLong())
            .thenReturn(randomCourseId)
            .thenReturn(randomStudentId);

        mainMenu.runMenu();

        verify(menuService).addStudentToTheCourseFromAList(randomStudentId, randomCourseId);
    }


    @Test
    void runMenu_shouldCallMainMenuServiceRemoveStudentFromTheCourse_whenMenuPositionIs6() {

        menuPosition = 6;
        long randomStudentId = 8;
        long randomCourseId = 14;

        when(menuScanner.readInt())
            .thenReturn(menuPosition)
            .thenReturn(EXIT_MENU_POSITION);

        when(menuScanner.readLong())
            .thenReturn(randomStudentId)
            .thenReturn(randomCourseId);
        mainMenu.runMenu();

        verify(menuService).removeStudentFromCourse(randomStudentId, randomCourseId);
    }

}

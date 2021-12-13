package ua.com.foxminded.menu;

import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.TestUtils;
import ua.com.foxminded.domain.Group;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainMenuFormatterTest {

    private String expectedString;
    private String actualString;

    @Test
    void getMenu_shouldReturnMenuString() {

        expectedString =
            "Hi! Choose number of position and press \"ENTER\"\n" +
                "\n" +
                "1. Find all groups with less or equals student count.\n" +
                "2. Find all students related to course with given name\n" +
                "3. Add new student\n" +
                "4. Delete student by STUDENT_ID\n" +
                "5. Add a student to the course (from a list)\n" +
                "6. Remove the student from one of his or her courses\n" +
                "0. Exit\n" +
                "\n" +
                "Enter request number:";
        actualString = MainMenuFormatter.getMenu();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getBackToMainMenuRequest_shouldReturnRequestString() {

        expectedString =
            "\n" +
                "Press any key to return to main menu ...";
        actualString = MainMenuFormatter.getBackToMainMenuRequest();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getValueError_shouldReturnValueErrorString() {

        expectedString =
            "Invalid value\n" +
                "To continue press \"ENTER\"";
        actualString = MainMenuFormatter.getValueError();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getListPositions_shouldReturnNumberedArrayStringFromList() {

        List<Group> randomList = TestUtils.getFiveRandomGroups();
        expectedString =
            "1 - DF-23\n" +
                "2 - GH-52\n" +
                "3 - RG-37\n" +
                "4 - GR-46\n" +
                "5 - HB-21\n";
        actualString = MainMenuFormatter.getTabularFormat(randomList);

        assertEquals(expectedString, actualString);
    }

    @Test
    void getEnterMessage_shouldReturnEnterMessageFromObjectAndFieldNames() {

        String randomObjectName = "student";
        String randomFieldName = "last name";
        expectedString = "Enter student last name:";
        actualString = MainMenuFormatter.getEnterMessage(randomObjectName, randomFieldName);

        assertEquals(expectedString, actualString);
    }

    @Test
    void getAddedStudent_shouldReturnAddedStudentStringFromNameAndLastName() {

        String randomStudentName = "Jack";
        String randomStudentLastName = "Johnson";
        expectedString = "Student Jack Johnson added";
        actualString = MainMenuFormatter.getAddedStudent(randomStudentName, randomStudentLastName);

        assertEquals(expectedString, actualString);
    }

    @Test
    void getDeletedStudent_shouldReturnDeletedStudentStringById() {

        long randomStudentId = 5;
        expectedString = "Student with id = 5 deleted";
        actualString = MainMenuFormatter.getDeletedStudent(randomStudentId);

        assertEquals(expectedString, actualString);
    }

    @Test
    void getAddingStudentToCourseError_shouldReturnErrorDuringAddingStudentToCourse() {

        expectedString = "Error during adding student to course";
        actualString = MainMenuFormatter.getAddingStudentToCourseError();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getAddedStudentToCourse_shouldReturnStudentAddedToCourseStudentStringById_whenStudentAddedToCourseIsFalse() {

        long randomStudentId = 3;
        long randomCourseId = 6;
        expectedString = "Student with id = 3 added to the course with id = 6";
        actualString = MainMenuFormatter.getAddedStudentToCourse
            (randomStudentId, randomCourseId);

        assertEquals(expectedString, actualString);
    }

    @Test
    void getStudentRemovedFromCourse_shouldReturnStudentRemovedFromTheCourseByIds() {

        long randomStudentId = 3;
        long randomCourseId = 6;
        expectedString = "Student with id = 3 removed from the course with id = 6";
        actualString = MainMenuFormatter.getStudentRemovedFromCourse(randomStudentId, randomCourseId);

        assertEquals(expectedString, actualString);
    }
}

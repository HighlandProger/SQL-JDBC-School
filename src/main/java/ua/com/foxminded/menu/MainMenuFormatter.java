package ua.com.foxminded.menu;

import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.util.Arrays;
import java.util.List;

public class MainMenuFormatter {

    static final String NEW_LINE = "\n";
    private static final String SPACE = " ";
    private static final String DASH = "-";
    private static final List<String> MENU_POSITIONS = Arrays.asList(
        "1. Find all groups with less or equals student count.",
        "2. Find all students related to course with given name",
        "3. Add new student",
        "4. Delete student by STUDENT_ID",
        "5. Add a student to the course (from a list)",
        "6. Remove the student from one of his or her courses",
        "0. Exit"
    );

    private MainMenuFormatter() {
    }

    static String getMenu() {

        StringBuilder builder = new StringBuilder(Messages.WELCOME_MESSAGE);
        builder
            .append(NEW_LINE)
            .append(NEW_LINE);
        for (String menuPosition : MENU_POSITIONS) {
            builder
                .append(menuPosition)
                .append(NEW_LINE);
        }
        builder.append(NEW_LINE);
        builder.append(Messages.ENTER_REQUEST_NUMBER_MESSAGE);

        return builder.toString();
    }

    static String getBackToMainMenuRequest() {

        return
            NEW_LINE +
                Messages.RETURN_TO_MAIN_MENU_MESSAGE;
    }

    static String getValueError() {

        return
            Messages.INVALID_VALUE_MESSAGE +
                NEW_LINE +
                Messages.PRESS_ENTER_TO_CONTINUE_MESSAGE;
    }

    static String getTabularFormat(List<?> list) {

        StringBuilder builder = new StringBuilder();

        if (list.isEmpty()){
            builder.append(Messages.NO_SUCH_OBJECTS_MESSAGE).append(NEW_LINE);
            return builder.toString();
        }

        if (list.get(0) instanceof Group) {
            for (int i = 1; i < list.size() + 1; i++) {
                Group group = (Group) list.get(i - 1);
                builder
                    .append(i)
                    .append(SPACE)
                    .append(DASH)
                    .append(SPACE)
                    .append(group.getName())
                    .append(NEW_LINE);
            }
        }

        if (list.get(0) instanceof Student) {
            for (int i = 1; i < list.size() + 1; i++) {
                Student student = (Student) list.get(i - 1);
                builder
                    .append(i)
                    .append(DASH)
                    .append(student.getName())
                    .append(SPACE)
                    .append(student.getLastName())
                    .append(NEW_LINE);
            }
        }

        if (list.get(0) instanceof Course) {
            for (int i = 1; i < list.size() + 1; i++) {
                Course course = (Course) list.get(i - 1);
                builder
                    .append(i)
                    .append(DASH)
                    .append(course.getName())
                    .append(NEW_LINE);
            }
        }

        return builder.toString();
    }

    static String getEnterMessage(String objectName, String fieldName) {
        return "Enter" + SPACE + objectName + SPACE + fieldName + ":";
    }

    static String getAddedStudent(String name, String lastName) {
        return "Student " + name + SPACE + lastName + " added";
    }

    static String getDeletedStudent(long studentId) {
        return Messages.STUDENT_WITH_ID_MESSAGE + studentId + " deleted";
    }

    static String getAddedStudentToCourse(long studentId, long courseId) {
            return Messages.STUDENT_WITH_ID_MESSAGE + studentId + " added to the course with id = " + courseId;
    }

    static String getStudentRemovedFromCourse(long studentId, long courseId) {
        return Messages.STUDENT_WITH_ID_MESSAGE + studentId + " removed from the course with id = " + courseId;
    }

    static String getAddingStudentToCourseError(){
        return Messages.ADDING_STUDENT_TO_COURSE_ERROR;
    }
}

package ua.com.foxminded.menu;


import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.exception.DAOException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final String WELCOME_MESSAGE = "Hi! Choose number of position and press \"ENTER\"";
    private static final String INVALID_VALUE_MESSAGE = "Please enter the number of position!";
    private static final String CONTINUE_MESSAGE = "To continue press \"ENTER\"";
    private static final String STUDENT_WITH_ID_STRING = "Student with id = ";
    private static final String NEW_LINE = "\n";
    private static final String SPACE = " ";
    private static final String STUDENT = "student";
    private static final String COURSE = "course";
    private static final String NAME_FIELD = "name";
    private static final String LAST_NAME_FIELD = "last name";
    private static final String COUNT_FIELD = "count";
    private static final String ID_FIELD = "id";
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> MENU_POSITIONS = Arrays.asList(
        "1. Find all groups with less or equals student count.",
        "2. Find all students related to course with given name",
        "3. Add new student",
        "4. Delete student by STUDENT_ID",
        "5. Add a student to the course (from a list)",
        "6. Remove the student from one of his or her courses",
        "0. Exit"
    );
    private final MainMenuService queries = new MainMenuService();

    public void runMenu() {

        try {
            printMenu();
            String input = readString();
            int numberPosition = Integer.parseInt(input);
            processRequest(numberPosition);
        } catch (NumberFormatException e) {
            backToMainMenuRequest();
            runMenu();
        } catch (IllegalStateException e) {
            printString("\nMenu closed.");
        }

    }

    private String getFormattedString(List<?> list) {

        StringBuilder builder = new StringBuilder();

        if (list.get(0) instanceof Group) {
            for (int i = 1; i < list.size() + 1; i++) {
                Group group = (Group) list.get(i - 1);
                builder
                    .append(i)
                    .append(". ")
                    .append(group.getName())
                    .append("\n");
            }
        }

        if (list.get(0) instanceof Student) {
            for (int i = 1; i < list.size() + 1; i++) {
                Student student = (Student) list.get(i - 1);
                builder
                    .append(i)
                    .append(". ")
                    .append(student.getName())
                    .append(" ")
                    .append(student.getLastName())
                    .append("\n");
            }
        }

        if (list.get(0) instanceof Course) {
            for (int i = 1; i < list.size() + 1; i++) {
                Course course = (Course) list.get(i - 1);
                builder
                    .append(i)
                    .append(". ")
                    .append(course.getName())
                    .append("\n");
            }
        }
        return builder.toString();
    }

    private void processRequest(int requestNumber) {

        switch (requestNumber) {
            case (1):
                findGroupsByStudentCount();
                break;
            case (2):
                findStudentsByCourseName();
                break;
            case (3):
                addStudent();
                break;
            case (4):
                deleteStudentById();
                break;
            case (5):
                addStudentToTheCourse();
                break;
            case (6):
                removeStudentFromTheCourse();
                break;
            case (0):
                closeMenu();
                break;
            default:
                valueError();
                runMenu();
                break;
        }
    }

    private void backToMainMenuRequest() {
        printString("\n0. To back to main menu");
        try {
            int request = Integer.parseInt(readString());
            if (request == 0) {
                runMenu();
            } else {
                valueError();
                backToMainMenuRequest();
            }
        } catch (NumberFormatException e) {
            valueError();
            backToMainMenuRequest();
        }
    }

    private void findGroupsByStudentCount() {

        printString(getEnterMessage(STUDENT, COUNT_FIELD));
        int studentsCount = Integer.parseInt(readString());
        List<Group> groups = queries.findAllGroupsWithLessOrEqualsStudentCount(studentsCount);
        printString(getFormattedString(groups));
        backToMainMenuRequest();
    }

    private void findStudentsByCourseName() {

        printString(getEnterMessage(COURSE, NAME_FIELD));
        String courseName = readString();
        List<Student> students = queries.findAllStudentsRelatedToCourseWithGivenName(courseName);
        printString(getFormattedString(students));
        backToMainMenuRequest();
    }

    private void addStudent() {

        printString(getEnterMessage(STUDENT, NAME_FIELD));
        String name = readString();
        printString(getEnterMessage(STUDENT, LAST_NAME_FIELD));
        String lastName = readString();
        queries.addNewStudent(name, lastName);
        printString("Student " + name + " " + lastName + " added");
        backToMainMenuRequest();
    }

    private void deleteStudentById() {

        printString(getEnterMessage(STUDENT, ID_FIELD));
        long studentId = Long.parseLong(readString());
        queries.deleteStudentByStudentId(studentId);
        printString(STUDENT_WITH_ID_STRING + studentId + " deleted");
        backToMainMenuRequest();
    }

    private void addStudentToTheCourse() {

        List<Course> courseList = queries.getCoursesList();
        printString(getFormattedString(courseList));
        printString(getEnterMessage(COURSE, ID_FIELD));
        long courseId = Long.parseLong(readString());
        printString(getEnterMessage(STUDENT, ID_FIELD));
        long studentId = Long.parseLong(readString());
        try {
            queries.addStudentToTheCourseFromAList(studentId, courseId);
            printString(STUDENT_WITH_ID_STRING + studentId + " added to the course with id = " + courseId);
        } catch (DAOException e) {
            printString(STUDENT_WITH_ID_STRING + studentId + " was already added to the course with id = " + courseId);
        }
    }

    private void removeStudentFromTheCourse() {

        printString(getEnterMessage(STUDENT, ID_FIELD));
        long studentId = Long.parseLong(readString());
        printString(getEnterMessage(COURSE, ID_FIELD));
        long courseId = Long.parseLong(readString());
        queries.removeStudentFromCourse(studentId, courseId);
        printString(STUDENT_WITH_ID_STRING + studentId + " removed from the course with id = " + courseId);
        backToMainMenuRequest();
    }

    private void closeMenu() {

        scanner.close();
        throw new IllegalStateException();
    }

    private void valueError() {

        printString(NEW_LINE);
        printString(INVALID_VALUE_MESSAGE);
        printString(NEW_LINE);
        printString(CONTINUE_MESSAGE);
        readString();
        printString(NEW_LINE);
    }

    private String getEnterMessage(String objectName, String fieldName) {
        return "Enter" + SPACE + objectName + SPACE + fieldName + ":";
    }

    private String readString() {
        return scanner.nextLine();
    }

    private void printMenu() {

        printString(WELCOME_MESSAGE);
        printString(NEW_LINE);
        printString(NEW_LINE);
        for (String menuPosition : MENU_POSITIONS) {
            printString(menuPosition);
            printString(NEW_LINE);
        }
        printString(NEW_LINE);
        printString("Enter requests number:");
    }

    private void printString(String string) {
        System.out.print(string);
    }
}

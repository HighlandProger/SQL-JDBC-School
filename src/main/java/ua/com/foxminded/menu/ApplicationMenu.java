package ua.com.foxminded.menu;


import ua.com.foxminded.domain.Course;
import ua.com.foxminded.exception.DAOException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ApplicationMenu {

    private static final String WELCOME_MESSAGE = "Hi! Choose number of position and press \"ENTER\"";
    private static final String FIRST_MENU_POSITION = "1. Find all groups with less or equals student count.";
    private static final String SECOND_MENU_POSITION = "2. Find all students related to course with given name";
    private static final String THIRD_MENU_POSITION = "3. Add new student";
    private static final String FOURTH_MENU_POSITION = "4. Delete student by STUDENT_ID";
    private static final String FIFTH_MENU_POSITION = "5. Add a student to the course (from a list)";
    private static final String SIXTH_MENU_POSITION = "6. Remove the student from one of his or her courses";
    private static final String STUDENT_WITH_ID_STRING = "Student with id = ";
    private static final String NEW_LINE = "\n";
    private static final String SPACE = " ";
    private static final String STUDENT = "student";
    private static final String COURSE = "course";
    private static final String NAME_FIELD = "name";
    private static final String LAST_NAME_FIELD = "last name";
    private static final String COUNT_FIELD = "count";
    private static final String ID_FIELD = "id";
    private static final List<String> menuPositions = getMenuPositions();
    private final SQLMenuQueries queries = new SQLMenuQueries();

    private static List<String> getMenuPositions() {

        List<String> menuPositions = new ArrayList<>();
        menuPositions.add(FIRST_MENU_POSITION);
        menuPositions.add(SECOND_MENU_POSITION);
        menuPositions.add(THIRD_MENU_POSITION);
        menuPositions.add(FOURTH_MENU_POSITION);
        menuPositions.add(FIFTH_MENU_POSITION);
        menuPositions.add(SIXTH_MENU_POSITION);

        return menuPositions;
    }

    public void runMenu() {

        printMenu();
        processRequest(Integer.parseInt(getInputString()));

    }

    private void processRequest(int requestNumber) {

        if (requestNumber == 1) {
            printString(getEnterMessage(STUDENT, COUNT_FIELD));
            int studentsCount = Integer.parseInt(getInputString());
            printString(queries.findAllGroupsWithLessOrEqualsStudentCount(studentsCount));
        }

        if (requestNumber == 2) {
            printString(getEnterMessage(COURSE, NAME_FIELD));
            String courseName = getInputString();
            printString(queries.findAllStudentsRelatedToCourseWithGivenName(courseName));
        }

        if (requestNumber == 3) {
            printString(getEnterMessage(STUDENT, NAME_FIELD));
            String name = getInputString();
            printString(getEnterMessage(STUDENT, LAST_NAME_FIELD));
            String lastName = getInputString();
            queries.addNewStudent(name, lastName);
            printString("Student " + name + " " + lastName + " added");
        }

        if (requestNumber == 4) {
            printString(getEnterMessage(STUDENT, ID_FIELD));
            long studentId = Long.parseLong(getInputString());
            queries.deleteStudentByStudentId(studentId);
            printString(STUDENT_WITH_ID_STRING + studentId + " deleted");
        }

        if (requestNumber == 5) {
            List<Course> courseList = queries.getCoursesList();
            for (Course course : courseList) {
                System.out.println(course.getId() + " " + course.getName());
            }
            printString(getEnterMessage(COURSE, ID_FIELD));
            long courseId = Long.parseLong(getInputString());
            printString(getEnterMessage(STUDENT, ID_FIELD));
            long studentId = Long.parseLong(getInputString());
            try {
                queries.addStudentToTheCourseFromAList(studentId, courseId);
                printString(STUDENT_WITH_ID_STRING + studentId + " added to the course with id = " + courseId);
            } catch (DAOException e) {
                printString(STUDENT_WITH_ID_STRING + studentId + " was already added to the course with id = " + courseId);
            }

        }

        if (requestNumber == 6) {
            printString(getEnterMessage(STUDENT, ID_FIELD));
            long studentId = Long.parseLong(getInputString());
            printString(getEnterMessage(COURSE, ID_FIELD));
            long courseId = Long.parseLong(getInputString());
            queries.removeStudentFromCourse(studentId, courseId);
            printString(STUDENT_WITH_ID_STRING + studentId + " removed from the course with id = " + courseId);
        }
    }

    private String getEnterMessage (String objectName, String fieldName){
        return "Enter" + SPACE + objectName + SPACE + fieldName + ":";
    }

    private String getInputString() {

        String input = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            input = reader.readLine();
            if (input == null) {
                throw new IOException("Input string is null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    private void printMenu() {

        printString(WELCOME_MESSAGE);
        printString(NEW_LINE);
        printString(NEW_LINE);
        for (String menuPosition : menuPositions) {
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

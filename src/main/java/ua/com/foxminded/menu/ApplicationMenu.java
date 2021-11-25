package ua.com.foxminded.menu;


import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.exception.DAOException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class ApplicationMenu {

    private static final String WELCOME_MESSAGE = "Hi! Choose number of position and press \"ENTER\"";
    private static final String INVALID_VALUE_MESSAGE = "Please enter the number of menu position!";
    private static final String CONTINUE_MESSAGE = "To continue press \"ENTER\"";
    private static final String GO_TO_THE_MENU_MESSAGE = "Press \"ENTER\" to go to the menu";
    private static final String EXIT = "exit";
    private static final String STUDENT_WITH_ID_STRING = "Student with id = ";
    private static final String NEW_LINE = "\n";
    private static final String SPACE = " ";
    private static final String STUDENT = "student";
    private static final String COURSE = "course";
    private static final String NAME_FIELD = "name";
    private static final String LAST_NAME_FIELD = "last name";
    private static final String COUNT_FIELD = "count";
    private static final String ID_FIELD = "id";
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final List<String> MENU_POSITIONS = Arrays.asList(
        "1. Find all groups with less or equals student count.",
        "2. Find all students related to course with given name",
        "3. Add new student",
        "4. Delete student by STUDENT_ID",
        "5. Add a student to the course (from a list)",
        "6. Remove the student from one of his or her courses"
    );
    private final SQLMenuQueries queries = new SQLMenuQueries();

    public void runMenu() {

        while (true) {
            try {
                printMenu();
                String input = readString();

                if (input == null || input.equals(EXIT)) {
                    break;
                }
                int numberPosition = Integer.parseInt(input);
                processRequest(numberPosition);
            } catch (NumberFormatException e) {
                printString(INVALID_VALUE_MESSAGE);
                printString(NEW_LINE);
                printString(CONTINUE_MESSAGE);
                readString();
                printString(NEW_LINE);
                runMenu();
            }
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

        if (requestNumber == 1) {
            printString(getEnterMessage(STUDENT, COUNT_FIELD));
            int studentsCount = Integer.parseInt(readString());
            List<Group> groups = queries.findAllGroupsWithLessOrEqualsStudentCount(studentsCount);
            printString(getFormattedString(groups));
        }


        if (requestNumber == 2) {
            printString(getEnterMessage(COURSE, NAME_FIELD));
            String courseName = readString();
            List<Student> students = queries.findAllStudentsRelatedToCourseWithGivenName(courseName);
            printString(getFormattedString(students));
        }

        if (requestNumber == 3) {
            printString(getEnterMessage(STUDENT, NAME_FIELD));
            String name = readString();
            printString(getEnterMessage(STUDENT, LAST_NAME_FIELD));
            String lastName = readString();
            queries.addNewStudent(name, lastName);
            printString("Student " + name + " " + lastName + " added");
        }

        if (requestNumber == 4) {
            printString(getEnterMessage(STUDENT, ID_FIELD));
            long studentId = Long.parseLong(readString());
            queries.deleteStudentByStudentId(studentId);
            printString(STUDENT_WITH_ID_STRING + studentId + " deleted");
        }

        if (requestNumber == 5) {
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

        if (requestNumber == 6) {
            printString(getEnterMessage(STUDENT, ID_FIELD));
            long studentId = Long.parseLong(readString());
            printString(getEnterMessage(COURSE, ID_FIELD));
            long courseId = Long.parseLong(readString());
            queries.removeStudentFromCourse(studentId, courseId);
            printString(STUDENT_WITH_ID_STRING + studentId + " removed from the course with id = " + courseId);
        }

        printString(NEW_LINE);
        printString(NEW_LINE);
        printString(GO_TO_THE_MENU_MESSAGE);
        readString();
        printString(NEW_LINE);
        printString(NEW_LINE);
    }

    private String getEnterMessage(String objectName, String fieldName) {
        return "Enter" + SPACE + objectName + SPACE + fieldName + ":";
    }

    private String readString() {

        String input = null;
        try {
            input = reader.readLine();
            if (input.equals("exit")) {
                reader.close();
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

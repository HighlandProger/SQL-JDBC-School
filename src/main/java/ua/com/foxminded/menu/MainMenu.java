package ua.com.foxminded.menu;

import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.exception.DAOException;

import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final String EMPTY_STRING = "";
    private static final String STUDENT = "student";
    private static final String COURSE = "course";
    private static final String NAME_FIELD = "name";
    private static final String LAST_NAME_FIELD = "last name";
    private static final String COUNT_FIELD = "count";
    private static final String ID_FIELD = "id";

    private static final Scanner scanner = new Scanner(System.in);

    private final MainMenuService queries = new MainMenuService();

    public void runMenu() {

        try {
            print(View.getMenu());
            String inputString = readString();
            int numberPosition = Integer.parseInt(inputString);
            processRequest(numberPosition);
        } catch (NumberFormatException e) {
            backToMainMenuRequest();
            runMenu();
        } catch (IllegalStateException e) {
            print(View.getClosedMenu());
        }
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
                print(View.getValueError());
                readEmptyString();
                runMenu();
                break;
        }
        backToMainMenuRequest();
    }

    private void backToMainMenuRequest() {
        print(View.getBackToMainMenuRequest());
        try {
            int request = Integer.parseInt(readString());
            if (request == 0) {
                runMenu();
            } else {
                print(View.getValueError());
                readEmptyString();
                backToMainMenuRequest();
            }
        } catch (NumberFormatException e) {
            print(View.getValueError());
            readEmptyString();
            backToMainMenuRequest();
        }
    }

    private void findGroupsByStudentCount() {

        try {
            print(View.getEnterMessage(STUDENT, COUNT_FIELD));
            int studentsCount = Integer.parseInt(readString());
            List<Group> groups = queries.findAllGroupsWithLessOrEqualsStudentCount(studentsCount);
            print(View.getListPositions(groups));
        } catch (NumberFormatException e) {
            print(View.getValueError());
            readEmptyString();
            findGroupsByStudentCount();
        }
    }

    private void findStudentsByCourseName() {

        print(View.getEnterMessage(COURSE, NAME_FIELD));
        String courseName = readString();
        List<Student> students = queries.findAllStudentsRelatedToCourseWithGivenName(courseName);
        print(View.getListPositions(students));
    }

    private void addStudent() {

        String name;
        String lastName;
        while (true) {
            print(View.getEnterMessage(STUDENT, NAME_FIELD));
            name = readString();
            if (name.equals(EMPTY_STRING)) {
                print(Message.EMPTY_VALUE_MESSAGE);
            } else {
                break;
            }
        }

        while (true) {
            print(View.getEnterMessage(STUDENT, LAST_NAME_FIELD));
            lastName = readString();
            if (lastName.equals(EMPTY_STRING)) {
                print(Message.EMPTY_VALUE_MESSAGE);
            } else {
                break;
            }
        }
        queries.addNewStudent(name, lastName);
        print(View.getAddedStudent(name, lastName));
    }

    private void deleteStudentById() {

        print(View.getEnterMessage(STUDENT, ID_FIELD));
        long studentId = Long.parseLong(readString());
        queries.deleteStudentByStudentId(studentId);
        print(View.getDeletedStudent(studentId));
    }

    private void addStudentToTheCourse() {

        List<Course> courseList = queries.getCoursesList();
        print(View.getListPositions(courseList));
        print(View.getEnterMessage(COURSE, ID_FIELD));
        long courseId = Long.parseLong(readString());
        print(View.getEnterMessage(STUDENT, ID_FIELD));
        long studentId = Long.parseLong(readString());
        try {
            queries.addStudentToTheCourseFromAList(studentId, courseId);
            print(View.getAddedStudentToCourse(studentId, courseId, false));
        } catch (DAOException e) {
            print(View.getAddedStudentToCourse(studentId, courseId, true));
        }
    }

    private void removeStudentFromTheCourse() {

        print(View.getEnterMessage(STUDENT, ID_FIELD));
        long studentId = Long.parseLong(readString());
        print(View.getEnterMessage(COURSE, ID_FIELD));
        long courseId = Long.parseLong(readString());
        queries.removeStudentFromCourse(studentId, courseId);
        print(View.getStudentRemovedFromCourse(studentId, courseId));
    }

    private void closeMenu() {
        scanner.close();
        throw new IllegalStateException();
    }

    private void readEmptyString() {
        readString();
        print(View.NEW_LINE);
    }

    private String readString() {
        return scanner.nextLine();
    }

    private void print(String string) {
        System.out.print(string);
    }
}

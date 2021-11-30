package ua.com.foxminded.menu;

import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.exception.DAOException;
import ua.com.foxminded.exception.MainMenuException;

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
    private final MainMenuService mainMenuService = new MainMenuService();
    private Scanner scanner = new Scanner(System.in);

    public void runMenu() {

        try {
            print(View.getMenu());
            processRequest();
        } catch (MainMenuException e) {
            runMenu();
        }
    }

    private void processRequest() {

        print(View.getMenu());
        try {
            int requestNumber = readInt();
            switch (requestNumber) {
                case 1:
                    findGroupsByStudentCount();
                    break;
                case 2:
                    findStudentsByCourseName();
                    break;
                case 3:
                    addStudent();
                    break;
                case 4:
                    deleteStudentById();
                    break;
                case 5:
                    addStudentToTheCourse();
                    break;
                case 6:
                    removeStudentFromTheCourse();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    break;
            }
            backToMainMenuRequest();
        } catch (MainMenuException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    private void findGroupsByStudentCount() {

        print(View.getEnterMessage(STUDENT, COUNT_FIELD));
        List<Group> groups = mainMenuService.findAllGroupsWithLessOrEqualsStudentCount(readInt());
        print(View.getListPositions(groups));
    }

    private void findStudentsByCourseName() {

        print(View.getEnterMessage(COURSE, NAME_FIELD));
        List<Student> students = mainMenuService.findAllStudentsRelatedToCourseWithGivenName(readString());
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
        mainMenuService.addNewStudent(name, lastName);
        print(View.getAddedStudent(name, lastName));
    }

    private void deleteStudentById() {

        print(View.getEnterMessage(STUDENT, ID_FIELD));
        long studentId = readLong();
        mainMenuService.deleteStudentByStudentId(studentId);
        print(View.getDeletedStudent(studentId));
    }

    private void addStudentToTheCourse() {

        List<Course> courseList = mainMenuService.getCoursesList();
        print(View.getListPositions(courseList));
        print(View.getEnterMessage(COURSE, ID_FIELD));
        long courseId = readLong();
        print(View.getEnterMessage(STUDENT, ID_FIELD));
        long studentId = readLong();
        try {
            mainMenuService.addStudentToTheCourseFromAList(studentId, courseId);
            print(View.getAddedStudentToCourse(studentId, courseId, false));
        } catch (DAOException e) {
            print(View.getAddedStudentToCourse(studentId, courseId, true));
        }
    }

    private void removeStudentFromTheCourse() {

        print(View.getEnterMessage(STUDENT, ID_FIELD));
        long studentId = readLong();
        print(View.getEnterMessage(COURSE, ID_FIELD));
        long courseId = readLong();
        mainMenuService.removeStudentFromCourse(studentId, courseId);
        print(View.getStudentRemovedFromCourse(studentId, courseId));
    }

    private void backToMainMenuRequest() {

        print(View.getBackToMainMenuRequest());
        readString();
        runMenu();
    }

    private int readInt() {
        this.scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else throw new MainMenuException("You entered a non-integer number");
    }

    private long readLong() {
        this.scanner = new Scanner(System.in);
        if (scanner.hasNextLong()) {
            return scanner.nextLong();
        } else throw new MainMenuException("You entered a non-long number");
    }

    private String readString() {
        this.scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void print(String string) {
        System.out.print(string);
    }
}

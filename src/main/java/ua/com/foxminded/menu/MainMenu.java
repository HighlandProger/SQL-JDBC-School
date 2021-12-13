package ua.com.foxminded.menu;

import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.exception.DAOException;
import ua.com.foxminded.exception.MainMenuException;

import java.util.List;

public class MainMenu {

    private static final String EMPTY_STRING = "";
    private static final String STUDENT = "student";
    private static final String COURSE = "course";
    private static final String NAME_FIELD = "name";
    private static final String LAST_NAME_FIELD = "last name";
    private static final String COUNT_FIELD = "count";
    private static final String ID_FIELD = "id";
    private MainMenuService mainMenuService = new MainMenuService();
    private MainMenuScanner menuScanner = new MainMenuScanner();

    public void runMenu() {

        try {
            print(MainMenuFormatter.getMenu());
            processRequest();
        } catch (MainMenuException e) {
            runMenu();
        }
    }

    private void processRequest() {

        try {
            int requestNumber = menuScanner.readInt();
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
                default:
                    break;
            }
        } catch (MainMenuException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    private void findGroupsByStudentCount() {

        print(MainMenuFormatter.getEnterMessage(STUDENT, COUNT_FIELD));
        List<Group> groups = mainMenuService.findAllGroupsWithLessOrEqualsStudentCount(menuScanner.readInt());
        print(MainMenuFormatter.getTabularFormat(groups));
        backToMainMenuRequest();
    }

    private void findStudentsByCourseName() {

        print(MainMenuFormatter.getEnterMessage(COURSE, NAME_FIELD));
        List<Student> students = mainMenuService.findAllStudentsRelatedToCourseWithGivenName(menuScanner.readString());
        print(MainMenuFormatter.getTabularFormat(students));
        backToMainMenuRequest();
    }

    private void addStudent() {

        String name;
        String lastName;
        while (true) {
            print(MainMenuFormatter.getEnterMessage(STUDENT, NAME_FIELD));
            name = menuScanner.readString();
            if (name.equals(EMPTY_STRING)) {
                print(Messages.EMPTY_VALUE_MESSAGE);
            } else {
                break;
            }
        }

        while (true) {
            print(MainMenuFormatter.getEnterMessage(STUDENT, LAST_NAME_FIELD));
            lastName = menuScanner.readString();
            if (lastName.equals(EMPTY_STRING)) {
                print(Messages.EMPTY_VALUE_MESSAGE);
            } else {
                break;
            }
        }
        mainMenuService.addNewStudent(name, lastName);
        print(MainMenuFormatter.getAddedStudent(name, lastName));
        backToMainMenuRequest();
    }

    private void deleteStudentById() {

        print(MainMenuFormatter.getEnterMessage(STUDENT, ID_FIELD));
        long studentId = menuScanner.readLong();
        mainMenuService.deleteStudentByStudentId(studentId);
        print(MainMenuFormatter.getDeletedStudent(studentId));
        backToMainMenuRequest();
    }

    private void addStudentToTheCourse() {

        List<Course> courseList = mainMenuService.getCoursesList();
        print(MainMenuFormatter.getTabularFormat(courseList));
        print(MainMenuFormatter.getEnterMessage(COURSE, ID_FIELD));
        long courseId = menuScanner.readLong();
        print(MainMenuFormatter.getEnterMessage(STUDENT, ID_FIELD));
        long studentId = menuScanner.readLong();
        try {
            mainMenuService.addStudentToTheCourseFromAList(studentId, courseId);
            print(MainMenuFormatter.getAddedStudentToCourse(studentId, courseId));
        } catch (DAOException e) {
            print(MainMenuFormatter.getAddingStudentToCourseError());
        }
        backToMainMenuRequest();
    }

    private void removeStudentFromTheCourse() {

        print(MainMenuFormatter.getEnterMessage(STUDENT, ID_FIELD));
        long studentId = menuScanner.readLong();
        print(MainMenuFormatter.getEnterMessage(COURSE, ID_FIELD));
        long courseId = menuScanner.readLong();
        mainMenuService.removeStudentFromCourse(studentId, courseId);
        print(MainMenuFormatter.getStudentRemovedFromCourse(studentId, courseId));
        backToMainMenuRequest();
    }

    private void backToMainMenuRequest() {
        print(MainMenuFormatter.getBackToMainMenuRequest());
        menuScanner.readString();
        runMenu();
    }

    private void print(String string) {
        System.out.print(string);
    }
}

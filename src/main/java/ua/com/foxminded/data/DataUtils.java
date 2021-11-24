package ua.com.foxminded.data;

import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.util.*;

public class DataUtils {

    protected static final List<Course> COURSE_LIST = Arrays.asList(
        new Course("math", "It's about calculating"),
        new Course("biology", "Anatomy, mamals, dinosaurs..."),
        new Course("chemists", "Don't try it at home"),
        new Course("history", "It was rewritten too many times"),
        new Course("physics", "Very cool course. Be careful with electricity"),
        new Course("geography", "London is a capital of Great Britain"),
        new Course("literature", "The war and the piece is very long"),
        new Course("informatics", "I hope they moved from Pascal and QBasic"),
        new Course("painting", "There are too many types of pencils"),
        new Course("drawing", "Great architecture experience")
    );
    private static final List<String> STUDENT_FIRST_NAMES = Arrays.asList(
        "John", "Joe", "Alex", "Steve", "Jessica", "Jeremie", "Bill",
        "Donald", "Mark", "Milena", "Diana", "Daniel", "Elli", "Fernando",
        "Clark", "Jacob", "Harry", "Ron", "Emily", "Stan"
    );
    private static final List<String> STUDENT_LAST_NAMES = Arrays.asList(
        "Kent", "Tramp", "Stalin", "Lenin", "Putin", "Buffet", "Clinton",
        "Russel", "Hanks", "Holland", "Spears", "Durst", "Cruse", "Button",
        "Toyoda", "Honda", "Kawasaki", "Potter", "Bush", "Muchachos"
    );
    private static final int FIRST_UPPERCASE_LETTER_UNICODE = 65;
    private static final int ALPHABET_LETTERS_COUNT = 26;
    private static final String DASH = "-";

    private DataUtils() {
    }

    protected static String getRandomGroupName() {

        return String.valueOf(
            getRandomUppercaseLetter()) +
            getRandomUppercaseLetter() +
            DASH +
            new Random().nextInt(10) +
            new Random().nextInt(10);
    }

    protected static Group getRandomGroupFromList(List<Group> groups) {
        return groups.get(new Random().nextInt(groups.size()));
    }

    protected static String getRandomStudentName() {

        List<String> nameList = STUDENT_FIRST_NAMES;
        return nameList.get(new Random().nextInt(nameList.size()));
    }

    protected static String getRandomStudentLastName() {

        List<String> lastNameList = STUDENT_LAST_NAMES;
        return lastNameList.get(new Random().nextInt(lastNameList.size()));
    }

    protected static Map<Group, List<Student>> getEmptyGroupStudentsMap(List<Group> groups) {

        Map<Group, List<Student>> groupStudentsMap = new HashMap<>();
        for (Group group : groups) {
            List<Student> emptyStudentsList = new ArrayList<>();
            groupStudentsMap.put(group, emptyStudentsList);
        }

        return groupStudentsMap;
    }

    private static char getRandomUppercaseLetter() {
        return (char) (new Random().nextInt(ALPHABET_LETTERS_COUNT) + FIRST_UPPERCASE_LETTER_UNICODE);
    }

}

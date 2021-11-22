package ua.com.foxminded.utils;

import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.util.*;

public class DataUtils {

    private static final int FIRST_UPPERCASE_LETTER_UNICODE = 65;
    private static final int ALPHABET_LETTERS_COUNT = 26;
    private static final String DASH = "-";

    private DataUtils() {
    }

    private static char getRandomUppercaseLetter() {
        return (char) (new Random().nextInt(ALPHABET_LETTERS_COUNT) + FIRST_UPPERCASE_LETTER_UNICODE);
    }

    public static String getRandomGroupName() {

        return String.valueOf(
            getRandomUppercaseLetter()) +
            getRandomUppercaseLetter() +
            DASH +
            new Random().nextInt(10) +
            new Random().nextInt(10);
    }

    public static Group getRandomGroupFromList(List<Group> groups) {
        return groups.get(new Random().nextInt(groups.size()));
    }


    public static String getRandomStudentName() {

        List<String> nameList = getStudentNames();
        return nameList.get(new Random().nextInt(nameList.size()));
    }

    public static String getRandomStudentLastName() {

        List<String> lastNameList = getStudentLastNames();
        return lastNameList.get(new Random().nextInt(lastNameList.size()));
    }

    public static Map<Group, List<Student>> getEmptyGroupStudentsMap(List<Group> groups) {

        Map<Group, List<Student>> groupStudentsMap = new HashMap<>();
        for (Group group : groups) {
            List<Student> emptyStudentsList = new ArrayList<>();
            groupStudentsMap.put(group, emptyStudentsList);
        }

        return groupStudentsMap;
    }

    public static List<Course> getCoursesList() {

        List<Course> courses = new ArrayList<>();
        courses.add(new Course("math", "It's about calculating"));
        courses.add(new Course("biology", "Anatomy, mamals, dinosaurs..."));
        courses.add(new Course("chemists", "Don't try it at home"));
        courses.add(new Course("history", "It was rewritten too many times"));
        courses.add(new Course("physics", "Very cool course. Be careful with electricity"));
        courses.add(new Course("geography", "London is a capital of Great Britain"));
        courses.add(new Course("literature", "The war and the piece is very long"));
        courses.add(new Course("informatics", "I hope they moved from Pascal and QBasic"));
        courses.add(new Course("painting", "There are too many types of pencils"));
        courses.add(new Course("drawing", "Great architecture experience"));

        return courses;
    }

    public static List<String> getStudentNames() {
        List<String> names = new ArrayList<>();
        names.add("John");
        names.add("Joe");
        names.add("Alex");
        names.add("Steve");
        names.add("Jessica");
        names.add("Jeremie");
        names.add("Bill");
        names.add("Donald");
        names.add("Mark");
        names.add("Milena");
        names.add("Diana");
        names.add("Daniel");
        names.add("Elli");
        names.add("Fernando");
        names.add("Clark");
        names.add("Jacob");
        names.add("Harry");
        names.add("Ron");
        names.add("Emily");
        names.add("Stan");

        return names;
    }

    public static List<String> getStudentLastNames() {

        List<String> lastNames = new ArrayList<>();
        lastNames.add("Kent");
        lastNames.add("Tramp");
        lastNames.add("Stalin");
        lastNames.add("Lenin");
        lastNames.add("Putin");
        lastNames.add("Buffet");
        lastNames.add("Clinton");
        lastNames.add("Bush");
        lastNames.add("Russel");
        lastNames.add("Hanks");
        lastNames.add("Holland");
        lastNames.add("Spears");
        lastNames.add("Durst");
        lastNames.add("Cruse");
        lastNames.add("Button");
        lastNames.add("Muchachos");
        lastNames.add("Toyoda");
        lastNames.add("Honda");
        lastNames.add("Kawasaki");
        lastNames.add("Potter");

        return lastNames;
    }
}

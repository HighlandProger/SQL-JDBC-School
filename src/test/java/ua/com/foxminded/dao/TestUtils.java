package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.util.Arrays;
import java.util.List;

public class TestUtils {

    public static List<Student> getFiveRandomStudentsWithoutGroupId() {

        return Arrays.asList(
            new Student(1, null, "Jack", "Richer"),
            new Student(2, null, "John", "Week"),
            new Student(3, null, "Frodo", "Begins"),
            new Student(4, null, "Tom", "Cruz"),
            new Student(5, null, "Andy", "Johnson"));
    }

    public static Student createStudent(long id, Long groupId, String name, String lastName) {
        return new Student(id, groupId, name, lastName);
    }

    public static List<Group> getFiveRandomGroups() {

        return Arrays.asList(
            new Group(1, "DF-23"),
            new Group(2, "GH-52"),
            new Group(3, "RG-37"),
            new Group(4, "GR-46"),
            new Group(5, "HB-21"));
    }

    public static Group createGroup(long id, String name) {
        return new Group(id, name);
    }

    public static List<Course> getFiveRandomCourses() {

        return Arrays.asList(
            new Course(1, "math", "Algebra, Geometry"),
            new Course(2, "biology", "Anatomy, mammals, dinosaurs..."),
            new Course(3, "chemists", "Don't try it at home"),
            new Course(4, "history", "It was rewritten too many times"),
            new Course(5, "physics", "Very cool course. Be careful with electricity"));
    }

    public static Course createCourse(long id, String name, String description) {
        return new Course(id, name, description);
    }
}

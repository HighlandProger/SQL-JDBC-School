package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.util.Arrays;
import java.util.List;

public class TestUtils {

    public static List<Student> getFiveRandomStudentsWithoutGroupId() {

        return Arrays.asList(
            createStudent(1, "Jack", "Richer"),
            createStudent(2, "John", "Week"),
            createStudent(3, "Frodo", "Begins"),
            createStudent(4, "Tom", "Cruz"),
            createStudent(5, "Andy", "Johnson"));
    }

    public static Student createStudent(long id, String name, String lastName) {
        return new Student(id, null, name, lastName);
    }

    public static List<Group> getFiveRandomGroups() {

        return Arrays.asList(
            createGroup(1, "DF-23"),
            createGroup(2, "GH-52"),
            createGroup(3, "RG-37"),
            createGroup(4, "GR-46"),
            createGroup(5, "HB-21"));
    }

    public static Group createGroup(long id, String name) {
        return new Group(id, name);
    }

    public static List<Course> getFiveRandomCourses() {

        return Arrays.asList(
            createCourse(1, "math", "Algebra, Geometry"),
            createCourse(2, "biology", "Anatomy, mammals, dinosaurs..."),
            createCourse(3, "chemists", "Don't try it at home"),
            createCourse(4, "history", "It was rewritten too many times"),
            createCourse(5, "physics", "Very cool course. Be careful with electricity"));
    }

    public static Course createCourse(long id, String name, String description) {
        return new Course(id, name, description);
    }
}

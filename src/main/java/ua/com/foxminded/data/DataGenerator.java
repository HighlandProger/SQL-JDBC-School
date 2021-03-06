package ua.com.foxminded.data;

import ua.com.foxminded.dao.postgresql.PostgreSqlCourseDAO;
import ua.com.foxminded.dao.postgresql.PostgreSqlGroupDAO;
import ua.com.foxminded.dao.postgresql.PostgreSqlStudentDAO;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DataGenerator {

    private static final int GROUPS_COUNT = 10;
    private static final int STUDENTS_COUNT = 200;
    private static final int MAX_ASSIGNED_COURSES_COUNT = 3;
    private final PostgreSqlGroupDAO groupDAO = new PostgreSqlGroupDAO();
    private final PostgreSqlStudentDAO studentDAO = new PostgreSqlStudentDAO();
    private final PostgreSqlCourseDAO courseDAO = new PostgreSqlCourseDAO();

    public void generateData() {
        createGroups();
        createCourses();
        createStudents();

        List<Student> students = studentDAO.getAll();
        List<Group> groups = groupDAO.getAll();
        List<Course> courses = courseDAO.getAll();

        assignStudentsToGroups(students, groups);
        assignStudentsToCourses(students, courses);
    }

    private void createGroups() {

        for (int i = 0; i < GROUPS_COUNT; i++) {
            groupDAO.create(new Group(DataUtils.getRandomGroupName()));
        }
    }

    private void createStudents() {

        for (int i = 0; i < STUDENTS_COUNT; i++) {
            String studentName = DataUtils.getRandomStudentName();
            String studentLastName = DataUtils.getRandomStudentLastName();
            studentDAO.create(new Student(studentName, studentLastName));
        }
    }

    private void createCourses() {

        for (Course course : DataUtils.COURSE_LIST) {
            courseDAO.create(course);
        }
    }

    private void assignStudentsToGroups(List<Student> students, List<Group> groups) {

        Map<Group, List<Student>> groupStudentsMap = DataUtils.getEmptyGroupStudentsMap(groups);

        for (int i = 0; i < students.size(); i++) {

            Group randomGroup = DataUtils.getRandomGroupFromList(groups);
            List<Student> addedStudents = groupStudentsMap.get(randomGroup);
            if (addedStudents.size() == 30) {
                for (Student studentInMap : addedStudents) {
                    studentDAO.assignToGroup(studentInMap, randomGroup);
                }
                groupStudentsMap.remove(randomGroup);
                i--;
            } else {
                addedStudents.add(students.get(i));
                groupStudentsMap.put(randomGroup, addedStudents);
            }
        }

        for (Map.Entry<Group, List<Student>> map : groupStudentsMap.entrySet()) {
            for (Student student : map.getValue()) {
                studentDAO.assignToGroup(student, map.getKey());
            }
        }
    }

    private void assignStudentsToCourses(List<Student> students, List<Course> bufferedCoursesList) {

        for (Student student : students) {
            int randomCoursesCount = new Random().nextInt(MAX_ASSIGNED_COURSES_COUNT);
            List<Course> courses = new ArrayList<>(bufferedCoursesList);
            for (int i = 0; i < randomCoursesCount; i++) {
                Course randomCourse = courses.get(new Random().nextInt(courses.size()));
                studentDAO.assignToCourse(student, randomCourse);
                courses.remove(randomCourse);
            }
        }
    }
}

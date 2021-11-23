package ua.com.foxminded.data;

import ua.com.foxminded.dao.postgres.PostgresSqlCourseDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlGroupDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlStudentDAO;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DataGenerator {

    private static final int COURSES_COUNT = 10;
    private static final int GROUPS_COUNT = 10;
    private static final int STUDENTS_COUNT = 200;
    private static final int MAX_ASSIGNED_COURSES_COUNT = 3;
    private final PostgresSqlGroupDAO groupDAO = new PostgresSqlGroupDAO();
    private final PostgresSqlStudentDAO studentDAO = new PostgresSqlStudentDAO();
    private final PostgresSqlCourseDAO courseDAO = new PostgresSqlCourseDAO();

    public void generateData() {
        createGroups();
        createCourses();
        createStudents();
        assignStudentsToGroups();
        assignCoursesToStudents();
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

        List<Course> courses = DataUtils.getCoursesList();
        for (int i = 0; i < COURSES_COUNT; i++) {
            courseDAO.create(courses.get(i));
        }
    }

    private void assignStudentsToGroups() {

        List<Group> groups = groupDAO.getAll();
        Map<Group, List<Student>> groupStudentsMap = DataUtils.getEmptyGroupStudentsMap(groups);

        List<Student> students = studentDAO.getAll();
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

    private void assignCoursesToStudents() {

        List<Student> students = studentDAO.getAll();
        for (Student student : students) {
            int randomCoursesCount = new Random().nextInt(MAX_ASSIGNED_COURSES_COUNT);
            List<Course> courses = new ArrayList<>(courseDAO.getAll());
            for (int i = 0; i < randomCoursesCount; i++) {
                Course randomCourse = courses.get(new Random().nextInt(courses.size()));
                studentDAO.assignToCourse(randomCourse, student);
                courses.remove(randomCourse);
            }
        }
    }
}

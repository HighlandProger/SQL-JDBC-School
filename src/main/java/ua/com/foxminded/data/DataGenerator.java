package ua.com.foxminded.data;

import ua.com.foxminded.dao.postgres.PostgresSqlCourseDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlGroupDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlStudentDAO;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.utils.StudentUtils;

import java.util.List;
import java.util.Random;

public class DataGenerator {

    private final PostgresSqlGroupDAO groupDAO = new PostgresSqlGroupDAO();
    private final PostgresSqlStudentDAO studentDAO = new PostgresSqlStudentDAO();
    private final PostgresSqlCourseDAO courseDAO = new PostgresSqlCourseDAO();

    public void generateData() {
        create10Groups();
        create10Courses();
        create200Students();
    }

    private void create10Groups() {
        groupDAO.create(new Group("EC-10"));
        groupDAO.create(new Group("EC-20"));
        groupDAO.create(new Group("YR-30"));
        groupDAO.create(new Group("YR-40"));
        groupDAO.create(new Group("FI-50"));
        groupDAO.create(new Group("FI-60"));
        groupDAO.create(new Group("PE-70"));
        groupDAO.create(new Group("PE-80"));
        groupDAO.create(new Group("IN-90"));
        groupDAO.create(new Group("IN-99"));
    }

    private void create200Students() {
        for (int i = 0; i < 200; i++) {
            studentDAO.create(new Student(getRandomStudentName(), getRandomStudentLastName()));
        }
    }

    private String getRandomStudentName() {

        List<String> nameList = StudentUtils.getStudentNames();
        return nameList.get(new Random().nextInt(nameList.size()));
    }

    private String getRandomStudentLastName() {

        List<String> lastNameList = StudentUtils.getStudentLastNames();
        return lastNameList.get(new Random().nextInt(lastNameList.size()));
    }

    private void create10Courses() {

        courseDAO.create(new Course("math", "It's about calculating"));
        courseDAO.create(new Course("biology", "Anatomy, mamals, dinosaurs..."));
        courseDAO.create(new Course("chemists", "Don't try it at home"));
        courseDAO.create(new Course("history", "It was rewritten too many times"));
        courseDAO.create(new Course("physics", "Very cool course. Be careful with electricity"));
        courseDAO.create(new Course("geography", "London is a capital of Great Britain"));
        courseDAO.create(new Course("literature", "The war and the piece is very long"));
        courseDAO.create(new Course("informatics", "I hope they moved from Pascal and QBasic"));
        courseDAO.create(new Course("painting", "There are too many types of pencils"));
        courseDAO.create(new Course("drawing", "Great architecture experience"));
    }
}

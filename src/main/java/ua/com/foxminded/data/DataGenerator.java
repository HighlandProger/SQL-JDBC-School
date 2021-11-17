package ua.com.foxminded.data;

import ua.com.foxminded.dao.postgres.PostgresSqlCourseDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlGroupDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlStudentDAO;
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
        groupDAO.create("EC-10");
        groupDAO.create("EC-20");
        groupDAO.create("YR-30");
        groupDAO.create("YR-40");
        groupDAO.create("FI-50");
        groupDAO.create("FI-60");
        groupDAO.create("PE-70");
        groupDAO.create("PE-80");
        groupDAO.create("IN-90");
        groupDAO.create("IN-99");
    }

    private void create200Students() {
        for (int i = 0; i < 200; i++) {
            studentDAO.create(getRandomStudentName(), getRandomStudentLastName());
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

        courseDAO.create("math", "It's about calculating");
        courseDAO.create("biology", "Anatomy, mamals, dinosaurs...");
        courseDAO.create("chemists", "Don't try it at home");
        courseDAO.create("history", "It was rewritten too many times");
        courseDAO.create("physics", "Very cool course. Be careful with electricity");
        courseDAO.create("geography", "London is a capital of Great Britain");
        courseDAO.create("literature", "The war and the piece is very long");
        courseDAO.create("informatics", "I hope they moved from Pascal and QBasic");
        courseDAO.create("painting", "There are too many types of pencils");
        courseDAO.create("drawing", "Great architecture experience");

    }
}

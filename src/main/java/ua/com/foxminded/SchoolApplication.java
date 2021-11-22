package ua.com.foxminded;

import ua.com.foxminded.dao.SQLQueries;
import ua.com.foxminded.dao.SqlRunner;
import ua.com.foxminded.data.DataGenerator;

public class SchoolApplication {
    public static void main(String[] args) {

        DataGenerator dataGenerator = new DataGenerator();
        SqlRunner sqlRunner = new SqlRunner();
        SQLQueries queries = new SQLQueries();

        sqlRunner.createTables();
        dataGenerator.generateData();

        System.out.println(queries.findAllGroupsWithLessOrEqualsStudentCount(25));
        System.out.println(queries.findAllStudentsRelatedToCourseWithGivenName("chemists"));
        queries.addNewStudent("Jonh", "Snow");
        queries.deleteStudentByStudentId(200);
        queries.addStudentToTheCourseFromAList(28, 5);
        queries.removeStudentFromCourse(28, 5);

    }
}

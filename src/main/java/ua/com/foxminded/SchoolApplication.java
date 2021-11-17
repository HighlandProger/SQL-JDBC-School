package ua.com.foxminded;

import ua.com.foxminded.dao.SqlRunner;
import ua.com.foxminded.data.DataGenerator;

public class SchoolApplication {
    public static void main(String[] args) {

        DataGenerator dataGenerator = new DataGenerator();
        SqlRunner sqlRunner = new SqlRunner();

        sqlRunner.createTables();
        dataGenerator.generateData();

    }
}

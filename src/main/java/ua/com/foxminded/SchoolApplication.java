package ua.com.foxminded;

import ua.com.foxminded.dao.SqlRunner;
import ua.com.foxminded.data.DataGenerator;
import ua.com.foxminded.menu.MainMenu;

public class SchoolApplication {
    public static void main(String[] args) {

        SqlRunner sqlRunner = new SqlRunner();
        sqlRunner.createTables();

        DataGenerator dataGenerator = new DataGenerator();
        dataGenerator.generateData();

        MainMenu menu = new MainMenu();
        menu.runMenu();

    }
}

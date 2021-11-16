package ua.com.foxminded;

import ua.com.foxminded.dao.SqlRunner;

public class SchoolApplication {
    public static void main(String[] args) {

        SqlRunner sqlRunner = new SqlRunner();
        sqlRunner.createTables();

    }
}

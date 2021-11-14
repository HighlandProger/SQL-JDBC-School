package ua.com.foxminded.dao;

import ua.com.foxminded.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlRunner {

    void createTables() {

        try(Connection connection = DriverManager.getConnection(
            Properties.URL, Properties.USER, Properties.PASSWORD);
            PreparedStatement statement = connection.prepareStatement(
            "CREATE TABLE IF NOT EXISTS students(" +
                " id SERIAL NOT NULL PRIMARY KEY," +
                " groupId SERIAL PRIMARY KEY," +
                " name varchar(20)," +
                " lastName varchar(20))"))
        {
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

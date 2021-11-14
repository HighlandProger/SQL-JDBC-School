package ua.com.foxminded.dao.postgres;

import org.apache.log4j.Logger;
import ua.com.foxminded.dao.DAOException;
import ua.com.foxminded.dao.DAOFactory;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.domain.Student;

import java.sql.*;

public class PostgresSqlStudentDao implements StudentDAO {

    private final DAOFactory daoFactory = DAOFactory.getInstance();
    private static final Logger log = Logger.getLogger(PostgresSqlStudentDao.class.getName());
    private static final String ID = "id";
    private static final String GROUP_ID = "groupId";
    private static final String NAME = "name";
    private static final String LAST_NAME = "lastName";

    @Override
    public Student createStudent(int id, int groupId, String name, String lastName) throws DAOException{

        log.info("Creating new student with id = " + id);
        String sql = "INSERT INTO STUDENTS (id, groupId, name, lastName) VALUES (?,?,?,?);";

        Student student = null;
        Connection connection = null;
        PreparedStatement statement =  null;
        ResultSet resultSet = null;

        try {
            openConnectionLog();
            connection = daoFactory.getConnection();
            try{
                createPreparedStatementLog();
                statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, id);
                statement.setInt(2, groupId);
                statement.setString(3, name);
                statement.setString(4, lastName);
                statement.execute();
                try {
                    createResultSetLog();
                    resultSet = statement.getGeneratedKeys();
                    resultSet.next();
                    createStudentToReturnLog();
                    student = new Student(resultSet.getInt(ID), resultSet.getInt(GROUP_ID)
                        ,resultSet.getString(NAME), resultSet.getString(LAST_NAME));
                    log.info("Student with id=" + id + " created");
                }finally {
                    if (resultSet != null){
                        closeResultSet(resultSet);
                    }
                }
            } finally{
                if(statement != null){
                    closePreparedStatement(statement);
                }
            }
        }catch (SQLException e){
            userCreateExceptionLog(e);
        }finally {
            if (connection != null){
                closeConnection(connection);
            }
        }
        log.trace("Returning student");
        return student;
    }

    @Override
    public Student getStudentById(int id) throws DAOException {

        log.trace("Looking for student with id = " + id);
        String sql = "SELECT * FROM STUDENTS WHERE id = ?;";

        Student student = null;
        Connection connection = null;
        PreparedStatement statement =  null;
        ResultSet resultSet = null;

        try {
            openConnectionLog();
            connection = daoFactory.getConnection();
            try{
                createPreparedStatementLog();
                statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                try {
                    createResultSetLog();
                    resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        createStudentToReturnLog();
                        student = new Student(resultSet.getInt(ID), resultSet.getInt(GROUP_ID)
                            ,resultSet.getString(NAME), resultSet.getString(LAST_NAME));
                    }
                }finally {
                    if (resultSet != null){
                        closeResultSet(resultSet);
                    }
                }
            } finally{
                if(statement != null){
                    closePreparedStatement(statement);
                }
            }
        }catch (SQLException e){
            userCreateExceptionLog(e);
        }finally {
            if (connection != null){
                closeConnection(connection);
            }
        }
        if (student == null){
            log.debug("Student is not found");
        }
        else {
            log.trace("Student with id = " + id + " is found");
        }
        log.trace("Returning student");
        return student;
    }

    @Override
    public Student updateStudent(int id, int groupId, String name, String lastName) throws DAOException{

        String sql = "UPDATE STUDENTS SET groupId = ?, name = ?, lastName = ?, WHERE id = ?;";

        Student student = null;
        Connection connection = null;
        PreparedStatement statement =  null;
        ResultSet resultSet = null;

        try {
            openConnectionLog();
            connection = daoFactory.getConnection();
            try{
                createPreparedStatementLog();
                statement = connection.prepareStatement(sql);
                statement.setInt(1, groupId);
                statement.setString(2, name);
                statement.setString(3, lastName);
                statement.setInt(4, id);
                statement.execute();
                try {
                    createResultSetLog();
                    resultSet = statement.getGeneratedKeys();
                    resultSet.next();
                    createStudentToReturnLog();
                    student = new Student(resultSet.getInt(ID), resultSet.getInt(GROUP_ID)
                        ,resultSet.getString(NAME), resultSet.getString(LAST_NAME));
                }finally {
                    if (resultSet != null){
                        closeResultSet(resultSet);
                    }
                }
            } finally{
                if(statement != null){
                    closePreparedStatement(statement);
                }
            }
        }catch (SQLException e){
            userCreateExceptionLog(e);
        }finally {
            if (connection != null){
                closeConnection(connection);
            }
        }
        log.trace("Student with id = " + id + " was updated");
        log.trace("Returning updated student");
        return student;
    }

    @Override
    public void deleteStudentById(int id) {
        log.trace("ToDo");
    }

    private void openConnectionLog(){
        log.trace("Open connection");
    }

    private void createPreparedStatementLog(){
        log.trace("Create prepared statement");
    }

    private void userCreateExceptionLog(Exception e) throws DAOException{
        log.error("Cannot create user", e);
        throw new DAOException("Cannot create user", e);
    }

    private void createResultSetLog(){
        log.trace("Get result set");
    }

    private void createStudentToReturnLog(){
        log.trace("Create student to return");
    }

    private void closeResultSet(ResultSet resultSet){
        try {
            resultSet.close();
            log.trace("result set closed");
        } catch (SQLException e){
            log.error("Cannot close result set", e);
        }
    }

    private void closePreparedStatement(PreparedStatement statement){
        try {
            statement.close();
            log.trace("Statement closed");
        } catch (SQLException e){
            log.error("Cannot close statement", e);
        }
    }

    private void closeConnection(Connection connection){
        try {
            connection.close();
            log.trace("Connection closed");
        } catch (SQLException e){
            log.error("Cannot close connection", e);
        }
    }

}

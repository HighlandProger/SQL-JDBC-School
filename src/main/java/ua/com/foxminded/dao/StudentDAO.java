package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Student;

public interface StudentDAO {

    void create(Student student) throws DAOException;

    Student getById(long id) throws DAOException;

}

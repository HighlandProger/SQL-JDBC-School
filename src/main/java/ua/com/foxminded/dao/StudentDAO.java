package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Student;

public interface StudentDAO {

    Student create(long id, long groupId, String name, String lastName) throws DAOException;

    Student getById(long id) throws DAOException;

    void deleteById(int id);

}

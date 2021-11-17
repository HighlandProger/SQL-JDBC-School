package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Group;

public interface GroupDAO {

    void create(String name) throws DAOException;

    Group getById(long id) throws DAOException;

}

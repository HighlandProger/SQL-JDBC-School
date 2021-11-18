package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Group;

public interface GroupDAO {

    void create(Group group) throws DAOException;

    Group getById(long id) throws DAOException;

}

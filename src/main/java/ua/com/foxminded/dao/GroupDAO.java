package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Group;

public interface GroupDAO {

    Group create(long id, String name);

    Group getById(long id);

}

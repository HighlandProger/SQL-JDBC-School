package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Group;

import java.util.List;

public interface GroupDAO {

    void create(Group group);

    List<Group> getAll();
}

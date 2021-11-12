package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Group;

public interface GroupDAO {

    void createGroup(int id, String name);

    Group getStudentById(int id);

    void updateGroup(int id, String name);

    void deleteGroupById(int id);
}

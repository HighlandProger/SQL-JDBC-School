package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Course;

public interface CourseDAO {

    void create(String name, String description);

    Course getById(long id) throws DAOException;

}

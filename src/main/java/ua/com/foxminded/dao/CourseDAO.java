package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Course;

public interface CourseDAO {

    void create(Course course);

    Course getById(long id) throws DAOException;

}

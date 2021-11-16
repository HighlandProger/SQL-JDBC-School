package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Course;

public interface CourseDAO {

    Course create(long id, String name, String description);

    Course getById(long id);

}

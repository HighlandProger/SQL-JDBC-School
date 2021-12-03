package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Course;

import java.util.List;

public interface CourseDAO {

    Course create(Course course);

    Course getById(long id);

    List<Course> getAll();
}

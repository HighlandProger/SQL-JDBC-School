package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Course;

public interface CourseDAO {

    void createCourse(int id, String name, String description);

    Course getCourseById(int id);

    void updateCourse(int id, String name, String description);

    void deleteGroupById(int id);
}

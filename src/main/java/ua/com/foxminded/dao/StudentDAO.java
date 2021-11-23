package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.util.List;

public interface StudentDAO {

    void create(Student student);

    Student getById(long id);

    List<Student> getAll();

    void assignToGroup(Student student, Group group);

    void delete(long id);

    void assignToCourse(Course course, Student student);

    List<Student> getByCourseName(String courseName);

    void deleteCourseRelation(long studentId, long courseId);
}

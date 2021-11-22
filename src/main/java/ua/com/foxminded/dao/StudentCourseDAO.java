package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Student;

import java.util.List;

public interface StudentCourseDAO {

    void assignCourseToStudent(Course course, Student student);

    List<Student> getStudentsByCourseName(String courseName);

    void delete(long studentId, long courseId);
}

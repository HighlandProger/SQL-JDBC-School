package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.util.List;

public interface StudentDAO {

    void create(Student student);

    Student getById(long id);

    List<Student> getAll();

    void assignStudentToGroup(Student student, Group group);

    void delete(long id);
}

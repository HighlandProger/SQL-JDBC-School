package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Student;

public interface StudentDAO {

    void createStudent(int id, int groupId, String name, String lastName);

    Student getStudentById(int id);

    void updateStudent(int id, int groupId, String name, String lastName);

    void deleteStudentById(int id);

}

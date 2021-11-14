package ua.com.foxminded.dao;

import ua.com.foxminded.domain.Student;

public interface StudentDAO {

    Student createStudent(int id, int groupId, String name, String lastName) throws DAOException;

    Student getStudentById(int id) throws DAOException;

    Student updateStudent(int id, int groupId, String name, String lastName) throws DAOException;

    void deleteStudentById(int id);

}

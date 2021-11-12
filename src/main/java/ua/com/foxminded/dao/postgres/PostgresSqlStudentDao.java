package ua.com.foxminded.dao.postgres;

import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.domain.Student;

public class PostgresSqlStudentDao implements StudentDAO {

    @Override
    public void createStudent(int id, int groupId, String name, String lastName) {

    }

    @Override
    public Student getStudentById(int id) {
        return null;
    }

    @Override
    public void updateStudent(int id, int groupId, String name, String lastName) {

    }

    @Override
    public void deleteStudentById(int id) {

    }

}

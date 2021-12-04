package ua.com.foxminded.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.postgres.PostgresSqlGroupDAO;
import ua.com.foxminded.dao.postgres.PostgresSqlStudentDAO;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupDAOTest {

    private final SqlRunner sqlRunner = new SqlRunner();
    private final PostgresSqlGroupDAO groupDAO = new PostgresSqlGroupDAO();
    private final PostgresSqlStudentDAO studentDAO = new PostgresSqlStudentDAO();

    @BeforeEach
    void initTables() {

        sqlRunner.createTables();
        assertEquals(0, groupDAO.getAll().size());
    }

    @Test
    void create() {

        Group expectedGroup = TestUtils.getGroup();
        Group actualGroup = groupDAO.create(expectedGroup);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void getAll() {

        List<Group> expectedGroups = TestUtils.getGroups();
        for (Group group : expectedGroups) {
            groupDAO.create(group);
        }
        List<Group> actualGroups = groupDAO.getAll();

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void getLessOrEqualsByStudentsCount() {

        List<Student> students = TestUtils.getStudentsWithoutGroupId();
        List<Group> groups = TestUtils.getGroups();
        for (Student student : students) {
            studentDAO.create(student);
        }
        for (Group group : groups) {
            groupDAO.create(group);
        }

        studentDAO.assignToGroup(students.get(0), groups.get(0));
        studentDAO.assignToGroup(students.get(1), groups.get(0));
        studentDAO.assignToGroup(students.get(2), groups.get(0));
        studentDAO.assignToGroup(students.get(3), groups.get(1));
        studentDAO.assignToGroup(students.get(4), groups.get(2));

        int maxStudentCount = 2;
        int expectedGroupsSize = 2;
        int actualGroupsSize = groupDAO.getLessOrEqualsByStudentsCount(maxStudentCount).size();

        assertEquals(expectedGroupsSize, actualGroupsSize);
    }
}

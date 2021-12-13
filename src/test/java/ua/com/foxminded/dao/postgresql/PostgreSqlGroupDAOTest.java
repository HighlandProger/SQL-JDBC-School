package ua.com.foxminded.dao.postgresql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.SqlRunner;
import ua.com.foxminded.dao.TestUtils;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostgreSqlGroupDAOTest {

    private final SqlRunner sqlRunner = new SqlRunner();
    private final PostgreSqlGroupDAO groupDAO = new PostgreSqlGroupDAO();
    private final PostgreSqlStudentDAO studentDAO = new PostgreSqlStudentDAO();

    @BeforeEach
    void initTables() {
        sqlRunner.createTables();
    }

    @Test
    void create_shouldCreateGroup() {

        assertEquals(0, groupDAO.getAll().size());
        Group expectedGroup = TestUtils.createGroup(1, "AB-10");
        Group actualGroup = groupDAO.create(expectedGroup);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void getAll_shouldReturnAllGroups() {

        assertEquals(0, groupDAO.getAll().size());
        List<Group> expectedGroups = TestUtils.getFiveRandomGroups();
        for (Group group : expectedGroups) {
            groupDAO.create(group);
        }
        List<Group> actualGroups = groupDAO.getAll();

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void getLessOrEqualsByStudentsCount_shouldReturnGroups() {

        assertEquals(0, groupDAO.getAll().size());
        assertEquals(0, studentDAO.getAll().size());
        List<Student> students = TestUtils.getFiveRandomStudentsWithoutGroupId();
        List<Group> groups = TestUtils.getFiveRandomGroups();
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

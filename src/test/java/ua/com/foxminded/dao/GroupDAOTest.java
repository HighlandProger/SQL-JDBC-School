package ua.com.foxminded.dao;

import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.postgres.PostgresSqlGroupDAO;
import ua.com.foxminded.domain.Group;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupDAOTest {

    private final SqlRunner sqlRunner = new SqlRunner();
    private final PostgresSqlGroupDAO groupDAO = new PostgresSqlGroupDAO();

    @Test
    void create() {

        sqlRunner.createTables();
        Group expectedGroup = new Group(1, "AB-32");
        Group actualGroup = groupDAO.create(expectedGroup);
        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void getAll() {

        sqlRunner.createTables();
        List<Group> expectedGroups = Arrays.asList(
            new Group(1, "AB-10"),
            new Group(2, "AF-49"),
            new Group(3, "DE-83"),
            new Group(4, "GC-23"));

        for (Group group : expectedGroups){
            groupDAO.create(group);
        }

        List<Group> actualGroups = groupDAO.getAll();
        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void getLessOrEqualsByStudentsCount() {

    }
}

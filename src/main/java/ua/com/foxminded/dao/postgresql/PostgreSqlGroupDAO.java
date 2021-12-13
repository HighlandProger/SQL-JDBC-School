package ua.com.foxminded.dao.postgresql;

import ua.com.foxminded.dao.DAOFactory;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgreSqlGroupDAO implements GroupDAO {

    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    public Group create(Group group) {

        String sql = "INSERT INTO groups (name) VALUES (?) RETURNING groups.*;";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setStatementParameters(statement, group.getName());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.next()) {
                throw new DAOException("Group is not found");
            }
            return createGroupFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Cannot create group", e);
        }
    }

    @Override
    public List<Group> getAll() {

        List<Group> groups = new ArrayList<>();
        String sql = "SELECT * FROM groups;";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                groups.add(createGroupFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            throw new DAOException("Cannot get groups", e);
        }

        return groups;
    }

    @Override
    public List<Group> getLessOrEqualsByStudentsCount(int studentsCount) {

        List<Group> groups = new ArrayList<>();
        String sql = "SELECT g.* FROM students s, groups g WHERE s.group_id=g.id GROUP BY g.id HAVING count(s.*)<=?;";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentsCount);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                groups.add(createGroupFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            throw new DAOException("Cannot get groups by students count", e);
        }

        return groups;
    }

    private void setStatementParameters(PreparedStatement statement, String name) throws SQLException {
        statement.setString(1, name);
    }

    private Group createGroupFromResultSet(ResultSet resultSet) throws SQLException {
        return new Group(
            resultSet.getInt(ID),
            resultSet.getString(NAME));
    }

}

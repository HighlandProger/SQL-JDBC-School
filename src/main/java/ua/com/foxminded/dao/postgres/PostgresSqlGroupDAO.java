package ua.com.foxminded.dao.postgres;

import ua.com.foxminded.dao.DAOException;
import ua.com.foxminded.dao.DAOFactory;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.domain.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresSqlGroupDAO implements GroupDAO {

    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    public void create(Group group) {

        String name = group.getName();
        String sql = "INSERT INTO groups (name) VALUES (?);";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setStatementParameters(statement, name);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Cannot create group", e);
        }
    }

    @Override
    public Group getById(long id) throws DAOException {

        String sql = "SELECT * FROM groups WHERE id = ?;";

        ResultSet resultSet = null;
        Group group = null;

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                group = createGroupFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            throw new DAOException("Cannot get group by id", e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (group == null) {
            throw new DAOException("Group is not found");
        }

        return group;
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

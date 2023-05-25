package Models.Serie;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static util.Utils.stringFromList;

public class SerieDatabase {
    static Connection connection;

    static {
        try {
            connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int add(Serie serie) throws SQLException {
        String query = "INSERT INTO serii(name, grupe) VALUES(?,?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, serie.getName());
        preparedStatement.setString(2, stringFromList(serie.getGrupe()));
        return preparedStatement.executeUpdate();
    }

    public int update(Serie serie) throws SQLException {
        String query = "UPDATE serii SET \"name\"=?, grupe=? WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, serie.getName());
        preparedStatement.setString(2, stringFromList(serie.getGrupe()));
        preparedStatement.setLong(3, serie.getId());
        return preparedStatement.executeUpdate();
    }

    public int delete(Long id) throws SQLException {
        String query = "DELETE FROM serii WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        return preparedStatement.executeUpdate();
    }

    public Serie getById(Long id) throws SQLException {
        String query = "SELECT * FROM serii WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        var resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return new Serie(resultSet);
        }
        return null;
    }

    public List<Serie> getAll() throws SQLException {
        String query = "SELECT * FROM serii";
        var preparedStatement = connection.prepareStatement(query);
        var resultSet = preparedStatement.executeQuery();
        List<Serie> serii = new java.util.ArrayList<>();
        while(resultSet.next()){
            serii.add(new Serie(resultSet));
        }
        return serii;
    }


}

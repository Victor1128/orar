package model.Materie;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MaterieDatabase {
    static Connection connection;

    static {
        try {
            connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int add(Materie materie) throws SQLException {
        String query = "INSERT INTO materii(name, nrCredite, nrOreCurs, nrOreSeminar, nrOreLaborator) VALUES(?, ?, ?, ?, ?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, materie.getName());
        preparedStatement.setInt(2, materie.getNrCredite());
        preparedStatement.setInt(3, materie.getNrOreCurs());
        preparedStatement.setInt(4, materie.getNrOreSeminar());
        preparedStatement.setInt(5, materie.getNrOreLaborator());
        return preparedStatement.executeUpdate();
    }

    public int update(Materie materie) throws SQLException {
        String query = "UPDATE materii SET name=?, nrCredite=?, nrOreCurs=?, nrOreSeminar=?, nrOreLaborator=? WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, materie.getName());
        preparedStatement.setInt(2, materie.getNrCredite());
        preparedStatement.setInt(3, materie.getNrOreCurs());
        preparedStatement.setInt(4, materie.getNrOreSeminar());
        preparedStatement.setInt(5, materie.getNrOreLaborator());
        preparedStatement.setLong(6, materie.getId());
        return preparedStatement.executeUpdate();
    }

    public int delete(Long id) throws SQLException {
        String query = "DELETE FROM materii WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        return preparedStatement.executeUpdate();
    }

    public List<Materie> getAll() throws SQLException {
        String query = "SELECT * FROM materii";
        var preparedStatement = connection.prepareStatement(query);
        var resultSet = preparedStatement.executeQuery();
        List<Materie> materii = new java.util.ArrayList<>();
        while(resultSet.next()){
            materii.add(new Materie(resultSet));
        }
        return materii;
    }

    public Materie getById(long id) throws SQLException {
        String query = "SELECT * FROM materii WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        var resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return new Materie(resultSet);
        }
        return null;
    }

    public Materie getByName(String name) throws SQLException{
      String query = "SELECT * FROM materii WHERE name=?";
      var preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, name);
      var resultSet = preparedStatement.executeQuery();
      if(resultSet.next()){
        return new Materie(resultSet);
      }
      return null;
    }

}

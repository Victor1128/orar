package model.Profesor;

import model.Materie.Materie;
import util.Dao;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfesorDatabase implements Dao<Profesor> {
    static Connection connection;

    static {
        try {
            connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int add(Profesor profesor) throws SQLException {
        String query = "INSERT INTO profesori(name) VALUES(?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, profesor.getName());
        int n = preparedStatement.executeUpdate();
        query = "SELECT LAST_INSERT_ID()";
        preparedStatement = connection.prepareStatement(query);
        var resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return 0;
        }
        long profId = resultSet.getLong(1);
        profesor.setId(profId);
        n &= insertMateriiForProfesor(profesor);
        return n;
    }

    public void update(Profesor profesor) throws SQLException {
        String query = "UPDATE profesori SET name=? WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, profesor.getName());
        preparedStatement.setLong(2, profesor.getId());
        int n = preparedStatement.executeUpdate();
        n &= insertMateriiForProfesor(profesor);
    }

    public int delete(Long id) throws SQLException {
        String query = "DELETE FROM profesori WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        return preparedStatement.executeUpdate();
    }

  public List<Profesor> getAll() throws SQLException {
        String query = "SELECT * FROM profesori";
        var preparedStatement = connection.prepareStatement(query);
        var resultSet = preparedStatement.executeQuery();
        List<Profesor> profesori = new java.util.ArrayList<>();
        while(resultSet.next()){
            Profesor p = new Profesor(resultSet);
            p.setMaterii(getMateriiByProfesorId(p.getId()));
            profesori.add(p);
        }
        return profesori;
    }

    public Profesor getById(Long id) throws SQLException {
        String query = "SELECT * FROM profesori WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        var resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            Profesor profesor = new Profesor(resultSet);
            profesor.setMaterii(getMateriiByProfesorId(profesor.getId()));
            return profesor;
        }
        return null;
    }

    public Profesor getByName(String name) throws SQLException{
      String query = "SELECT * FROM profesori WHERE name=?";
      var preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, name);
      var resultSet = preparedStatement.executeQuery();
      if(resultSet.next()){
        Profesor profesor = new Profesor(resultSet);
        profesor.setMaterii(getMateriiByProfesorId(profesor.getId()));
        return profesor;
      }
      return null;
    }
    private List<Materie> getMateriiByProfesorId(long id) throws SQLException {
        String query = "SELECT * FROM materii JOIN profesor_materie ON materii.id = profesor_materie.materie_id WHERE profesor_materie.profesor_id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        var materiiResultSet = preparedStatement.executeQuery();
        List<Materie> materii = new ArrayList<>();
        while(materiiResultSet.next()){
            materii.add(new Materie(materiiResultSet));
        }
        return materii;
    }

    private int insertMateriiForProfesor(Profesor profesor) throws SQLException {
      int n = 1;
      Long profId = profesor.getId();
      for (var materie : profesor.getMaterii()){
        String query = "INSERT INTO profesor_materie(profesor_id, materie_id) VALUES(?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, profId);
        preparedStatement.setLong(2, materie.getId());
        n &= preparedStatement.executeUpdate();
      }
      return n;
    }
}

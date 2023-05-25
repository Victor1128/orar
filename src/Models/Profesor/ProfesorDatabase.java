package Models.Profesor;

import Models.Materie.Materie;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProfesorDatabase {
    static Connection connection;

    static {
        try {
            connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int add(Profesor profesor) throws SQLException {
        String query = "INSERT INTO profesori(\"name\") VALUES(?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, profesor.getName());
        int n = preparedStatement.executeUpdate();
        for (var materie : profesor.getMaterii()){
            query = "INSERT INTO profesor_materie(profesor_id, materie_id) VALUES(?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, profesor.getId());
            preparedStatement.setLong(2, materie.getId());
            n = n & preparedStatement.executeUpdate();
        }
        return n;
    }

    public int update(Profesor profesor) throws SQLException {
        String query = "UPDATE profesori SET name=? WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, profesor.getName());
        preparedStatement.setLong(2, profesor.getId());
        return preparedStatement.executeUpdate();
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

    public Profesor getById(long id) throws SQLException {
        String query = "SELECT * FROM profesori WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        var resultSet = preparedStatement.executeQuery();
        query = "SELECT * FROM materii INNER JOIN profesor_materie ON materii.id = profesor_materie.materie_id WHERE profesor_materie.profesor_id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        var materiiResultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            Profesor profesor = new Profesor(resultSet);
            while(materiiResultSet.next()){
                profesor.addMaterie(new Materie(materiiResultSet));
            }
            return profesor;
        }
        return null;
    }

    private List<Materie> getMateriiByProfesorId(long id) throws SQLException {
        String query = "SELECT * FROM materii INNER JOIN profesor_materie ON materii.id = profesor_materie.materie_id WHERE profesor_materie.profesor_id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        var materiiResultSet = preparedStatement.executeQuery();
        List<Materie> materii = new java.util.ArrayList<>();
        while(materiiResultSet.next()){
            materii.add(new Materie(materiiResultSet));
        }
        return materii;
    }
}

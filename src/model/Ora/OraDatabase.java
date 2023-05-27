package model.Ora;

import model.Materie.Materie;
import model.Materie.MaterieDatabase;
import model.Profesor.Profesor;
import model.Profesor.ProfesorDatabase;
import model.Sala.Sala;
import model.Sala.SalaDatabase;
import model.Serie.Serie;
import model.Serie.SerieDatabase;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OraDatabase {
    static Connection connection;
    private MaterieDatabase materieDatabase = new MaterieDatabase();
    private ProfesorDatabase profesorDatabase = new ProfesorDatabase();
    private SalaDatabase salaDatabase = new SalaDatabase();
    private SerieDatabase serieDatabase = new SerieDatabase();

    static {
        try {
            connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int addCurs(Curs curs) throws SQLException{
        int id = insertOra(curs);
        String query = "INSERT INTO cursuri(id, serie_id) VALUES(?, ?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, Math.toIntExact(curs.getSerie().getId()));
        return preparedStatement.executeUpdate();
    }

    public int addSeminar(Seminar seminar) throws SQLException{
        int id = insertOra(seminar);
        String query = "INSERT INTO seminare(id, grupa) VALUES(?, ?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, seminar.getGrupa());
        return preparedStatement.executeUpdate();
    }

    public int addLaborator(Laborator laborator) throws SQLException{
        int id = insertOra(laborator);
        String query = "INSERT INTO laboratoare(id, grupa) VALUES(?, ?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, laborator.getGrupa());
        return preparedStatement.executeUpdate();
    }

    public int delete(Long id) throws SQLException {
        String query = "DELETE FROM ore WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        return preparedStatement.executeUpdate();
    }

    public Curs getCurs(int id) throws SQLException {
        String query = "SELECT * FROM ore JOIN cursuri ON ore.id = cursuri.id WHERE ore.id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        var resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            Profesor profesor = profesorDatabase.getById(resultSet.getInt("profesor_id"));
            Materie materie = materieDatabase.getById(resultSet.getInt("materie_id"));
            Sala sala = salaDatabase.getAmf(resultSet.getInt("sala_id"));
            Serie serie = serieDatabase.getById(resultSet.getLong("serie_id"));
            return new Curs(resultSet, profesor, materie, sala, serie);
        }
        return null;
    }

    public Seminar getSeminar(int id) throws SQLException {
        String query = "SELECT * FROM ore JOIN seminare ON ore.id = seminare.id WHERE ore.id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        var resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            Profesor profesor = profesorDatabase.getById(resultSet.getInt("profesor_id"));
            Materie materie = materieDatabase.getById(resultSet.getInt("materie_id"));
            Sala sala = salaDatabase.getAmf(resultSet.getInt("sala_id"));
            String grupa = resultSet.getString("grupa");
            return new Seminar(resultSet, profesor, materie, sala, grupa);
        }
        return null;
    }

    public Laborator getLaborator(int id) throws SQLException {
        String query = "SELECT * FROM ore JOIN laboratoare ON ore.id = laboratoare.id WHERE ore.id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        var resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            Profesor profesor = profesorDatabase.getById(resultSet.getInt("profesor_id"));
            Materie materie = materieDatabase.getById(resultSet.getInt("materie_id"));
            Sala sala = salaDatabase.getAmf(resultSet.getInt("sala_id"));
            String grupa = resultSet.getString("grupa");
            return new Laborator(resultSet, profesor, materie, sala, grupa);
        }
        return null;
    }

    public List<Curs> getAllCurs() throws SQLException {
            String query = "SELECT * FROM ore JOIN cursuri ON ore.id = cursuri.id";
            var preparedStatement = connection.prepareStatement(query);
            var resultSet = preparedStatement.executeQuery();
            List<Curs> cursuri = new ArrayList<>();
            while(resultSet.next()){
                Profesor profesor = profesorDatabase.getById(resultSet.getInt("profesor_id"));
                Materie materie = materieDatabase.getById(resultSet.getInt("materie_id"));
                Sala sala = salaDatabase.getAmf(resultSet.getInt("sala_id"));
                Serie serie = serieDatabase.getById(resultSet.getLong("serie_id"));
                cursuri.add(new Curs(resultSet, profesor, materie, sala, serie));
            }
            return cursuri;
        }

    public List<Seminar> getAllSeminar() throws SQLException {
        String query = "SELECT * FROM ore JOIN seminare ON ore.id = seminare.id";
        var preparedStatement = connection.prepareStatement(query);
        var resultSet = preparedStatement.executeQuery();
        List<Seminar> seminare = new ArrayList<>();
        while(resultSet.next()){
            Profesor profesor = profesorDatabase.getById(resultSet.getInt("profesor_id"));
            Materie materie = materieDatabase.getById(resultSet.getInt("materie_id"));
            Sala sala = salaDatabase.getSalaSeminar(resultSet.getInt("sala_id"));
            String grupa = resultSet.getString("grupa");
            seminare.add(new Seminar(resultSet, profesor, materie, sala, grupa));
        }
        return seminare;
    }

    public List<Laborator> getAllLaborator() throws SQLException {
        String query = "SELECT * FROM ore JOIN laboratoare ON ore.id = laboratoare.id";
        var preparedStatement = connection.prepareStatement(query);
        var resultSet = preparedStatement.executeQuery();
        List<Laborator> laboratoare = new ArrayList<>();
        while(resultSet.next()){
            Profesor profesor = profesorDatabase.getById(resultSet.getInt("profesor_id"));
            Materie materie = materieDatabase.getById(resultSet.getInt("materie_id"));
            Sala sala = salaDatabase.getSalaLaborator(resultSet.getInt("sala_id"));
            String grupa = resultSet.getString("grupa");
            laboratoare.add(new Laborator(resultSet, profesor, materie, sala, grupa));
        }
        return laboratoare;
    }

    public List<Ora> getAll() throws SQLException {
        List<Ora> ore = new ArrayList<>();
        ore.addAll(getAllCurs());
        ore.addAll(getAllLaborator());
        ore.addAll(getAllSeminar());
        return ore;
    }

    private int insertOra(Ora ora) throws SQLException{
        String query = "INSERT INTO ore (ora_inceput, ora_sfarsit, ziua_saptamanii, sala_id, materie_id, profesor_id) VALUES(?, ?, ?, ?, ?, ?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, ora.getOraInceput());
        preparedStatement.setInt(2, ora.getOraSfarsit());
        preparedStatement.setInt(3, ora.getZiuaSaptamanii());
        preparedStatement.setInt(4, Math.toIntExact(ora.getSala().getId()));
        preparedStatement.setInt(5, Math.toIntExact(ora.getMaterie().getId()));
        preparedStatement.setInt(6, Math.toIntExact(ora.getProfesor().getId()));
        preparedStatement.executeUpdate();
        query = "SELECT LAST_INSERT_ID()";
        preparedStatement = connection.prepareStatement(query);
        var resultSet =  preparedStatement.executeQuery();
        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }
}

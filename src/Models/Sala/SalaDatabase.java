package Models.Sala;

import Models.Materie.Materie;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaDatabase {
    static Connection connection;

    static {
        try {
            connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int addAmfiteatru(Amfiteatru sala) throws SQLException{
         return insertSala(sala, "amfiteatre");
    }

    public int addSalaSeminar(SalaSeminar sala) throws SQLException{
        return insertSala(sala, "sali_seminar");
    }

    public int addLaborator(SalaLaborator sala) throws SQLException{
        int idSala = insertSala(sala, "sali_laborator");
        for (var materie : sala.getMateriiPosibile()) {
            String query = "INSERT INTO sali_laborator_materii(sala_id, materie_id) VALUES(?, ?)";
            var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, idSala);
            preparedStatement.setLong(2, materie.getId());

            preparedStatement.executeUpdate();
        }
        return idSala;
    }

    public int delete(int id) throws SQLException {
        String query = "DELETE FROM sali WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        return preparedStatement.executeUpdate();
    }

    public Amfiteatru getAmf(int id) throws SQLException{
        String query = "SELECT * FROM sali JOIN amfiteatre ON sali.id = amfiteatre.id WHERE sali.id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        var resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return new Amfiteatru(resultSet);
        }
        return null;
    }

    public SalaSeminar getSalaSeminar(int id) throws SQLException {
        String query = "SELECT * FROM sali JOIN sali_seminar ON sali.id = sali_seminar.id WHERE sali.id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        var resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return new SalaSeminar(resultSet);
        }
        return null;
    }

    public SalaLaborator getSalaLaborator(int id) throws SQLException {
        String query = "SELECT * FROM sali JOIN sali_laborator ON sali.id = sali_laborator.id WHERE sali.id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        var resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return new SalaLaborator(resultSet);
        }
        return null;
    }

    public List<Amfiteatru> getAllAmf() throws SQLException {
        String query = "SELECT * FROM sali JOIN amfiteatre ON sali.id = amfiteatre.id";
        var preparedStatement = connection.prepareStatement(query);
        var resultSet = preparedStatement.executeQuery();
        List<Amfiteatru> sali = new java.util.ArrayList<>();
        while(resultSet.next()){
            sali.add(new Amfiteatru(resultSet));
        }
        return sali;
    }

    public List<SalaSeminar> getAllSaliSeminar() throws SQLException {
        String query = "SELECT * FROM sali JOIN sali_seminar ON sali.id = sali_seminar.id";
        var preparedStatement = connection.prepareStatement(query);
        var resultSet = preparedStatement.executeQuery();
        List<SalaSeminar> sali = new java.util.ArrayList<>();
        while(resultSet.next()){
            sali.add(new SalaSeminar(resultSet));
        }
        return sali;
    }

    public List<SalaLaborator> getAllSaliLaborator() throws SQLException {
        String query = "SELECT * FROM sali JOIN sali_laborator ON sali.id = sali_laborator.id";
        var preparedStatement = connection.prepareStatement(query);
        var resultSet = preparedStatement.executeQuery();
        List<SalaLaborator> sali = new java.util.ArrayList<>();
        while(resultSet.next()){
            SalaLaborator salaLaborator = new SalaLaborator(resultSet);
            query = "SELECT * FROM sali_laborator_materii JOIN materii ON sali_laborator_materii.materie_id = materii.id WHERE sala_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, salaLaborator.getId());
            var materiiResultSet = preparedStatement.executeQuery();
            List<Materie> materii = new ArrayList<>();
            while(materiiResultSet.next()){
                materii.add(new Materie(materiiResultSet));
            }
            salaLaborator.setMateriiPosibile(materii);
            sali.add(salaLaborator);
        }
        return sali;
    }

    public List<Sala> getAll() throws SQLException{
        List<Sala> sali = new ArrayList<>();
        sali.addAll(getAllAmf());
        sali.addAll(getAllSaliLaborator());
        sali.addAll(getAllSaliSeminar());
        return sali;
    }

    private int insertSala(Sala sala, String tableName) throws SQLException{
        String query = "INSERT INTO sali(name, capacity) VALUES(?, ?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, sala.getName());
        preparedStatement.setInt(2, sala.getCapacity());
        preparedStatement.executeUpdate();
        query = "SELECT LAST_INSERT_ID()";
        preparedStatement = connection.prepareStatement(query);
        var resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            int id = resultSet.getInt(1);
            query = "INSERT INTO " + tableName + "(id) VALUES(?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return id;
        }
        return 0;
    }

}

package model.Student;


import util.Dao;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class StudentDatabase implements Dao<Student> {
    static Connection connection;

    static {
        try {
            connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int add(Student student) throws SQLException {
        String query = "INSERT INTO studenti(name, birthdate, grupa, study_year) VALUES(?,?,?,?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, student.getName());
        Date date = Date.from(student.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        preparedStatement.setDate(2, new java.sql.Date(date.getTime()));
        preparedStatement.setString(3, student.getGrupa());
        preparedStatement.setInt(4, student.getStudyYear());
        return preparedStatement.executeUpdate();
    }

    @Override
    public void update(Student student) throws SQLException {
        String query = "UPDATE studenti SET name=?, birthdate=?, grupa=?, study_year=? WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, student.getName());
        Date date = Date.from(student.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        preparedStatement.setDate(2, new java.sql.Date(date.getTime()));
        preparedStatement.setString(3, student.getGrupa());
        preparedStatement.setInt(4, student.getStudyYear());
        preparedStatement.setLong(5, student.getId());
      preparedStatement.executeUpdate();
    }

    @Override
    public int delete(Long id) throws SQLException {
        String query = "DELETE FROM studenti WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        return preparedStatement.executeUpdate();
    }

    @Override
    public List<Student> getAll() throws SQLException {
        String query = "SELECT * FROM studenti";
        var preparedStatement = connection.prepareStatement(query);
        var resultSet = preparedStatement.executeQuery();
        List<Student> students = new java.util.ArrayList<>();
        while(resultSet.next()){
            students.add(new Student(resultSet));
        }
        return students;
    }

    @Override
    public Student getById(Long id) throws SQLException {
        String query = "SELECT * FROM studenti WHERE id=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        var resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return new Student(resultSet);
        }
        return null;
    }

    @Override
    public Student getByName(String name) throws SQLException {
        String query = "SELECT * FROM studenti WHERE name=?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        var resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return new Student(resultSet);
        }
        return null;
    }

}

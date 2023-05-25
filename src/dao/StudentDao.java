package dao;

import Models.Student.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDao {
    public int add(Student student)
        throws SQLException;
    public void update(Student student)
        throws SQLException;
    public void delete(Long id)
        throws SQLException;
    public Student getStudent(Long id)
        throws SQLException;
    public List<Student> getStudents()
        throws SQLException;
}

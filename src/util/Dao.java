package util;

import model.Profesor.Profesor;

import java.sql.SQLException;
import java.util.List;

public interface Dao {
   int add(Object obj) throws SQLException;
   int delete(Long id) throws SQLException;
   int update(Object obj) throws SQLException;
   Object getById(Long id) throws SQLException;
   List<Object> getAll() throws SQLException;

}

package util;

import model.ModelBase;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T extends ModelBase> {
  int add(T obj) throws SQLException;
  void update(T obj) throws SQLException;
  int delete(Long id) throws SQLException;
  T getById(Long id) throws SQLException;
  T getByName(String name) throws SQLException;
  List<T> getAll() throws SQLException;

}

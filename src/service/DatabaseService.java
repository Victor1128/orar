package service;

import model.Materie.Materie;
import model.ModelBase;
import model.Profesor.Profesor;
import model.Serie.Serie;
import model.Student.Student;
import util.Dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DatabaseService {
  private static DatabaseService instance = null;

  private DatabaseService() {
  }

  public static DatabaseService getInstance() {
    if (instance == null) {
      instance = new DatabaseService();
    }
    return instance;
  }

  public <T extends ModelBase> void add(Scanner in, Dao<T> dao, String numeTip, List<Materie> materii) {
    List<String> tipuri = Arrays.asList("student", "profesor", "materie", "serie");
    if(!tipuri.contains(numeTip)) throw new IllegalStateException("Unexpected value: " + numeTip);
    T obj = createObject(in, numeTip, materii);
    try {
      int n = dao.add(obj);
      System.out.println(n!=0 ? "Adaugat cu succes!" : "Eroare la adaugare!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public <T extends ModelBase> void update(Scanner in, Dao<T> dao, String numeTip, List<Materie> materii) {
    List<String> tipuri = Arrays.asList("student", "profesor", "materie", "serie");
    if(!tipuri.contains(numeTip)) throw new IllegalStateException("Unexpected value: " + numeTip);
    System.out.println("Introduceti id pentru "+numeTip+": ");
    Long id = Long.parseLong(in.nextLine());
    try {
      System.out.println(dao.getById(id));
    }catch (SQLException e){
      System.out.println("Nu exista niciun "+numeTip+" cu acest ID!");
      return;
    }
    try{
      T obj = createObject(in, numeTip, materii);
      obj.setId(id);
      dao.update(obj);
    }catch(SQLException e){
      e.printStackTrace();
    }
  }

  public <T extends ModelBase> void delete(Scanner in, Dao<T> dao, String numeTip){
    System.out.println("Introducesti id-ul pentru" + numeTip + ": ");
    Long id = in.nextLong();
    try{
      int n = dao.delete(id);
      System.out.println(n!=0 ? numeTip + " sters cu succes!" : "Eroare la stergere!");
    } catch (SQLException e) {
      System.out.println("Nu exista niciun "+numeTip+" cu acest ID!");
    }
  }

  public <T extends ModelBase> T get(Scanner in, Dao<T> dao, String numeTip) {
    System.out.println("Introduceti numele pentru " + numeTip + ": ");
    String nume = in.nextLine();
    T obj;
    try {
      obj = dao.getByName(nume);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    if (obj != null) {
      System.out.println(obj);
      return obj;
    }
    System.out.println("Nu exista " + numeTip + " cu acest nume!");
    System.out.println("Introducesti id-ul pentru" + numeTip + ": ");
    long id = Long.parseLong(in.nextLine());
    try {
      obj = dao.getById(id);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println(obj != null ? obj : "Nu exista " + numeTip + " cu acest ID!");
    return obj;
  }

  private <T extends ModelBase> T createObject(Scanner in, String numeTip, List<Materie> materii){
    T obj = null;
    switch (numeTip){
      case "student" -> obj = (T) new Student(in);
      case "profesor" -> obj = (T) new Profesor(in, materii);
      case "materie" -> obj = (T) new Materie(in, "Simple");
      case "serie" -> obj = (T) new Serie(in);
      default -> throw new IllegalStateException("Unexpected value: " + numeTip);
    }
    return obj;
  }
}

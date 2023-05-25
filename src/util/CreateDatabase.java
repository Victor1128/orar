package util;

import java.sql.Connection;
import java.sql.SQLException;

public class CreateDatabase {
    private static Connection connection;
    static{
        try{
            connection = DatabaseConnection.getInstance().getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void createDatabase(){
        try{
            connection.createStatement().execute("CREATE DATABASE IF NOT EXISTS orar");
            connection.createStatement().execute("USE orar");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void createStudentsTable(){
        try{
             connection.createStatement().execute("CREATE TABLE IF NOT EXISTS studenti(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(50) NOT NULL," +
                    "birthdate DATE NOT NULL," +
                    "grupa VARCHAR(50) NOT NULL," +
                    "study_year INT NOT NULL)");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void createProfesoriTable(){
        try{
             connection.createStatement().execute("CREATE TABLE IF NOT EXISTS profesori(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(50) NOT NULL)"
            );
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void createMateriiTable(){
        try{
             connection.createStatement().execute("CREATE TABLE IF NOT EXISTS materii(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(50) NOT NULL,"+
                    "nrCredite INT," +
                    "nrOreCurs INT,"+
                    "nrOreSeminar INT,"+
                    "nrOreLaborator INT)"
            );
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void createProfsorMaterieTable(){
        try{
             connection.createStatement().execute("CREATE TABLE IF NOT EXISTS profesor_materie(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "profesor_id INT NOT NULL," +
                    "materie_id INT NOT NULL," +
                    "FOREIGN KEY (profesor_id) REFERENCES profesori(id)ON DELETE CASCADE," +
                    "FOREIGN KEY (materie_id) REFERENCES materii(id)ON DELETE CASCADE)"
            );
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void createSaliTable(){
        try{
             connection.createStatement().execute("CREATE TABLE IF NOT EXISTS sali(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(50) NOT NULL," +
                    "capacity INT NOT NULL)"
            );
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void createAmfiteatreTable(){
        try{
             connection.createStatement().execute("CREATE TABLE IF NOT EXISTS amfiteatre(" +
                    "id INT PRIMARY KEY REFERENCES sali(id)ON DELETE CASCADE)"
            );
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void createSaliLaboratorTable(){
        try{
             connection.createStatement().execute("CREATE TABLE IF NOT EXISTS sali_laborator(" +
                    "id INT PRIMARY KEY REFERENCES sali(id)ON DELETE CASCADE)"
            );
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void createSaliLaboratorMateriiTable(){
        try{
             connection.createStatement().execute("CREATE TABLE IF NOT EXISTS sali_laborator_materii(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "sala_id INT NOT NULL," +
                    "materie_id INT NOT NULL," +
                    "FOREIGN KEY (sala_id) REFERENCES sali_laborator(id)ON DELETE CASCADE," +
                    "FOREIGN KEY (materie_id) REFERENCES materii(id)ON DELETE CASCADE)"
            );
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void createSaliSeminarTable(){
        try{
             connection.createStatement().execute("CREATE TABLE IF NOT EXISTS sali_seminar(" +
                    "id INT PRIMARY KEY REFERENCES sali(id)ON DELETE CASCADE)"
            );
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void createOreTable(){
        try{
             connection.createStatement().execute("CREATE TABLE IF NOT EXISTS ore(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "ora_inceput INT NOT NULL," +
                    "ora_sfarsit INT NOT NULL," +
                    "ziua_saptamanii INT NOT NULL," +
                    "frecventa INT," +
                    "sala_id INT NOT NULL REFERENCES sali(id) ON DELETE CASCADE," +
                    "materie_id INT NOT NULL REFERENCES materii(id) ON DELETE CASCADE," +
                    "profesor_id INT NOT NULL REFERENCES profesori(id)ON DELETE CASCADE)"
            );
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void createCursTable(){
        try{
             connection.createStatement().execute("CREATE TABLE IF NOT EXISTS cursuri(" +
                    "id INT PRIMARY KEY REFERENCES ore(id) ON DELETE CASCADE," +
                    "serie_id INT NOT NULL REFERENCES serii(id) ON DELETE CASCADE)"
            );
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void createSeminarTable(){
        try{
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS seminare(" +
                    "id INT PRIMARY KEY REFERENCES ore(id)ON DELETE CASCADE," +
                    "grupa VARCHAR(50) NOT NULL)"
            );
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void createLaboratorTable(){
        try{
             connection.createStatement().execute("CREATE TABLE IF NOT EXISTS laboratoare(" +
                    "id INT PRIMARY KEY REFERENCES ore(id)ON DELETE CASCADE," +
                    "grupa VARCHAR(50) NOT NULL)"
            );
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void createSerieTable(){
        try{
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS serii(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(50) NOT NULL," +
                    "grupe VARCHAR(100) NOT NULL)"
            );
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void createAll(){
        createDatabase();
        createStudentsTable();
        createProfesoriTable();
        createMateriiTable();
        createProfsorMaterieTable();
        createSaliTable();
        createAmfiteatreTable();
        createSaliLaboratorTable();
        createSaliLaboratorMateriiTable();
        createSaliSeminarTable();
        createOreTable();
        createCursTable();
        createSeminarTable();
        createLaboratorTable();
        createSerieTable();
    }
}

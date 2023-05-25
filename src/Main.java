import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Services.AuditService;
import Services.MainService;
import util.CreateDatabase;
import util.DatabaseConnection;

public class Main {
    private final static List<String> availableCommands = Arrays.asList("add_student", "add_profesor", "add_materie", "add_serie", "add_sala", "add_ora", "get_student", "get_profsor", "show_orar_student", "show_orar_student_azi", "show_orar_profesor", "show_orar_profesor_azi", "help", "exit");

    private static void printAllCommands(){
        for(int i=0;i<availableCommands.size();++i)
            System.out.println((i+1) + ". " + " (" + availableCommands.get(i) + ")");
    }

//    public static Connection getConnection(){
//        Connection connection = null;
//        try {
//            connection = DatabaseConnection.getInstance().getConnection();
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM Test");
//            while(resultSet.next()){
//                System.out.println(resultSet.getString(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        MainService mainService = new MainService();
        AuditService auditService = new AuditService();
        boolean exit = false;
//        getConnection();
        CreateDatabase.createAll();
        while(!exit){
            System.out.println("Introduceti comanda:");
            String command = in.nextLine();
            switch (command){
                case "add_student":
                    mainService.createStudent(in);
                    break;
                case "add_profesor":
                    mainService.createProfesor(in);
                    break;
                case "add_materie":
                    mainService.createMaterie(in);
                    break;
                case "add_serie":
                    mainService.createSerie(in);
                    break;
                case "add_sala":
                    mainService.createSala(in);
                    break;
                case "add_ora":
                    mainService.createOra(in);
                    break;
                case "get_student":
                    mainService.getStudent(in);
                    break;
                case "get_profesor":
                    mainService.getProfesor(in);
                    break;
                case "show_orar_student":
                    mainService.afisareOrarStudent(in);
                    break;
                case "show_orar_student_azi":
                    mainService.afisareOrarStudentZiCurenta(in);
                    break;
                case "show_orar_profesor":
                    mainService.afisareOrarProfesor(in);
                    break;
                case "show_orar_profesor_azi":
                    mainService.afisareOrarProfesorZiCurenta(in);
                    break;
                case "help":
                    printAllCommands();
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Comanda invalida!");
                    break;
            }
            if (!command.equals("help") && !command.equals("exit") && availableCommands.contains(command))
                try {
                    auditService.write(command);
                } catch (IOException e) {
                    System.out.println(e);
                }
        }

    }
}
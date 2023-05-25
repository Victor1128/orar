import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Services.AuditService;
import Services.MainService;
import util.CreateDatabase;

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
        CreateDatabase.createAll();
        Scanner in = new Scanner(System.in);
        MainService mainService = null;
        try{
            mainService = new MainService();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        AuditService auditService = new AuditService();
        boolean exit = false;
//        getConnection();

//        mainService.test();
        try {
            while (!exit) {
                System.out.println("Introduceti comanda:");
                String command = in.nextLine();
                switch (command) {
                    case "add_student" -> mainService.createStudent(in);
                    case "add_profesor" -> mainService.createProfesor(in);
                    case "add_materie" -> mainService.createMaterie(in);
                    case "add_serie" -> mainService.createSerie(in);
                    case "add_sala" -> mainService.createSala(in);
                    case "add_ora" -> mainService.createOra(in);
                    case "get_student" -> mainService.getStudent(in);
                    case "get_profesor" -> mainService.getProfesor(in);
                    case "show_orar_student" -> mainService.afisareOrarStudent(in);
                    case "show_orar_student_azi" -> mainService.afisareOrarStudentZiCurenta(in);
                    case "show_orar_profesor" -> mainService.afisareOrarProfesor(in);
                    case "show_orar_profesor_azi" -> mainService.afisareOrarProfesorZiCurenta(in);
                    case "help" -> printAllCommands();
                    case "exit" -> exit = true;
                    case "test" -> mainService.test();
                    default -> System.out.println("Comanda invalida!");
                }
                if (!command.equals("help") && !command.equals("exit") && availableCommands.contains(command))
                    try {
                        auditService.write(command);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import service.AuditService;
import service.MainService;
import util.CreateDatabase;

public class Main {
    private final static List<String> availableCommands = Arrays.asList("add_student", "add_profesor", "add_materie", "add_serie", "add_sala", "add_ora", "get_student", "get_profsor", "get_materie", "get_serie", "delete_student", "delete_profesor", "delete_ora", "delete_serie", "delete_materie", "update_student", "update_profesor", "update_materie", "update_serie", "show_orar_student", "show_orar_student_azi", "show_orar_profesor", "show_orar_profesor_azi", "help", "exit");

    private static void printAllCommands(){
        for(int i=0;i<availableCommands.size();++i)
            System.out.println((i+1) + ". " + availableCommands.get(i));
    }

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
                    case "get_materie" -> mainService.getMaterie(in);
                    case "get_serie" -> mainService.getSerie(in);
                    case "delete_student" -> mainService.deleteStudent(in);
                    case "delete_profesor" -> mainService.deleteProfesor(in);
                    case "delete_ora" -> mainService.deleteOra(in);
                    case "delete_serie" -> mainService.deleteSerie(in);
                    case "delete_materie" -> mainService.deleteMaterie(in);
                    case "update_student" -> mainService.updateStudent(in);
                    case "update_profesor" -> mainService.updateProfesor(in);
                    case "update_serie" -> mainService.updateSerie(in);
                    case "update_materie" -> mainService.updateMaterie(in);
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
                        e.printStackTrace();
                    }
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }

    }
}
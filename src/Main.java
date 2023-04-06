import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Services.Service;

public class Main {
    private final static List<String> availableCommands = Arrays.asList("add_student", "add_profesor", "add_materie", "add_serie", "add_sala", "add_ora", "get_student", "get_profsor", "show_orar_student", "show_orar_student_azi", "show_orar_profesor", "show_orar_profesor_azi", "help", "exit");

    private static void printAllCommands(){
        for(int i=0;i<availableCommands.size();++i)
            System.out.println((i+1) + ". " + " (" + availableCommands.get(i) + ")");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Service service = new Service();
        boolean exit = false;

        while(!exit){
            System.out.println("Introduceti comanda:");
            String command = in.nextLine();
            switch (command){
                case "add_student":
                    service.createStudent(in);
                    break;
                case "add_profesor":
                    service.createProfesor(in);
                    break;
                case "add_materie":
                    service.createMaterie(in);
                    break;
                case "add_serie":
                    service.createSerie(in);
                    break;
                case "add_sala":
                    service.createSala(in);
                    break;
                case "add_ora":
                    service.createOra(in);
                    break;
                case "get_student":
                    service.getStudent(in);
                    break;
                case "get_profesor":
                    service.getProfesor(in);
                    break;
                case "show_orar_student":
                    service.afisareOrarStudent(in);
                    break;
                case "show_orar_student_azi":
                    service.afisareOrarStudentZiCurenta(in);
                    break;
                case "show_orar_profesor":
                    service.afisareOrarProfesor(in);
                    break;
                case "show_orar_profesor_azi":
                    service.afisareOrarProfesorZiCurenta(in);
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
        }

    }
}
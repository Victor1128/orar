package Models.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Student {
    private Long id = null;

    private String name;
    private LocalDate birthDate;
    private String grupa;
    private int studyYear;

    public Student(Long id, String name, LocalDate birthDate, String grupa, int studyYear) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.grupa = grupa;
        this.studyYear = studyYear;
    }

    public Student(String name, LocalDate birthDate, String grupa, int studyYear) {
        this.name = name;
        this.birthDate = birthDate;
        this.grupa = grupa;
        this.studyYear = studyYear;
    }

    public Student(Scanner in){
        read(in);
    }

    public void read(Scanner in){
        System.out.println("Nume: ");
        name = in.nextLine();
        System.out.println("Data nasterii (zz.ll.aaaa): ");
        String dataNasterii = in.nextLine();
        System.out.println("Grupa: ");
        grupa = in.nextLine();
        System.out.println("An studiu: ");
        studyYear = Integer.parseInt(in.nextLine());
        birthDate = LocalDate.parse(dataNasterii, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getGrupa() {
        return grupa;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    @Override
    public String toString(){
        return "Student: " + name + " Nascut pe " + birthDate + "\n Grupa: " + grupa + " din anul " + studyYear;
    }
}

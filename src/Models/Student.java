package Models;

import java.time.LocalDate;

public class Student {
    private final Long id;

    private static Long idCounter = 0L;
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
        this.id = idCounter++;
        this.name = name;
        this.birthDate = birthDate;
        this.grupa = grupa;
        this.studyYear = studyYear;
    }

    public Long getId() {
        return id;
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

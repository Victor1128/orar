package Models.Profesor;

import Models.Materie.Materie;
import utils.ClassWithName;

import java.util.List;
import java.util.Scanner;

import static utils.Utils.selectFromMultipleChoices;

public class Profesor implements ClassWithName {
    private Long id = null;
    private String name;
    private List<Materie> materii;

    public Profesor(Long id, String name, List<Materie> materii) {
        this.id = id;
        this.name = name;
        this.materii = materii;
    }

    public Profesor(String name, List<Materie> materii) {
        this.name = name;
        this.materii = materii;
    }

    public Profesor(Scanner in, List<Materie> materii){
        read(in, materii);
    }

    public void read(Scanner in, List<Materie> materii) {
        System.out.println("Nume: ");
        name = in.nextLine();
        System.out.println("Numar de materii: ");
        int nrMaterii = Integer.parseInt(in.nextLine());
        for (int i = 0; i < nrMaterii; i++) {
            int optiune = selectFromMultipleChoices(in, materii, "Materie " + (i + 1) + ": ");
            this.materii.add(materii.get(optiune));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Materie> getMaterii() {
        return materii;
    }

    public void setMaterii(List<Materie> materii) {
        this.materii = materii;
    }

    public void addMaterie(Materie materie) {
        this.materii.add(materie);
    }

    public void removeMaterie(Materie materie) {
        this.materii.remove(materie);
    }

    @Override
    public String toString(){
        return this.name;
    }
}

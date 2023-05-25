package Models.Serie;

import utils.ClassWithName;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Serie implements ClassWithName {
    private String name;
    private List<String> grupe;

    public Serie(String name, List<String> grupe) {
        this.name = name;
        Collections.sort(grupe);
        this.grupe = grupe;
    }

    public Serie(Scanner in){
        read(in);
    }

    public void read(Scanner in){
        System.out.println("Nume: ");
        name = in.nextLine();
        System.out.println("Numar de grupe: ");
        int nrGrupe = Integer.parseInt(in.nextLine());
        for (int i = 0; i < nrGrupe; i++) {
            System.out.println("Grupa " + (i + 1) + ": ");
            String grupa = in.nextLine();
            grupe.add(grupa);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGrupe() {
        return grupe;
    }

    public void setGrupe(List<String> grupe) {
        this.grupe = grupe;
    }

    public Boolean contains(String grupa){
        return Collections.binarySearch(grupe, grupa) >= 0;
    }
}

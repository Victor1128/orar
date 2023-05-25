package Models.Serie;

import util.ClassWithName;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static util.Utils.listFromString;

public class Serie implements ClassWithName {
    private Long id;
    private String name;
    private List<String> grupe = new ArrayList<>();

    public Serie(String name, List<String> grupe) {
        this.name = name;
        Collections.sort(grupe);
        this.grupe = grupe;
    }
    public Serie(Long id, String name, List<String> grupe) {
        this.id = id;
        this.name = name;
        this.grupe = grupe;
    }

    public Serie(ResultSet rs){
        try{
            id = rs.getLong("id");
            name = rs.getString("name");
            String grupe = rs.getString("grupe");
            this.grupe = listFromString(grupe);
        } catch (SQLException e){
            e.printStackTrace();
        }
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



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Serie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grupe=" + grupe +
                '}';
    }
}

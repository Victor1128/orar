package Models.Materie;

import util.ClassWithName;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Materie implements ClassWithName {
    private Long id = null;
    private String name;
    private int nrCredite;
    private int nrOreCurs;
    private int nrOreSeminar;
    private int nrOreLaborator;

    public Materie(Long id, String name, int nrCredite, int nrOreCurs, int nrOreSeminar, int nrOreLaborator) {
        this.id = id;
        this.name = name;
        this.nrCredite = nrCredite;
        this.nrOreCurs = nrOreCurs;
        this.nrOreSeminar = nrOreSeminar;
        this.nrOreLaborator = nrOreLaborator;
    }

    public Materie(String name, int nrCredite, int nrOre, int nrOreSeminar, int nrOreLaborator) {
        this.name = name;
        this.nrCredite = nrCredite;
        this.nrOreCurs = nrOre;
        this.nrOreSeminar = nrOreSeminar;
        this.nrOreLaborator = nrOreLaborator;
    }

    public Materie(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Materie(Scanner in, String opt) {
        switch (opt) {
            case "Simple" -> readSimple(in);
            case "Full" -> read(in);
            default -> throw new IllegalArgumentException("Optiunea nu exista");
        }
    }

    public Materie(ResultSet rs){
        try {
            id = rs.getLong("id");
            name = rs.getString("name");
            try {
                nrCredite = rs.getInt("nr_credite");
                nrOreCurs = rs.getInt("nr_ore_curs");
                nrOreSeminar = rs.getInt("nr_ore_seminar");
                nrOreLaborator = rs.getInt("nr_ore_laborator");
            } catch (SQLException ex) {
                nrCredite = 0;
                nrOreCurs = 0;
                nrOreSeminar = 0;
                nrOreLaborator = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Materie() {}


    public Materie(String name) {
        this.name = name;
    }

    public int getNrOreCurs() {
        return nrOreCurs;
    }

    public void setNrOreCurs(int nrOreCurs) {
        this.nrOreCurs = nrOreCurs;
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

    public int getNrCredite() {
        return nrCredite;
    }

    public void setNrCredite(int nrCredite) {
        this.nrCredite = nrCredite;
    }

    public int getNrOre() {
        return nrOreCurs;
    }

    public void setNrOre(int nrOre) {
        this.nrOreCurs = nrOre;
    }

    public int getNrOreSeminar() {
        return nrOreSeminar;
    }

    public void setNrOreSeminar(int nrOreSeminar) {
        this.nrOreSeminar = nrOreSeminar;
    }

    public int getNrOreLaborator() {
        return nrOreLaborator;
    }

    public void setNrOreLaborator(int nrOreLaborator) {
        this.nrOreLaborator = nrOreLaborator;
    }

    public void read(Scanner in){
        System.out.println("Numele materiei: ");
        this.name = in.nextLine();
        System.out.println("Numarul de credite: ");
        this.nrCredite = Integer.parseInt(in.nextLine());
        System.out.println("Numarul de ore de curs: ");
        this.nrOreCurs = Integer.parseInt(in.nextLine());
        System.out.println("Numarul de ore de seminar: ");
        this.nrOreSeminar = Integer.parseInt(in.nextLine());
        System.out.println("Numarul de ore de laborator: ");
        this.nrOreLaborator = Integer.parseInt(in.nextLine());
    }

    public void readSimple(Scanner in){
        System.out.println("Numele materiei: ");
        this.name = in.nextLine();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Materie m))
            return false;
        return this.id.equals(m.getId());
    }

    @Override
    public String toString() {
        return "Materie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nrCredite=" + nrCredite +
                ", nrOreCurs=" + nrOreCurs +
                ", nrOreSeminar=" + nrOreSeminar +
                ", nrOreLaborator=" + nrOreLaborator +
                '}';
    }
}

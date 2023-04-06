package Models;

import java.util.Scanner;

public class Materie {
    private String nume;
    private int nrCredite;
    private int nrOreCurs;
    private int nrOreSeminar;
    private int nrOreLaborator;

    public Materie(String nume, int nrCredite, int nrOre, int nrOreSeminar, int nrOreLaborator) {
        this.nume = nume;
        this.nrCredite = nrCredite;
        this.nrOreCurs = nrOre;
        this.nrOreSeminar = nrOreSeminar;
        this.nrOreLaborator = nrOreLaborator;
    }

    public Materie() {}

    public Materie(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
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
        this.nume = in.nextLine();
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
        this.nume = in.nextLine();
    }
}

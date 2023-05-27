package model.Sala;

import model.Materie.Materie;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static util.Utils.selectFromMultipleChoices;

public class SalaLaborator extends Sala {
    private List<Materie> materiiPosibile = new ArrayList<>();


    public SalaLaborator(String nume, int capacitate, List<Materie> materiiPosibile) {
        super(nume, capacitate);
        this.materiiPosibile = materiiPosibile;
    }

    public SalaLaborator(String nume, int capacitate) {
        super(nume, capacitate);
    }

    public SalaLaborator(Scanner in, List<Materie> materii){
        super(in);
        read(in, materii);
    }

    public SalaLaborator(ResultSet rs){super(rs);}

    public void read(Scanner in, List<Materie> materii) {
        System.out.println("Numar de materii posibile in laborator: ");
        int nrMaterii = Integer.parseInt(in.nextLine());
        for (int i = 0; i < nrMaterii; i++) {
            int optiune = selectFromMultipleChoices(in, materii, "o materie");
            this.materiiPosibile.add(materii.get(optiune));
        }
    }

    public List<Materie> getMateriiPosibile() {
        return materiiPosibile;
    }

    public void setMateriiPosibile(List<Materie> materiiPosibile) {
        this.materiiPosibile = materiiPosibile;
    }
}

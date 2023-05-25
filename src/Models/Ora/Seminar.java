package Models.Ora;

import Models.Materie.Materie;
import Models.Profesor.Profesor;
import Models.Sala.Amfiteatru;
import Models.Sala.Sala;
import Models.Sala.SalaSeminar;

import java.util.List;
import java.util.Scanner;

public class Seminar extends Ora{
    private String grupa;

    public Seminar(Materie materie, Profesor profesor, SalaSeminar sala, int oraInceput, int oraSfarsit, int ziuaSaptamanii, String frecventa, String grupa) {
        super(materie, profesor, sala, oraInceput, oraSfarsit, ziuaSaptamanii, frecventa);
        this.grupa = grupa;
    }

    public Seminar(Materie materie, Profesor profesor, SalaSeminar sala, int oraInceput, int oraSfarsit, int ziuaSaptamanii, String grupa) {
        super(materie, profesor, sala, oraInceput, oraSfarsit, ziuaSaptamanii);
        this.grupa = grupa;
    }

    public Seminar(Scanner in, List<Profesor> profesori, List<Materie> materii, List<Sala> sali){
        super(in, profesori, materii, sali);
        read(in, profesori, materii, sali);
    }

    public void read(Scanner in, List<Profesor> profesori, List<Materie> materii, List<Sala> sali){
        sala = selectSala(in, sali);
        System.out.println("Grupa: ");
        grupa = in.nextLine();
    }

    @Override
    public Class<? extends Sala> getSalaNecesara() {
        return SalaSeminar.class;
    }

    @Override
    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }
}

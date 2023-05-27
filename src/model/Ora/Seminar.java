package model.Ora;

import model.Materie.Materie;
import model.Profesor.Profesor;
import model.Sala.Sala;
import model.Sala.SalaSeminar;

import java.sql.ResultSet;
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
        super(in, profesori, materii);
        readSeminar(in, sali);
    }

    public Seminar(ResultSet rs, Profesor profesor, Materie materie, Sala sala, String grupa){
        super(rs);
        this.profesor = profesor;
        this.materie = materie;
        this.sala = sala;
        this.grupa = grupa;
    }

    public void readSeminar(Scanner in, List<Sala> sali){
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

    @Override
    public String toString(){
        String s = super.toString();
        return s.substring(0, s.indexOf('\n')) + " ---- Seminar\n" + s.substring(s.indexOf('\n') + 1);
    }
}

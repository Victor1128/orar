package model.Ora;

import model.Sala.Amfiteatru;
import model.Materie.Materie;
import model.Profesor.Profesor;
import model.Sala.Sala;
import model.Serie.Serie;

import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;

import static util.Utils.selectFromMultipleChoices;

public class Curs extends Ora{
    private Serie serie;

    public Curs(Materie materie, Profesor profesor, Amfiteatru sala, int oraInceput, int oraSfarsit, int ziuaSaptamanii, String frecventa, Serie serie) {
        super(materie, profesor, sala, oraInceput, oraSfarsit, ziuaSaptamanii, frecventa);
        this.serie = serie;
    }

    public Curs(Materie materie, Profesor profesor, Amfiteatru sala, int oraInceput, int oraSfarsit, int ziuaSaptamanii, Serie serie) {
        super(materie, profesor, sala, oraInceput, oraSfarsit, ziuaSaptamanii);
        this.serie = serie;
    }

    public Curs(Materie materie, Profesor profesor, Amfiteatru sala, int oraInceput, int oraSfarsit, int ziuaSaptamanii, String frecventa) {
        super(materie, profesor, sala, oraInceput, oraSfarsit, ziuaSaptamanii, frecventa);
    }

    public Curs(Scanner in, List<Profesor> profesori, List<Materie> materii, List<Sala> sali, List<Serie> serii){
        super(in, profesori, materii);
        readCurs(in, sali, serii);
    }

    public Curs(ResultSet rs, Profesor profesor, Materie materie, Sala sala, Serie serie){
        super(rs);
        this.profesor = profesor;
        this.materie = materie;
        this.sala = sala;
        this.serie = serie;
    }

    public void readCurs(Scanner in, List<Sala> sali, List<Serie> serii){
        sala = selectSala(in, sali);
        System.out.println("Seria: ");
        int optiune = selectFromMultipleChoices(in, serii, "o serie: ");
        serie = serii.get(optiune);
    }


    @Override
    public Class<? extends Sala> getSalaNecesara() {
        return Amfiteatru.class;
    }

    @Override
    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    @Override
    public String toString(){
        String s = super.toString();
        return s.substring(0, s.indexOf('\n')) + " ---- Curs\n" + s.substring(s.indexOf('\n') + 1);
    }

}

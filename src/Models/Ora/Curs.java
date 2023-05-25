package Models.Ora;

import Models.Sala.Amfiteatru;
import Models.Materie.Materie;
import Models.Profesor.Profesor;
import Models.Sala.Sala;
import Models.Serie.Serie;

import java.util.List;
import java.util.Scanner;

import static utils.Utils.selectFromMultipleChoices;

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
        super(in, profesori, materii, sali);
    }

    public void read(Scanner in, List<Sala> sali, List<Serie> serii){
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

}

package Models.Ora;

import Models.Sala.Amfiteatru;
import Models.Materie.Materie;
import Models.Profesor.Profesor;
import Models.Serie.Serie;

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

    @Override
    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

}

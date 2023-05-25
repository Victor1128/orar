package Models.Ora;

import Models.Materie.Materie;
import Models.Profesor.Profesor;
import Models.Sala.SalaSeminar;

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

    @Override
    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }
}

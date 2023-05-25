package Models.Ora;

import Models.Materie.Materie;
import Models.Profesor.Profesor;
import Models.Sala.Sala;
import Models.Serie.Serie;

public abstract class Ora implements Comparable<Ora> {
    protected Materie materie;
    protected Profesor profesor;
    protected Sala sala;
    protected int oraInceput;
    protected int oraSfarsit;
    protected int ziuaSaptamanii;
    protected String frecventa; // SP, SI, all

    public Ora(Materie materie, Profesor profesor, Sala sala, int oraInceput, int oraSfarsit, int ziuaSaptamanii, String frecventa) {
        this.materie = materie;
        this.profesor = profesor;
        this.sala = sala;
        this.oraInceput = oraInceput;
        this.oraSfarsit = oraSfarsit;
        this.ziuaSaptamanii = ziuaSaptamanii;
        this.frecventa = frecventa;
    }

    public Ora(Materie materie, Profesor profesor, Sala sala, int oraInceput, int oraSfarsit, int ziuaSaptamanii) {
        this.materie = materie;
        this.profesor = profesor;
        this.sala = sala;
        this.oraInceput = oraInceput;
        this.oraSfarsit = oraSfarsit;
        this.ziuaSaptamanii = ziuaSaptamanii;
    }

    public Materie getMaterie() {
        return materie;
    }

    public void setMaterie(Materie materie) {
        this.materie = materie;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public int getOraInceput() {
        return oraInceput;
    }

    public void setOraInceput(int oraInceput) {
        this.oraInceput = oraInceput;
    }

    public int getOraSfarsit() {
        return oraSfarsit;
    }

    public void setOraSfarsit(int oraSfarsit) {
        this.oraSfarsit = oraSfarsit;
    }

    public int getZiuaSaptamanii() {
        return ziuaSaptamanii;
    }

    public void setZiuaSaptamanii(int ziuaSaptamanii) {
        this.ziuaSaptamanii = ziuaSaptamanii;
    }

    public String getFrecventa() {
        return frecventa;
    }

    public void setFrecventa(String frecventa) {
        this.frecventa = frecventa;
    }

    @Override
    public int compareTo(Ora o) {
        return this.ziuaSaptamanii - o.ziuaSaptamanii != 0? this.ziuaSaptamanii - o.ziuaSaptamanii : this.oraInceput - o.oraInceput;
    }

    @Override
    public String toString() {
        return materie.getName() + "\n\tProfesor: " + profesor.getName() + "\n\tSala: " + sala.getName() + "\n\tOra: " + oraInceput + " - " + oraSfarsit + "\n\n";
    }

    public String toStringProfesor(){
        if(this instanceof Laborator || this instanceof Seminar)
            return materie.getName() + "\n\tGrupa: " + this.getGrupa() + "\n\tSala: " + sala.getName() + "\n\tOra: " + oraInceput + " - " + oraSfarsit + "\n\n";
        else if(this instanceof Curs)
            return materie.getName() + "\n\tSerie: " + this.getSerie() + "\n\tSala: " + sala.getName() + "\n\tOra: " + oraInceput + " - " + oraSfarsit + "\n\n";
        return "Ora nu e definita";
    }

    public String getGrupa() {
        return null;
    }

    public Serie getSerie(){
        return null;
    }

}

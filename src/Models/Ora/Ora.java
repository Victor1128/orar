package Models.Ora;

import Models.Materie.Materie;
import Models.Profesor.Profesor;
import Models.Sala.Sala;
import Models.Serie.Serie;

import java.util.List;
import java.util.Scanner;

import static utils.Utils.selectFromMultipleChoices;

public abstract class Ora implements Comparable<Ora> {
    protected Long id;

    protected Materie materie;
    protected Profesor profesor;
    protected Sala sala;
    protected int oraInceput;
    protected int oraSfarsit;
    protected int ziuaSaptamanii;
    protected String frecventa; // SP, SI, all

    public Ora(Long id, Materie materie, Profesor profesor, Sala sala, int oraInceput, int oraSfarsit, int ziuaSaptamanii, String frecventa) {
        this.id = id;
        this.materie = materie;
        this.profesor = profesor;
        this.sala = sala;
        this.oraInceput = oraInceput;
        this.oraSfarsit = oraSfarsit;
        this.ziuaSaptamanii = ziuaSaptamanii;
        this.frecventa = frecventa;
    }

    public Ora(Long id, Materie materie, Profesor profesor, Sala sala, int oraInceput, int oraSfarsit, int ziuaSaptamanii) {
        this.id = id;
        this.materie = materie;
        this.profesor = profesor;
        this.sala = sala;
        this.oraInceput = oraInceput;
        this.oraSfarsit = oraSfarsit;
        this.ziuaSaptamanii = ziuaSaptamanii;
    }

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

    public Ora(Scanner in, List<Profesor> profesori, List<Materie> materii, List<Sala> sali){
        read(in, profesori, materii, sali);
    }


    public void read(Scanner in, List<Profesor> profesori, List<Materie> materii, List<Sala> sali){
        int optiune = selectFromMultipleChoices(in, materii, "o materie");
        materie = materii.get(optiune);
        List<Profesor> profesoriPosibili = profesori.stream().filter(profesor -> profesor.getMaterii().contains(materie)).toList();
        optiune = selectFromMultipleChoices(in, profesoriPosibili, "un profesor");
        profesor = profesoriPosibili.get(optiune);
        System.out.println("Ora inceput: ");
        oraInceput = Integer.parseInt(in.nextLine());
        System.out.println("Ora sfarsit: ");
        oraSfarsit = Integer.parseInt(in.nextLine());
        ziuaSaptamanii = -1;
        while (ziuaSaptamanii < 1 || ziuaSaptamanii > 7){
            System.out.println("Ziua saptamanii (1 - 7): ");
            ziuaSaptamanii = Integer.parseInt(in.nextLine());
        }
        ziuaSaptamanii--;
    }

    public abstract Class<? extends Sala> getSalaNecesara();

    public List<Sala> getSaliPosibile(List<Sala> sali){
        return sali.stream().filter(sala -> sala.getClass() == this.getSalaNecesara()).toList();
    }

    public Sala selectSala(Scanner in, List<Sala> sali){
        List<Sala> saliPosibile = getSaliPosibile(sali);
        int optiune = selectFromMultipleChoices(in, saliPosibile, "o sala");
        return saliPosibile.get(optiune);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

package Models.Ora;

import Models.Materie.Materie;
import Models.Profesor.Profesor;
import Models.Sala.Sala;
import Models.Sala.SalaLaborator;

import java.util.List;
import java.util.Scanner;

import static util.Utils.selectFromMultipleChoices;

public class Laborator extends Ora{
    private String grupa;

    public Laborator(Materie materie, Profesor profesor, SalaLaborator sala, int oraInceput, int oraSfarsit, int ziuaSaptamanii, String frecventa, String grupa) {
        super(materie, profesor, sala, oraInceput, oraSfarsit, ziuaSaptamanii, frecventa);
        this.grupa = grupa;
    }

    public Laborator(Materie materie, Profesor profesor, SalaLaborator sala, int oraInceput, int oraSfarsit, int ziuaSaptamanii, String grupa) {
        super(materie, profesor, sala, oraInceput, oraSfarsit, ziuaSaptamanii);
        this.grupa = grupa;
    }

    public Laborator(Scanner in, List<Profesor> profesori, List<Materie> materii, List<Sala> sali){
        super(in, profesori, materii, sali);
        read(in, profesori, materii, sali);
    }

    public void read(Scanner in, List<Profesor> profesori, List<Materie> materii, List<Sala> sali){
        List<SalaLaborator> saliPosibile = getSaliPosibile(sali).stream().map(s -> (SalaLaborator) s).filter(s -> s.getMateriiPosibile().contains(materie)).toList();
        int optiune = selectFromMultipleChoices(in, saliPosibile, "sala de laborator");
        sala = saliPosibile.get(optiune);
        System.out.println("Grupa: ");
        grupa = in.nextLine();
    }

    @Override
    public Class<? extends Sala> getSalaNecesara() {
        return SalaLaborator.class;
    }

    @Override
    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public void read(Scanner in){

    }
}

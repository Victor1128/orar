package Models;

import java.util.List;

public class SalaLaborator extends Sala {
    private List<Materie> materiiPosibile;


    public SalaLaborator(String nume, int capacitate, List<Materie> materiiPosibile) {
        super(nume, capacitate);
        this.materiiPosibile = materiiPosibile;
    }

    public SalaLaborator(String nume, int capacitate) {
        super(nume, capacitate);
    }

    public List<Materie> getMateriiPosibile() {
        return materiiPosibile;
    }

    public void setMateriiPosibile(List<Materie> materiiPosibile) {
        this.materiiPosibile = materiiPosibile;
    }
}

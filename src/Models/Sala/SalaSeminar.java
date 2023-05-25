package Models.Sala;

import java.util.Scanner;

public class SalaSeminar extends Sala {
    public SalaSeminar(String nume, int capacitate) {
        super(nume, capacitate);
    }

    public SalaSeminar(Scanner in){
        super(in);
    }
}

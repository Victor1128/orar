package Models.Sala;

import java.util.Scanner;

public class Amfiteatru extends Sala {
    public Amfiteatru(String nume, int capacitate) {
        super(nume, capacitate);
    }

    public Amfiteatru(Scanner in){
        super(in);
    }

}

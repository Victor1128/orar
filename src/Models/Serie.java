package Models;

import java.util.Collections;
import java.util.List;

public class Serie {
    private String nume;
    private List<String> grupe;

    public Serie(String nume, List<String> grupe) {
        this.nume = nume;
        Collections.sort(grupe);
        this.grupe = grupe;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public List<String> getGrupe() {
        return grupe;
    }

    public void setGrupe(List<String> grupe) {
        this.grupe = grupe;
    }

    public Boolean contains(String grupa){
        return Collections.binarySearch(grupe, grupa) >= 0;
    }
}

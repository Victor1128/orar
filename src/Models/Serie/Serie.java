package Models.Serie;

import util.ClassWithName;

import java.util.Collections;
import java.util.List;

public class Serie implements ClassWithName {
    private String name;
    private List<String> grupe;

    public Serie(String name, List<String> grupe) {
        this.name = name;
        Collections.sort(grupe);
        this.grupe = grupe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

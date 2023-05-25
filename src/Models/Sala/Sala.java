package Models.Sala;

import util.ClassWithName;

public abstract class Sala implements ClassWithName {
    protected String name;
    protected int capacity;

    public Sala(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}

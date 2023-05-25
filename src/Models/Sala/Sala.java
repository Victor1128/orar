package Models.Sala;

import util.ClassWithName;

import java.sql.ResultSet;
import java.util.Scanner;

public abstract class Sala implements ClassWithName {
    protected Long id;

    protected String name;
    protected int capacity;

    public Sala(Long id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public Sala(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public Sala(Scanner in){
        read(in);
    }

    public Sala(ResultSet rs){
        try{
            id = rs.getLong("id");
            name = rs.getString("name");
            capacity = rs.getInt("capacity");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void read(Scanner in){
        System.out.println("Nume: ");
        name = in.nextLine();
        System.out.println("Numar de locuri: ");
        capacity = Integer.parseInt(in.nextLine());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

package service;

import java.rmi.UnexpectedException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import model.Materie.*;
import model.Ora.*;
import model.Profesor.*;
import model.Sala.*;
import model.Serie.*;
import model.Student.*;

public class MainService {
    private  List<Student> studenti = new ArrayList<>();
    private  List<Profesor> profesori = new ArrayList<>();
    private  List<Sala> sali = new ArrayList<>();
    private  List<Materie> materii = new ArrayList<>();
    private  List<Serie> serii = new ArrayList<>();
    private List<Ora> ore = new ArrayList<>();
    private final Map<String, List<Ora>> grupaOreMap = new HashMap<>();
    private final Map<Profesor, List<Ora>> profesorOreMap = new HashMap<>();
    private final List<String> zileleSaptamanii = Arrays.asList("Luni", "Marti", "Miercuri", "Joi", "Vineri", "Sambata", "Duminica");
    private final StudentDatabase studentDatabase = new StudentDatabase();
    private final ProfesorDatabase profesorDatabase = new ProfesorDatabase();
    private final MaterieDatabase materieDatabase = new MaterieDatabase();
    private final SalaDatabase salaDatabase = new SalaDatabase();
    private final SerieDatabase serieDatabase = new SerieDatabase();
    private final OraDatabase oraDatabase = new OraDatabase();

    public MainService() throws SQLException {
        studenti = studentDatabase.getAll();
        profesori = profesorDatabase.getAll();
        materii = materieDatabase.getAll();
        sali = salaDatabase.getAll();
        serii = serieDatabase.getAll();
        ore = oraDatabase.getAll();
        for (var ora : ore){
            if(!(ora instanceof Curs curs)){
                addOraToMaps(ora.getProfesor(), ora.getGrupa(), ora);
            }else{
                for (String g : curs.getSerie().getGrupe()) {
                    if(!grupaOreMap.containsKey(g))
                        grupaOreMap.put(g, new ArrayList<>());
                    grupaOreMap.get(g).add(ora);
                }
                if(!profesorOreMap.containsKey(curs.getProfesor()))
                    profesorOreMap.put(curs.getProfesor(), new ArrayList<>());
                profesorOreMap.get(curs.getProfesor()).add(ora);
            }
        }
    }

    private void addOraToMaps(Profesor p, String grupa, Ora ora) {
        if(!grupaOreMap.containsKey(grupa))
            grupaOreMap.put(grupa, new ArrayList<>());
        grupaOreMap.get(grupa).add(ora);
        if(!profesorOreMap.containsKey(p))
            profesorOreMap.put(p, new ArrayList<>());
        profesorOreMap.get(p).add(ora);
    }
    public void createStudent(Scanner in) throws SQLException {
        DatabaseService.getInstance().add(in, studentDatabase, "student", null);
        studenti = studentDatabase.getAll();
    }

    public void createProfesor(Scanner in) throws SQLException {
        DatabaseService.getInstance().add(in, profesorDatabase, "profesor", materii);
        profesori = profesorDatabase.getAll();
    }

    public void createSerie(Scanner in) throws SQLException {
        DatabaseService.getInstance().add(in, serieDatabase, "serie", null);
        serii = serieDatabase.getAll();
    }

    public void createMaterie(Scanner in) throws SQLException {
        DatabaseService.getInstance().add(in, materieDatabase, "materie", null);
        materii = materieDatabase.getAll();
    }

    public void createSala(Scanner in) throws SQLException {
        int optiune = -1;
        while(optiune < 0 || optiune > 2) {
            System.out.println("Tip (0 - amfiteatru, 1 - laborator, 2 - seminar):");
            optiune = Integer.parseInt(in.nextLine());
        }
        switch (optiune) {
            case 0 -> salaDatabase.addAmfiteatru(new Amfiteatru(in));
            case 1 -> salaDatabase.addLaborator(new SalaLaborator(in, materii));
            case 2 -> salaDatabase.addSalaSeminar(new SalaSeminar(in));
        }
        sali = salaDatabase.getAll();
        System.out.println("Sala a fost creata cu succes!");
    }

    public void createOra(Scanner in) throws SQLException {
        int tip = -1;
        while(tip < 0 || tip > 2) {
            System.out.println("Tip (0 - curs, 1 - laborator, 2 - seminar):");
            tip = Integer.parseInt(in.nextLine());
        }
        switch (tip){
            case 0 -> {
                Curs curs = new Curs(in, profesori, materii, sali, serii);
                oraDatabase.addCurs(curs);
                for (String g : curs.getSerie().getGrupe()) {
                    if(!grupaOreMap.containsKey(g))
                        grupaOreMap.put(g, new ArrayList<>());
                    grupaOreMap.get(g).add(curs);
                }
                if(!profesorOreMap.containsKey(curs.getProfesor()))
                    profesorOreMap.put(curs.getProfesor(), new ArrayList<>());
                profesorOreMap.get(curs.getProfesor()).add(curs);
            }
            case 1 -> {
                Laborator laborator = new Laborator(in, profesori, materii, sali);
                oraDatabase.addLaborator(laborator);
                addOraToMaps(laborator.getProfesor(), laborator.getGrupa(), laborator);
            }
            case 2 -> {
                Seminar seminar = new Seminar(in, profesori, materii, sali);
                oraDatabase.addSeminar(seminar);
                addOraToMaps(seminar.getProfesor(), seminar.getGrupa(), seminar);
            }
        }
        ore = oraDatabase.getAll();
        System.out.println("Ora a fost adaugata cu succes!");
    }

    public Student getStudent(Scanner in){
        return DatabaseService.getInstance().get(in, studentDatabase, "student");
    }

    public Profesor getProfesor(Scanner in){
        return DatabaseService.getInstance().get(in, profesorDatabase, "profesor");
    }

    public void getMaterie(Scanner in){
     DatabaseService.getInstance().get(in, materieDatabase, "materie");
    }

    public void getSerie(Scanner in){
      DatabaseService.getInstance().get(in, serieDatabase, "serie");
    }
    public void deleteStudent(Scanner in){
     DatabaseService.getInstance().delete(in, studentDatabase, "student");
     try {
       studenti = studentDatabase.getAll();
     }catch (SQLException e){
       e.printStackTrace();
     }
    }

    public void deleteProfesor(Scanner in){
      DatabaseService.getInstance().delete(in, profesorDatabase, "profesor");
      try {
        profesori = profesorDatabase.getAll();
      }catch (SQLException e){
        e.printStackTrace();
      }
    }

    public void deleteOra(Scanner in){
        System.out.println("Introduceti ID-ul orei: ");
        Long id = in.nextLong();
        try{
            oraDatabase.delete(id);
            ore = oraDatabase.getAll();
            System.out.println("Ora a fost stearsa cu succes!");
        } catch (SQLException e) {
            System.out.println("Nu exista nicio ora cu acest ID!");
        }
    }

    public void deleteMaterie(Scanner in){
      DatabaseService.getInstance().delete(in, materieDatabase, "materie");
      try {
        materii = materieDatabase.getAll();
      }catch (SQLException e){
        e.printStackTrace();
      }
    }

    public void deleteSerie(Scanner in){
      DatabaseService.getInstance().delete(in, serieDatabase, "serie");
      try {
        serii = serieDatabase.getAll();
      }catch (SQLException e){
        e.printStackTrace();
      }
    }

    public void updateStudent(Scanner in){
        DatabaseService.getInstance().update(in, studentDatabase, "student", null);
      try{
        studenti = studentDatabase.getAll();
      }catch (SQLException e){
        e.printStackTrace();
      }
    }

    public void updateProfesor(Scanner in){
      DatabaseService.getInstance().update(in, profesorDatabase, "profesor", materii);
      try{
        profesori = profesorDatabase.getAll();
      }catch (SQLException e){
        e.printStackTrace();
      }
    }

    public void updateMaterie(Scanner in){
      DatabaseService.getInstance().update(in, materieDatabase, "materie", null);
      try{
        materii = materieDatabase.getAll();
      }catch (SQLException e){
        e.printStackTrace();
      }
    }

    public void updateSerie(Scanner in){
     DatabaseService.getInstance().update(in, serieDatabase, "serie", null);
      try{
        serii = serieDatabase.getAll();
      }catch (SQLException e){
        e.printStackTrace();
      }
    }

    public void afisareOrarStudent(Scanner in){
      Student s = getStudent(in);
      String grupa = s.getGrupa();
      Collections.sort(grupaOreMap.get(grupa));
      int zs = -1;
      for (Ora o : grupaOreMap.get(grupa)) {
          if(o.getZiuaSaptamanii() != zs){
              zs = o.getZiuaSaptamanii();
              System.out.println("\n\n" + zileleSaptamanii.get(zs)+":");
          }
          System.out.println(o);
      }

    }

    public void afisareOrarProfesor(Scanner in){
        Profesor p = getProfesor(in);
        try {
            Collections.sort(profesorOreMap.get(p));
        }catch (NullPointerException e){
            System.out.println("Profesorul nu are ore!");
            return;
        }
        int zs = -1;
        for (Ora o : profesorOreMap.get(p)) {
                if(o.getZiuaSaptamanii() != zs){
                    zs = o.getZiuaSaptamanii();
                    System.out.println("\n\n" + zileleSaptamanii.get(zs)+":");
                }
                System.out.println(o.toStringProfesor());
        }

    }

    public void afisareOrarStudentZiCurenta(Scanner in){
        Student s = getStudent(in);
        String grupa = s.getGrupa();
        int ziuaCurenta = LocalDate.now().getDayOfWeek().getValue()-1;
        List<Ora> ore = grupaOreMap.get(grupa).stream().filter(o -> o.getZiuaSaptamanii() == ziuaCurenta).sorted().toList();
        for (Ora o : ore) {
            System.out.println(o);
        }
    }

    public void afisareOrarProfesorZiCurenta(Scanner in){
        Profesor p = getProfesor(in);
        int ziuaCurenta = LocalDate.now().getDayOfWeek().getValue()-1;
        List<Ora> ore = profesorOreMap.get(p).stream().filter(o -> o.getZiuaSaptamanii() == ziuaCurenta).sorted().toList();
        for (Ora o : ore) {
            System.out.println(o.toStringProfesor());
        }
    }



    public void test() throws SQLException{
        oraDatabase.delete(1L);

    }
}

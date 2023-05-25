package Services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import Models.Materie.*;
import Models.Ora.*;
import Models.Profesor.*;
import Models.Sala.*;
import Models.Serie.*;
import Models.Student.*;

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
        Student student = new Student(in);
        studentDatabase.add(student);
        studenti = studentDatabase.getAll();
        System.out.println("Studentul a fost creat cu succes!");
    }

    public void createProfesor(Scanner in) throws SQLException {
        Profesor profesor = new Profesor(in, materii);
        profesorDatabase.add(profesor);
        profesori = profesorDatabase.getAll();
        System.out.println("Profesorul a fost creat cu succes!");
    }

    public void createSerie(Scanner in) throws SQLException {
        Serie serie = new Serie(in);
        serieDatabase.add(serie);
        serii = serieDatabase.getAll();
        System.out.println("Serie creata cu succes!");
    }

    public void createMaterie(Scanner in) throws SQLException {
        Materie materie = new Materie(in, "Simple");
        materieDatabase.add(materie);
        materii = materieDatabase.getAll();
        System.out.println("Materia a fost creata cu succes!");
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

    public Student findStudentById(Long id){
        for (Student s : studenti) {
            if(Objects.equals(s.getId(), id))
                return s;
        }
        return null;
    }

    public Student findStudentByName(String name){
        int cnt = 0, index = -1;
        for (Student s : studenti) {
            if(s.getName().equals(name))
            {
                cnt++;
                index = studenti.indexOf(s);
            }
        }
        if(cnt == 0)
        {
            System.out.println("Nu exista niciun student cu acest nume!");
            return null;
        }
        if(cnt == 1)
            return studenti.get(index);
        System.out.println("Exista mai multi studenti cu acest nume!");
        return null;
    }

    public Profesor findProfesorById(Long id){
        for (Profesor p : profesori) {
            if(Objects.equals(p.getId(), id))
                return p;
        }
        return null;
    }

    public Profesor findProfesorByName(String name){
        int cnt = 0, index = -1;
        for (int i = 0; i < profesori.size(); i++) {
            if(profesori.get(i).getName().equals(name))
            {
                cnt++;
                index = i;
            }
        }
        if(cnt == 0)
            System.out.println("Nu exista niciun profesor cu acest nume!");
        else if(cnt == 1)
            return profesori.get(index);
        else System.out.println("Exista mai multi profesori cu acest nume!");
        return null;
    }

    public void getStudent(Scanner in){
        System.out.println("Introduceti numele studentului: ");
        String nume = in.nextLine();
        Student student = findStudentByName(nume);
        if (student != null)
            System.out.println(student);
        else{
            System.out.println("Introduceti id-ul studentului: ");
            Long id = in.nextLong();
            System.out.println(findStudentById(id));
        }
    }

    public void getProfesor(Scanner in){
        System.out.println("Introduceti numele profesorului: ");
        String nume = in.nextLine();
        System.out.println(findProfesorByName(nume));
    }
    public void afisareOrarStudent(Scanner in){
        System.out.println("Introduceti numele studentului: ");
        String nume = in.nextLine();
        Student s = findStudentByName(nume);
        if(s == null){
            System.out.println("Introduceti id-ul studentului: ");
            Long id = in.nextLong();
            s = findStudentById(id);
            if(s == null){
                System.out.println("Nu exista niciun student cu id!");
                return;
            }
        }
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
        System.out.println("Introduceti numele profesorului: ");
        String nume = in.nextLine();
        Profesor p = findProfesorByName(nume);
        if(p == null){
            System.out.println("Introduceti id-ul profesorului: ");
            Long id = in.nextLong();
            p = findProfesorById(id);
            if(p == null){
                System.out.println("Nu exista niciun profesor cu id!");
                return;
            }
        }
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
        System.out.println("Introduceti numele studentului: ");
        String nume = in.nextLine();
        Student s = findStudentByName(nume);
        if(s == null){
            System.out.println("Introduceti id-ul studentului: ");
            Long id = in.nextLong();
            s = findStudentById(id);
            if(s == null){
                System.out.println("Nu exista niciun student cu id!");
                return;
            }
        }
        String grupa = s.getGrupa();
        int ziuaCurenta = LocalDate.now().getDayOfWeek().getValue()-1;
        List<Ora> ore = grupaOreMap.get(grupa).stream().filter(o -> o.getZiuaSaptamanii() == ziuaCurenta).sorted().toList();
        for (Ora o : ore) {
            System.out.println(o);
        }
    }

    public void afisareOrarProfesorZiCurenta(Scanner in){
        System.out.println("Introduceti numele profesorului: ");
        String nume = in.nextLine();
        Profesor p = findProfesorByName(nume);
        if(p == null){
            System.out.println("Introduceti id-ul profesorului: ");
            Long id = in.nextLong();
            p = findProfesorById(id);
            if(p == null){
                System.out.println("Nu exista niciun profesor cu id!");
                return;
            }
        }
        int ziuaCurenta = LocalDate.now().getDayOfWeek().getValue()-1;
        List<Ora> ore = profesorOreMap.get(p).stream().filter(o -> o.getZiuaSaptamanii() == ziuaCurenta).sorted().toList();
        for (Ora o : ore) {
            System.out.println(o.toStringProfesor());
        }
    }

    public void test() throws SQLException{
        profesori = profesorDatabase.getAll();
        for (var materie : materii){
            System.out.println(materie + " ");
            for(var profesor : profesori){
                for(var materie2: profesor.getMaterii())
                    System.out.print(materie2+".....");
                System.out.println(profesor.getMaterii().contains(materie));
                System.out.println();
            }
        }

    }
}

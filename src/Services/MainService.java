package Services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import Models.Materie.Materie;
import Models.Ora.Curs;
import Models.Ora.Laborator;
import Models.Ora.Ora;
import Models.Ora.Seminar;
import Models.Profesor.Profesor;
import Models.Sala.Amfiteatru;
import Models.Sala.Sala;
import Models.Sala.SalaLaborator;
import Models.Sala.SalaSeminar;
import Models.Serie.Serie;
import Models.Student.Student;
import util.ClassWithName;

public class MainService {
    private List<Student> studenti = new ArrayList<>();
    private List<Profesor> profesori = new ArrayList<>();
    private List<Sala> sali = new ArrayList<>();
    private List<Materie> materii = new ArrayList<>();
    private List<Serie> serii = new ArrayList<>();
    private Map<String, List<Ora>> grupaOreMap = new HashMap<>();
    private Map<Profesor, List<Ora>> profesorOreMap = new HashMap<>();
    private List<Ora> ore = new ArrayList<>();
    private final List<Class<? extends Sala>> tipuriSali = Arrays.asList(Amfiteatru.class, SalaLaborator.class, SalaSeminar.class);
    private final List<String> zileleSaptamanii = Arrays.asList("Luni", "Marti", "Miercuri", "Joi", "Vineri", "Sambata", "Duminica");

    private void addOraToMaps(Profesor p, String grupa) {
        if(!grupaOreMap.containsKey(grupa))
            grupaOreMap.put(grupa, new ArrayList<>());
        grupaOreMap.get(grupa).add(ore.get(ore.size() - 1));
        if(!profesorOreMap.containsKey(p))
            profesorOreMap.put(p, new ArrayList<>());
        profesorOreMap.get(p).add(ore.get(ore.size() - 1));
    }
    public void createStudent(Scanner in){
        System.out.println("Nume: ");
        String nume = in.nextLine();
        System.out.println("Data nasterii (zz.ll.aaaa): ");
        String dataNasterii = in.nextLine();
        System.out.println("Grupa: ");
        String grupa = in.nextLine();
        System.out.println("An studiu: ");
        int anStudiu = Integer.parseInt(in.nextLine());
        LocalDate dataNasterii2 = LocalDate.parse(dataNasterii, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        studenti.add(new Student(nume, dataNasterii2, grupa, anStudiu));
        System.out.println("Studentul a fost creat cu succes!");
    }

    public void createProfesor(Scanner in){
        System.out.println("Nume: ");
        String nume = in.nextLine();
        System.out.println("Numar de materii: ");
        int nrMaterii = Integer.parseInt(in.nextLine());
        List<Materie> materiiProfesor = new ArrayList<>();
        for (int i = 0; i < nrMaterii; i++) {
            int optiune = selectFromMultipleChoices(in, materii, "Materie " + (i + 1) + ": ");
            materiiProfesor.add(materii.get(optiune));
        }
        profesori.add(new Profesor(nume, materiiProfesor));
        System.out.println("Profesorul a fost creat cu succes!");
    }

    public void createSerie(Scanner in) {
        System.out.println("Nume: ");
        String nume = in.nextLine();
        System.out.println("Numar de grupe: ");
        int nrGrupe = Integer.parseInt(in.nextLine());
        List<String> grupe = new ArrayList<>();
        for (int i = 0; i < nrGrupe; i++) {
            System.out.println("Grupa " + (i + 1) + ": ");
            String grupa = in.nextLine();
            grupe.add(grupa);
        }
        serii.add(new Serie(nume, grupe));
        System.out.println("Serie creata cu succes!");
    }

    public void createMaterie(Scanner in){
        Materie m = new Materie();
        m.readSimple(in);
        materii.add(m);
        System.out.println("Materia a fost creata cu succes!");
    }

    public void createSala(Scanner in){
        System.out.println("Nume: ");
        String nume = in.nextLine();
        System.out.println("Numar de locuri: ");
        int nrLocuri = Integer.parseInt(in.nextLine());
        int optiune = -1;
        while(optiune < 0 || optiune > 2) {
            System.out.println("Tip (0 - amfiteatru, 1 - laborator, 2 - seminar):");
            optiune = Integer.parseInt(in.nextLine());
        }
        switch (optiune) {
            case 0 -> sali.add(new Amfiteatru(nume, nrLocuri));
            case 1 -> sali.add(new SalaLaborator(nume, nrLocuri));
            case 2 -> sali.add(new SalaSeminar(nume, nrLocuri));
        }
        System.out.println("Sala a fost creata cu succes!");

//        sali.add(new tipuriSali.get(optiune)(nume, nrLocuri));
//        Class<? extends Sala> selectedClass = tipuriSali.get(optiune);
//        Sala s = new selectedClass.getConstructors()[0].newInstance(nume, nrLocuri);

    }

    public void createOra(Scanner in){
        int tip = -1;
        while(tip < 0 || tip > 2) {
            System.out.println("Tip (0 - curs, 1 - laborator, 2 - seminar):");
            tip = Integer.parseInt(in.nextLine());
        }
        final int finalTip = tip;
        int optiune = selectFromMultipleChoices(in, materii, "o materie: ");
        Materie m = materii.get(optiune);
        List<Profesor> profesoriPosibili = profesori.stream().filter(profesor -> profesor.getMaterii().contains(m)).toList();
        optiune = selectFromMultipleChoices(in, profesoriPosibili, "un profesor: ");
        Profesor p = profesoriPosibili.get(optiune);
        System.out.println("Selectati o sala: ");
        List<Sala> saliPosibile;
        saliPosibile = sali.stream().filter(sala -> sala.getClass() == tipuriSali.get(finalTip)).toList();
        if (saliPosibile.size() == 0) {
            System.out.println("Nu exista nicio sala pentru aceasta ora!");
            return;
        }
        optiune = selectFromMultipleChoices(in, saliPosibile, "o sala: ");
        Sala s = saliPosibile.get(optiune);
        System.out.println("Ora inceput: ");
        int oraInceput = Integer.parseInt(in.nextLine());
        System.out.println("Ora sfarsit: ");
        int oraSfarsit = Integer.parseInt(in.nextLine());
        int ziuaSaptamanii = -1;
        while (ziuaSaptamanii < 1 || ziuaSaptamanii > 7){
            System.out.println("Ziua saptamanii (1 - 7): ");
            ziuaSaptamanii = Integer.parseInt(in.nextLine());
        }
        ziuaSaptamanii--;
//        System.out.println("Frecventa: ");
//        String frecventa = in.nextLine();
        String grupa = null;
        Serie serie = null;
        if(tip != 0){
            System.out.println("Grupa: ");
            grupa = in.nextLine();
        }
        else {
            System.out.println("Seria: ");
            optiune = selectFromMultipleChoices(in, serii, "o serie: ");
            serie = serii.get(optiune);
        }
        switch (tip){
            case 0 -> {
                ore.add(new Curs(m, p, (Amfiteatru) s, oraInceput, oraSfarsit, ziuaSaptamanii, serie));
                for (String g : serie.getGrupe()) {
                    if(!grupaOreMap.containsKey(g))
                        grupaOreMap.put(g, new ArrayList<>());
                    grupaOreMap.get(g).add(ore.get(ore.size() - 1));
                }
                if(!profesorOreMap.containsKey(p))
                    profesorOreMap.put(p, new ArrayList<>());
                profesorOreMap.get(p).add(ore.get(ore.size() - 1));
            }
            case 1 -> {
                ore.add(new Laborator(m, p, (SalaLaborator) s, oraInceput, oraSfarsit, ziuaSaptamanii, grupa));
                addOraToMaps(p, grupa);
            }
            case 2 -> {
                ore.add(new Seminar(m, p, (SalaSeminar) s, oraInceput, oraSfarsit, ziuaSaptamanii, grupa));
                addOraToMaps(p, grupa);
            }
            }
        System.out.println("Ora a fost adaugata cu succes!");
    }

    private int selectFromMultipleChoices(Scanner in, List<? extends ClassWithName> list, String message){
        int optiune = -1;
        while(optiune < 0 || optiune >= materii.size()) {
            System.out.println("Selectati " + message + ": ");
            for (int i = 0; i < materii.size(); i++) {
                System.out.println((i + 1) + ". " + list.get(i).getName());
            }
            optiune = Integer.parseInt(in.nextLine());
            optiune--;
        }
        return optiune;
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
        for (Profesor p : profesori) {
            if(p.getName().equals(name))
            {
                cnt++;
                index = profesori.indexOf(p);
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
        System.out.println(findStudentByName(nume));
    }

    public void getProfesor(Scanner in){
        System.out.println("Introduceti numele profesorului: ");
        String nume = in.nextLine();
        System.out.println( findProfesorByName(nume));
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
        Collections.sort(profesorOreMap.get(p));
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
}


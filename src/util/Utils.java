package util;

import java.util.List;
import java.util.Scanner;

public final class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static int selectFromMultipleChoices(Scanner in, List<? extends ClassWithName> list, String message){
        int optiune = -1;
        while(optiune < 0 || optiune >= list.size()) {
            System.out.println("Selectati " + message + ": ");
            for (int i = 0; i < list.size(); i++) {
                System.out.println((i + 1) + ". " + list.get(i).getName());
            }
            optiune = Integer.parseInt(in.nextLine());
            optiune--;
        }
        return optiune;
    }

    public static String stringFromList(List<String> list){
        StringBuilder result = new StringBuilder();
        for (String s : list){
            result.append(s).append(", ");
        }
        return result.substring(0, result.length() - 2);
    }

    public static List<String> listFromString(String s){
        return List.of(s.split(", "));
    }
}
